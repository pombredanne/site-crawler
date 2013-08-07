package org.github.site.crawler.actor.site

import java.util.ArrayList
import scala.collection.mutable.HashSet
import org.github.site.crawler.actor.page.PageAnalyzer
import org.github.site.crawler.actor.page.PageAnalyzerActor
import org.github.site.crawler.actor.page.PageResult
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import org.github.site.crawler.actor.State
import org.github.site.crawler.model.CrawlerConfig
import org.github.site.crawler.model.filter.url.IncludeOnce
import org.github.site.crawler.model.filter.url.UrlFilter
import org.github.site.crawler.model.filter.url.Include
import org.github.site.crawler.model.filter.url.Exclude

class SiteAnalyzerActor extends Actor {

	var analyzedUrls = new HashSet[String]()

	var results = new ArrayList[String]()
	var pageAnalyzerActor = ActorSystem("PageAnalyzer").actorOf(Props[PageAnalyzerActor], name = "PageAnalyzer")

	var conf: CrawlerConfig = null

	def receive = {
		case SiteAnalyzer(config) => {
			conf = config

			if (config != null && !config.urlsToAnalyze.isEmpty) {
				for (url <- config.urlsToAnalyze) {
					pageAnalyzerActor ! PageAnalyzer(url, config.analyzers)
				}
			}
		}
		case PageResult(url, pageResult, scannedUrls) => {
			println(url)
			analyzedUrls += url

			for (scannedUrl <- scannedUrls) {
				for (urlFilter <- conf.urlFilters) {
					if (urlFilter.matchURL(scannedUrl)) {
						urlFilter.getFilterType match {
							case Include => pageAnalyzerActor ! PageAnalyzer(scannedUrl, conf.analyzers)
							case IncludeOnce => {
								urlFilter.setFilterType(Exclude)
								pageAnalyzerActor ! PageAnalyzer(scannedUrl, conf.analyzers)
							}
							case _ =>
						}
					}
				}
			}
		}

		case State.Done => context.stop(self)
	}
}
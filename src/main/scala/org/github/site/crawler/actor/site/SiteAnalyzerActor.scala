package org.github.site.crawler.actor.site

import java.util.ArrayList

import scala.collection.mutable.HashSet
import scala.concurrent.duration._

import org.github.site.crawler.actor.State
import org.github.site.crawler.actor.page.PageAnalyzer
import org.github.site.crawler.actor.page.PageAnalyzerActor
import org.github.site.crawler.actor.page.PageResult
import org.github.site.crawler.model.CrawlerConfig
import org.github.site.crawler.model.filter.url.Exclude
import org.github.site.crawler.model.filter.url.Include
import org.github.site.crawler.model.filter.url.IncludeOnce
import org.github.site.crawler.model.filter.url.UrlFilter

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.actor.actorRef2Scala

class SiteAnalyzerActor extends Actor {

	var analyzedUrls = new HashSet[String]()

	var results = new ArrayList[String]()
	var pageAnalyzerActor = ActorSystem("PageAnalyzer").actorOf(Props[PageAnalyzerActor], name = "PageAnalyzer")

	var conf: CrawlerConfig = null
	var count : Int = 0

	def receive = {
		case SiteAnalyzer(config) => {
			conf = config

			if (config != null && !config.urlsToAnalyze.isEmpty) {
				for (url <- config.urlsToAnalyze) {
					pageAnalyzerActor ! PageAnalyzer(url, null, config.analyzers)
				}
			}
		}
		case PageResult(url, from, pageResult, scannedUrls) => {
//			println("Scanned URL " + url + " From: " + from + " URLs" + scannedUrls.toString)
			println("From: " + from + "Scanned URL " + url )

			var filteredUrls = scannedUrls.filter(!analyzedUrls.contains(_))
			
			for (
				scannedUrl: String <- filteredUrls
				if (!analyzedUrls.par.contains(scannedUrl))
			) {
				analyzedUrls += scannedUrl
				conf.urlFilters.par.view.filter(_.matchURL(scannedUrl)).headOption match {
					case Some(f) => {
						f.getFilterType match {
							case Include => pageAnalyzerActor ! PageAnalyzer(scannedUrl, url, conf.analyzers)
							case IncludeOnce => {
								f.setFilterType(Exclude)
								pageAnalyzerActor ! PageAnalyzer(scannedUrl, url, conf.analyzers)
							}
							case _ => println("end")
						}
					}
					case None =>
				}
			}
		}

		case State.Done => context.stop(self)
		case ReceiveTimeout => {
			println("Service timeout")
			context.stop(self)
		}
	}
}
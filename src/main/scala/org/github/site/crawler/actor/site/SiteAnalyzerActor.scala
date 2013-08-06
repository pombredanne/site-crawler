package org.github.site.crawler.actor.site

import java.util.ArrayList

import scala.collection.mutable.HashSet

import org.github.site.crawler.actor.page.PageAnalyzer
import org.github.site.crawler.actor.page.PageAnalyzerActor
import org.github.site.crawler.actor.page.PageResult
import org.github.site.crawler.model.CrawlerConfig

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class SiteAnalyzerActor extends Actor {
	var analyzedUrls = new HashSet[String]()
	var urlsToAnalyze = new HashSet[String]()
	var pageAnalyzerActor = ActorSystem("PageAnalyzer").actorOf(Props[PageAnalyzerActor], name = "PageAnalyzer")
	
	var results = new ArrayList[String]()
	
	def receive = {
		case SiteAnalyzer(config) => {
			for(url <- config.urlsToAnalyze) {
				pageAnalyzerActor ! PageAnalyzer(url, config.analyzers)
			}
		}	
		case PageResult(url, pageResult, scannedUrls) => {
			println(url)
			println(scannedUrls.toString)
		}
	}
}
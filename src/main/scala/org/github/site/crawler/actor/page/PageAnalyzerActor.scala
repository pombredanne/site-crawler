package org.github.site.crawler.actor.page

import scala.collection.mutable.ArrayLike
import scala.collection.mutable.HashMap
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import akka.actor.Actor
import akka.actor.ActorLogging
import scala.collection.mutable.Queue
import org.github.site.crawler.actor.State

class PageAnalyzerActor extends Actor with ActorLogging {
	def receive = {
		case PageAnalyzer(url, from , pageAnalyzers) => {
			var page = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; HBot/1.0)").get()
			
			var links = page.select("a[href]").iterator
			var urls = new Queue[String]()
			
			while(links.hasNext()) {
				urls += links.next().absUrl("href")
			}
			
			var result = new HashMap[String, String]()
			result.put("test", "test")
			
			sender ! PageResult(url, from, result, urls.toList)
		}
		case State.Done => context.stop(self)
	}
}
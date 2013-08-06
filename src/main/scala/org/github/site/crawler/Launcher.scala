package org.github.site.crawler

import scala.collection.JavaConversions
import org.github.site.crawler.actor.page.PageAnalyzerActor
import org.github.site.crawler.analyzer.Analyzer
import org.github.site.crawler.model.ApplicationConfig
import org.jsoup.Jsoup
import org.springframework.scala.context.function.FunctionalConfigApplicationContext
import akka.actor.ActorSystem
import akka.actor.Props
import org.github.site.crawler.actor.site.SiteAnalyzerActor
import org.github.site.crawler.model.CrawlerConfig
import org.github.site.crawler.model.CrawlerConfig
import scala.collection.Seq
import scala.collection.immutable.List
import org.github.site.crawler.actor.site.SiteAnalyzerActor
import org.github.site.crawler.actor.site.SiteAnalyzer

object Launcher extends App {
	val context = FunctionalConfigApplicationContext(classOf[ApplicationConfig])

	var analyzers = JavaConversions.mapAsScalaMap(context.getBeansOfType(classOf[Analyzer])).values.groupBy(_.tagName.toString())

	val system = ActorSystem("SiteAnalyzer")
	val siteAnalyzerActor = system.actorOf(Props[SiteAnalyzerActor], name = "SiteAnalyzerActor")
	
	var urlsToAnalyze = List(
			"http://www.abcroisiere.com"
	)
	
	var config = new CrawlerConfig(urlsToAnalyze, analyzers) 
	
	siteAnalyzerActor ! SiteAnalyzer(config)
	
}
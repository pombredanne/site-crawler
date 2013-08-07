package org.github.site.crawler

import scala.collection.JavaConversions
import scala.collection.immutable.List
import scala.collection.immutable.Stream
import scala.util.Random
import org.github.site.crawler.actor.site.SiteAnalyzerActor
import org.github.site.crawler.actor.site.SiteAnalyzer
import org.github.site.crawler.analyzer.Analyzer
import org.github.site.crawler.model.ApplicationConfig
import org.github.site.crawler.model.CrawlerConfig
import org.springframework.scala.context.function.FunctionalConfigApplicationContext
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Actor
import org.github.site.crawler.actor.State
import org.github.site.crawler.model.filter.url.RegexUrlFilter
import org.github.site.crawler.model.filter.url.FilterType
import org.github.site.crawler.model.filter.url.FilterType
import org.github.site.crawler.model.filter.url.IncludeOnce
import org.github.site.crawler.model.filter.url.Include

object Launcher extends App {
	val keySize = 10

	val springContext = FunctionalConfigApplicationContext(classOf[ApplicationConfig])

	var system = ActorSystem("SiteAnalyzer")
	var siteAnalyzerActor = system.actorOf(Props[SiteAnalyzerActor], name = "SiteAnalyzerActor")

	//demo 
	val analyzers = JavaConversions.mapAsScalaMap(springContext.getBeansOfType(classOf[Analyzer])).values.groupBy(_.tagName.toString())

	var urlsToAnalyze = List(
		"http://www.abcroisiere.com"
	)
	
	var urlFilters = List(
		new RegexUrlFilter(IncludeOnce, ".*/fr/croisieres/.*" )
	)
	
	var config = new CrawlerConfig(Stream.continually(Random.nextLong).take(keySize).toString, 
			urlsToAnalyze, urlFilters, analyzers)
	
	siteAnalyzerActor ! SiteAnalyzer(config)
}
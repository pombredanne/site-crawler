package org.github.site.crawler.actor.page

import org.github.site.crawler.model.CrawlerConfig
import scala.collection.Map

case class PageResult(val url:String, 
		val from: String,
		val analyzerResult: Map[String, String], 
		val acannedUrls: List[String])
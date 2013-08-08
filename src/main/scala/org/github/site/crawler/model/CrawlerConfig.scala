package org.github.site.crawler.model

import org.github.site.crawler.analyzer.Analyzer
import scala.collection.Iterable
import org.github.site.crawler.model.filter.url.UrlFilter

class CrawlerConfig(val id: String, 
		val urlsToAnalyze: List[String], 
		val urlFilters : List[UrlFilter],
		val analyzers: Map[String, Iterable[Analyzer]],
		val maxScannedUrls : Int)
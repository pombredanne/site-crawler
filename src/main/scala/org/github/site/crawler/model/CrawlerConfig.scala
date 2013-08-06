package org.github.site.crawler.model

import org.github.site.crawler.analyzer.Analyzer
import scala.collection.Iterable

class CrawlerConfig (val urlsToAnalyze : List[String], val analyzers: Map[String, Iterable[Analyzer]])
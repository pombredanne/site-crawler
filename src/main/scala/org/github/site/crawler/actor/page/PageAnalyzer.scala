package org.github.site.crawler.actor.page

import org.github.site.crawler.analyzer.Analyzer

case class PageAnalyzer(val url: String, val pageAnalyzers: Map[String, Iterable[Analyzer]]) 
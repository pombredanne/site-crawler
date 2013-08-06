package org.github.site.crawler.analyzer

import org.github.site.crawler.model.seo.Page

abstract class Analyzer(var tagName: String) {
	def analyze(page: Page): Unit
}
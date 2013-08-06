package org.github.site.crawler.analyzer

import org.github.site.crawler.model.seo.Page

class CountAnalyzer(tagName: String,
		var attributeName: String,
		var value: String,
		var countInterval: String) extends Analyzer(tagName) {

	def this() = this(null, null, null, null)

	override def analyze(page: Page): Unit = {

	}
}
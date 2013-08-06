package org.github.site.crawler.model.seo

import java.net.URL

class Page(var url: URL,
		var content: String,
		var elapsedTime: Long,
		var charset: String) {
}
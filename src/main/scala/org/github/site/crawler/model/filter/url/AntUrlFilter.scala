package org.github.site.crawler.model.filter.url

import org.springframework.util.AntPathMatcher

class AntUrlFilter(var filterType: FilterType, val pattern: String) extends AntPathMatcher with UrlFilter {

	override def matchURL(url: String): Boolean = {
		doMatch(pattern, url, true, null)
	}

	def getFilterType(): FilterType = filterType

	def setFilterType(filterType: FilterType): Unit = {
		this.filterType = filterType
	}
}
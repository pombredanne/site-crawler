package org.github.site.crawler.model.filter.url

class RegexUrlFilter(var filterType: FilterType, val pattern: String) extends UrlFilter {

	private val regexPattern = pattern.r
	
	override def matchURL(url: String): Boolean = {
		regexPattern unapplySeq url isDefined
	}
	
	def getFilterType() : FilterType = filterType
	
	def setFilterType(filterType: FilterType) : Unit = {
		this.filterType = filterType
	}
}
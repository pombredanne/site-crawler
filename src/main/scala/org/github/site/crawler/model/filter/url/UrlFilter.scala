package org.github.site.crawler.model.filter.url

sealed abstract class FilterType

case object Include extends FilterType
case object IncludeOnce extends FilterType
case object Exclude extends FilterType

trait UrlFilter {

	def matchURL(url: String): Boolean

	def getFilterType(): FilterType

	def setFilterType(filterType: FilterType): Unit
}

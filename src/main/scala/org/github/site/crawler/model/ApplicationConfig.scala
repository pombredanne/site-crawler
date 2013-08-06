package org.github.site.crawler.model

import org.springframework.scala.context.function.FunctionalConfiguration
import org.springframework.scala.context.function.ContextSupport

class ApplicationConfig extends FunctionalConfiguration with ContextSupport {

	importXml("/analyzers.xml")

}
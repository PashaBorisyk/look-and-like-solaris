package web.impl

import javax.inject.Inject
import play.api.Configuration
import play.api.libs.ws.WSClient
import web.SearchClient

import scala.concurrent.Future

class SearchClientImpl @Inject()(ws: WSClient, config: Configuration) extends SearchClient[Future] {

   private final val url = config.get[String]("search.url")
   private final val contentType = config.get[String]("search.contentType")
   private final val apiKey = config.get[String]("search.apiKey")
   private final val apiVersion = config.get[String]("search.apiVersion")

   override def search(query: String, top: Int,count:Boolean = true,gender:String="женщ") = {
      ws.url(url)
         .addHttpHeaders(
            "Content-Type" -> contentType,
            "api-key" -> apiKey
         )
         .addQueryStringParameters(
            "api-version" -> apiVersion,
            "search" -> s"""data/sex:$gender* $query~""",
            "$top" -> top.toString,
            "$count" -> count.toString,
            "queryType" -> "full"
         ).get()
   }

}

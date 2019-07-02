package services.impl

import javax.inject.Inject
import play.api.Logger
import play.api.libs.json.{JsArray, JsString, JsValue}
import services.ProductService
import web.SearchClient

import scala.concurrent.{ExecutionContext, Future}

class ProductServiceImpl @Inject()(searchClient: SearchClient[Future])(implicit ec: ExecutionContext) extends
   ProductService[Future] {

   private val logger = Logger("application")

   def searchProductByQuery(query: String, top: Int): Future[(JsValue, Int)] = {
      searchClient.search(query, top)
         .map { response =>

            val payload = response.status match {
               case 200 | 201 =>
                  (response.json \ "value").getOrElse(JsArray.empty)
               case _ =>
                  logger.error(s"Query : $query; response : $response")
                  JsString(response.body + "; " + response.statusText)
            }

            payload -> response.status

         }
   }

}

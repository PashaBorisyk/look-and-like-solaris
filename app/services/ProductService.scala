package services

import com.google.inject.ImplementedBy
import play.api.libs.json.{JsValue}
import services.impl.ProductServiceImpl

@ImplementedBy(classOf[ProductServiceImpl])
trait ProductService[T[_]] {

   def searchProductByQuery(query: String, top: Int): T[(JsValue, Int)]

}

package controllers

import javax.inject._
import play.api.mvc._
import services.ProductService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductsController @Inject()(cc: ControllerComponents,productService: ProductService[Future])
                                  (implicit exec: ExecutionContext) extends AbstractController(cc) {

  def search(query:String,top:Int) = Action.async {
     productService.searchProductByQuery(query,top)
        .map{ case (payload,status) =>
           Status(status)(payload)
        }
  }

}

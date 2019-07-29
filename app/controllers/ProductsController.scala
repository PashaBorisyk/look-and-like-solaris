package controllers

import io.swagger.annotations.{Api, ApiParam, ApiResponse, ApiResponses}
import javax.inject._
import play.api.mvc._
import services.ProductService

import scala.concurrent.{ExecutionContext, Future}

@Api("products")
@Singleton
class ProductsController @Inject()(cc: ControllerComponents, productService: ProductService[Future])
                                  (implicit exec: ExecutionContext) extends AbstractController(cc) {

   @ApiResponses(Array(new ApiResponse(code = 200, message = "Result list retrieved")))
   def search(@ApiParam(value="Query string from user's request") query: String,
              @ApiParam(value = "Number of firs n elements from search response") top: Int)
   = Action.async {
      productService.searchProductByQuery(query, top)
         .map { case (payload, status) =>
            Status(status)(payload)
         }
   }

}

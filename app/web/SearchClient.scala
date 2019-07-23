package web

import com.google.inject.ImplementedBy
import play.api.libs.ws.WSResponse
import web.impl.SearchClientImpl

@ImplementedBy(classOf[SearchClientImpl])
trait SearchClient [T[_]]{

   def search(query:String,top:Int,count:Boolean=true,gender:String="male") : T[WSResponse]

}

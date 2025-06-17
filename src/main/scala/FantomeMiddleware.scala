import zio.http.Middleware
import zio.json._
import zio._
import zio.http._
import zio.http.Routes
import zio.http.Handler
import zio.http.Body
import zio.http.codec.HttpContentCodec.json
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec
import zio.schema._

val fantomeData: Middleware[Any] =
  new Middleware[Any] {
    override def apply[Env1 <: Any, Err](
        routes: Routes[Env1, Err]
    ): Routes[Env1, Err] = routes.transform { handler =>
      Handler.scoped[Env1] {
        Handler.fromFunctionZIO { request =>
          handler(
            request.updateBody(
              body =>
                if (body.isEmpty) Body.fromString(EmptyBodyPlaceholder)
                else body
            )
          )
        }
      }
    }
  }

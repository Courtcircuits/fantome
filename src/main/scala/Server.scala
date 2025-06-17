import zio._
import zio.http._
import zio.json._
import zio.schema.codec.AvroSchemaCodec
import zio.schema.Schema
import zio.schema.DeriveSchema
import zio.schema.codec.{AvroCodec, BinaryCodec}

object FantomeServer extends ZIOAppDefault {
  val jsonRoute: Route[Any, Throwable] = {
    Method.POST / "json" ->
      handler { (req: Request) =>
        for {
          body <- req.body.asString
        } yield {
          val user = body
            .fromJson[UserGuard]
            .getOrElse(throw new Exception("Invalid data"))
          val serialize = user.toJson
          Response.json(serialize)
        }
      }
  }


  val avroRoute: Route[Any, Throwable] = {
    Method.POST / "avro" ->
      handler { (req: Request) =>
        for {
          body <- req.body.asString
          user = body.fromJson[UserGuard].getOrElse(throw new Exception("Invalid data"))
          encodedUser <- ZIO.succeed(UserGuard.binaryCodec.encode(user))
        } yield Response.text(encodedUser.toString)
      }
  }


  val routes: Routes[Any, Nothing] =
    Routes(jsonRoute, avroRoute).handleError(e =>
      Response.internalServerError(e.getMessage)
    )

  val routesWithMiddleware = routes

  def run = Server.serve(routesWithMiddleware).provide(Server.default)
}

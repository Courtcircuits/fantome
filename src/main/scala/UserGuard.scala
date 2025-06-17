import upickle.default.*
import zio.json.JsonDecoder
import zio.json.DeriveJsonDecoder
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec
import zio.schema.Schema
import zio.schema._
import zio.schema.DeriveSchema
import zio.schema.codec.{AvroCodec, BinaryCodec}
import zio.schema.codec.AvroSchemaCodec


case class UserGuard(username: String, email: SensitiveField, address: SensitiveField, nationalId: SensitiveField) derives ReadWriter;

object UserGuard{
    implicit val decoder: JsonDecoder[UserGuard] = DeriveJsonDecoder.gen[UserGuard]

    implicit val encoder: zio.json.JsonEncoder[UserGuard] = zio.json.DeriveJsonEncoder.gen[UserGuard]

    implicit val schema: Schema[UserGuard] = DeriveSchema.gen[UserGuard]

    implicit val binaryCodec: BinaryCodec[UserGuard] = AvroCodec.schemaBasedBinaryCodec[UserGuard]

    def unapply(value: UserGuard): Option[UserGuard] = Some(UserGuard(value.username, value.email, value.address, value.nationalId))
}

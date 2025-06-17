import upickle.default._
import zio.json.JsonDecoder
import zio.json.DeriveJsonDecoder
import zio.json.JsonEncoder
import zio.schema.Schema

case class SensitiveField(value: String)
implicit val rw: ReadWriter[SensitiveField] = readwriter[String]
  .bimap[SensitiveField](x => x.value, y => SensitiveField("***"))

object SensitiveField {
  implicit val decoder: JsonDecoder[SensitiveField] =
    JsonDecoder[String].map(x => SensitiveField("***"))
  implicit val encoder: zio.json.JsonEncoder[SensitiveField] =
    JsonEncoder[String].contramap[SensitiveField](x => "***")

  implicit val schema: Schema[SensitiveField] =
    Schema[String].transform[SensitiveField](
      value => SensitiveField("***"),
      sensitiveField => sensitiveField.value
    )
  def unapply(value: SensitiveField): Option[String] = Some("***")
}

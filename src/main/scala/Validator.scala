import upickle.default.*
import zio.json.JsonDecoder

case class FantomeValidator[T : ReadWriter](data: String) {
    def apply(): Option[T] = {
        try {
            Some(read[T](data))
        } catch {
            case _: Exception => None
       }
   }
}
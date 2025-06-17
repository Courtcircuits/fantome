# Fantome

Fantome is an API that anonymizes and serializes data in Scala. It was written thanks to [ZIO](https://zio.dev/) and [ZIO-HTTP](https://zio.dev/zio-http/).

## Usage

For the following examples, we will assuming the following schema : 

```json
{
    "username": "string",
    "email": "string", //confidential
    "address": "string", //confidential
    "nationalId": "string" //confidential
}

```

### HTTP

#### JSON

```bash
curl -X POST http://localhost:8080/json \
-H "Content-Type: application/json" \
-d '{
  "username": "toto",
  "email": "toto@foo.bar",
  "address": "1 Avenue des Champs-Élysées",
  "nationalId": "98491"
}'
```

#### Avro

```bash
curl -X POST http://localhost:8080/avro \
-H "Content-Type: application/avro" \
-d '{
  "username": "toto",
  "email": "toto@foo.bar",
  "address": "1 Avenue des Champs-Élysées",
  "nationalId": "98491"
}'
```

### SDK

Here is an example of how to use the SDK to anonymize data in Scala.

```scala
case class UserGuard(username: String, email: SensitiveField, address: SensitiveField, nationalId: SensitiveField) derives ReadWriter;

object UserGuard{
    implicit val decoder: JsonDecoder[UserGuard] = DeriveJsonDecoder.gen[UserGuard]

    implicit val encoder: zio.json.JsonEncoder[UserGuard] = zio.json.DeriveJsonEncoder.gen[UserGuard]

    implicit val schema: Schema[UserGuard] = DeriveSchema.gen[UserGuard]

    implicit val binaryCodec: BinaryCodec[UserGuard] = AvroCodec.schemaBasedBinaryCodec[UserGuard]

    def unapply(value: UserGuard): Option[UserGuard] = Some(UserGuard(value.username, value.email, value.address, value.nationalId))
}

```



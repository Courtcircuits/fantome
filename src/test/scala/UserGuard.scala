// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("test parse user guard while not discovering data") {
    val data = """
  {
 "username": "toto",
 "email": "toto@foo.bar",
 "address": "1 Avenue des Champs-Élysées",
 "nationalId": "98491"
}
  """
    val expected = UserGuard(
      username = "toto",
      email = SensitiveField("***"),
      address = SensitiveField("***"),
      nationalId = SensitiveField("***")
    )
    val validator = FantomeValidator[UserGuard](data)
    val obtained = validator() match {
      case Some(userGuard) => userGuard
      case _ => throw new Exception("Invalid data")
    }
    assertEquals(obtained, expected)
  }
}

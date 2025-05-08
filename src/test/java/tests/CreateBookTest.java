package tests;

import config.TestBase;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

@Epic("Book API Tests")
@Feature("Create Book Feature")
public class CreateBookTest extends TestBase {

private Response response;
private String bookId;

@Test(groups = {"smoke"}, description = "Create a new book with valid details")
@Severity(SeverityLevel.CRITICAL)
@Description("Validate book creation with valid payload")
@Story("Positive Test - Create Book")
public void testCreateBookWithValidData() {
  String bookJson = """
        {
            "name": "Naz Name",
            "author": "Author Name",
            "published_year": "2024",
            "book_summary": "This is a test book created by automation."
        }
    """;

  response = apiClient.createBook(bookJson);

  response.then()
          .statusCode(200)
          .body("id", notNullValue())
          .body("name", equalTo("Naz Name"))
          .body("author", equalTo("Author Name"));

  bookId = response.jsonPath().getString("id");
}

@Test(groups = {"negative"}, description = "Send invalid book payload and verify error response")
@Severity(SeverityLevel.NORMAL)
@Description("Validate error response when book JSON is invalid")
@Story("Negative Test - Invalid Book Creation")
public void testCreateBookWithInvalidData() {
  String invalidJson = "{invalid_json}";

  response = apiClient.createBook(invalidJson);

  response.then()
          .statusCode(422)
          .body("detail[0].msg", containsString("decode error"));
}
}

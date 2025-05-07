package tests;

import config.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class CreateBookTest extends TestBase {

  @Test(groups = "smoke", description = "Create new book with valid data")
  public void testCreateBookValidData() {
    String bookJson = """
      {
          "name": "Naz Name",
          "author": "Author Name",
          "published_year": "2023",
          "book_summary": "Some summary"
      }
      """;

    Response response = apiClient.createBook(bookJson);

    response.then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", equalTo("Naz Name"))
            .body("author", equalTo("Author Name"));
  }


  @Test(groups = "negative", description = "Create book with invalid data")
  public void testCreateBookInvalidData() {
    String invalidJson = "{invalid_json}";

    apiClient.createBook(invalidJson)
            .then()
            .statusCode(422)  // <-- update this
            .body("detail[0].msg", containsString("decode error"));
  }
}

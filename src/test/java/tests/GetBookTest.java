package tests;

import config.TestBase;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class GetBookTest extends TestBase {

  private int existingBookId;

  @Test(groups = "smoke", description = "Get existing book details")
  public void testGetExistingBook() {
    existingBookId = createTestBook();

    apiClient.getBook(existingBookId)
            .then()
            .statusCode(200)
            .body("id", equalTo(existingBookId))
            .body("name", not(emptyString()))
            .body("author", not(emptyString()));
  }

  @Test(groups = "negative", description = "Get non-existent book")
  public void testGetNonExistentBook() {
    int invalidId = 99999;

    apiClient.getBook(invalidId)
            .then()
            .statusCode(404)
            .body("detail", equalTo("Book not found"));
  }
}
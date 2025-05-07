package tests;

import config.TestBase;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class DeleteBookTest extends TestBase {

  private int existingBookId;

  @Test(groups = "smoke", description = "Delete an existing book")
  public void testDeleteExistingBook() {
    existingBookId = createTestBook();

    apiClient.deleteBook(existingBookId)
            .then()
            .statusCode(200);

    apiClient.getBook(existingBookId)
            .then()
            .statusCode(404)
            .body("detail", equalTo("Book not found"));
  }

  @Test(groups = "negative", description = "Delete a non-existent book")
  public void testDeleteNonExistentBook() {
    int invalidId = 9999; // or config.getOrDefault("INVALID_BOOK_ID", "9999");

    apiClient.deleteBook(invalidId)
            .then()
            .statusCode(404)
            .body("detail", equalTo("Book not found"));
  }
}

package tests;

import config.TestBase;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class UpdateBookTest extends TestBase {

  private int existingBookId;

  @Test(groups = "smoke", description = "Update existing book details")
  public void testUpdateExistingBook() {
    // First, create a book
    existingBookId = createTestBook();

    // Prepare the updated book details
    String updatedBookJson = """
        {
            "name": "Book Name1",
            "author": "Author Name",
            "published_year": "2023",
            "book_summary": "Some summary"
        }
    """;

    // Send the PUT request to update the book
    apiClient.updateBook(existingBookId, updatedBookJson)
            .then()
            .statusCode(200)
            .body("id", equalTo(existingBookId))
            .body("name", equalTo("Book Name1"))
            .body("author", equalTo("Author Name"))
            .body("published_year", equalTo(2023))
            .body("book_summary", equalTo("Some summary"));
  }

  @Test(groups = "negative", description = "Update non-existent book")
  public void testUpdateNonExistentBook() {
    // Non-existent book id
    String invalidIdStr = config.get("INVALID_BOOK_ID");
    if (invalidIdStr == null || invalidIdStr.isEmpty()) {
      invalidIdStr = "9999"; // A known invalid ID
    }
    int invalidId = Integer.parseInt(invalidIdStr);

    String updatedBookJson = """
        {
            "name": "Book Name1",
            "author": "Author Name",
            "published_year": "2023",
            "book_summary": "Some summary"
        }
    """;

    System.out.println("Updating book with ID: " + existingBookId);
    System.out.println("Updated Book Data: " + updatedBookJson);
    apiClient.updateBook(invalidId, updatedBookJson)
            .then()
            .statusCode(404)
            .body("detail", equalTo("Book not found"));
  }
}

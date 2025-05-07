package tests;
import io.qameta.allure.*;
import config.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.cucumber.java.en.*;
import static org.hamcrest.Matchers.*;

public class CreateBookTest extends TestBase {

  private Response response;
  private String bookId;

  @Given("I have a book JSON with name {string}, author {string}, published year {string}, and summary {string}")
  public void i_have_a_book_json(String name, String author, String year, String summary) {
    String bookJson = String.format("""
          {
              "name": "%s",
              "author": "%s",
              "published_year": "%s",
              "book_summary": "%s"
          }
          """, name, author, year, summary);

    // Store the JSON data for future use
    response = apiClient.createBook(bookJson);
  }

  @When("I create a new book with the provided details")
  public void i_create_a_new_book_with_the_provided_details() {
    // Nothing to do here, as the book is created in the @Given step.
  }

  @Then("The book should be created successfully")
  public void the_book_should_be_created_successfully() {
    response.then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", equalTo("Naz Name"))
            .body("author", equalTo("Author Name"));

    // Storing the bookId for later tests
    bookId = response.jsonPath().getString("id");
  }

  @Given("I have an invalid book JSON")
  public void i_have_an_invalid_book_json() {
    String invalidJson = "{invalid_json}";
    response = apiClient.createBook(invalidJson);
  }

  @Then("I should receive an error for invalid data")
  public void i_should_receive_an_error_for_invalid_data() {
    response.then()
            .statusCode(422)  // <-- Update this status code as necessary
            .body("detail[0].msg", containsString("decode error"));
  }
}

package config;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ApiClient;

public class TestBase {
  protected ApiClient apiClient;
  protected static Dotenv config;

//  @BeforeClass(alwaysRun = true)
//  public void setup() {
//    config = Dotenv.configure().ignoreIfMissing().load();
//    apiClient = new ApiClient(
//            config.get("BASE_URL", "http://localhost:8000"),
//            Integer.parseInt(config.get("DEFAULT_TIMEOUT", "5000")),
//            Integer.parseInt(config.get("MAX_RETRIES", "3"))
//    );
//    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//  }

  @BeforeClass(alwaysRun = true)
  public void setup() {
    config = Dotenv.configure().ignoreIfMissing().load();
    String baseUrl = config.get("BASE_URL", "http://localhost:8000");
    int timeout = Integer.parseInt(config.get("DEFAULT_TIMEOUT", "5000"));
    int maxRetries = Integer.parseInt(config.get("MAX_RETRIES", "3"));

    String email = config.get("TEST_EMAIL", "test@gmail.com");
    String password = config.get("TEST_PASSWORD", "12345");

    apiClient = new ApiClient(baseUrl, timeout, maxRetries, email, password);
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    apiClient.resetTestData();
  }
    /**
     * Creates a test book and returns its ID.
     *
     * @return the ID of the created book
     */
  protected int createTestBook() {
    String response = apiClient.createBook("""
            {
              "name": "Test Book",
              "author": "Test Author",
              "published_year": "2023",
              "book_summary": "Delete test summary"
            }
        """).asString();

    System.out.println("Created book response: " + response);
    return io.restassured.path.json.JsonPath.from(response).getInt("id");
  }
}

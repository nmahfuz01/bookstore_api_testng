package utils;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

  private final String baseUrl;
  private final int timeout;
  private final int maxRetries;
  private String token;  // Store the token for reuse

  private static final String BASE_URL = "http://localhost:8000";
  private static final String LOGIN_ENDPOINT = "/login/";
  private static final String EMAIL = "test73@gmail.com";
  private static final String PASSWORD = "Tango@30";

  public ApiClient(String baseUrl, int timeout, int maxRetries, String email, String password) {
    this.baseUrl = baseUrl;
    this.timeout = timeout;
    this.maxRetries = maxRetries;

    RestAssured.baseURI = baseUrl;
    RestAssured.config = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", timeout)
                    .setParam("http.socket.timeout", timeout)
            );

    // Signup before login (safe for first run)
    signup(email, password);
    this.token = login(email, password);
  }

  public static void signup(String email, String password) {
    String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

    Response response = RestAssured.given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/signup/");

    // Handle redirect if present
    if (response.getStatusCode() == 307) {
      String redirectUrl = response.getHeader("Location");
      if (redirectUrl == null) {
        throw new RuntimeException("Signup 307 redirect without Location header");
      }

      // Retry with redirect
      response = RestAssured.given()
              .contentType(ContentType.JSON)
              .body(requestBody)
              .post(redirectUrl);
    }

    if (response.getStatusCode() != 200 && response.getStatusCode() != 400) {
      throw new RuntimeException("Signup failed: " + response.getStatusLine() + " - " + response.getBody().asString());
    }
  }

  // Method to login and store the token

  public static String login(String email, String password) {
    String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

    Response response = RestAssured.given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post(LOGIN_ENDPOINT);

    if (response.getStatusCode() == 307) {
      String redirectUrl = response.getHeader("Location");
      response = RestAssured.given()
              .contentType(ContentType.JSON)
              .body(requestBody)
              .post(redirectUrl);
    }

    if (response.getStatusCode() != 200) {
      throw new RuntimeException("Login failed: " + response.getStatusLine() + " - " + response.getBody().asString());
    }

    return response.jsonPath().getString("access_token");
  }

  // Use the token for subsequent API calls
  public Response createBook(Object bookData) {
    return given()
            .baseUri(baseUrl)
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body(bookData)
            .post("/books/");
  }

  public Response getBook(int bookId) {
    return given()
            .baseUri(baseUrl)
            .header("Authorization", "Bearer " + token)
            .get("/books/" + bookId);
  }

  public Response updateBook(int bookId, Object updateData) {
    return given()
            .baseUri(baseUrl)
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body(updateData)
            .put("/books/" + bookId);
  }

  public Response deleteBook(int bookId) {
    return given()
            .baseUri(baseUrl)
            .header("Authorization", "Bearer " + token)
            .delete("/books/" + bookId);
  }

  public void resetTestData() {
    given()
            .baseUri(baseUrl)
            .header("Authorization", "Bearer " + token)
            .delete("/test-reset");
  }

  public String getToken() {
    return this.token;
  }
}

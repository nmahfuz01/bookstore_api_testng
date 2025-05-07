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
  private String token;

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

    Response loginResponse = login(email, password);

    System.out.println("Login status: " + loginResponse.getStatusCode());
    System.out.println("Login response body: " + loginResponse.getBody().asString());

    try {
      this.token = loginResponse.jsonPath().getString("access_token");
      if (this.token == null) {
        throw new RuntimeException("Token not found in response: " + loginResponse.asString());
      }
    } catch (Exception e) {
      throw new RuntimeException("Login failed: Could not extract token from response: " + loginResponse.asString(), e);
    }
  }

  public static Response signup(String email, String password) {
    String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
    Response response = RestAssured
            .given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(body)
            .post("/signup/");

    if (response.getStatusCode() == 307) {
      String redirectUrl = response.getHeader("Location");
      response = RestAssured.given().contentType(ContentType.JSON).body(body).post(redirectUrl);
    }

    return response;
  }

  public static Response login(String email, String password) {
    String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
    Response response = RestAssured
            .given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(body)
            .post(LOGIN_ENDPOINT);

    if (response.getStatusCode() == 307) {
      String redirectUrl = response.getHeader("Location");
      System.out.println("Redirecting login to: " + redirectUrl);
      response = RestAssured.given().contentType(ContentType.JSON).body(body).post(redirectUrl);
    }

    return response;
  }

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

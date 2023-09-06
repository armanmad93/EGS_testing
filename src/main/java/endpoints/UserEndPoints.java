package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import payload.UserPayload;

import static endpoints.Routes.ACCESS_TOKEN;
import static endpoints.Routes.CREATE_USER_URL;
import static io.restassured.RestAssured.given;
import static utilities.Constants.INVALID_ACCESS_TOKEN;

public final class UserEndPoints {

    /**
     * Creates a new user with a valid authorization token.
     *
     * @param payload The user data to be used for creation.
     * @return A response object containing the result of the user creation request.
     */
    @Step("Create User")
    public static Response createUser(UserPayload payload) {
        return given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(CREATE_USER_URL);
    }

    /**
     * Creates a new user without an authorization token.
     *
     * @param payload The user data to be used for creation.
     * @return A response object containing the result of the user creation request.
     */
    @Step("Create User without Authorization token")
    public static Response createUserWithoutAuthorizationToken(UserPayload payload) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(CREATE_USER_URL);
    }

    /**
     * Creates a new user with an invalid authorization token.
     *
     * @param payload The user data to be used for creation.
     * @return A response object containing the result of the user creation request.
     */
    @Step("Create User with Invalid Authorization token")
    public static Response createUserWithInvalidAuthorizationToken(UserPayload payload) {
        return given()
                .header("Authorization", "Bearer " + INVALID_ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(CREATE_USER_URL);
    }

}


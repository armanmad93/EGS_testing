package test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import java.io.IOException;

import static endpoints.Routes.BASE_URL;
import static endpoints.Routes.POST_USER_URL;
import static endpoints.UserEndPoints.createUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static utilities.Constants.*;

public class CreateUserTests extends BaseTest {

    @DisplayName("Create User with Valid Request Body:gender:male,status:active ")
    @Order(1)
    @Test
    public void testCreateUserWithGenderMaleStatusActive() {
        setUserData(
                faker.name().username(),
                GENDER_MALE,
                STATUS_ACTIVE,
                faker.internet().safeEmailAddress()
        );

        //Create User
        Response response = createUser(userPayload);
        response.then().log().all();

        Object id = response.jsonPath().get(ID);

        //Validate Response Body,Status Code, Headers
        response.then().assertThat()
                .statusCode(201)
                .body(NAME, equalTo(userPayload.getName()))
                .body(GENDER, equalTo(GENDER_MALE))
                .body(STATUS, equalTo(STATUS_ACTIVE))
                .body(EMAIL, equalTo(userPayload.getEmail()))
                .header(CONTENT_TYPE, CONTENT_TYPE_HEADER)
                .header(LOCATION, containsString(BASE_URL + POST_USER_URL + Integer.parseInt(id.toString())));

        // insert to database
        testDataDAO.insertTestDataIntoMySQL(userPayload, "With valid request body", 1);
    }


    @DisplayName("Create User with Valid Request Body:gender:female,status:inactive ")
    @Order(2)
    @Test
    public void testCreateUserWithGenderFemaleStatusInactive() {
        setUserData(
                faker.name().username(),
                GENDER_FEMALE,
                STATUS_INACTIVE,
                faker.internet().safeEmailAddress()
        );

        //Create User Response Body,Status Code, Headers
        Response response = createUser(userPayload);
        response.then().log().all();

        Object id = response.jsonPath().get(ID);

        //Validate
        response.then().assertThat()
                .statusCode(201)
                .body(NAME, equalTo(userPayload.getName()))
                .body(GENDER, equalTo(GENDER_FEMALE))
                .body(STATUS, equalTo(STATUS_INACTIVE))
                .body(EMAIL, equalTo(userPayload.getEmail()))
                .header(CONTENT_TYPE, CONTENT_TYPE_HEADER)
                .header(LOCATION, containsString(BASE_URL + POST_USER_URL + Integer.parseInt(id.toString())));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "With inactive status", 1);
    }

    @DisplayName("Create User with Empty Name on Request Body")
    @Order(3)
    @Test
    public void testCreateUserWithEmptyNameOnRequestBody() throws IOException {
        setUserData(
                EMPTY_NAME,
                GENDER_FEMALE,
                STATUS_INACTIVE,
                faker.internet().safeEmailAddress()
        );

        //Create User
        Response response = createUser(userPayload);
        response.then().log().all();
        String responseBody = response.getBody().asString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);

        //Validate Status Code, Error Message
        JsonNode node = jsonNode.get(0);

        assertThat(jsonNode.size(), equalTo(1));
        assertThat(node.get(FIELD).asText(), equalTo(NAME));
        assertThat(node.get(MESSAGE).asText(), equalTo(FIELD_MESSAGE));
        assertThat(response.getStatusCode(), equalTo(422));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "With empty name", 2);
    }

    @DisplayName("Create User with {String} Email on Request Body")
    @Order(4)
    @Test
    public void testCreateUserWithStringEmailOnRequestBody() throws IOException {
        setUserData(
                faker.name().username(),
                GENDER_FEMALE,
                STATUS_INACTIVE,
                faker.name().username()
        );

        //Create User
        Response response = createUser(userPayload);
        response.then().log().all();
        String responseBody = response.getBody().asString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);

        //Validate Status Code, Error Message
        JsonNode node = jsonNode.get(0);

        assertThat(jsonNode.size(), equalTo(1));
        assertThat(node.get(FIELD).asText(), equalTo(EMAIL));
        assertThat(node.get(MESSAGE).asText(), equalTo(FIELD_EMAIL_MESSAGE));
        assertThat(response.getStatusCode(), equalTo(422));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "With invalid email", 2);
    }

    @DisplayName("Create User with Invalid Gender on Request Body")
    @Order(5)
    @Test
    public void testCreateUserWithInvalidGenderOnRequestBody() throws IOException {
        setUserData(
                faker.name().username(),
                faker.name().username(),
                STATUS_INACTIVE,
                faker.internet().safeEmailAddress()
        );

        //Create User
        Response response = createUser(userPayload);
        response.then().log().all();

        //Validate Status Code, Error Message
        String responseBody = response.getBody().asString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
        JsonNode node = jsonNode.get(0);

        assertThat(jsonNode.size(), equalTo(1));
        assertThat(node.get(FIELD).asText(), equalTo(GENDER));
        assertThat(node.get(MESSAGE).asText(), equalTo(FIELD_GENDER_MESSAGE));
        assertThat(response.getStatusCode(), equalTo(422));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "With invalid gender", 2);
    }

    @DisplayName("Create User with Invalid Status on Request Body")
    @Order(6)
    @Test
    public void testCreateUserWithInvalidStatusOnRequestBody() throws IOException {
        setUserData(
                faker.name().username(),
                GENDER_FEMALE,
                faker.name().username(),
                faker.internet().safeEmailAddress()
        );

        //Create User
        Response response = createUser(userPayload);
        response.then().log().all();
        String responseBody = response.getBody().asString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);

        //Validate Status Code, Error Message
        JsonNode node = jsonNode.get(0);

        assertThat(jsonNode.size(), equalTo(1));
        assertThat(node.get(FIELD).asText(), equalTo(STATUS));
        assertThat(node.get(MESSAGE).asText(), equalTo(FIELD_MESSAGE));
        assertThat(response.getStatusCode(), equalTo(422));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "With invalid status", 2);
    }

    @DisplayName("Create User with Already Created Email on Request Body")
    @Order(7)
    @Test
    public void testCreateUserWithAlreadyCreatedEmailOnRequestBody() throws IOException {
        setUserData(
                faker.name().username(),
                GENDER_MALE,
                STATUS_ACTIVE,
                faker.internet().safeEmailAddress()
        );

        //Create User A
        Response response = createUser(userPayload);
        response.then().log().all();
        String created_email = response.jsonPath().get(EMAIL);

        setUserData(
                faker.name().username(),
                GENDER_MALE,
                STATUS_ACTIVE,
                created_email
        );

        //Create User B with E-mail of User A
        Response new_response = createUser(userPayload);
        new_response.then().log().all();
        String responseBody = new_response.getBody().asString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);

        //Validate Status Code, Error Message
        JsonNode node = jsonNode.get(0);

        assertThat(jsonNode.size(), equalTo(1));
        assertThat(node.get(FIELD).asText(), equalTo(EMAIL));
        assertThat(node.get(MESSAGE).asText(), equalTo(FIELD_CREATED_EMAIL_MESSAGE));
        assertThat(new_response.getStatusCode(), equalTo(422));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "With already existing email", 2);
    }

    @DisplayName("Create User without Authorization Token")
    @Order(8)
    @Test
    public void testCreateUserWithoutAuthorizationToken() {
        setUserData(
                faker.name().username(),
                GENDER_MALE,
                STATUS_ACTIVE,
                faker.internet().safeEmailAddress()
        );
        Response response = UserEndPoints.createUserWithoutAuthorizationToken(userPayload);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(401)
                .body(MESSAGE, equalTo(AUTHENTICATION_FAILED_MESSAGE));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "Without authorization token", 2);
    }

    @DisplayName("Create User with Invalid Authorization Token")
    @Order(9)
    @Test
    public void testCreateUserWithInvalidAuthorizationToken() {
        setUserData(
                faker.name().username(),
                GENDER_MALE,
                STATUS_ACTIVE,
                faker.internet().safeEmailAddress()
        );

        //Create User
        Response response = UserEndPoints.createUserWithInvalidAuthorizationToken(userPayload);
        response.then().log().all();

        //Validate Status Code, Error Message
        response.then().assertThat()
                .statusCode(401)
                .body(MESSAGE, equalTo(INVALID_TOKEN_MESSAGE));

        testDataDAO.insertTestDataIntoMySQL(userPayload, "With Invalid Authorization Token", 2);
    }

    private void setUserData(final String name,
                             final String gender,
                             final String status,
                             final String email) {
        userPayload.setName(name);
        userPayload.setGender(gender);
        userPayload.setStatus(status);
        userPayload.setEmail(email);
    }

}

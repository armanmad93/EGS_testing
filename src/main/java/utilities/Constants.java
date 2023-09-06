package utilities;

public final class Constants {

    private Constants() {
        // empty constructor
    }

    //Request Body Fields
    public final static String GENDER_MALE = "male";

    public final static String GENDER_FEMALE = "female";

    public final static String STATUS_ACTIVE = "active";

    public final static String STATUS_INACTIVE = "inactive";

    public final static String EMPTY_NAME = "";

    public final static String INVALID_ACCESS_TOKEN = "5951e7d42c0d17eaae62a0611ea40bb772744bd4603a4db6b14990c0876a249d";

    //Error messages
    public final static String FIELD_MESSAGE = "can't be blank";

    public final static String FIELD_EMAIL_MESSAGE = "is invalid";

    public final static String FIELD_GENDER_MESSAGE = "can't be blank, can be male of female";

    public final static String FIELD_CREATED_EMAIL_MESSAGE = "has already been taken";

    public final static String INVALID_TOKEN_MESSAGE = "Invalid token";

    public final static String AUTHENTICATION_FAILED_MESSAGE = "Authentication failed";

    // Response Body Fields
    public final static String ID = "id";

    public final static String NAME = "name";

    public final static String GENDER = "gender";

    public final static String STATUS = "status";

    public final static String EMAIL = "email";

    public final static String FIELD = "field";

    public final static String MESSAGE = "message";

    //Response Headers
    public final static String CONTENT_TYPE = "Content-Type";

    public final static String CONTENT_TYPE_HEADER = "application/json; charset=utf-8";

    public final static String LOCATION = "Location";

}

package payload;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserPayload {

    private static UserPayload instance;

    private String name;

    private String gender;

    private String email;

    private String status;

    private UserPayload() {
        //empty constructor
    }

    public static UserPayload getInstance() {
        if (instance == null) {
            instance = new UserPayload();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("gender", gender)
                .append("email", email)
                .append("status", status)
                .toString();
    }

}

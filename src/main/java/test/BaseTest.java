package test;

import com.github.javafaker.Faker;
import connection.DatabaseConnectionFactory;
import dao.TestDataDAO;
import org.junit.BeforeClass;
import payload.UserPayload;

import static payload.UserPayload.getInstance;

public class BaseTest {

    protected static UserPayload userPayload;

    protected static Faker faker;

    protected static TestDataDAO testDataDAO;

    @BeforeClass
    public static void setup() {
        faker = new Faker();
        userPayload = getInstance();
        testDataDAO = new TestDataDAO(new DatabaseConnectionFactory());
    }

}

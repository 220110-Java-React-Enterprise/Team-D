package objects;

// holds the user data
// The annotations help translate between the different names for user_id, id, userId etc.
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

//@Table(tableName="User")
public class User {
    //@Column(columnName="user_id", primaryKey=true)
    Integer id;
    String firstName;
    String lastName;
    String email;

    @JsonGetter("user_id")
    public Integer getId() {
        return id;
    }

    @JsonSetter("user_id")
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Used by UserServlet.doPost() to handle new user registration
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Empty constructor needed for Jackson
    public User(){}
}

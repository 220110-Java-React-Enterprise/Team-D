package objects;

// holds the user data
// The annotations help translate between the different names for user_id, id, userId etc.
import annotations.Column;
import annotations.Table;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * The User class represents a user of the app.
 */
@Table(tableName="User")
@JsonPropertyOrder({"user_id", "first_name", "last_name", "email"})
public class User {
    @Column(columnName="user_id", primaryKey=true)
    Integer id;
    @Column(columnName="first_name", primaryKey=false)
    String firstName;
    @Column(columnName="last_name", primaryKey=false)
    String lastName;
    @Column(columnName="email", primaryKey=false)
    String email;

    @JsonGetter("user_id")
    public Integer getId() {
        return id;
    }

    @JsonSetter("user_id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonGetter("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonSetter("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonGetter("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonSetter("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This constructor returns a User object.
     * Requires the first_name, last_name, and email.
     * This is used by UserServlet.doPost() for non-json requests.
     */
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Empty constructor needed for Jackson
    public User(){}
}

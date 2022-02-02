package objects;

// holds the user data

//@Table(tableName="User")
public class User {
    //@Column(columnName="user_id", primaryKey=true)
    Integer userId;
    String firstName;
    String lastName;
    String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    // Empty constructor needed for Jackson
    public User(){}
}

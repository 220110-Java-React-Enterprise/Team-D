package objects;

// holds the user data

//@Table(tableName="User")
public class User {
    //@Column(columnName="user_id", primaryKey=true)
    Integer user_id;
    String firstName;
    String lastName;
    String email;

    public Integer getuser_id() {
        return user_id;
    }

    public void setuser_id(Integer user_id) {
        this.user_id = user_id;
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

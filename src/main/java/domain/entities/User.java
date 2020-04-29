package domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "users")
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;

    private UserType userType;

    public User() {
    }

    @Id
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "user_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "user_phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    @Column(name = "user_email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "user_password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

package domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "users")
public class User {
    private String id;
    private String name;
    private String phone;
    private String card;
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

    @Column(name = "user_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "user_phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column(name = "user_card")
    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

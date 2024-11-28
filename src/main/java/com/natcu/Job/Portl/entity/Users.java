package com.natcu.Job.Portl.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static javax.print.attribute.Size2DSyntax.MM;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_Id;

    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    private boolean is_active;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registration_date;

    @ManyToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name="userTypeId", referencedColumnName = "userTypeId")
    private UsersType userTypeId;

    public Users() {
    }

    public Users(int user_Id, String email, String password, boolean is_active, Date registration_date, UsersType userTypeId) {
        this.user_Id = user_Id;
        this.email = email;
        this.password = password;
        this.is_active = is_active;
        this.registration_date = registration_date;
        this.userTypeId = userTypeId;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public UsersType getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(UsersType userTypeId) {
        this.userTypeId = userTypeId;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_Id=" + user_Id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", is_active=" + is_active +
                ", registration_date=" + registration_date +
                ", userTypeId=" + userTypeId +
                '}';
    }
}

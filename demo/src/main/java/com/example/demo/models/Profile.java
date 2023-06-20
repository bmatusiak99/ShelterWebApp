package com.example.demo.models;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userAddress;
    private String email;


    @OneToOne(cascade=CascadeType.ALL)
    private User user;

    public Profile() {
        this.user = new User();
    }

    public Profile(Long id, String firstName, String lastName, String phoneNumber, String userAddress, String email, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userAddress = userAddress;
        this.email = email;
        this.user = new User();
    }
}

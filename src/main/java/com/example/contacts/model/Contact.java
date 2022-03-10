package com.example.contacts.model;

import com.example.contacts.model.util.Label;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String surname;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Label phoneLabel;


    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Label emailLabel;

    public String stringify() {
        return "" + name +
                "" + surname +
                "" + phone +
                "" + phoneLabel +
                "" + email +
                "" + emailLabel;
    }
}
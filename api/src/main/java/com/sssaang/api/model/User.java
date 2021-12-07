package com.sssaang.api.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "username" }),
        @UniqueConstraint(columnNames = { "email" })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Size( max = 50 )
    public String name;

    @NotBlank
    @Size( max = 15 )
    public String username;

    @NaturalId
    @NotBlank
    @Size( max = 50 )
    @Email
    public String email;

    @NotBlank
    @Size( max = 100 )
    public String password;

//    @ManytoMany(fetch = FetchType.LAZY)
//    @JoinTable( name = "user_roles",
//            joinColumns = @JoinColumn( name = "user_id"),
//            inverseJoinColumns = @JoinColumn( name = "role_id" )
//    )
//    public Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

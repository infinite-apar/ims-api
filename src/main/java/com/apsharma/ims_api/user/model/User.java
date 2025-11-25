package com.apsharma.ims_api.user.model;

import com.apsharma.ims_api.role.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    private String firstName;
    private String lastName;

    @Email(regexp = ".+@infinite\\.com" , message = "Email must be a valid Infinite email address")
    @Column(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Positive
    private double phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

}

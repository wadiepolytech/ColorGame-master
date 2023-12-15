package com.Laajili.ColorGame.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String role = "USER";


//    public void setRole(String role) {
//        this.role = "USER";
//    }
}

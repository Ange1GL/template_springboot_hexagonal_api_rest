package com.app.app.infraestructure.entities;

import com.app.app.domain.model.Product;
import com.app.app.domain.model.User;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_app")
public class UserEntity extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String password;
    private boolean active;

}

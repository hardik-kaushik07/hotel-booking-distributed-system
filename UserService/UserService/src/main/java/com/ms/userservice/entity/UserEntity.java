package com.ms.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "User")
public class UserEntity {

    @Id
    private String user_id;
    private String name;
    private String email;
    private String about;

    @Builder.Default
    @Transient
    private List<Rating> ratings = new ArrayList<>();
}

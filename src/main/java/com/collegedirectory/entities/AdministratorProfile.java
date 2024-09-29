package com.collegedirectory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "administrator_profile")
@Getter
@Setter
@NoArgsConstructor
public class AdministratorProfile {
    @Id
    @Column(name = "user_id")
    private Long userId; // Foreign key to User

    @Column
    private String photo;

    @Column(name = "department_id")
    private Long departmentId; // Foreign key to Department

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user; // Link to User

    public AdministratorProfile(Long userId, String photo, Long departmentId) {
        this.userId = userId;
        this.photo = photo;
        this.departmentId = departmentId;
    }
}


package net.focik.Smartgaz.userservice.infrastructure.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}

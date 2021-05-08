package com.churilovich.bortnik.darya.shop.on.sofa.repository.model;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    @Column
    private String description;
}

package com.churilovich.bortnik.darya.shop.on.sofa.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user_information")
@Getter
@Setter
public class UserInformation {
    @GenericGenerator(
            name = "generator_user_id",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @Id
    @GeneratedValue(generator = "generator_user_id")
    @Column(name = "user_id")
    private Long id;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;
}

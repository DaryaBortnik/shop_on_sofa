package com.churilovich.bortnik.darya.shop.on.sofa.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@EqualsAndHashCode(exclude = "user")
@NoArgsConstructor
public class UserProfile {

    public UserProfile(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    @GenericGenerator(
            name = "generator_user_id",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @Id
    @GeneratedValue(generator = "generator_user_id")
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;
}

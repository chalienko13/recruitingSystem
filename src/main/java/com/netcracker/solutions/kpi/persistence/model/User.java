package com.netcracker.solutions.kpi.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.solutions.kpi.controller.auth.UserAuthority;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */

@Entity
@Table(name = "user")
public class User implements UserDetails{

    private static final long serialVersionUID = -5190252598383342478L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserTime> userTimes;

    @Column(name = "confirm_token")
    private String confirmToken;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @Column(name = "password")
    private String password;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @Transient
    private List<SocialInformation> socialInformations;

//    @ManyToMany
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    @Transient
    private Set<UserAuthority> userAuthorities;


    /******************** UserDetails */
    @Transient
    private Long expireDate;
    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    @Transient
    private boolean enabled = true;

    /***********************************/

    public User(Long id, String email, String firstName, String secondName, String lastName, String password,
                Set<Role> roles, List<SocialInformation> socialInformations) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.roles = roles;
        this.password = password;
        this.socialInformations = socialInformations;
    }

    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    public User(String email, String firstName, String secondName, String lastName, String password, boolean isActive,
                Timestamp registrationDate, String confirmToken) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.password = password;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
        this.confirmToken = confirmToken;

    }

    public User(String email, String firstName, String secondName, String lastName, Set<Role> roles) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public User(String email, String firstName, String secondName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    /*public List<ScheduleTimePoint> getScheduleTimePoint() {
        return scheduleTimePoint;
    }

    public void setScheduleTimePoint(List<ScheduleTimePoint> scheduleTimePoint) {
        this.scheduleTimePoint = scheduleTimePoint;
    }*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public List<SocialInformation> getSocialInformations() {
        return socialInformations;
    }

    @JsonIgnore
    public void setSocialInformations(List<SocialInformation> socialInformations) {
        this.socialInformations = socialInformations;
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }

    public Set<UserTime> getUserTimes() {
        return userTimes;
    }
    public void setUserTimes(Set<UserTime> userTimes) {
        this.userTimes = userTimes;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return false;
    }

    public boolean isAccountNonLocked() {
        return false;
    }

    public boolean isCredentialsNonExpired() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                ", confirmToken='" + confirmToken + '\'' +
                ", isActive=" + isActive +
                ", registrationDate=" + registrationDate +
                ", password='" + password + '\'' +
                ", socialInformations=" + socialInformations +
                ", userAuthorities=" + userAuthorities +
                ", expireDate=" + expireDate +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(email, user.email)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(email)
                .append(firstName)
                .append(lastName)
                .toHashCode();
    }

    public String getUserId() {
        return email;
    }

    /** userAuth/
     *
     *
     *
     */
//    public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
//        this.userAuthorities = userAuthorities;
//    }
//
//    public Set<UserAuthority> getUserAuthorities() {
//        return userAuthorities;
//    }
//
//    public void grantRole(RoleEnum role) {
//        if (userAuthorities == null) {
//            userAuthorities = new HashSet<UserAuthority>();
//        }
//        userAuthorities.add(role.asAuthorityFor(this));
//    }

}

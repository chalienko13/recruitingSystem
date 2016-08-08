package com.netcracker.solutions.kpi.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {

    private static final long serialVersionUID = -5190252598383342478L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private Set<Role> roles;

    @Column(name = "confirm_token")
    private String confirmToken;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @Column(name = "password")
    private String password;

    @Transient
    private List<ScheduleTimePoint> scheduleTimePoint;
    @Transient
    private List<UserTimePriority> userTimePriorities;


    /**
     * ***************** UserDetails
     */
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

    /**
     * *******************************
     */

    public User(Long id, String email, String firstName, String secondName, String lastName, String password,
                Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.roles = roles;
        this.password = password;
    }

    public User() {
    }


    public User(User user) {
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

    public List<ScheduleTimePoint> getScheduleTimePoint() {
        return scheduleTimePoint;
    }

    public void setScheduleTimePoint(List<ScheduleTimePoint> scheduleTimePoint) {
        this.scheduleTimePoint = scheduleTimePoint;
    }

    public List<UserTimePriority> getUserTimePriorities() {
        return userTimePriorities;
    }

    public void setUserTimePriorities(List<UserTimePriority> userTimePriorities) {
        this.userTimePriorities = userTimePriorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
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
//                ", socialInformations=" + socialInformations +
//               // ", userAuthorities=" + userAuthorities +
//                ", userAuthorities=" + userAuthorities +
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

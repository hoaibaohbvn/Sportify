/*
 * Created on 2023-06-14 ( 09:06:03 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users", catalog="sportify" )
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Pattern(regexp = "^(.{6,})$", message = "{Pattern.users.username}")
    @Pattern(regexp = "^(.{1,15})$", message = "{Pattern.users.username}")
    @NotBlank(message = "{NotBlank.users.username}")
    @Id
    @Column(name="username", nullable=false, length=16)
    private String     username ;

    //--- ENTITY DATA FIELDS 
    @Pattern(regexp = "^(.{6,})$", message = "{Pattern.users.passwords}")
    @Pattern(regexp = "^(.{1,15})$", message = "{Pattern.users.passwords}")
    @NotBlank(message = "{{Pattern.users.passwords}}")
    @Column(name="passwords", nullable=false, length=16)
    private String     passwords ;
    @Pattern(regexp = "^[\\p{L} ]+$", message = "{Pattern.users.firstname}")
    @NotBlank(message = "{NotBlank.users.firstname}")
    @Column(name="firstname", length=50)
    private String     firstname ;
    @Pattern(regexp = "^[\\p{L} ]+$", message = "{Pattern.users.firstname}")
    @NotBlank(message = "{NotBlank.users.firstname}")
    @Column(name="lastname", length=50)
    private String     lastname ;
    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$", message = "{Pattern.users.phone}")
    @NotBlank(message = "{NotBlank.users.phone}")
    @Column(name="phone", nullable=false, length=10)
    private String     phone ;
    @NotBlank(message = "{NotBlank.users.email}")
    @Email(message = "{Email.users.email}")
    @Column(name="email", length=50)
    private String     email ;
    @NotBlank(message = "{NotBlank.users.address}")
    @Column(name="address", length=100)
    private String     address ;

    @Column(name="image", length=50)
    private String     image ;

    @Column(name="gender")
    private Boolean    gender ;
    @Column(name="status")
    private Boolean    status ;

    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="users")
    private List<Teamdetails> listOfTeamdetails ; 
    @JsonIgnore
    @OneToMany(mappedBy="users")
    private List<Orders> listOfOrders ; 
    @JsonIgnore
    @OneToMany(mappedBy="users", fetch = FetchType.EAGER)
    private List<Authorized> listOfAuthorized ; 
    @JsonIgnore
    @OneToMany(mappedBy="users")
    private List<Bookings> listOfBookings ; 
    @JsonIgnore
    @OneToMany(mappedBy="users")
    private List<Favoritefield> listOfFavoritefield ; 
    @JsonIgnore
    @OneToMany(mappedBy="users")
    private List<Teams> listOfTeams ;  

}

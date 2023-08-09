/*
 * Created on 2023-06-14 ( 09:06:03 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity class for "Field"
 *
 * @author Telosys
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="field", catalog="sportify" )
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="fieldid", nullable=false)
    private Integer    fieldid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="sporttypeid", nullable=false, length=6)
    private String     sporttypeid ;
    @NotBlank(message = "{NotBlank.field.namefield}")
    @Column(name="namefield", nullable=false, length=50)
    private String     namefield ;
    @NotBlank(message = "{NotBlank.field.descriptionfield}")
    @Column(name="descriptionfield", length=200)
    private String     descriptionfield ;
    @Min(value = 0, message = "{Min.field.price}")
    @NotNull(message = "{NotNull.field.price}")
    @Column(name="price", nullable=false)
    private Double     price ;

    @Column(name="image", length=100)
    private String     image ;
    @NotBlank(message = "{NotBlank.field.address}")
    @Column(name="address", nullable=false, length=200)
    private String     address ;
    @Column(name="status")
    private Boolean     status ;


    //--- ENTITY LINKS ( RELATIONSHIP )
   

    @ManyToOne
    @JoinColumn(name="sporttypeid", referencedColumnName="sporttypeid", insertable=false, updatable=false)
     Sporttype  sporttype ; 
    @JsonIgnore
    @OneToMany(mappedBy="field")
    private List<Bookingdetails> listOfBookingdetails ; 


   

}

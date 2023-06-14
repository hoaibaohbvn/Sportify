/*
 * Created on 2023-06-14 ( 09:06:03 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="fieldid", nullable=false)
    private Integer    fieldid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="sporttypeid", nullable=false, length=6)
    private String     sporttypeid ;

    @Column(name="namefield", nullable=false, length=50)
    private String     namefield ;

    @Column(name="descriptionfield", length=200)
    private String     descriptionfield ;

    @Column(name="price", nullable=false)
    private Double     price ;

    @Column(name="image", length=100)
    private String     image ;

    @Column(name="address", nullable=false, length=200)
    private String     address ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="field")
    private List<Favoritefield> listOfFavoritefield ; 

    @ManyToOne
    @JoinColumn(name="sporttypeid", referencedColumnName="sporttypeid", insertable=false, updatable=false)
    private Sporttype  sporttype ; 

    @OneToMany(mappedBy="field")
    private List<Bookingdetails> listOfBookingdetails ; 


   

}

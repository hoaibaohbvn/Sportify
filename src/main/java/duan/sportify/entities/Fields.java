/*
 * Created on 2023-06-08 ( 15:10:58 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

/**
 * JPA entity class for "Fields"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="fields", catalog="sportify" )
public class Fields implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="fieldID", nullable=false)
    private Integer    fieldid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="sportTypeID", nullable=false, length=6)
    private String     sporttypeid ;

    @Column(name="nameField", nullable=false, length=50)
    private String     namefield ;

    @Column(name="descriptionField", length=200)
    private String     descriptionfield ;

    @Column(name="price", nullable=false)
    private Double     price ;

    @Column(name="image", length=100)
    private String     image ;

    @Column(name="address", nullable=false, length=200)
    private String     address ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="sportTypeID", referencedColumnName="sportTypeID", insertable=false, updatable=false)
    private Sporttype  sporttype ; 

    @OneToMany(mappedBy="fields")
    private List<Favoritefield> listOfFavoritefield ; 

    @OneToMany(mappedBy="fields")
    private List<Bookingdetails> listOfBookingdetails ; 


    /**
     * Constructor
     */
    public Fields() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setFieldid( Integer fieldid ) {
        this.fieldid = fieldid ;
    }
    public Integer getFieldid() {
        return this.fieldid;
    }

    public void setSporttypeid( String sporttypeid ) {
        this.sporttypeid = sporttypeid ;
    }
    public String getSporttypeid() {
        return this.sporttypeid;
    }

    public void setNamefield( String namefield ) {
        this.namefield = namefield ;
    }
    public String getNamefield() {
        return this.namefield;
    }

    public void setDescriptionfield( String descriptionfield ) {
        this.descriptionfield = descriptionfield ;
    }
    public String getDescriptionfield() {
        return this.descriptionfield;
    }

    public void setPrice( Double price ) {
        this.price = price ;
    }
    public Double getPrice() {
        return this.price;
    }

    public void setImage( String image ) {
        this.image = image ;
    }
    public String getImage() {
        return this.image;
    }

    public void setAddress( String address ) {
        this.address = address ;
    }
    public String getAddress() {
        return this.address;
    }

    //--- GETTERS FOR LINKS
    public Sporttype getSporttype() {
        return this.sporttype;
    } 

    public List<Favoritefield> getListOfFavoritefield() {
        return this.listOfFavoritefield;
    } 

    public List<Bookingdetails> getListOfBookingdetails() {
        return this.listOfBookingdetails;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(fieldid);
        sb.append("|");
        sb.append(sporttypeid);
        sb.append("|");
        sb.append(namefield);
        sb.append("|");
        sb.append(descriptionfield);
        sb.append("|");
        sb.append(price);
        sb.append("|");
        sb.append(image);
        sb.append("|");
        sb.append(address);
        return sb.toString(); 
    } 

}
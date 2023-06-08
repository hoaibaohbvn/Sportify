/*
 * Created on 2023-06-06 ( 15:20:49 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

/**
 * JPA entity class for "Categories"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="categories", catalog="sportify" )
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="categoryID", nullable=false)
    private Integer    categoryid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="categoryName", nullable=false, length=50)
    private String     categoryname ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="categories")
    private List<Products> listOfProducts ; 


    /**
     * Constructor
     */
    public Categories() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setCategoryid( Integer categoryid ) {
        this.categoryid = categoryid ;
    }
    public Integer getCategoryid() {
        return this.categoryid;
    }

    public void setCategoryname( String categoryname ) {
        this.categoryname = categoryname ;
    }
    public String getCategoryname() {
        return this.categoryname;
    }

    //--- GETTERS FOR LINKS
    public List<Products> getListOfProducts() {
        return this.listOfProducts;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(categoryid);
        sb.append("|");
        sb.append(categoryname);
        return sb.toString(); 
    } 

}

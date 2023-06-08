/*
 * Created on 2023-06-06 ( 15:20:49 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

/**
 * JPA entity class for "Products"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="products", catalog="sportify" )
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="productID", nullable=false)
    private Integer    productid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="categoryID", nullable=false)
    private Integer    categoryid ;

    @Column(name="productName", nullable=false, length=50)
    private String     productname ;

    @Column(name="image", length=50)
    private String     image ;

    @Column(name="discountPrice")
    private Double     discountprice ;

    @Temporal(TemporalType.DATE)
    @Column(name="dateCreate", nullable=false)
    private Date       datecreate ;

    @Column(name="price", nullable=false)
    private Double     price ;

    @Column(name="productStatus", nullable=false)
    private Boolean    productstatus ;

    @Column(name="descriptions", length=200)
    private String     descriptions ;

    @Column(name="quantity", nullable=false)
    private Integer    quantity ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="products")
    private List<Orderdetails> listOfOrderdetails ; 

    @ManyToOne
    @JoinColumn(name="categoryID", referencedColumnName="categoryID", insertable=false, updatable=false)
    private Categories categories ; 


    /**
     * Constructor
     */
    public Products() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setProductid( Integer productid ) {
        this.productid = productid ;
    }
    public Integer getProductid() {
        return this.productid;
    }

    public void setCategoryid( Integer categoryid ) {
        this.categoryid = categoryid ;
    }
    public Integer getCategoryid() {
        return this.categoryid;
    }

    public void setProductname( String productname ) {
        this.productname = productname ;
    }
    public String getProductname() {
        return this.productname;
    }

    public void setImage( String image ) {
        this.image = image ;
    }
    public String getImage() {
        return this.image;
    }

    public void setDiscountprice( Double discountprice ) {
        this.discountprice = discountprice ;
    }
    public Double getDiscountprice() {
        return this.discountprice;
    }

    public void setDatecreate( Date datecreate ) {
        this.datecreate = datecreate ;
    }
    public Date getDatecreate() {
        return this.datecreate;
    }

    public void setPrice( Double price ) {
        this.price = price ;
    }
    public Double getPrice() {
        return this.price;
    }

    public void setProductstatus( Boolean productstatus ) {
        this.productstatus = productstatus ;
    }
    public Boolean getProductstatus() {
        return this.productstatus;
    }

    public void setDescriptions( String descriptions ) {
        this.descriptions = descriptions ;
    }
    public String getDescriptions() {
        return this.descriptions;
    }

    public void setQuantity( Integer quantity ) {
        this.quantity = quantity ;
    }
    public Integer getQuantity() {
        return this.quantity;
    }

    //--- GETTERS FOR LINKS
    public List<Orderdetails> getListOfOrderdetails() {
        return this.listOfOrderdetails;
    } 

    public Categories getCategories() {
        return this.categories;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(productid);
        sb.append("|");
        sb.append(categoryid);
        sb.append("|");
        sb.append(productname);
        sb.append("|");
        sb.append(image);
        sb.append("|");
        sb.append(discountprice);
        sb.append("|");
        sb.append(datecreate);
        sb.append("|");
        sb.append(price);
        sb.append("|");
        sb.append(productstatus);
        sb.append("|");
        sb.append(descriptions);
        sb.append("|");
        sb.append(quantity);
        return sb.toString(); 
    } 

}

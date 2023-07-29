package duan.sportify.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * JPA entity class for "Products"
 *
 * @author Telosys
 *
 */
@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products", catalog="sportify" )
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="productid", nullable=false)
    private Integer    productid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="categoryid", nullable=false)
    private Integer    categoryid ;

    @Column(name="productname", nullable=false, length=50)
    private String     productname ;

    @Column(name="image", length=50)
    private String     image ;

    @Column(name="discountprice")
    private Double     discountprice ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="datecreate", nullable=false)
    private Date       datecreate ;

    @Column(name="price", nullable=false)
    private Double     price ;

    @Column(name="productstatus", nullable=false)
    private Boolean    productstatus ;

    @Column(name="descriptions", length=200)
    private String     descriptions ;

    @Column(name="quantity", nullable=false)
    private Integer    quantity ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="categoryid", referencedColumnName="categoryid", insertable=false, updatable=false)
    private Categories categories ; 
    @JsonIgnore
    @OneToMany(mappedBy="products")
    private List<Orderdetails> listOfOrderdetails ; 


    

}

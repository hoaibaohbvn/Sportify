/*
 * Created on 2023-06-14 ( 09:06:03 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity class for "Orders"
 *
 * @author Telosys
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders", catalog="sportify" )
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="orderid", nullable=false)
    private Integer    orderid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="username", nullable=false, length=16)
    private String     username ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdate", nullable=false)
    private Date       createdate ;

    @Column(name="address", nullable=false, length=100)
    private String     address ;

    @Column(name="note", length=1000)
    private String     note ;

    @Column(name="orderstatus", nullable=false, length=12)
    private String     orderstatus ;
    @Column(name="paymentstatus")
    private Boolean    paymentstatus ;

    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="username", referencedColumnName="username", insertable=false, updatable=false)
    private Users      users ; 

    @OneToMany(mappedBy="orders")
    private List<Orderdetails> listOfOrderdetails ; 


     

}

/*
 * Created on 2023-06-06 ( 15:20:49 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

/**
 * JPA entity class for "Voucher"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="voucher", catalog="sportify" )
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="voucherID", nullable=false, length=20)
    private String     voucherid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="discountPercent", nullable=false)
    private Integer    discountpercent ;

    @Temporal(TemporalType.DATE)
    @Column(name="startDate", nullable=false)
    private Date       startdate ;

    @Temporal(TemporalType.DATE)
    @Column(name="endDate", nullable=false)
    private Date       enddate ;


    //--- ENTITY LINKS ( RELATIONSHIP )

    /**
     * Constructor
     */
    public Voucher() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setVoucherid( String voucherid ) {
        this.voucherid = voucherid ;
    }
    public String getVoucherid() {
        return this.voucherid;
    }

    public void setDiscountpercent( Integer discountpercent ) {
        this.discountpercent = discountpercent ;
    }
    public Integer getDiscountpercent() {
        return this.discountpercent;
    }

    public void setStartdate( Date startdate ) {
        this.startdate = startdate ;
    }
    public Date getStartdate() {
        return this.startdate;
    }

    public void setEnddate( Date enddate ) {
        this.enddate = enddate ;
    }
    public Date getEnddate() {
        return this.enddate;
    }

    //--- GETTERS FOR LINKS
    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(voucherid);
        sb.append("|");
        sb.append(discountpercent);
        sb.append("|");
        sb.append(startdate);
        sb.append("|");
        sb.append(enddate);
        return sb.toString(); 
    } 

}

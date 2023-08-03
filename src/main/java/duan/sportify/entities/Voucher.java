/*
 * Created on 2023-06-14 ( 09:06:03 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="voucher", catalog="sportify" )
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @NotNull(message = "{NotNull.voucher.voucherid}")
    @Id
    @Column(name="voucherid", nullable=false, length=20)
    private String     voucherid ;
    @NotNull(message  = "{NotNull.voucher.discountpercent}")
    //--- ENTITY DATA FIELDS 
    @Column(name="discountpercent", nullable=false)
    private Integer    discountpercent ;
    @NotNull(message = "{NotNull.voucher.startdate}")
    @Temporal(TemporalType.DATE)
    @Column(name="startdate", nullable=false)
    private Date       startdate ;
    @NotNull(message = "{NotNull.voucher.enddate}")
    @Temporal(TemporalType.DATE)
    @Column(name="enddate", nullable=false)
    private Date       enddate ;


    

}

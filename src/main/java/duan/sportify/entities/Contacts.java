package duan.sportify.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="contact", catalog="sportify" )
public class Contacts {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="contactid", nullable=false)
	private String contactid;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="datecontact", nullable=false)
    private Date       datecontact ;
	@Column(name = "category", length = 30, nullable = false)
	private String category;
	@Column(name = "title", length = 200, nullable = false)
	private String title;
	@Column(name = "meesagecontact", length = 5000, nullable = false)
	private String meesagecontact;
	@Column(name = "username", length = 16, nullable = false)
	private String username;
	// quan hệ
	
	@ManyToOne
    @JoinColumn(name="username", referencedColumnName="username", insertable=false, updatable=false)
    private Users      users ; 
}

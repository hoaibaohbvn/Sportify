package duan.sportify.entities;

import java.util.Date;


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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.regex.*;

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
	@Column(name="username", nullable=false, length=16)
    private String     username ;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="datecontact", nullable=false)
	private Date       	datecontact ;
	@Column(name = "category", length = 30, nullable = false)
	private String category;
	@NotBlank(message = "{NotBlank.contact.title}")
	@Pattern(regexp = "[\\p{L}0-9\\s]+",message = "{Pattern.contact.title}")
	@Column(name = "title", length = 200, nullable = false)
	private String title;
	@Column(name = "meesagecontact", length = 5000, nullable = false)
	@NotBlank(message = "{NotBlank.contact.meesagecontact}")
	private String meesagecontact;
	
	// quan hệ
	
	@ManyToOne
    @JoinColumn(name="username", referencedColumnName="username", insertable=false, updatable=false)
    private Users      users ; 
	
	
}

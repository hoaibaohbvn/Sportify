/*
 * Created on 2023-06-14 ( 09:06:03 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity class for "Categories"
 *
 * @author Telosys
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories", catalog="sportify" )
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="categoryid", nullable=false)
    private Integer    categoryid ;

    //--- ENTITY DATA FIELDS 
    @NotBlank(message = "{NotNull.categories.categoryname}")
    @Column(name="categoryname", nullable=false, length=50)
    private String     categoryname ;

    @JsonIgnore
    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="categories")
    private List<Products> listOfProducts ; 


   

}

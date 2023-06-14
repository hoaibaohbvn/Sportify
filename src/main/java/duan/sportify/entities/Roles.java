/*
 * Created on 2023-06-08 ( 15:10:58 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package duan.sportify.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

/**
 * JPA entity class for "Roles"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="roles", catalog="sportify" )
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="roleId", nullable=false, length=10)
    private String     roleid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="roleName", nullable=false, length=50)
    private String     rolename ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="roles")
    private List<Authorized> listOfAuthorized ; 


    /**
     * Constructor
     */
    public Roles() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setRoleid( String roleid ) {
        this.roleid = roleid ;
    }
    public String getRoleid() {
        return this.roleid;
    }

    public void setRolename( String rolename ) {
        this.rolename = rolename ;
    }
    public String getRolename() {
        return this.rolename;
    }

    //--- GETTERS FOR LINKS
    public List<Authorized> getListOfAuthorized() {
        return this.listOfAuthorized;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(roleid);
        sb.append("|");
        sb.append(rolename);
        return sb.toString(); 
    } 

}
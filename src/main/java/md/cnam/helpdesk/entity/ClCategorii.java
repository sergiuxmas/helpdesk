/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "cl_categorii")
@NamedQueries({
    @NamedQuery(name = "ClCategorii.findAll", query = "SELECT c FROM ClCategorii c"),
    @NamedQuery(name = "ClCategorii.findById", query = "SELECT c FROM ClCategorii c WHERE c.id = :id"),
    @NamedQuery(name = "ClCategorii.findByNume", query = "SELECT c FROM ClCategorii c WHERE c.nume = :nume")})
public class ClCategorii implements Serializable {
    @OneToMany(mappedBy = "idRol")
    private Collection<AtribUserRol> atribUserRolCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nume")
    private String nume;

    public ClCategorii() {
    }

    public ClCategorii(Integer id) {
        this.id = id;
    }

    public ClCategorii(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClCategorii)) {
            return false;
        }
        ClCategorii other = (ClCategorii) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.ClCategorii[ id=" + id + " ]";
    }

    public Collection<AtribUserRol> getAtribUserRolCollection() {
        return atribUserRolCollection;
    }

    public void setAtribUserRolCollection(Collection<AtribUserRol> atribUserRolCollection) {
        this.atribUserRolCollection = atribUserRolCollection;
    }
    
}

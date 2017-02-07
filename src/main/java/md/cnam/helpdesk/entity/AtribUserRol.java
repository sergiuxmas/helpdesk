/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "atrib_user_rol")
@NamedQueries({
    @NamedQuery(name = "AtribUserRol.findAll", query = "SELECT a FROM AtribUserRol a"),
    @NamedQuery(name = "AtribUserRol.findById", query = "SELECT a FROM AtribUserRol a WHERE a.id = :id")})
public class AtribUserRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    @ManyToOne
    private ClCategorii idRol;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne
    private ClUseri idUser;

    public AtribUserRol() {
    }

    public AtribUserRol(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClCategorii getIdRol() {
        return idRol;
    }

    public void setIdRol(ClCategorii idRol) {
        this.idRol = idRol;
    }

    public ClUseri getIdUser() {
        return idUser;
    }

    public void setIdUser(ClUseri idUser) {
        this.idUser = idUser;
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
        if (!(object instanceof AtribUserRol)) {
            return false;
        }
        AtribUserRol other = (AtribUserRol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.AtribUserRol[ id=" + id + " ]";
    }
    
}

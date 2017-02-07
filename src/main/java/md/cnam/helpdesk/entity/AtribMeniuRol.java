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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "atrib_meniu_rol")
@NamedQueries({
    @NamedQuery(name = "AtribMeniuRol.findAll", query = "SELECT a FROM AtribMeniuRol a"),
    @NamedQuery(name = "AtribMeniuRol.findById", query = "SELECT a FROM AtribMeniuRol a WHERE a.id = :id"),
    @NamedQuery(name = "AtribMeniuRol.findByIdRol", query = "SELECT a FROM AtribMeniuRol a WHERE a.idRol = :idRol"),
    @NamedQuery(name = "AtribMeniuRol.findByIdMeniu", query = "SELECT a FROM AtribMeniuRol a WHERE a.idMeniu = :idMeniu")})
public class AtribMeniuRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_rol")
    private int idRol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_meniu")
    private int idMeniu;

    public AtribMeniuRol() {
    }

    public AtribMeniuRol(Integer id) {
        this.id = id;
    }

    public AtribMeniuRol(Integer id, int idRol, int idMeniu) {
        this.id = id;
        this.idRol = idRol;
        this.idMeniu = idMeniu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdMeniu() {
        return idMeniu;
    }

    public void setIdMeniu(int idMeniu) {
        this.idMeniu = idMeniu;
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
        if (!(object instanceof AtribMeniuRol)) {
            return false;
        }
        AtribMeniuRol other = (AtribMeniuRol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.AtribMeniuRol[ id=" + id + " ]";
    }
    
}

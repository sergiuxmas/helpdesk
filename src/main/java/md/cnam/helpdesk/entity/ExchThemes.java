/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "exch_themes")
@NamedQueries({
    @NamedQuery(name = "ExchThemes.findAll", query = "SELECT e FROM ExchThemes e"),
    @NamedQuery(name = "ExchThemes.findById", query = "SELECT e FROM ExchThemes e WHERE e.id = :id"),
    @NamedQuery(name = "ExchThemes.findByIdUser", query = "SELECT e FROM ExchThemes e WHERE e.idUser = :idUser"),
    @NamedQuery(name = "ExchThemes.findByDateCreated", query = "SELECT e FROM ExchThemes e WHERE e.dateCreated = :dateCreated"),
    @NamedQuery(name = "ExchThemes.findByActive", query = "SELECT e FROM ExchThemes e WHERE e.active = :active")})
public class ExchThemes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user")
    private int idUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "active")
    private Boolean active;

    public ExchThemes() {
    }

    public ExchThemes(Integer id) {
        this.id = id;
    }

    public ExchThemes(Integer id, String name, int idUser, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.idUser = idUser;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        if (!(object instanceof ExchThemes)) {
            return false;
        }
        ExchThemes other = (ExchThemes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.ExchThemes[ id=" + id + " ]";
    }
    
}

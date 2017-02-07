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
@Table(name = "cl_diviziuni")
@NamedQueries({
    @NamedQuery(name = "ClDiviziuni.findAll", query = "SELECT c FROM ClDiviziuni c"),
    @NamedQuery(name = "ClDiviziuni.findByIdDiviziune", query = "SELECT c FROM ClDiviziuni c WHERE c.idDiviziune = :idDiviziune"),
    @NamedQuery(name = "ClDiviziuni.findByNume", query = "SELECT c FROM ClDiviziuni c WHERE c.nume = :nume"),
    @NamedQuery(name = "ClDiviziuni.findByParentId", query = "SELECT c FROM ClDiviziuni c WHERE c.parentId = :parentId"),
    @NamedQuery(name = "ClDiviziuni.findByFullname", query = "SELECT c FROM ClDiviziuni c WHERE c.fullname = :fullname"),
    @NamedQuery(name = "ClDiviziuni.findByUserDSI", query = "SELECT c FROM ClDiviziuni c WHERE c.userDSI = :userDSI")})
public class ClDiviziuni implements Serializable {
    @OneToMany(mappedBy = "idDiviziune")
    private Collection<ClUseri> clUseriCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDiviziune")
    private Integer idDiviziune;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nume")
    private String nume;
    @Basic(optional = false)
    @NotNull
    @Column(name = "parentId")
    private int parentId;
    @Size(max = 200)
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "userDSI")
    private Integer userDSI;

    public ClDiviziuni() {
    }

    public ClDiviziuni(Integer idDiviziune) {
        this.idDiviziune = idDiviziune;
    }

    public ClDiviziuni(Integer idDiviziune, String nume, int parentId) {
        this.idDiviziune = idDiviziune;
        this.nume = nume;
        this.parentId = parentId;
    }

    public Integer getIdDiviziune() {
        return idDiviziune;
    }

    public void setIdDiviziune(Integer idDiviziune) {
        this.idDiviziune = idDiviziune;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getUserDSI() {
        return userDSI;
    }

    public void setUserDSI(Integer userDSI) {
        this.userDSI = userDSI;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDiviziune != null ? idDiviziune.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClDiviziuni)) {
            return false;
        }
        ClDiviziuni other = (ClDiviziuni) object;
        if ((this.idDiviziune == null && other.idDiviziune != null) || (this.idDiviziune != null && !this.idDiviziune.equals(other.idDiviziune))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.ClDiviziuni[ idDiviziune=" + idDiviziune + " ]";
    }

    public Collection<ClUseri> getClUseriCollection() {
        return clUseriCollection;
    }

    public void setClUseriCollection(Collection<ClUseri> clUseriCollection) {
        this.clUseriCollection = clUseriCollection;
    }
    
}

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "cl_useri")
@NamedQueries({
    @NamedQuery(name = "ClUseri.findAll", query = "SELECT c FROM ClUseri c"),
    @NamedQuery(name = "ClUseri.findById", query = "SELECT c FROM ClUseri c WHERE c.id = :id"),
    @NamedQuery(name = "ClUseri.findByIdnp", query = "SELECT c FROM ClUseri c WHERE c.idnp = :idnp"),
    @NamedQuery(name = "ClUseri.findByNume", query = "SELECT c FROM ClUseri c WHERE c.nume = :nume"),
    @NamedQuery(name = "ClUseri.findByPrenume", query = "SELECT c FROM ClUseri c WHERE c.prenume = :prenume"),
    @NamedQuery(name = "ClUseri.findByUsername", query = "SELECT c FROM ClUseri c WHERE c.username = :username"),
    @NamedQuery(name = "ClUseri.findByIdloginCnam", query = "SELECT c FROM ClUseri c WHERE c.idloginCnam = :idloginCnam"),
    @NamedQuery(name = "ClUseri.findByAtasat", query = "SELECT c FROM ClUseri c WHERE c.atasat = :atasat"),
    @NamedQuery(name = "ClUseri.findByPriority", query = "SELECT c FROM ClUseri c WHERE c.priority = :priority"),
    @NamedQuery(name = "ClUseri.findByPassword", query = "SELECT c FROM ClUseri c WHERE c.password = :password")})
public class ClUseri implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 13)
    @Column(name = "idnp")
    private String idnp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nume")
    private String nume;
    @Size(max = 100)
    @Column(name = "prenume")
    private String prenume;
    @Size(max = 100)
    @Column(name = "username")
    private String username;
    @Column(name = "id_loginCnam")
    private Integer idloginCnam;
    @Size(max = 10)
    @Column(name = "atasat")
    private String atasat;
    @Column(name = "priority")
    private Integer priority;
    @Size(max = 100)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "id_diviziune", referencedColumnName = "idDiviziune")
    @ManyToOne
    private ClDiviziuni idDiviziune;

    public ClUseri() {
    }

    public ClUseri(Integer id) {
        this.id = id;
    }

    public ClUseri(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdnp() {
        return idnp;
    }

    public void setIdnp(String idnp) {
        this.idnp = idnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdloginCnam() {
        return idloginCnam;
    }

    public void setIdloginCnam(Integer idloginCnam) {
        this.idloginCnam = idloginCnam;
    }

    public String getAtasat() {
        return atasat;
    }

    public void setAtasat(String atasat) {
        this.atasat = atasat;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClDiviziuni getIdDiviziune() {
        return idDiviziune;
    }

    public void setIdDiviziune(ClDiviziuni idDiviziune) {
        this.idDiviziune = idDiviziune;
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
        if (!(object instanceof ClUseri)) {
            return false;
        }
        ClUseri other = (ClUseri) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.ClUseri[ id=" + id + " ]";
    }
    
}

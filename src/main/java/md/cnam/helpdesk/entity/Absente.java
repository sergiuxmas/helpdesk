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
@Table(name = "absente")
@NamedQueries({
    @NamedQuery(name = "Absente.findAll", query = "SELECT a FROM Absente a"),
    @NamedQuery(name = "Absente.findById", query = "SELECT a FROM Absente a WHERE a.id = :id"),
    @NamedQuery(name = "Absente.findByIdUser", query = "SELECT a FROM Absente a WHERE a.idUser = :idUser"),
    @NamedQuery(name = "Absente.findByFrom", query = "SELECT a FROM Absente a WHERE a.from = :from"),
    @NamedQuery(name = "Absente.findByTo", query = "SELECT a FROM Absente a WHERE a.to = :to"),
    @NamedQuery(name = "Absente.findByCause", query = "SELECT a FROM Absente a WHERE a.cause = :cause")})
public class Absente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user")
    private int idUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date from;
    @Basic(optional = false)
    @NotNull
    @Column(name = "to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date to;
    @Size(max = 255)
    @Column(name = "cause")
    private String cause;

    public Absente() {
    }

    public Absente(Integer id) {
        this.id = id;
    }

    public Absente(Integer id, int idUser, Date from, Date to) {
        this.id = id;
        this.idUser = idUser;
        this.from = from;
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
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
        if (!(object instanceof Absente)) {
            return false;
        }
        Absente other = (Absente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.Absente[ id=" + id + " ]";
    }
    
}

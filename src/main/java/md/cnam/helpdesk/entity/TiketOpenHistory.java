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

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "tiket_open_history")
@NamedQueries({
    @NamedQuery(name = "TiketOpenHistory.findAll", query = "SELECT t FROM TiketOpenHistory t"),
    @NamedQuery(name = "TiketOpenHistory.findById", query = "SELECT t FROM TiketOpenHistory t WHERE t.id = :id"),
    @NamedQuery(name = "TiketOpenHistory.findByIdUser", query = "SELECT t FROM TiketOpenHistory t WHERE t.idUser = :idUser"),
    @NamedQuery(name = "TiketOpenHistory.findByIdTiket", query = "SELECT t FROM TiketOpenHistory t WHERE t.idTiket = :idTiket"),
    @NamedQuery(name = "TiketOpenHistory.findByDateOpened", query = "SELECT t FROM TiketOpenHistory t WHERE t.dateOpened = :dateOpened")})
public class TiketOpenHistory implements Serializable {
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
    @Column(name = "id_tiket")
    private int idTiket;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_opened")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOpened;

    public TiketOpenHistory() {
    }

    public TiketOpenHistory(Integer id) {
        this.id = id;
    }

    public TiketOpenHistory(Integer id, int idUser, int idTiket, Date dateOpened) {
        this.id = id;
        this.idUser = idUser;
        this.idTiket = idTiket;
        this.dateOpened = dateOpened;
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

    public int getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
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
        if (!(object instanceof TiketOpenHistory)) {
            return false;
        }
        TiketOpenHistory other = (TiketOpenHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.TiketOpenHistory[ id=" + id + " ]";
    }
    
}

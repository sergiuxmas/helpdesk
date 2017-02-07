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
@Table(name = "messages")
@NamedQueries({
    @NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m"),
    @NamedQuery(name = "Messages.findById", query = "SELECT m FROM Messages m WHERE m.id = :id"),
    @NamedQuery(name = "Messages.findByIdSolicitare", query = "SELECT m FROM Messages m WHERE m.idSolicitare = :idSolicitare"),
    @NamedQuery(name = "Messages.findByIdExecutor", query = "SELECT m FROM Messages m WHERE m.idExecutor = :idExecutor"),
    @NamedQuery(name = "Messages.findByDataExpedierii", query = "SELECT m FROM Messages m WHERE m.dataExpedierii = :dataExpedierii")})
public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitare")
    private int idSolicitare;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_executor")
    private int idExecutor;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_expedierii")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpedierii;

    public Messages() {
    }

    public Messages(Integer id) {
        this.id = id;
    }

    public Messages(Integer id, int idSolicitare, int idExecutor, Date dataExpedierii) {
        this.id = id;
        this.idSolicitare = idSolicitare;
        this.idExecutor = idExecutor;
        this.dataExpedierii = dataExpedierii;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdSolicitare() {
        return idSolicitare;
    }

    public void setIdSolicitare(int idSolicitare) {
        this.idSolicitare = idSolicitare;
    }

    public int getIdExecutor() {
        return idExecutor;
    }

    public void setIdExecutor(int idExecutor) {
        this.idExecutor = idExecutor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDataExpedierii() {
        return dataExpedierii;
    }

    public void setDataExpedierii(Date dataExpedierii) {
        this.dataExpedierii = dataExpedierii;
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
        if (!(object instanceof Messages)) {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.Messages[ id=" + id + " ]";
    }
    
}

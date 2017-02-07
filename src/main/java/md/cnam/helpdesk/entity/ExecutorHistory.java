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
@Table(name = "executor_history")
@NamedQueries({
    @NamedQuery(name = "ExecutorHistory.findAll", query = "SELECT e FROM ExecutorHistory e"),
    @NamedQuery(name = "ExecutorHistory.findById", query = "SELECT e FROM ExecutorHistory e WHERE e.id = :id"),
    @NamedQuery(name = "ExecutorHistory.findByIdSolicitare", query = "SELECT e FROM ExecutorHistory e WHERE e.idSolicitare = :idSolicitare"),
    @NamedQuery(name = "ExecutorHistory.findByUserDecizie", query = "SELECT e FROM ExecutorHistory e WHERE e.userDecizie = :userDecizie"),
    @NamedQuery(name = "ExecutorHistory.findByUserExecutor", query = "SELECT e FROM ExecutorHistory e WHERE e.userExecutor = :userExecutor"),
    @NamedQuery(name = "ExecutorHistory.findByDataDecizie", query = "SELECT e FROM ExecutorHistory e WHERE e.dataDecizie = :dataDecizie"),
    @NamedQuery(name = "ExecutorHistory.findByAcceptat", query = "SELECT e FROM ExecutorHistory e WHERE e.acceptat = :acceptat"),
    @NamedQuery(name = "ExecutorHistory.findByDataAcceptare", query = "SELECT e FROM ExecutorHistory e WHERE e.dataAcceptare = :dataAcceptare")})
public class ExecutorHistory implements Serializable {
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
    @Column(name = "user_decizie")
    private Integer userDecizie;
    @Column(name = "user_executor")
    private Integer userExecutor;
    @Column(name = "data_decizie")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDecizie;
    @Column(name = "acceptat")
    private Boolean acceptat;
    @Column(name = "data_acceptare")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAcceptare;

    public ExecutorHistory() {
    }

    public ExecutorHistory(Integer id) {
        this.id = id;
    }

    public ExecutorHistory(Integer id, int idSolicitare) {
        this.id = id;
        this.idSolicitare = idSolicitare;
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

    public Integer getUserDecizie() {
        return userDecizie;
    }

    public void setUserDecizie(Integer userDecizie) {
        this.userDecizie = userDecizie;
    }

    public Integer getUserExecutor() {
        return userExecutor;
    }

    public void setUserExecutor(Integer userExecutor) {
        this.userExecutor = userExecutor;
    }

    public Date getDataDecizie() {
        return dataDecizie;
    }

    public void setDataDecizie(Date dataDecizie) {
        this.dataDecizie = dataDecizie;
    }

    public Boolean getAcceptat() {
        return acceptat;
    }

    public void setAcceptat(Boolean acceptat) {
        this.acceptat = acceptat;
    }

    public Date getDataAcceptare() {
        return dataAcceptare;
    }

    public void setDataAcceptare(Date dataAcceptare) {
        this.dataAcceptare = dataAcceptare;
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
        if (!(object instanceof ExecutorHistory)) {
            return false;
        }
        ExecutorHistory other = (ExecutorHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.ExecutorHistory[ id=" + id + " ]";
    }
    
}

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
@Table(name = "executare")
@NamedQueries({
    @NamedQuery(name = "Executare.findAll", query = "SELECT e FROM Executare e"),
    @NamedQuery(name = "Executare.findById", query = "SELECT e FROM Executare e WHERE e.id = :id"),
    @NamedQuery(name = "Executare.findByIdPrioritate", query = "SELECT e FROM Executare e WHERE e.idPrioritate = :idPrioritate"),
    @NamedQuery(name = "Executare.findByTipSolicitare", query = "SELECT e FROM Executare e WHERE e.tipSolicitare = :tipSolicitare"),
    @NamedQuery(name = "Executare.findByIdDomeniu", query = "SELECT e FROM Executare e WHERE e.idDomeniu = :idDomeniu"),
    @NamedQuery(name = "Executare.findByIdSolicitare", query = "SELECT e FROM Executare e WHERE e.idSolicitare = :idSolicitare"),
    @NamedQuery(name = "Executare.findByIdExecutor", query = "SELECT e FROM Executare e WHERE e.idExecutor = :idExecutor"),
    @NamedQuery(name = "Executare.findByDateBegin", query = "SELECT e FROM Executare e WHERE e.dateBegin = :dateBegin"),
    @NamedQuery(name = "Executare.findByDateEnd", query = "SELECT e FROM Executare e WHERE e.dateEnd = :dateEnd"),
    @NamedQuery(name = "Executare.findByIdHz", query = "SELECT e FROM Executare e WHERE e.idHz = :idHz"),
    @NamedQuery(name = "Executare.findByDataExecutarii", query = "SELECT e FROM Executare e WHERE e.dataExecutarii = :dataExecutarii")})
public class Executare implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_prioritate")
    private Integer idPrioritate;
    @Column(name = "tip_solicitare")
    private Integer tipSolicitare;
    @Column(name = "id_domeniu")
    private Integer idDomeniu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitare")
    private int idSolicitare;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_executor")
    private int idExecutor;
    @Column(name = "date_begin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBegin;
    @Column(name = "date_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    @Column(name = "id_hz")
    private Integer idHz;
    @Lob
    @Size(max = 65535)
    @Column(name = "descriere")
    private String descriere;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_executarii")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExecutarii;

    public Executare() {
    }

    public Executare(Integer id) {
        this.id = id;
    }

    public Executare(Integer id, int idSolicitare, int idExecutor, Date dataExecutarii) {
        this.id = id;
        this.idSolicitare = idSolicitare;
        this.idExecutor = idExecutor;
        this.dataExecutarii = dataExecutarii;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPrioritate() {
        return idPrioritate;
    }

    public void setIdPrioritate(Integer idPrioritate) {
        this.idPrioritate = idPrioritate;
    }

    public Integer getTipSolicitare() {
        return tipSolicitare;
    }

    public void setTipSolicitare(Integer tipSolicitare) {
        this.tipSolicitare = tipSolicitare;
    }

    public Integer getIdDomeniu() {
        return idDomeniu;
    }

    public void setIdDomeniu(Integer idDomeniu) {
        this.idDomeniu = idDomeniu;
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

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getIdHz() {
        return idHz;
    }

    public void setIdHz(Integer idHz) {
        this.idHz = idHz;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Date getDataExecutarii() {
        return dataExecutarii;
    }

    public void setDataExecutarii(Date dataExecutarii) {
        this.dataExecutarii = dataExecutarii;
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
        if (!(object instanceof Executare)) {
            return false;
        }
        Executare other = (Executare) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.Executare[ id=" + id + " ]";
    }
    
}

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
@Table(name = "solicitare")
@NamedQueries({
    @NamedQuery(name = "Solicitare.findAll", query = "SELECT s FROM Solicitare s"),
    @NamedQuery(name = "Solicitare.findById", query = "SELECT s FROM Solicitare s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitare.findByTip", query = "SELECT s FROM Solicitare s WHERE s.tip = :tip"),
    @NamedQuery(name = "Solicitare.findByStatut", query = "SELECT s FROM Solicitare s WHERE s.statut = :statut"),
    @NamedQuery(name = "Solicitare.findByIdPrioritate", query = "SELECT s FROM Solicitare s WHERE s.idPrioritate = :idPrioritate"),
    @NamedQuery(name = "Solicitare.findByIdDomeniu", query = "SELECT s FROM Solicitare s WHERE s.idDomeniu = :idDomeniu"),
    @NamedQuery(name = "Solicitare.findByIdUser", query = "SELECT s FROM Solicitare s WHERE s.idUser = :idUser"),
    @NamedQuery(name = "Solicitare.findByDataSolicitarii", query = "SELECT s FROM Solicitare s WHERE s.dataSolicitarii = :dataSolicitarii"),
    @NamedQuery(name = "Solicitare.findByDataStabiliriiPrioritatii", query = "SELECT s FROM Solicitare s WHERE s.dataStabiliriiPrioritatii = :dataStabiliriiPrioritatii"),
    @NamedQuery(name = "Solicitare.findByPrioritateDSI", query = "SELECT s FROM Solicitare s WHERE s.prioritateDSI = :prioritateDSI"),
    @NamedQuery(name = "Solicitare.findByDataRezervarii", query = "SELECT s FROM Solicitare s WHERE s.dataRezervarii = :dataRezervarii"),
    @NamedQuery(name = "Solicitare.findByOpened", query = "SELECT s FROM Solicitare s WHERE s.opened = :opened"),
    @NamedQuery(name = "Solicitare.findByDataInchideriiSolicitarii", query = "SELECT s FROM Solicitare s WHERE s.dataInchideriiSolicitarii = :dataInchideriiSolicitarii")})
public class Solicitare implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tip")
    private Integer tip;
    @Column(name = "statut")
    private Integer statut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_prioritate")
    private int idPrioritate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_domeniu")
    private int idDomeniu;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descriere")
    private String descriere;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user")
    private int idUser;
    @Column(name = "data_solicitarii")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitarii;
    @Column(name = "data_stabilirii_prioritatii")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataStabiliriiPrioritatii;
    @Column(name = "prioritate_DSI")
    private Integer prioritateDSI;
    @Column(name = "data_rezervarii")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRezervarii;
    @Column(name = "opened")
    private Boolean opened;
    @Column(name = "data_inchiderii_solicitarii")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInchideriiSolicitarii;

    public Solicitare() {
    }

    public Solicitare(Integer id) {
        this.id = id;
    }

    public Solicitare(Integer id, int idPrioritate, int idDomeniu, String descriere, int idUser) {
        this.id = id;
        this.idPrioritate = idPrioritate;
        this.idDomeniu = idDomeniu;
        this.descriere = descriere;
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public int getIdPrioritate() {
        return idPrioritate;
    }

    public void setIdPrioritate(int idPrioritate) {
        this.idPrioritate = idPrioritate;
    }

    public int getIdDomeniu() {
        return idDomeniu;
    }

    public void setIdDomeniu(int idDomeniu) {
        this.idDomeniu = idDomeniu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDataSolicitarii() {
        return dataSolicitarii;
    }

    public void setDataSolicitarii(Date dataSolicitarii) {
        this.dataSolicitarii = dataSolicitarii;
    }

    public Date getDataStabiliriiPrioritatii() {
        return dataStabiliriiPrioritatii;
    }

    public void setDataStabiliriiPrioritatii(Date dataStabiliriiPrioritatii) {
        this.dataStabiliriiPrioritatii = dataStabiliriiPrioritatii;
    }

    public Integer getPrioritateDSI() {
        return prioritateDSI;
    }

    public void setPrioritateDSI(Integer prioritateDSI) {
        this.prioritateDSI = prioritateDSI;
    }

    public Date getDataRezervarii() {
        return dataRezervarii;
    }

    public void setDataRezervarii(Date dataRezervarii) {
        this.dataRezervarii = dataRezervarii;
    }

    public Boolean getOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public Date getDataInchideriiSolicitarii() {
        return dataInchideriiSolicitarii;
    }

    public void setDataInchideriiSolicitarii(Date dataInchideriiSolicitarii) {
        this.dataInchideriiSolicitarii = dataInchideriiSolicitarii;
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
        if (!(object instanceof Solicitare)) {
            return false;
        }
        Solicitare other = (Solicitare) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.Solicitare[ id=" + id + " ]";
    }
    
}

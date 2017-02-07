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
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "files")
@NamedQueries({
    @NamedQuery(name = "Files.findAll", query = "SELECT f FROM Files f"),
    @NamedQuery(name = "Files.findById", query = "SELECT f FROM Files f WHERE f.id = :id"),
    @NamedQuery(name = "Files.findByIdTiket", query = "SELECT f FROM Files f WHERE f.idTiket = :idTiket"),
    @NamedQuery(name = "Files.findByIdUser", query = "SELECT f FROM Files f WHERE f.idUser = :idUser"),
    @NamedQuery(name = "Files.findByUrl", query = "SELECT f FROM Files f WHERE f.url = :url"),
    @NamedQuery(name = "Files.findByFilename", query = "SELECT f FROM Files f WHERE f.filename = :filename"),
    @NamedQuery(name = "Files.findByDateUpload", query = "SELECT f FROM Files f WHERE f.dateUpload = :dateUpload")})
public class Files implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tiket")
    private Integer idTiket;
    @Column(name = "id_user")
    private Integer idUser;
    @Size(max = 200)
    @Column(name = "url")
    private String url;
    @Size(max = 200)
    @Column(name = "filename")
    private String filename;
    @Column(name = "date_upload")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpload;

    public Files() {
    }

    public Files(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(Integer idTiket) {
        this.idTiket = idTiket;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
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
        if (!(object instanceof Files)) {
            return false;
        }
        Files other = (Files) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.Files[ id=" + id + " ]";
    }
    
}

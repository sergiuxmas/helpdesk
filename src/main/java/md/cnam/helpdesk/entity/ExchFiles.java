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
@Table(name = "exch_files")
@NamedQueries({
    @NamedQuery(name = "ExchFiles.findAll", query = "SELECT e FROM ExchFiles e"),
    @NamedQuery(name = "ExchFiles.findById", query = "SELECT e FROM ExchFiles e WHERE e.id = :id"),
    @NamedQuery(name = "ExchFiles.findByIdTema", query = "SELECT e FROM ExchFiles e WHERE e.idTema = :idTema"),
    @NamedQuery(name = "ExchFiles.findByIdUser", query = "SELECT e FROM ExchFiles e WHERE e.idUser = :idUser"),
    @NamedQuery(name = "ExchFiles.findByUrl", query = "SELECT e FROM ExchFiles e WHERE e.url = :url"),
    @NamedQuery(name = "ExchFiles.findByFilename", query = "SELECT e FROM ExchFiles e WHERE e.filename = :filename"),
    @NamedQuery(name = "ExchFiles.findByDateUpload", query = "SELECT e FROM ExchFiles e WHERE e.dateUpload = :dateUpload"),
    @NamedQuery(name = "ExchFiles.findByExtention", query = "SELECT e FROM ExchFiles e WHERE e.extention = :extention")})
public class ExchFiles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tema")
    private int idTema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user")
    private int idUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "url")
    private String url;
    @Size(max = 200)
    @Column(name = "filename")
    private String filename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_upload")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpload;
    @Size(max = 20)
    @Column(name = "extention")
    private String extention;

    public ExchFiles() {
    }

    public ExchFiles(Integer id) {
        this.id = id;
    }

    public ExchFiles(Integer id, int idTema, int idUser, String url, Date dateUpload) {
        this.id = id;
        this.idTema = idTema;
        this.idUser = idUser;
        this.url = url;
        this.dateUpload = dateUpload;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
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
        if (!(object instanceof ExchFiles)) {
            return false;
        }
        ExchFiles other = (ExchFiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.ExchFiles[ id=" + id + " ]";
    }
    
}

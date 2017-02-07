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
import javax.persistence.Lob;
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
@Table(name = "meniu")
@NamedQueries({
    @NamedQuery(name = "Meniu.findAll", query = "SELECT m FROM Meniu m"),
    @NamedQuery(name = "Meniu.findById", query = "SELECT m FROM Meniu m WHERE m.id = :id"),
    @NamedQuery(name = "Meniu.findByParentId", query = "SELECT m FROM Meniu m WHERE m.parentId = :parentId"),
    @NamedQuery(name = "Meniu.findByWeight", query = "SELECT m FROM Meniu m WHERE m.weight = :weight"),
    @NamedQuery(name = "Meniu.findByName", query = "SELECT m FROM Meniu m WHERE m.name = :name"),
    @NamedQuery(name = "Meniu.findByPath", query = "SELECT m FROM Meniu m WHERE m.path = :path"),
    @NamedQuery(name = "Meniu.findByFullname", query = "SELECT m FROM Meniu m WHERE m.fullname = :fullname"),
    @NamedQuery(name = "Meniu.findByType", query = "SELECT m FROM Meniu m WHERE m.type = :type")})
public class Meniu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "parentId")
    private Integer parentId;
    @Column(name = "weight")
    private Integer weight;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "path")
    private String path;
    @Size(max = 100)
    @Column(name = "fullname")
    private String fullname;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 8)
    @Column(name = "type")
    private String type;

    public Meniu() {
    }

    public Meniu(Integer id) {
        this.id = id;
    }

    public Meniu(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(object instanceof Meniu)) {
            return false;
        }
        Meniu other = (Meniu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "md.cnam.helpdesk.entity.Meniu[ id=" + id + " ]";
    }
    
}

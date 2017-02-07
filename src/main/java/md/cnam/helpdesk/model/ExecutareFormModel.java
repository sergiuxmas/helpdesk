/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Admin
 */
public class ExecutareFormModel {
    private Integer idTiket;
    private Integer prioritate;
    private Integer tip;
    private Integer domeniu;
    @NotEmpty
    private String descriere;

    public ExecutareFormModel() {
    }

    public Integer getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(Integer idTiket) {
        this.idTiket = idTiket;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(Integer prioritate) {
        this.prioritate = prioritate;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public Integer getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(Integer domeniu) {
        this.domeniu = domeniu;
    }
    
    
}

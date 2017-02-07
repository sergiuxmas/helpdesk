/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import javax.persistence.Entity;
import md.cnam.helpdesk.entity.ClUseri;

//@Entity
public class UserSession extends ClUseri{
    
    private String diviziuneNume;
    private int[] id_categoria;
    private String[] categoria_nume;

    
    public String getDiviziuneNume() {
        return diviziuneNume;
    }

    public void setDiviziuneNume(String diviziuneNume) {
        this.diviziuneNume = diviziuneNume;
    }

    public int[] getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int[] id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String[] getCategoria_nume() {
        return categoria_nume;
    }

    public void setCategoria_nume(String[] categoria_nume) {
        this.categoria_nume = categoria_nume;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import java.sql.Date;
import md.cnam.helpdesk.entity.ClUseri;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


public class TiketForm {
    private Integer id;
    
    @NotEmpty
    private String tip;
    
    @NotEmpty
    private String domeniu;
    
    @NotEmpty
    private String prioritate;
    
    @NotEmpty
    private String descrierea;
    
    private MultipartFile fileUpload; 
    

    public TiketForm() {
        prioritate="1";
    }

    public TiketForm(String domeniu, String prioritate, String descrierea) {
        this.domeniu = domeniu;
        this.prioritate = prioritate;
        this.descrierea = descrierea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    public String getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(String prioritate) {
        this.prioritate = prioritate;
    }

    public String getDescrierea() {
        return descrierea;
    }

    public void setDescrierea(String descrierea) {
        this.descrierea = descrierea;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

}

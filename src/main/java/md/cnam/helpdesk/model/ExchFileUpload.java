/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import org.springframework.web.multipart.MultipartFile;

public class ExchFileUpload {
    private Integer id;
    private Integer idTema;
    private MultipartFile fileupload;

    public ExchFileUpload() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MultipartFile getFileupload() {
        return fileupload;
    }

    public void setFileupload(MultipartFile fileupload) {
        this.fileupload = fileupload;
    }

    public Integer getIdTema() {
        return idTema;
    }

    public void setIdTema(Integer idTema) {
        this.idTema = idTema;
    }
}

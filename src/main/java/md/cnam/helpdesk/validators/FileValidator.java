/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.validators;

import md.cnam.helpdesk.model.TiketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileValidator implements Validator {
    //@Autowired
    //CommonsMultipartResolver multipartResolver;

    @Override
    public boolean supports(Class<?> clazz) {
        return TiketForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TiketForm tiketForm = (TiketForm) target;

        if (tiketForm.getTip()!=null && tiketForm.getTip().isEmpty()) {
            errors.rejectValue("tip", "tip.empty");
        }
        if (tiketForm.getPrioritate()!=null && tiketForm.getPrioritate().isEmpty()) {
            errors.rejectValue("prioritate", "prioritate.empty");
        }
        if (tiketForm.getDomeniu()!=null && tiketForm.getDomeniu().isEmpty()) {
            errors.rejectValue("domeniu", "domeniu.empty");
        }
        if (tiketForm.getDescrierea()!=null && tiketForm.getDescrierea().isEmpty()) {
            errors.rejectValue("descrierea", "descrierea.empty");
        }
        if (tiketForm.getFileUpload()!=null && 
            tiketForm.getFileUpload().getSize()>5242880){//5Mb
            errors.rejectValue("fileUpload", "file.maxsize",new Object[]{5},"");
        }
        
        if (tiketForm.getFileUpload()!=null && !tiketForm.getFileUpload().isEmpty() &&
            (!tiketForm.getFileUpload().getContentType().contains("image")
              && !tiketForm.getFileUpload().getContentType().contains("application/x-rar-compressed")
              //&& !tiketForm.getFileUpload().getContentType().contains("application/octet-stream")
              && !tiketForm.getFileUpload().getContentType().contains("application/zip")
              && !tiketForm.getFileUpload().getContentType().contains("pdf")
              && !tiketForm.getFileUpload().getContentType().contains("application/vnd.ms-excel") //.xls files
              && !tiketForm.getFileUpload().getContentType().contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") //.xlsx files
              && !tiketForm.getFileUpload().getContentType().contains("application/msword") //.doc
              && !tiketForm.getFileUpload().getContentType().contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document") //.docx
            )){
            errors.rejectValue("fileUpload", "file.type");
        }
    }

}

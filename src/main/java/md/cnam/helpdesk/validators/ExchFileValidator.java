/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.validators;

import md.cnam.helpdesk.model.ExchFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Component
public class ExchFileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ExchFileUpload.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExchFileUpload form=(ExchFileUpload)target;
        
        if (form.getFileupload()!=null && form.getFileupload().isEmpty()) {
            errors.rejectValue("fileupload", "file.empty");
        }
        
        if (form.getFileupload()!=null && FilenameUtils.getExtension(form.getFileupload().getOriginalFilename()).isEmpty()) {
            errors.rejectValue("fileupload", "file.noextention");
        }
        
        if (form.getFileupload()!=null && 
            form.getFileupload().getSize()>157286400){//150Mb
            errors.rejectValue("fileupload", "file.maxsize",new Object[]{150},"");
        }
        if (form.getFileupload()!=null && !form.getFileupload().isEmpty() &&
            (!form.getFileupload().getContentType().contains("image")
              && !form.getFileupload().getContentType().contains("application/x-rar-compressed")
              && !form.getFileupload().getContentType().contains("application/zip")
              && !form.getFileupload().getContentType().contains("pdf")
              && !form.getFileupload().getContentType().contains("application/vnd.ms-excel") //.xls files
              && !form.getFileupload().getContentType().contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") //.xlsx files
              && !form.getFileupload().getContentType().contains("application/msword") //.doc
              && !form.getFileupload().getContentType().contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document") //.docx
              && !form.getFileupload().getContentType().contains("application/vnd.ms-powerpoint") //.ppt, .pot, .pps, .ppa
              && !form.getFileupload().getContentType().contains("application/vnd.openxmlformats-officedocument.presentationml.presentation") //.pptx
              && !form.getFileupload().getContentType().contains("application/vnd.openxmlformats-officedocument.presentationml.template") //.potx
              && !form.getFileupload().getContentType().contains("application/vnd.openxmlformats-officedocument.presentationml.slideshow") //.ppsx
              && !form.getFileupload().getContentType().contains("application/vnd.ms-powerpoint.addin.macroEnabled.12") //.ppam
              && !form.getFileupload().getContentType().contains("application/vnd.ms-powerpoint.presentation.macroEnabled.12") //.pptm
              && !form.getFileupload().getContentType().contains("application/vnd.ms-powerpoint.template.macroEnabled.12") //.potm
              && !form.getFileupload().getContentType().contains("application/vnd.ms-powerpoint.slideshow.macroEnabled.12") //.ppsm
            )){
            errors.rejectValue("fileupload", "file.type");
        }
        
    }
    
}

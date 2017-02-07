/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import md.cnam.helpdesk.config.AppConfig;
import md.cnam.helpdesk.entity.Files;
import md.cnam.helpdesk.service.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    FilesService fileService;
    
    @Autowired
    AppConfig appConfig;
    
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    //@ResponseBody
    public void get(
            @RequestParam(value = "id", required = true) Integer id,
            HttpServletResponse response
    ){
      
        InputStream is=null;
        Files file=fileService.findById(id);
        try {
            is = new FileInputStream(appConfig.getFilesLocation()+file.getUrl());
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename="+file.getFilename());
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import md.cnam.helpdesk.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

//@Configuration
@Component
public class AppConfig {
    //static public String baseUrl="http://localhost:8080/helpdesk3";
    private String configUrl;
    private String logUrl;
    private String ticketDataUrl;
    private String filesLocation;
    private String exchFilesLocation;
    private String knowledgebaseLocation;
    private Boolean authorizationRemote;
    @Autowired
    CustomerService customerService;
    
    public AppConfig() {
        //Resource ticketResource = customerService.getResource("classpath:config.conf");
    }
    
    public String getConfigUrl() {
        if (configUrl == null) {
            loadConfigFile();
        }
        return configUrl;
    }

    public String getLogUrl() {
        if (logUrl == null) {
            loadConfigFile();
        }
        return logUrl;
    }
    
    public String getTicketDataUrl() {
        if (ticketDataUrl == null) {
            loadConfigFile();
        }
        return ticketDataUrl;
    }

    public String getFilesLocation() {
        if (ticketDataUrl == null) {
            loadConfigFile();
        }
        return filesLocation;
    }
    
    public String getExchFilesLocation() {
        if (ticketDataUrl == null) {
            loadConfigFile();
        }
        return exchFilesLocation;
    }
    
    public String getKnowledgebaseLocation() {
        if (ticketDataUrl == null) {
            loadConfigFile();
        }
        return knowledgebaseLocation;
    }

    public Boolean isAuthorizationRemote() {
        if (ticketDataUrl == null) {
            loadConfigFile();
        }
        return authorizationRemote;
    }

    
    public void loadConfigFile() {
        Resource configResource = customerService.getResource("classpath:config.conf");
        Properties pconfig = new Properties();
        try {
            pconfig.load(new FileInputStream(configResource.getFile()));
        } catch (IOException ex) {
            Logger.getLogger(AppConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        authorizationRemote=Boolean.valueOf(pconfig.getProperty("authorization_remote"));
        Boolean productionState=Boolean.valueOf(pconfig.getProperty("productionState"));
        if (productionState==true) {
            configUrl=pconfig.getProperty("productionConfigPath");
            logUrl=pconfig.getProperty("productionLogPath");
            ticketDataUrl=pconfig.getProperty("productionTicketDataPath");
            filesLocation=pconfig.getProperty("productionFilesLocation");
            exchFilesLocation=pconfig.getProperty("productionExchFilesLocation");
            knowledgebaseLocation=pconfig.getProperty("productionKnowledgebaseLocation");
        }else{
            configUrl=pconfig.getProperty("debugConfigPath");
            logUrl=pconfig.getProperty("debugLogPath");
            ticketDataUrl=pconfig.getProperty("debugTicketDataPath");
            filesLocation=pconfig.getProperty("debugFilesLocation");
            exchFilesLocation=pconfig.getProperty("debugExchFilesLocation");
            knowledgebaseLocation=pconfig.getProperty("debugKnowledgebaseLocation");
        }
    }
    
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }
    
    /*
    @Bean
    ObjectMapper getJacksonMapper(){
        ObjectMapper mapper=new ObjectMapper();
        mapper.registerModule(new Hibernate4Module());
        return mapper;
    }
    */
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import org.springframework.stereotype.Component;

@Component
public class NotifyUserSession {
    private Integer id;
    private String username;
    private String password;
    private String category;
    private boolean isAuthorized;

    public NotifyUserSession() {
        isAuthorized=false;
    }
    
    public void destroy(){
        id=null;
        username=null;
        password=null;
        category=null;
        isAuthorized=false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

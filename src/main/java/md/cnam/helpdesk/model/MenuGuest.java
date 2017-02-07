/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.dao.MeniuImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("menuGuest")
public class MenuGuest {
    private List treeMenu;
    private String htmlMenu="";
    private String contextPath;
    @Autowired
    MeniuImpl meniuDao;

    public MenuGuest() {
        treeMenu=new LinkedList();
    }

    public List getTreeMenu() {
        return treeMenu;
    }

    public void setTreeMenu(List treeMenu) {
        this.treeMenu = treeMenu;
    }

    public String getHtmlMenu() {
        return htmlMenu;
    }

    public void setHtmlMenu(String htmlMenu) {
        this.htmlMenu = htmlMenu;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    public void clear(){
        treeMenu.clear();
        htmlMenu="";
        contextPath="";
    }
    
    public boolean isAccesiblePage(String reguestPath){
        if (reguestPath.contains("/resources/")) {
            return true;
        }
        for (Object ob : treeMenu) {
            if (((String)((Map)ob).get("path")).equals(reguestPath)) {
                return true;
            }
        }
        return false;
    }
}

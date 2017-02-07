/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MenuSession{
    private List treeMenu;
    private List resources;
    private String contextPath;
    private String rolName;
    private String htmlMenu="";
    //@Autowired MeniuService menuService;

    public MenuSession() {
        treeMenu=new LinkedList();
    }

    public List getTreeMenu() {
        return treeMenu;
    }

    public void setTreeMenu(List treeMenu) {
        this.treeMenu = treeMenu;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getHtmlMenu() {
        return htmlMenu;
    }

    public void setHtmlMenu(String htmlMenu) {
        this.htmlMenu = htmlMenu;
    }

    public List getResources() {
        return resources;
    }

    public void setResources(List resources) {
        this.resources = resources;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }
    
    public boolean isAccesiblePage(String reguestPath){
        //if (rolName.equals("Administrator")) {//Administratorul are acces la tot
            //return true;
        //}
        if (reguestPath.contains("/resources/")) {//daca path e resursa
            return true;
        }
        for (Object ob : treeMenu) {//daca este in sesiune
            if (((String)((Map)ob).get("path")).equals(reguestPath)) {
                return true;
            }
        }
        if (true) {//daca e resursa inregistrata in bd pentru rolul dat
            
        }
        return false;
    }
}

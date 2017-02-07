/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.dao.ClDiviziuniImpl;

/**
 *
 * @author Admin
 */
public class JqxTreeGridModel {
    private String filterslength;
    private int pagenum;
    private int pagesize;
    private List data;
    private Map criterya;
    
    private int level=1;
    private int levels=10;
    private int parent=0;

    public JqxTreeGridModel() {
        criterya=new HashMap<String,Object>();
        data=new ArrayList<HashMap<String,Object>>();
    }
    
    public void autocompleteParameter(HttpServletRequest request){
        
    }
    
    public void loadData(List criterya){
        data=criterya;//diviziuniDao.listByCriterya(criterya)
        
        /*
        if (level<=levels) {
            criterya.put("parentId", parent);
            Object result=diviziuniDao.listByCriterya(criterya);
            if (result!=null) {
                data=(List)result;
                for (Object item : data) {
                    Map row = (Map)item;
                    Logger.getLogger(JqxTreeGridModel.class.getName()).log(Level.INFO, row.toString());
                    parent=(int)row.get("idDiviziune");
                    criterya.put("parentId", parent);
                    Object childrens=diviziuniDao.listByCriterya(criterya);
                    if (childrens!=null) {
                        List list=(List)childrens;
                        row.put("children", list.get(0));
                    }
                    
                    level++;
                }
            }
        }
        */
    }

    public String getFilterslength() {
        return filterslength;
    }

    public void setFilterslength(String filterslength) {
        this.filterslength = filterslength;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Map getCriterya() {
        return criterya;
    }

    public void setCriterya(Map criterya) {
        this.criterya = criterya;
    }

    public void addCriterya(String itemName,Object item){
        criterya.put(itemName, item);
    }

}


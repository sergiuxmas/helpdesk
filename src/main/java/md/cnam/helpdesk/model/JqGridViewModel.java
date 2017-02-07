/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.config.ObjectMapperM4;
import org.hibernate.SQLQuery;

/**
 *
 * @author Admin
 */
public class JqGridViewModel {
    private String _search;
    private String nd;
    private int page;
    private int total=0;
    private int records;
    private String sidx;
    private int rowsx;
    private String sord;
    private List rows;
    private Map<String,Object> search;
    private String searchField;
    private String searchString;

    public JqGridViewModel() {
        this.page=1;
        this.sidx="id";
        this.sord="asc";
        this.rows=new ArrayList();
        this.search=new HashMap<>();
    }
    
     public Map getCriterya(){
        Map criterya=new HashMap<String,Object>();
        criterya.put("_search", _search);
        criterya.put("page", page);
        criterya.put("total", total);
        criterya.put("records", records);
        criterya.put("sord", sord);
        criterya.put("sidx", sidx);
        criterya.put("from", getLimitFrom());
        criterya.put("search", search);
        return criterya;
    }
     
    public void autocompleteParameter(HttpServletRequest request, String[] searchFields){
        for (String field : searchFields) {
            if (request.getParameter(field)!=null && !request.getParameter(field).isEmpty())
                search.put(field, request.getParameter(field));
        }
        autocompleteParameter(request);
    }
    
    public void autocompleteParameter(HttpServletRequest request){
       setRowsx(request.getParameter("rows"));
       setSord(request.getParameter("sord"));
       setSidx(request.getParameter("sidx"));
       setPage(request.getParameter("page"));
       setSearchField(request.getParameter("searchField"));
       setSearchString(request.getParameter("searchString"));
        
        //cautarea din navbar
        if (request.getParameter("filters")!=null && !request.getParameter("filters").isEmpty()) {
            Map<String,Object> map = new HashMap<String,Object>();
            
            ObjectMapperM4 mapper=new ObjectMapperM4();
            try {
                map=mapper.readValue(request.getParameter("filters"), HashMap.class);
                List<Map> rules=(ArrayList<Map>)map.get("rules");
                for (Map field : rules) {
                    search.put((String)field.get("field"), field.get("data"));
                }
                //Logger.getLogger(JqGridViewModel.class.getName()).log(Level.INFO, search.toString());
            } catch (IOException ex) {
                Logger.getLogger(JqGridViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (searchField!=null && !searchField.isEmpty()) {
            search.put(request.getParameter("searchField"), request.getParameter("searchString"));
        }
    }
    
    public String getSearch() {
        return _search;
    }
    
    public String getSearchQuery() {
        return "";
    }

    public void setSearch(String _search) {
        if (_search!=null && !_search.isEmpty()) {
            this._search = _search;
        }
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        if (nd!=null && !nd.isEmpty()) {
            this.nd = nd;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(String page) {
        if (page!=null && !page.isEmpty()) {
            this.page = Integer.parseInt(page);
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        if (sidx!=null && !sidx.isEmpty()) {
            this.sidx = sidx;
        }
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        if (sord!=null && !sord.isEmpty()) {
            this.sord = sord;
        }
    }

    public List getRows() {
        return rows;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        
        this.records = records;
    }

    public int getRowsx() {
        return rowsx;
    }

    public void setRowsx(String rowsx) {
        if (rowsx!=null && !rowsx.isEmpty()) {
            this.rowsx = Integer.parseInt(rowsx);
        }else{
            this.rowsx=1;
        }
    }

    public void setRows(List rows) {
        this.records=rows.size();
        if (this.rowsx!=0) {
            this.total=Math.round(this.records/this.rowsx)+1;
        }

        //Limit, paginarea e la nivel de aplicatie, nu baza de date
        int count=0;
        for (Object row : rows) {
            if (count<getLimitFrom()) {
                count++;
                continue;
            }
            
            if (count>getLimitTo()) {
                break;
            }
            
            this.rows.add(row);
            
            /*
            if (count>=getLimitFrom() && count<=getLimitTo()) {
                this.rows.add(row);
            }
            */
            count++;
        }
    }
    
    public int getLimitFrom(){
        return (page-1)*rowsx;
    }
    
    public int getLimitTo(){
        return this.records;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        if (searchField!=null && !searchField.isEmpty()) {
            this.searchField = searchField;
        }
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        if (searchString!=null && !searchString.isEmpty()) {
            this.searchString = searchString;
        }
    }

    public Map<String, Object> getSearchMap() {
        return search;
    }

}

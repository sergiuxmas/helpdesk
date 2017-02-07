/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;

public class TreeUtil {
    private List<Map> array;
    public List<Map> arrAppliedModles;
    public Boolean showAppliedModles;
    public Boolean hideAppliedModles;
    public String contextPath="";

    //public Integer role;
    //public Integer module;
    public TreeUtil() {
    }

    public TreeUtil(List<Map> array) {
        this.array = array;
    }
    
    public String treeArrayToSelectElem(int id, int level) {
        String select = "";
        if (level==10) {//control suplimentar
            return select;
        }
        //System.out.println("id="+id+" | finded="+findAppliedModule(id)+" | arrAppliedModles="+arrAppliedModles);
        //if (findAppliedModule(id)) {
            //System.out.println("excludere "+id);
            //select +="<option value='hz' >hz("+id+")</option>";
            //return select;
        //}
        
        List<Integer> findedChilds=findChilds(id);
        if (findedChilds!=null && findedChilds.size() != 0) {
            for (Integer itemId : findedChilds) {
                Map itemChild=findItem(itemId);
                String selectTree=treeArrayToSelectElem((int)itemChild.get("id"), level+1);
                //System.out.println("hz "+itemChild.get("id"));
                if (
                     (level==0)||
                     (hideAppliedModles!=null && hideAppliedModles && !findAppliedModule((int)itemChild.get("id"))) ||
                     (showAppliedModles!=null && showAppliedModles && findAppliedModule((int)itemChild.get("id")))
                   ) {
                    //System.out.println("excludere "+(int)itemChild.get("id")+" | string="+selectTree);
                    select += "<option title='" + itemChild.get("name") + "' value='" + itemChild.get("id") + "' " + ((level==0 || !selectTree.isEmpty())?" disabled ":"") + " >" + getSpace(level) + itemChild.get("name") + "</option>";
                }
                
                select +=selectTree;
            }
        }
        //$level++;
        return select;
    }
    
    public String renderMenu(int id, int level) {
        String select = "";
        if (level == 10) {//control suplimentar
            return select;
        }

        if (level == 0) {
            select += "<div class=\"container-menu\">"
                    + "<div class=\"col-sm-12 col-md-12\">"
                    + "<div id=\"accordion\" class=\"panel-group\">";
        }
        List<Integer> findedChilds = findChilds(id);
        if (findedChilds != null && findedChilds.size() != 0) {
            for (Integer itemId : findedChilds) {
                Map itemChild = findItem(itemId);
                String selectTree = renderMenu((int) itemChild.get("id"), level + 1);

                if (!selectTree.isEmpty()) {
                    //if (((String)itemChild.get("type")).equals("menu")) {
                        select += "<div class=\"panel panel-default\">";
                        select += renderGroup(itemChild)+ "<div id=\"collapse_" + itemChild.get("id") + "\" class=\"panel-collapse collapse in\"><table class=\"table\">" + selectTree+"</table></div>";
                        select += "</div>";
                    //}
                } else {
                    if (
                     (hideAppliedModles!=null && hideAppliedModles && !findAppliedModule((int)itemChild.get("id"))) ||
                     (showAppliedModles!=null && showAppliedModles && findAppliedModule((int)itemChild.get("id")))
                   )
                    select += renderRow(itemChild);
                }
            }
        }
        if (level == 0) {
            select += "</div></div></div>";
        }
        return select;
    }
    
    public String renderGroup(Map ob) {
        return ""
                + " <div class=\"panel-heading\">"
                + "  <h4 class=\"panel-title\">"
                + "    <a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse_" + ob.get("id") + "\">" + ob.get("name") + "</a>"
                + "  </h4>"
                + " </div>";
    }

    public String renderRow(Map ob) {
        if (ob.get("type")==null || ((String)ob.get("type")).equals("menu")) {
            return ""
                + ""
                + "  <tr>"
                + "    <td>"
                + "      <a href=\"" + contextPath+"/"+ob.get("path") + "\">" + ob.get("name") + "</a>"
                + "    </td>"
                + "  </tr>"
                + ""
                + "";
        }
        return "";
    }
    
    public List treeArray(int id, int level) {
        List select = new LinkedList();
        if (level == 10) {//control suplimentar
            return null;
        }
        List<Integer> findedChilds = findChilds(id);
        if (findedChilds != null && findedChilds.size() != 0) {
            for (Integer itemId : findedChilds) {
                Map itemChild = findItem(itemId);
                List selectTree = treeArray((int) itemChild.get("id"), level + 1);
                //System.out.println("hz "+itemChild.get("id"));
                //System.out.println("excludere "+(int)itemChild.get("id")+" | string="+selectTree);
                select.addAll(selectTree);
            }
        }
        return select;
    }
    
    public static String getSpace(int order) {
        String space = "";
        for (int i = 0; i < order; i++) {
            space += "&nbsp;&nbsp;&nbsp;";
        }
        return space;
    }
    
    public List<Integer> findChilds(int id) {
        List<Integer> childs=new LinkedList();
        for (Map map : array) {
            if ((int)map.get("parentId")==id) {
                childs.add((int)map.get("id"));
            }
        }
        return childs;
    }
    
    public Map findItem(int id){
        for (Map map : array) {
            if ((int)map.get("id")==id) {
                return map;
            }
        }
        return new LinkedMap();
    }
    
    public boolean findAppliedModule(int id){
        for (Map map : arrAppliedModles) {
            if ((int)map.get("id")==id) {
                return true;
            }
        }
        return false;
    }
}

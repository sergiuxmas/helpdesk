/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.dao.ClDiviziuniImpl;
import md.cnam.helpdesk.entity.ClDiviziuni;
import md.cnam.helpdesk.model.JqxTreeGridModel;
import md.cnam.helpdesk.util.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClDiviziuniService implements GenericServiceIntf<ClDiviziuni>{

    @Autowired
    private ClDiviziuniImpl diviziuniDao;

    @Override
    public void save(ClDiviziuni t) {
        diviziuniDao.save(t);
    }

    @Override
    public ClDiviziuni findById(long id) {
        return diviziuniDao.findById(id);
    }

    @Override
    public void update(ClDiviziuni t) {
        diviziuniDao.update(t);
    }

    @Override
    public void delete(ClDiviziuni t) {
        diviziuniDao.delete(t);
    }

    @Override
    public List<ClDiviziuni> list() {
        return diviziuniDao.list();
    }
    
    public void fillGridModel(HttpServletRequest request,JqxTreeGridModel gridModel){
        gridModel.autocompleteParameter(request);
        //gridModel.addCriterya("parentId", 0);
        gridModel.loadData(diviziuniDao.listByCriterya(gridModel.getCriterya()));
        //gridModel.setData(diviziuniDao.listTreeByCriterya(gridModel.getCriterya()));
    }
    
    public List<ClDiviziuni> listByParent(int parentId){
        return diviziuniDao.listByParent(parentId);
    }
    
    public List listTree(int parentId, int level){
        int index=1;
        List<Object> diviziuniTreeList=new ArrayList<>();
        List<ClDiviziuni> diviziuniList=listByParent(parentId);
        
        if (index==level) {
            return diviziuniTreeList;
        }
        
        for (ClDiviziuni diviziune : diviziuniList) {
            index++;
            Integer idDiviziuneItem=diviziune.getIdDiviziune();
            
            if (listTree(idDiviziuneItem,index).size()==0) {
                diviziuniTreeList.add(diviziune);
                //System.out.println(diviziune.getNume());
            }else{
                diviziuniTreeList.add(diviziune);
                diviziuniTreeList.add(listTree(idDiviziuneItem,index));
                //System.out.println("list");
            }
        }
        return diviziuniTreeList;
    }
    
    public String renderListTreeCombobox(List lista,int level,Integer activId){
        String html="";
        for (Object object : lista) {
            if (object instanceof ArrayList) {
                List obList=(ArrayList)object;
                //html+="<optgroup class=\"delimiter\">";
                //System.out.println("(");
                html+=renderListTreeCombobox(obList,level+1,activId);
                html+="</optgroup>";
                //System.out.println(")");
            }else{
                ClDiviziuni diviziune=(ClDiviziuni)object;
                html+="<option value=\""+diviziune.getIdDiviziune()+"\"";
                if (diviziune.getIdDiviziune()==activId) {
                    html+=" selected=\"selected\" ";
                }
                html+=">"; 
                if (level==0)html+="<b>";
                html+=Space.getHtmlSpace(level)+diviziune.getFullname();
                if (level==0)html+="</b>";
                html+="</option>";
                //System.out.println(diviziune.getNume());
            }
        }
        return html;
    }
    
    public String renderListTreeCombobox(List lista, Integer activId){
        return renderListTreeCombobox(lista, 0, activId);
    }
    public String renderListTreeCombobox(List lista){
        return renderListTreeCombobox(lista, 0, null);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.dao.ExchThemesImpl;
import md.cnam.helpdesk.entity.ExchThemes;
import md.cnam.helpdesk.model.JqGridViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchThemesService implements GenericServiceIntf<ExchThemes> {
    @Autowired private ExchThemesImpl exchThemeDao;
    
    @Override
    public void save(ExchThemes t) {
        exchThemeDao.save(t);
    }

    @Override
    public ExchThemes findById(long id) {
        return exchThemeDao.findById(id);
    }

    @Override
    public void update(ExchThemes t) {
        exchThemeDao.update(t);
    }

    @Override
    public void delete(ExchThemes t) {
        exchThemeDao.delete(t);
    }

    @Override
    public List<ExchThemes> list() {
        return exchThemeDao.list();
    }
    
    public void fillUserGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        //gridModel.setSidx("data_solicitarii");
        //gridModel.autocompleteParameter(request,new String[]{"data_solicitarii","data_solicitarii_from","data_solicitarii_to","statut","domeniu","prioritate","diviziune","user","user_executor_name","descriere"});
        //gridModel.setRows(exchThemeDao.gridlist(gridModel.getCriterya()));
    }
    
}

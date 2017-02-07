/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.dao.ExecutorHistoryImpl;
import md.cnam.helpdesk.dao.TiketDao;
import md.cnam.helpdesk.entity.ClDiviziuni;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.entity.ExecutorHistory;
import md.cnam.helpdesk.entity.Solicitare;
import md.cnam.helpdesk.model.JqGridViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class TiketService implements GenericServiceIntf<Solicitare>{
    @Autowired
    TiketDao tiketDao;
    @Autowired
    ExecutorHistoryImpl execHistoryDao;
    @Autowired
    ClUseriService userService;

    @Override
    public void save(Solicitare t) {
        tiketDao.save(t);
    }
    
    public void save(Solicitare t,ExecutorHistory eh) {
        int idNewTiket=tiketDao.saveAndGetId(t);
        eh.setIdSolicitare(idNewTiket);
        if ((Object)eh.getUserExecutor()!=null) {
            execHistoryDao.save(eh);
        }
    }

    @Override
    public Solicitare findById(long id) {
        return tiketDao.findById(id);
    }

    @Override
    public void update(Solicitare t) {
        tiketDao.update(t);
    }

    @Override
    public void delete(Solicitare t) {
        tiketDao.delete(t);
    }

    @Override
    public List<Solicitare> list() {
        return tiketDao.list();
    }
    
    public void fillAdminGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        gridModel.setSidx("data_solicitarii");
        gridModel.autocompleteParameter(request,new String[]{"data_solicitarii","data_solicitarii_from","data_solicitarii_to","domeniu","prioritate","diviziune","user","descriere"});
        gridModel.setRows(tiketDao.listForAdmin(gridModel.getCriterya()));
    }
    
    public void fillUserDSIGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        gridModel.setSidx("data_solicitarii");
        gridModel.autocompleteParameter(request,new String[]{"data_solicitarii","data_solicitarii_from","data_solicitarii_to","statut","domeniu","prioritate","diviziune","user","descriere"});
        gridModel.setRows(tiketDao.listForUserDSI(gridModel.getCriterya()));
    }
    
     public void fillUserGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        gridModel.setSidx("data_solicitarii");
        gridModel.autocompleteParameter(request,new String[]{"data_solicitarii","data_solicitarii_from","data_solicitarii_to","statut","domeniu","prioritate","diviziune","user","user_executor_name","descriere"});
        gridModel.setRows(tiketDao.listForUser(gridModel.getCriterya()));
    }
    
    public List responsableTikets(int idTiket){
        return execHistoryDao.responsableTikets(idTiket);
    }
    
    public Map getTiket(int id){
        return tiketDao.getTiket(id);
    }
    
    public int updateOpenedTiket(Solicitare sol){
        return tiketDao.updateOpenedTiket(sol);
    }
    
    public int updateStatusTiket(Solicitare sol){
        return tiketDao.updateStatusTiket(sol);
    }
    
    public ExecutorHistory defineDSIResponsible(Solicitare sol){
        ExecutorHistory execHistory=new ExecutorHistory();
        return execHistory;
    }
    
    public int editDescriptionTiket(int id, String description){
        return tiketDao.editDescriptionTiket(id,description);
    }

}

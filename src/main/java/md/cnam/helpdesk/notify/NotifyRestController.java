/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.notify;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.dao.NotifyTiketDao;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.service.ClUseriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotifyRestController {
    @Autowired
    ClUseriService userService;
    @Autowired
    NotifyTiketDao tiketDao;
    
    @RequestMapping(value = "/tiket/list", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map>> listAllUsers(
            //@PathVariable("idUser") long idUser
            @RequestParam(value = "idUser", required = true) Integer idUser
    ) {
        ClUseri user=userService.findById(idUser);
        if(user==null){
            return new ResponseEntity(null, HttpStatus.OK);//You many decide to return HttpStatus.NOT_FOUND
        }
        //List<Map> tiketList = tiketDao.tiketList(user);
        //if(tiketList==null){
            //return new ResponseEntity<List<Map>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        //}
        //return new ResponseEntity<List<Map>>(tiketList, HttpStatus.OK);
        return null;
    }
    
    @RequestMapping(value = "/getUser", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getUser(
            @RequestParam(value = "username", required = true) String username
    ) {
        Map userData=tiketDao.getUserIdAndCat(username);
        if(userData==null){
            return new ResponseEntity(null, HttpStatus.OK);//You many decide to return HttpStatus.NOT_FOUND
        }else return new ResponseEntity<Map>(userData, HttpStatus.OK);
    }
}

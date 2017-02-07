/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.util;

/**
 *
 * @author Admin
 */
public class Space {
    static public String delimiter="&nbsp;&nbsp;&nbsp;";
    static public String getHtmlSpace(int count){
        String space="";
        for (int i = 0; i < count; i++) {
            space+=delimiter;
        }
        return space;
    }
    
}

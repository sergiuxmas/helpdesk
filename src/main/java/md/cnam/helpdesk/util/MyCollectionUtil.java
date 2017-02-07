/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.util;

import java.util.List;


public class MyCollectionUtil {
    public static boolean findInArray(String[] arr,String val){
        for (String str : arr) {
            if (val.contains("/resources/") || str.equals(val)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean findInArray(List<String> arr,String val){
        for (String str : arr) {
            if (val.contains("/resources/") || str.equals(val)) {
                return true;
            }
        }
        return false;
    }
}

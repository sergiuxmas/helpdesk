package md.cnam.helpdesk.model;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class ChangePasswordForm {
    @NotEmpty
    private String parolaCurenta;
    
    @NotEmpty
    private String parolaNoua;
    
    @NotEmpty
    @Size(min = 4, max = 100)
    private String confirmareaParolei;
    
    private boolean isModified;

    public ChangePasswordForm() {
        isModified=false;
    }

    public String getParolaCurenta() {
        return parolaCurenta;
    }

    public void setParolaCurenta(String parolaCurenta) {
        this.parolaCurenta = parolaCurenta;
    }

    public String getParolaNoua() {
        return parolaNoua;
    }

    public void setParolaNoua(String parolaNoua) {
        this.parolaNoua = parolaNoua;
    }

    public String getConfirmareaParolei() {
        return confirmareaParolei;
    }

    public void setConfirmareaParolei(String confirmareaParolei) {
        this.confirmareaParolei = confirmareaParolei;
    }

    public boolean isIsModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }
    
    public String getMessage(){
        if (isModified) {
            return "Parola a fost modificata cu succes";
        }
        return "Parola n-a fost modificata";
    }
}

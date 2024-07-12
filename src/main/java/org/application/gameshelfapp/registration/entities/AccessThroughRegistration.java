package org.application.gameshelfapp.registration.entities;


import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.exception.CheckFailedException;

import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;

public class AccessThroughRegistration extends Access {
    private int codeGenerated;

    private boolean isValid;

    public AccessThroughRegistration(String username, String email, String password, String typeOfUser){
        super(username, email, password, typeOfUser);
    }

    public void generateCode(){
        SecureRandom secureRandom = new SecureRandom();
        this.codeGenerated = secureRandom.nextInt(1000000);
        this.codeGenerated = Math.abs(this.codeGenerated);
        this.isValid = true;
    }
    public void setUserType(String userType) throws CheckFailedException {
        if(!userType.equals("Customer") && !userType.equals("Seller") && !userType.equals("Admin")) throw new CheckFailedException("Wrong user");
    }
    public void checkCode(int insertedCode) throws CheckFailedException {
        if(this.codeGenerated != insertedCode || !isValid){
                throw new CheckFailedException("Code is incorrect");
            }
    }
    public int getCodeGenerated(){
        return this.codeGenerated;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    public void validationTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                AccessThroughRegistration.this.setValid(false);
            }
        };
        timer.schedule(task, 60000);
    }
}

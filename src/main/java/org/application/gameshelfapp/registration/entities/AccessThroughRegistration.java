package org.application.gameshelfapp.registration.entities;


import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import java.security.SecureRandom;

public class AccessThroughRegistration extends Access {
    private int codeGenerated;

    public AccessThroughRegistration(String username, String email, String password, String typeOfUser){
        super(username, email, password, typeOfUser);
    }

    public void generateCode(){
        SecureRandom secureRandom = new SecureRandom();
        this.codeGenerated = secureRandom.nextInt(1000000);
        this.codeGenerated = Math.abs(this.codeGenerated);
    }
    public void checkCode(int insertedCode) throws CheckFailedException {
        if(this.codeGenerated != insertedCode){
                throw new CheckFailedException("Code is incorrect");
            }
    }
    public int getCodeGenerated(){
        return this.codeGenerated;
    }
}

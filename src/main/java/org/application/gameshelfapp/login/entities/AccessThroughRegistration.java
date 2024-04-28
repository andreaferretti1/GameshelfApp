package org.application.gameshelfapp.login.entities;


import org.application.gameshelfapp.login.exception.CheckFailedException;
import java.security.SecureRandom;

public class AccessThroughRegistration extends Access {

    private int codeGenerated;

    public AccessThroughRegistration(String username, String email, String password, String typeOfUser){
        super(username, email, password, typeOfUser);
    }

    public String getMessageToSend(){
        SecureRandom secureRandom = new SecureRandom();
        this.codeGenerated = secureRandom.nextInt(1000000);
        this.codeGenerated = Math.abs(this.codeGenerated);
        return "Your code is " + this.codeGenerated;
    }

    public void checkCode(int insertedCode) throws CheckFailedException {
        if(this.codeGenerated != insertedCode){
                throw new CheckFailedException("Code is incorrect");
            }
    }

    public void checkAccount(Access access) throws CheckFailedException{

        if(access == null) return;

        String username = access.getUsername();
        String email = access.getEmail();

        if(this.username.equals(username)){
            throw new CheckFailedException("Username already used");
        }
        else if(this.email.equals(email)){
            throw new CheckFailedException("Email already used");
        }
    }

    public int getCodeGenerated(){
        return this.codeGenerated;
    }
}

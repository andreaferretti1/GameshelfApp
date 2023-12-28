package org.application.gameshelfapp.login.entities;


import java.security.SecureRandom;

public class AccessThroughRegistration extends Access {

    private String username;
    private Gmailer checkGmail;
    private int codeGenerated;
    private final SecureRandom secureRandom;


    public AccessThroughRegistration(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.encoder = new MD5Encoder(password);
        this.secureRandom = new SecureRandom();
        try {
            this.checkGmail = new Gmailer();
        } catch (Exception e){
            System.exit(1);
        }
        this.sendCheckEmail();
    }

    public void sendCheckEmail(){
        String emailToSend = "Dear client,\n" +
                "your authentication code is: ";
        this.codeGenerated = this.secureRandom.nextInt();
        emailToSend += this.codeGenerated;
        try {
            checkGmail.sendMail("authentication code", emailToSend, this.email);
        } catch(Exception e){
            System.exit(1);
        }
    }

    @Override
    public void checkCorrectness(int insertedCode){
        if(this.codeGenerated != insertedCode){
                //lancia eccezione
            }
    }
}

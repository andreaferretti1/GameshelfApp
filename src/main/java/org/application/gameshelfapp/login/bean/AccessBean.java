package org.application.gameshelfapp.login.bean;

import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessBean {

    protected String emailBean;
    protected String passwordBean;

    protected String emailRegex =  "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    protected String usernameRegex = "[a-zA-Z]+[0-9._]*";

    public void setEmailBean(String emailBean) throws SyntaxErrorException {
        this.checkSyntax(emailBean, emailRegex, "email");
        this.emailBean = emailBean;
    }

    public void setPasswordBean(String passwordBean) throws SyntaxErrorException, NullPasswordException {
        if(passwordBean == null) throw new NullPasswordException("You should give me a password");
        if(passwordBean.isEmpty()) throw new SyntaxErrorException("You should enter a password");
        this.passwordBean = passwordBean;
    }

    public String getEmailBean(){
        return this.emailBean;
    }

    public String getPasswordBean(){
        return this.passwordBean;
    }

    protected void checkSyntax(String s, String syntaxRegex, String subject) throws SyntaxErrorException {
        Pattern p = Pattern.compile(syntaxRegex);
        Matcher m = p.matcher(s);
        boolean b = m.matches();
        if(!b && subject.equals("email")) {
            throw new SyntaxErrorException("Invalid syntax of email address");
        }  else if(!b && subject.equals("username")){
            throw new SyntaxErrorException("Invalid syntax of username. You can use numbers, letters, and . or _");
        }
    }

}

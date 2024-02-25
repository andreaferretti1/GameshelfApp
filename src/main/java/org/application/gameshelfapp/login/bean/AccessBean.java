package org.application.gameshelfapp.login.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessBean {

    private final String emailBean;
    private final String passwordBean;

    protected String emailRegex =  "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    protected String pwdRegex = "[a-zA-Z0-9._%&?^!]+";
    protected String usernameRegex = "[a-zA-Z0-9._]+";

    public AccessBean(String emailBean, String passwordBean) throws SyntaxErrorException {

        this.checkSyntax(emailBean, emailRegex, "email");
        this.emailBean = emailBean;
        this.checkSyntax(passwordBean, pwdRegex, "password");
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
        } else if(!b && subject.equals("password")){
            throw new SyntaxErrorException("Invalid syntax of password. You can use letters, numbers, and .,_,%,&,?,^,!");
        } else if(!b && subject.equals("username")){
            throw new SyntaxErrorException("Invalid syntax of username. You can use numbers, letters, and . or _");
        }
    }

}

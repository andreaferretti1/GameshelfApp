package org.application.gameshelfapp.login.boundary;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import org.application.gameshelfapp.login.exception.GmailException;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Set;

public class GoogleBoundary {
    private final Gmail service;
    private String email;
    private String subject;
    private String messageToSend;
    public GoogleBoundary() throws GmailException {
        final NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new GmailException("Couldn't send email");
        }
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        this.service = new Gmail.Builder(httpTransport, jsonFactory, this.getCredentials(httpTransport, jsonFactory))
                .setApplicationName("Gameshelfapp")
                .build();
    }

    private Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory) throws GmailException{
        // Load client secrets.
        InputStream in = GoogleBoundary.class.getResourceAsStream("/org/application/gameshelfapp/client_secret_862393017889-3e1kmgmdq3jmgltkc0rg0g4svvsl7lej.apps.googleusercontent.com.json");
        if (in == null) {
            throw new GmailException("Resource not found: " + "/org/application/gameshelfapp/client_secret_862393017889-3e1kmgmdq3jmgltkc0rg0g4svvsl7lej.apps.googleusercontent.com.json");
        }

        try {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, clientSecrets, Set.of(GmailScopes.GMAIL_SEND))
                    .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (IOException e) {
            throw new GmailException("Couldn't send email");
        }
    }

    private void sendMail() throws GmailException{

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        try {
            email.setFrom(new InternetAddress("fer.andrea35@gmail.com"));
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(this.email));
            email.setSubject(this.subject);
            email.setText(this.messageToSend);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
            Message message = new Message();
            message.setRaw(encodedEmail);
            this.service.users().messages().send("me", message).execute();
        } catch (MessagingException | IOException e) {
            throw new GmailException("Couldn't send mail");
        }
    }
    public void sendReceiptMessage(String gameName, int copies, float totalPrice, String customerEmail) throws GmailException{
        this.messageToSend = "You have bought " + copies + " of " + gameName + " for " + totalPrice;
        this.subject = "Receipt";
        this.email = customerEmail;
        this.sendMail();
    }

    public void sendNewSaleMessage(String userEmail, String gameName, int quantity, float totalPrice) throws GmailException{
        this.messageToSend = userEmail + " bought " + quantity + " of " + gameName + " for " + totalPrice;
        this.subject = "Videogame bought";
        this.email = "fer.andrea35@gmail.com";
        this.sendMail();
    }

    public void sendSaleConfirmationMessage(String customerEmail, String gameName) throws GmailException{
        this.messageToSend = "Your " + gameName + " order has been confirmed.";
        this.subject = "Delivery confirmed";
        this.email = customerEmail;
        this.sendMail();
    }

    public void sendCheckCodeMessage(String email, int code) throws GmailException{
        this.messageToSend = "Your code is " + code + ".";
        this.email = email;
        this.subject = "Check email";
        this.sendMail();
    }

    public void sendMailToRandomCustomer(String customerEmail, String gameName, String platform) throws GmailException{
        this.messageToSend = String.format("New Sale: %s for %s", gameName, platform);
        this.subject = "New sale";
        this.email = customerEmail;
        this.sendMail();
    }
}

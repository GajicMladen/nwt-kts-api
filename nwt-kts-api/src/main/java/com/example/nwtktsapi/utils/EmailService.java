package com.example.nwtktsapi.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

	private Email EMAIL = new Email("gofishingteam7@gmail.com");
	
	@Value("${SENDGRID_API_KEY}")
	private String apiKey;
	
	public void sendConfirmationEmail(User user) {
		Email to = new Email(user.getEmail());
		String body = "Poštovani " + user.getName() + ",\n";
		body += "Primili smo Vaš zahtev za registrovanje na RideShare! Kako biste aktivirali Vaš nalog, ";
		body += "kliknite na ovaj link: http://localhost:8080/api/reg/activate/" + user.getId().toString();
		Content content = new Content("text/plain", body);
		Mail mail = new Mail(EMAIL, "RideShare Registracija", to, content);
		
		SendGrid sg = new SendGrid(apiKey);
		Request request = new Request();
	    try {
	        request.setMethod(Method.POST);
	        request.setEndpoint("mail/send");
	        request.setBody(mail.build());
	        Response response = sg.api(request);
	        System.out.println(response.getStatusCode());
	        System.out.println(response.getBody());
	        System.out.println(response.getHeaders());
	      } catch (IOException ex) {
	        ex.printStackTrace();
	      }
	}
}

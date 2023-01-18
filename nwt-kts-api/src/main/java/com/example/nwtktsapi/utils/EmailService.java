package com.example.nwtktsapi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.ResetPassword;
import com.example.nwtktsapi.model.User;


@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final String CONFIRMATION_SUBJECT = "RideShare - Aktivacija naloga";
	private final String RESET_PASSWORD_SUBJECT = "RideShare - Promena lozinke";

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("rideshare2023@outlook.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
		System.out.println("Email sent successfully!");
	}
	
	public void sendConfirmationEmail(User user) {
		String body = "Poštovani " + user.getName() + ",\n";
		body += "Primili smo Vaš zahtev za registrovanje na RideShare! Kako biste aktivirali Vaš nalog, ";
		body += "kliknite na ovaj link: http://localhost:8080/api/reg/activate/" + user.getId().toString();
		sendSimpleMessage(user.getEmail(), CONFIRMATION_SUBJECT, body);
	}
	
	public void sendResetPasswordEmail(ResetPassword resetPassword) {
		String body = "Primili smo Vaš zahtev za promenu lozinke. Da biste promenili lozinku, molimo Vas da pritisnete na sledeći link: ";
		body += "http://localhost:4200/resetPassword/" + resetPassword.getToken() + "\n";
		body += "Vodite računa o tome da je link aktivan 30 minuta od prijema maila. \n";
		body += "Ukoliko niste poslali zahtev za promenu lozinke, molimo Vas da ignorišete ovu poruku.";
		sendSimpleMessage(resetPassword.getEmail(), RESET_PASSWORD_SUBJECT, body);
	}
}

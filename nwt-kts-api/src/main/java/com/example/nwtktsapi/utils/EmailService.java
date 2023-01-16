package com.example.nwtktsapi.utils;

import com.example.nwtktsapi.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.User;

import java.util.List;


@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final String CONFIRMATION_SUBJECT = "RideShare - Aktivacija naloga";
	private final String SPLIT_FARE_SUBJECT = "RideShare - Plaćanje vožnje";
	private final String NEW_RIDE_SUBJECT = "RideShare - Nova vožnja";

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

	public void sendSplitFareAgreement(String email, List<String> locations, float price, Long id) {
		String body = "Poštovani, \n";
		body += "Dodati ste kao jedan od platiša za vožnju na relaciji: \n";
		body += String.join(" - ", locations) + "\n";
		body += "Iznos koji će vam biti naplaćen je: " + price + " dinara\n";
		body += "Klikom na link ispod potvrđujete da ste učesnik ove vožnje i da vam možemo naplatiti vožnju: \n";
		body += "Link: http://localhost:8080/api/ride/agree/" + id.toString();
		sendSimpleMessage(email, SPLIT_FARE_SUBJECT,body);
	}

	public void sendNewRideToDriver(Driver driver, List<String> locations, float price) {
		String body = "Poštovani " + driver.getName() + ",\n";
		body = "Izabrani ste da budete vozač na relaciji: \n";
		body += String.join(" - ", locations) + "\n";
		body += "Cena ove vožnje iznosi: " + price + " dinara.";
		sendSimpleMessage(driver.getEmail(), NEW_RIDE_SUBJECT, body);
	}
}

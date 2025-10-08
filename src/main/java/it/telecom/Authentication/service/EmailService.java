package it.telecom.Authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import it.telecom.Authentication.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;



@Service
public class EmailService {
	
	@Autowired
	public UserRepository userRepository;

	@Autowired
	public JavaMailSender mailSender;

	@Autowired
	public TemplateEngine templateEngine;
	
	public void textMail(String fromEmail, String toEmail, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);;
		
		mailSender.send(message);
		
		
	}
	
	public void htmlMail(String fromEmail, String toemail, String subject, String body) throws Exception {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		helper.setFrom(fromEmail);
		helper.setTo(toemail);
		helper.setSubject(subject);
		helper.setText(body, true);
		
		mailSender.send(message);
		

	}
	
	public void templateMail(String fromEmail, String toemail, String subject, String filename) throws Exception {
		
		Context context = new Context();
		context.setVariable("name", "siva");
		String emailbody = templateEngine.process(filename, context); 
		
		
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		helper.setFrom(fromEmail);
		helper.setTo(toemail);
		helper.setSubject(subject);
		helper.setText(emailbody, true);
		
		mailSender.send(message);
		
		
		
}
	

}

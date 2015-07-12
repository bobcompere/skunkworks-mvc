package net.fourstrategery.cloud.email;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;
	
	private String DEFAULT_FROM = "fourstrategery@gmail.com";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void sendMail(MailInfo info) {
			try {
			MimeMessage mime = mailSender.createMimeMessage();
			
			MimeMessageHelper email = new MimeMessageHelper(mime);
			email.setTo(info.getToAddresses());
			String from = info.getFromAddress();
			if (from == null) from = DEFAULT_FROM;
			email.setFrom(from);
			if (info.getCcAddresses() != null) email.setCc(info.getCcAddresses());
			if (info.getBccAddresses() != null) email.setBcc(info.getBccAddresses());
		
			email.setSubject(info.getSubject());
			email.setText(info.getMessageBody(), info.isHtml());
			logger.info("Sending email to [" + info.getToAddresses() + "][" + info.getSubject() + "]");
		
			mailSender.send(mime);
			logger.info("Successfully Sent email to [" + info.getToAddresses() + "][" + info.getSubject() + "]");
		}
		catch (Exception e1) {
			e1.printStackTrace();
			logger.error("Failed to send email to [" + info.getToAddresses() + "][" + info.getSubject() + "]",e1);
		}
		
	}

}

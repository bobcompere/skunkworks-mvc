package net.fourstrategery.cloud;

import java.util.Date;

import net.fourstrategery.cloud.email.MailInfo;
import net.fourstrategery.cloud.email.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupBean implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	MailService mailService;
	
	 @Override
	 public void onApplicationEvent(final ContextRefreshedEvent event) {
		MailInfo info = new MailInfo();
		
		info.setToAddresses("bcompere@gmail.com");
		info.setSubject("Four Strategery - services initializing");
		info.setMessageBody("Starting up " + new Date() + "......");
		
		mailService.sendMail(info);
	}
}

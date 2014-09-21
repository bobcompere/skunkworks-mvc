package net.fourstrategery.cloud;

import java.util.Date;
import java.util.List;

import net.fourstrategery.cloud.email.MailInfo;
import net.fourstrategery.cloud.email.MailService;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.PlayerRepository;
import net.fourstrategery.cloud.security.FsPasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupBean implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	MailService mailService;
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	FsPasswordEncoder passwordEncoder;
	
	@Value("${fourStrat.sendStartupEmail:TRUE}")
	private String sendStartEmails;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 @Override
	 public void onApplicationEvent(final ContextRefreshedEvent event) {
		 
		 //
		 // fix players w/o a PW
		 //
		 List<PlayerEntity> noPwPlayers = playerRepository.getPlayersWithoutPassword();
		 
		 for (PlayerEntity player : noPwPlayers) {
			 player.setPassword(passwordEncoder.encode("password"));
			 playerRepository.save(player);
		 }
		 //
		 //
		 //
		 if (sendStartEmails.equalsIgnoreCase("TRUE")) {
			MailInfo info = new MailInfo();
			
			info.setToAddresses("bcompere@gmail.com");
			info.setSubject("Four Strategery - services initializing");
			info.setMessageBody("Starting up " + new Date() + "......");
			
			mailService.sendMail(info);
		 }
		 else {
			 logger.info("Startup emails are disabled");
		 }
	}
}

package net.fourstrategery.cloud.gameplay;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.fourstrategery.cloud.email.MailInfo;
import net.fourstrategery.cloud.email.MailService;
import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.GamePlayerRepository;
import net.fourstrategery.cloud.repository.GameRepository;

@Service
public class GameAnnoucementServiceImpl implements GameAnnouncementsService {

	@Autowired
	GamePlayerRepository gamePlayerRepository;
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired 
	MailService mailService;
	
	@Override
	public void sendFinishGameEmails() {
		List<GamePlayerEntity> currentGPs = gamePlayerRepository.getGamePlayersInActiveGames();
		
		PlayerEntity currentPlayer = null;
		StringBuilder html = null;
		long now = new Date().getTime();
		
		for (GamePlayerEntity gamePlayer : currentGPs) {
			//
			// Skip if game not over
			//
			GameEntity game = gamePlayer.getGame();
			if (game.getEnds().getTime() > now) continue;
			//
			// check to see if game should be updated
			//
			if (game.getFinish_email_sent() == null) {
				game.setFinish_email_sent(new Date());
				gameRepository.save(game);
			}
			//
			//
			if ((currentPlayer != null) && (currentPlayer.getId() != gamePlayer.getPlayer().getId())) { 
				// finish up the last one
				
				sendEndEmail(currentPlayer, html);
				currentPlayer = null;
				html = null;
			}
			if (currentPlayer == null) {
				currentPlayer = gamePlayer.getPlayer();
				html = initHtmlGameEnded();
			}
			addGameData(gamePlayer.getGame(),html);
		}
		if (currentPlayer != null) {
			sendEndEmail(currentPlayer, html);
		}
	}

	@Override
	public void sendDailyGameStatusEmails() {
		List<GamePlayerEntity> currentGPs = gamePlayerRepository.getGamePlayersInActiveGames();
		
		PlayerEntity currentPlayer = null;
		StringBuilder html = null;
		
		for (GamePlayerEntity gamePlayer : currentGPs) {
			if ((currentPlayer != null) && (currentPlayer.getId() != gamePlayer.getPlayer().getId())) { 
				// finish up the last one
				
				send(currentPlayer, html);
				currentPlayer = null;
				html = null;
			}
			if (currentPlayer == null) {
				currentPlayer = gamePlayer.getPlayer();
				html = initHtml();
			}
			addGameData(gamePlayer.getGame(),html);
		}
		if (currentPlayer != null) {
			send(currentPlayer, html);
		}
	}
	
	private void send(PlayerEntity player, StringBuilder html) {
		
		html.append("</body></html>");
		
		MailInfo info = new MailInfo();
		
		info.setToAddresses(player.getEmailAddress());
		info.setMessageBody(html.toString());
		info.setSubject("Your Ongoing FourStrategery Game Status");
		info.setHtml(true);
		mailService.sendMail(info); 
	}
	
	
	private void sendEndEmail(PlayerEntity player, StringBuilder html) {
		
		html.append("</body></html>");
		
		MailInfo info = new MailInfo();
		
		info.setToAddresses(player.getEmailAddress());
		info.setMessageBody(html.toString());
		info.setSubject("Completed FourStrategery Game Status");
		info.setHtml(true);
		mailService.sendMail(info); 
	}
	
	private StringBuilder initHtml() {
		StringBuilder retval = new StringBuilder();
		
		retval.append("<html><body>" +
				"<h1>Your Current FourStrategery Game Status(es)!</h1>");
		return retval;
	}
	
	private StringBuilder initHtmlGameEnded() {
		StringBuilder retval = new StringBuilder();
		
		retval.append("<html><body>" +
				"<h1>Your Completed FourStrategery Game Status(es)!</h1>");
		return retval;
	}
	
	private void addGameData(GameEntity game,StringBuilder html) {
		List<GamePlayerEntity> players = game.getPlayers();
		Collections.sort(players, new ScoreComparator());
		
		html.append("<h2>Game: " + game.getDescription() + "</h2>");
		html.append("<table>");
		html.append("<tr><th>From</th><td>" + game.getStarts() + "</td></tr>");
		html.append("<tr><th>To</th><td>" + game.getEnds() + "</td></tr>");
		html.append("</table><br/>");
		
		html.append("<table>");
		html.append("<tr><th>Screen Name</th><th>First Name</th><th>Last Name</th><th>Score</th></tr>");
		for (GamePlayerEntity gp : players) {
			html.append("<tr><td>" + gp.getPlayer().getScreenName() + "</td>"
					+ "<td>" + gp.getPlayer().getFirstName() + "</td>"
					+ "<td>" + gp.getPlayer().getLastName() + "</td>"
					+ "<td>" + gp.getScore() + "</td></tr>");
		}
		html.append("</table><br/>");
		html.append("<br/>");
		html.append("<a href=\"http://services-fourstrategery.rhcloud.com/cloud\">Four Strategery Web Site</a>");
	}

}

class ScoreComparator implements Comparator<GamePlayerEntity> {

	@Override
	public int compare(GamePlayerEntity o1, GamePlayerEntity o2) {
		return o2.getScore() - o1.getScore();
	}


	
}
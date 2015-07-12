package net.fourstrategery.cloud.gameplay;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.fourstrategery.cloud.email.MailInfo;
import net.fourstrategery.cloud.email.MailService;
import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.GamePlayerRepository;

@Service
public class GameAnnoucementServiceImpl implements GameAnnouncementsService {

	@Autowired
	GamePlayerRepository gamePlayerRepository;
	
	@Autowired 
	MailService mailService;
	
	@Override
	public void sendFinishGameEmails() {
		// TODO Auto-generated method stub

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
	
	private StringBuilder initHtml() {
		StringBuilder retval = new StringBuilder();
		
		retval.append("<html><body>" +
				"<h1>Your Curent FourStrategery Game Status(es)!</h1>");
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
	}

}

class ScoreComparator implements Comparator<GamePlayerEntity> {

	@Override
	public int compare(GamePlayerEntity o1, GamePlayerEntity o2) {
		return o2.getScore() - o1.getScore();
	}


	
}
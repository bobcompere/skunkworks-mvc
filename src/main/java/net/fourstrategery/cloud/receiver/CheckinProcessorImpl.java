package net.fourstrategery.cloud.receiver;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.fourstrategery.cloud.email.MailInfo;
import net.fourstrategery.cloud.email.MailService;
import net.fourstrategery.cloud.entity.CheckinEntity;
import net.fourstrategery.cloud.entity.GameCheckinEntity;
import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.UnitStatus;
import net.fourstrategery.cloud.entity.UnitTransactionEntity;
import net.fourstrategery.cloud.entity.UnitTransactionType;
import net.fourstrategery.cloud.entity.VenueEntity;
import net.fourstrategery.cloud.repository.CheckinRepository;
import net.fourstrategery.cloud.repository.GameCheckinRepository;
import net.fourstrategery.cloud.repository.GameRepository;
import net.fourstrategery.cloud.repository.PlayerRepository;
import net.fourstrategery.cloud.repository.UnitRepository;
import net.fourstrategery.cloud.repository.UnitTransactionRepository;
import net.fourstrategery.cloud.repository.VenueRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.foyt.foursquare.api.entities.Checkin;

@Service
public class CheckinProcessorImpl implements CheckinProcessor {

	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	VenueRepository venueRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CheckinRepository checkinRepository;
	
	@Autowired
	GameCheckinRepository gameCheckinRepository;
	
	@Autowired
	UnitTransactionRepository unitTransactionRepository;
	
	@Autowired
	UnitNameGenerator unitNameGenerator;
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	MailService mailService;
	
	@Override
	@Transactional
	public void processCheckin(Checkin checkinObj) throws Exception {
		//
		// find player
		//
		PlayerEntity player = playerRepository.getPlayerByfourSquareId(checkinObj.getUser().getId());
		//
		// get checkin if exists
		//
		CheckinEntity checkinEnt = checkinRepository.findOne(checkinObj.getId());
		//
		// if not exists make a new one
		//
		if (checkinEnt == null) {
			checkinEnt = new CheckinEntity();
			checkinEnt.setId(checkinObj.getId());
		}
		//
		// find venue if exists
		//
		VenueEntity venue = venueRepository.findOne(checkinObj.getVenue().getId());
		//
		// if not found create new
		//
		if (venue == null) {
			venue = new VenueEntity();
			venue.setId(checkinObj.getVenue().getId());
		}
		//
		// Start filling in
		//
		venue.setName(checkinObj.getVenue().getName());
		venue.setAddress(checkinObj.getVenue().getLocation().getAddress());
		venue.setCity(checkinObj.getVenue().getLocation().getCity());
		venue.setState(checkinObj.getVenue().getLocation().getState());
		venue.setPostalCode(checkinObj.getVenue().getLocation().getPostalCode());
		venue.setCountry(checkinObj.getVenue().getLocation().getCountry());
		venue.setCheckins(checkinObj.getVenue().getStats().getCheckinsCount());
		venue.setUsers(checkinObj.getVenue().getStats().getUsersCount());
		venue.setLongitude(new BigDecimal(checkinObj.getVenue().getLocation().getLng()));
		venue.setLatitude(new BigDecimal(checkinObj.getVenue().getLocation().getLat()));
		
		checkinEnt.setCheckinType(checkinObj.getType());
		if (player != null) checkinEnt.setPlayer(player);
		checkinEnt.setShout(checkinObj.getShout());
		checkinEnt.setUserId(checkinObj.getUser().getId());
		checkinEnt.setVenueName(checkinObj.getVenue().getName());
		checkinEnt.setCheckinsCount(checkinObj.getVenue().getStats().getCheckinsCount());
		checkinEnt.setUsersCount(checkinObj.getVenue().getStats().getUsersCount());
		//
		// save the venue
		//
		venue = venueRepository.save(venue);
		//
		//
		//
		checkinEnt.setVenue(venue);
		//
		checkinRepository.save(checkinEnt);
		//
		if (player != null) {
			player.setLastActivity(new Date());
			player = playerRepository.save(player);
			
			//
			// check for games in progress
			//
			Date date = new Date();
			//
			logger.debug("Looking for active games for [" + date + "]");
			//
			List<GameEntity> games = gameRepository.findActiveGamesForPlayer(player,date);
			
			for (GameEntity game : games) {
				processGameCheckin(game,player,checkinEnt, venue);
			}
		}
		
		//
		logger.debug("CHECKIN LOGGED!!");
	}
	
	private void processGameCheckin(GameEntity game, PlayerEntity player, CheckinEntity checkin, VenueEntity venue ) {
		//
		//
		//
		int newTroops = determineCheckinValue(venue,player);
		if (newTroops == 0) return;
		//
		// find unit to increase
		//
		boolean existingUnit = true;
		UnitEntity unit = unitRepository.getUnitAtVenue(game,player,venue);
		
		if (unit == null) {
			existingUnit = false;
			//
			// no unit there, need to create one
			//
			unit = new UnitEntity();
			unit.setGame(game);
			unit.setPlayer(player);
			unit.setLocation(venue);
			unit.setStatus(UnitStatus.GARRISONED);
			
			unit.setName(unitNameGenerator.makeBSName(venue,player,game));
			
			unit.setNextMoveTime(new Date());
			
		}
		unit.setTroops(unit.getTroops() + newTroops);
		
		unit = unitRepository.save(unit);
		
		UnitTransactionEntity unitTransaction = new UnitTransactionEntity();
		
		unitTransaction.setUnit(unit);
		unitTransaction.setLocation(venue);
		if (existingUnit) {
			unitTransaction.setTransactionType(UnitTransactionType.UnitReinforced);
		} else {
			unitTransaction.setTransactionType(UnitTransactionType.UnitCreated);
		}
		unitTransaction.setTroopChange(newTroops);
		
		unitTransaction = unitTransactionRepository.save(unitTransaction);
		
		GameCheckinEntity gameCheckin = new GameCheckinEntity();
		
		gameCheckin.setGame(game);
		gameCheckin.setPlayer(player);
		gameCheckin.setCheckin(checkin);
		gameCheckin.setUnit(unit);
		gameCheckin.setTroopsEarned(newTroops);
		gameCheckin.setUnitTransaction(unitTransaction);
		
		gameCheckinRepository.save(gameCheckin);
		
		MailInfo mailInfo = new MailInfo();
		
		String unitNote = null;
		if (existingUnit) {
			unitNote = " Was reinforced to by " + unitTransaction.getTroopChange() + " to a Strength of " + unit.getTroops();
		}
		else {
			unitNote = " Was created, initial force level = " + unitTransaction.getTroopChange();
		}
		
		mailInfo.setToAddresses(player.getEmailAddress());
		mailInfo.setSubject("Game On! - your checkin at " + venue.getName() + " has been credited to game " + game.getDescription());
		mailInfo.setMessageBody("Game: " + game.getDescription() + "\n" +
				"Location: " + venue.getName() + " " + venue.getCity() + " " + venue.getState() + " " + venue.getCountry() + " \n" +
				"Unit: " + unit.getName() + unitNote
				);
		
		mailService.sendMail(mailInfo);
	}

	
	private int determineCheckinValue(VenueEntity venue, PlayerEntity player) {
		return venue.getUsers();
	}
}

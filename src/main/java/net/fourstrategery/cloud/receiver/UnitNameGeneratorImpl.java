package net.fourstrategery.cloud.receiver;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.VenueEntity;
import net.fourstrategery.cloud.repository.UnitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UnitNameGeneratorImpl implements UnitNameGenerator{

	@Autowired
	UnitRepository unitRepository;
	
	@Override
	public String makeBSName(VenueEntity venue, PlayerEntity player,GameEntity game) {
		int regNumber = 1;
	
		String name = null;
		String vname = venue.getName().split(" ")[0];
		for (;;) {
			name = vname + " " + ithize(regNumber) + " Regiment";
			UnitEntity unit = unitRepository.findUnitByNameAndGame(game, name);
			if (unit == null) break;
		}
		return name;
	}
	
	private String ithize(int num) {
		int lastDigit = num % 10;
		
		switch(lastDigit) {
		case 0:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return num + "th";
		case 1:
			return num + "st";
		case 2:
			return num + "nd";
		case 3:
			return num + "rd";
		}
		return num + "th"; // should not hit
	}

}

package net.fourstrategery.cloud.utility;

import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.UnitStatus;
import net.fourstrategery.cloud.entity.VenueEntity;

public class VenueUtility {
	
	private static final double KM_CONSTANT = 1.609344; // KM

	/**
	 * Source: http://www.geodatasource.com/developers/java
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int distanceBetween(VenueEntity v1, VenueEntity v2) {
		if (v1.getId().equals(v2.getId())) return 0;
		
		double theta = v1.getLongitude().doubleValue() - v2.getLongitude().doubleValue();
		
		double dist = Math.sin(toRad(v1.getLatitude().doubleValue())) *
				 Math.sin(toRad(v2.getLatitude().doubleValue())) +
				 Math.cos(toRad(v1.getLatitude().doubleValue())) *
				 Math.cos(toRad(v2.getLatitude().doubleValue()));
		
		
		
		dist = dist * Math.cos(toRad(theta));
		
		dist = Math.acos(dist);
		
		dist = toDegrees(dist);
		
		dist = dist *  60 * 1.1515;
		
		return (int)Math.ceil(dist);	 
	}
	
	public static double toRad(double degrees) {
		return (degrees * Math.PI) / 180.0;
	}
	
	private static double toDegrees(double rad) {
	  return (rad * 180 / Math.PI);
	}

	
	public static boolean isInVenue(UnitEntity unit, VenueEntity venue) {
		if (unit.getStatus() == UnitStatus.GARRISONED && unit.getLocation().getId().equals(venue.getId())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String getMoveMethod(UnitEntity moveUnit, UnitEntity occupingUnit, VenueEntity location) {
		String returnVal = null;
		
		int movePlayerID = moveUnit.getPlayer().getId();
		int occupingPlayerID  = -1;
		
		
		if (moveUnit.getStatus() != UnitStatus.GARRISONED) {
			returnVal = "doDialog('Invalid','That is the unit is already on the move...');";
		}
		else {
			if (occupingUnit == null) {
				returnVal = "moveUnit(" + moveUnit.getId() + ",'" + location.getId() + "',"
					+ "'Move " + fixForJs(moveUnit.getName()) + " to occupy empty location: " + fixForJs(location.getName()) +
					"');";
			}
			else {
				occupingPlayerID = occupingUnit.getPlayer().getId();
				if (moveUnit.getId() == occupingUnit.getId()) {
					returnVal = "doDialog('Invalid','That is the same unit...');";
				}
				else {
					if (moveUnit.getPlayer().getId() == occupingUnit.getPlayer().getId()) {
						returnVal = "moveUnit(" + moveUnit.getId() + ",'" + location.getId() + "',"
								+ "'Move " + fixForJs(moveUnit.getName()) + " to join " + fixForJs(occupingUnit.getName()) +
								" at: " + fixForJs(location.getName()) +
								"');";
					}
					else {
						returnVal =  "moveUnit(" + moveUnit.getId() + ",'" + location.getId() + "',"
								+ "'Move " + fixForJs(moveUnit.getName()) + " to attack " + fixForJs(occupingUnit.getName()) +
								" at: " + fixForJs(location.getName()) +
								"');";
					}
				}
			}
		}
		return returnVal;
	}
	
	private static String fixForJs(String input) {
		return input.replace("\'","").replace("\"", "");
	}
}

package net.fourstrategery.cloud.receiver;

import org.springframework.stereotype.Service;

import fi.foyt.foursquare.api.entities.Checkin;



@Service
public interface CheckinProcessor {

	public void processCheckin(Checkin checkinObj) throws Exception;
}

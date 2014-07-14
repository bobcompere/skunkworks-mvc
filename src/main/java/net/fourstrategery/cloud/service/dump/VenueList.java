package net.fourstrategery.cloud.service.dump;

import java.util.List;

import net.fourstrategery.cloud.entity.VenueEntity;
import net.fourstrategery.cloud.repository.VenueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VenueList {
	@Autowired
	private VenueRepository venueRepository;

	@RequestMapping(value = "/service/dump/venues",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VenueEntity> getVenues() {
		return venueRepository.findAll();
	}
}

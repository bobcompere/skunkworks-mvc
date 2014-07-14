package net.fourstrategery.cloud.service.dump;

import java.util.List;

import net.fourstrategery.cloud.entity.CheckinEntity;
import net.fourstrategery.cloud.repository.CheckinRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckinList {

	@Autowired
	private CheckinRepository checkinRepository;

	@RequestMapping(value = "/service/dump/checkins",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CheckinEntity> getCheckins() {
		return checkinRepository.findAll();
	}
}

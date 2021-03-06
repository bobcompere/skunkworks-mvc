package net.fourstrategery.cloud.service.dump;

import java.util.List;

import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.repository.UnitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UnitList {

	@Autowired
	private UnitRepository unitRepository;
	
	@RequestMapping(value = "/service/dump/units",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<UnitEntity> getUnits() {
		return unitRepository.findAll();
	}
}

package org.iot.trucker.rules;

import org.iot.trucker.entity.Alert;
import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Vehicle;
import org.iot.trucker.entity.Alert.types;
import org.iot.trucker.repository.AlertRepository;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Easy rule to check rpm
 * @author Harish Krishnamurthi
 *
 */
@Component
@Rule(name = "rpm rule", description = "alert when engine rpm is high")
public class RpmRule implements TruckerRule{

	@Autowired
	private AlertRepository alertRepository;
	
	private String message;
	
	/**
	 * Condition to check if engine is greater than the red line rpm
	 * @param reading : reading object
	 * @param vehicle : vehicle object
	 * @return true if engine is greater than the red line rpm, false otherwise
	 */
	@Condition
	public boolean condition(@Fact("reading") Reading reading, @Fact("vehicle") Vehicle vehicle) {
		if (reading.getEngineRpm() > vehicle.getRedlineRpm()){
			message = "Engine Rpm is higher than vehicle's redLine. Current value :"+ reading.getEngineRpm();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Persists alert object if engine is greater than the red line rpm
	 * @param reading : reading object
	 */
	@Action
	public void action(@Fact("reading") Reading reading) {
		Alert alert = new Alert();
		alert.setVin(reading.getVin());
		alert.setTimeStamp(reading.getTimestamp());
		alert.setMessage(message);
		alert.setPriority(1);
		alert.setType(types.ENGINE.toString());
		alertRepository.save(alert);
	}
}

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
 * Easy rule to check engine coolant and engine light indicators
 * @author Harish Krishnamurthi
 *
 */
@Component
@Rule(name = "indicators rule", description = "alert when engine coolant indicator or check engine light is on")
public class IndicatorRule implements TruckerRule{

	@Autowired
	private AlertRepository alertRepository;
	private String message;
	
	/**
	 * Condition to check if engine light or coolant light is on
	 * @param reading : reading object
	 * @param vehicle : vehicle object
	 * @return true if engine light or coolant light is on, false otherwise
	 */
	@Condition
	public boolean condition(@Fact("reading") Reading reading, @Fact("vehicle") Vehicle vehicle) {
		if(reading.isCheckEngineLightOn()){
			message = "Check Engine Light is on";
			return true;
		}
		if(reading.isEngineCoolantLow()){
			message = "Check Engine coolant";
			return true;
		}
			
		return false;
	}

	/**
	 * Persists alert object if engine light or coolant light is on
	 * @param reading : reading object
	 */
	@Action
	public void action(@Fact("reading") Reading reading) {
		Alert alert = new Alert();
		alert.setVin(reading.getVin());
		alert.setTimeStamp(reading.getTimestamp());
		alert.setMessage(message);
		alert.setPriority(3);
		alert.setType(types.ENGINE.toString());
		alertRepository.save(alert);
	}

}

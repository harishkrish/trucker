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
 * Easy rule for cheking fuel readings
 * @author Harish Krishnamurthi
 *
 */
@Component
@Rule(name = "fuel rule", description = "alert if fuel is low")
public class FuelRule implements TruckerRule{
	@Autowired
	private AlertRepository alertRepository;
	private String message;
	
	/**
	 * Condition to check if the Fuel volume is low
	 * @param reading : reading object
	 * @param vehicle : vehicle object
	 * @return true if Fuel volume is low, false otherwise
	 */
	@Condition
	public boolean condition(@Fact("reading") Reading reading, @Fact("vehicle") Vehicle vehicle) {
		if(reading.getFuelVolume() < (0.10*vehicle.getMaxFuelVolume())){
			message="Vehicle's Fuel is low. Current Fuel reading :"+ reading.getFuelVolume();
			return true;
		}
		else
			return false;
	}
	/**
	 * Persists alert if fuel volume is low
	 * @param reading : reading object
	 */
	@Action
	public void action(@Fact("reading") Reading reading) {
		Alert alert = new Alert();
		alert.setVin(reading.getVin());
		alert.setTimeStamp(reading.getTimestamp());
		alert.setMessage(message);
		alert.setPriority(2);
		alert.setType(types.FUEL.toString());
		alertRepository.save(alert);
	}

}

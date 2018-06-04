package org.iot.trucker.rules;

import org.iot.trucker.entity.Alert;
import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Tires;
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
 * Easy rule to check tire pressure
 * @author Harish Krishnamurthi
 *
 */
@Component
@Rule(name = "tire pressure rule", description = "alert if tire pressure is low/high")
public class TirePressureRule implements TruckerRule{
	@Autowired
	private AlertRepository alertRepository;
	private String message;
	
	/**
	 * Condition to check if tire pressures are low/high
	 * @param reading : reading object
	 * @param vehicle : vehicle object
	 * @return true if tire pressures are low/high, false otherwise
	 */
	@Condition
	public boolean condition(@Fact("reading") Reading reading, @Fact("vehicle") Vehicle vehicle) {
		Tires tires = reading.getTires();
		if (tires.getFrontLeft() < 32 || tires.getFrontRight() <32 || tires.getRearLeft() <32 || tires.getRearRight() < 32 ||	
				tires.getFrontLeft() > 36 || tires.getFrontRight() > 36 || tires.getRearLeft() > 36 || tires.getRearRight() > 36)
		{
			message = "Check tire pressure. Current readings of tires -> Front Left:"+tires.getFrontLeft()+" Front Right:"+tires.getFrontRight()+"  Rear Left:"+tires.getRearLeft()+" Rear right:"+tires.getRearRight();
			return true;
		}
		else
			return false;
	}
	/**
	 * Persists alert object if tire pressures are low/high
	 * @param reading : reading object
	 */
	@Action
	public void action(@Fact("reading") Reading reading) {
		Alert alert = new Alert();
		alert.setVin(reading.getVin());
		alert.setTimeStamp(reading.getTimestamp());
		alert.setMessage(message);
		alert.setPriority(3);
		alert.setType(types.TIRES.toString());
		alertRepository.save(alert);
	}
}

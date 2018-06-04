package org.iot.trucker.rules;

import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Vehicle;


/**
 * Easy rule interface for Trucker
 * @author Harish Krishnamurthi
 *
 */
public interface TruckerRule {
	/**
	 * easy rule condition
	 * @param reading : reading entry
	 * @param vehicle : vehicle entry
	 * @return true if condition satisfied, false otherwise
	 */
	public boolean condition(Reading reading,Vehicle vehicle);
	
	/**
	 * easy rule action
	 * @param reading : reading entry
	 */
	public void action(Reading reading);
}

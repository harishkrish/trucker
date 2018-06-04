package org.iot.trucker.service;

import java.util.List;
import java.util.Map;

import org.iot.trucker.entity.Alert;
import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Vehicle;

/**
 * Trucker services Interface
 * @author Harish Krishnamurthi
 *
 */
public interface TruckerService {

	/**
	 * service to create vehicle entry
	 * @param vehicles : list of vehicles
	 */
	List<Vehicle> createVehicle(List<Vehicle> vehicle);
	
	/**
	 * service to create reading entries and calls to alert
	 * @param reading : reading object
	 */
	Reading createReading(Reading reading);
	
	/**
	 * service to get list of all vehicles
	 * @return List<Vehicle>: list of vehicle details
	 */
	List<Vehicle> findAllVehicles();
	
	/**
	 * service to get alerts within last two hours sortedb by time
	 * @param priority
	 * @return List<Alert> : List of alerts in last two hours
	 */
	List<Alert> findAllAlerts(int priority);
	
	/**
	 * service to get geo location of vehicle within last 30 minute
	 * @param vin : vehicle vin
	 * @return Object[] : latitude and longitude
	 */
	Map<String,Double> findLocation(String vin);
	
	/** 
	 * service to get Historic alerts of vehicle
	 * @param vin: Vehicle vin
	 * @return List<Alert> : list of alerts
	 */
	List<Alert> findVehicleAlerts(String vin);
	
	/**
	 * Service to find vehicle details by vin
	 * @param vin: Vehicle vin
	 * @return Vehicle : vehicle details
	 */
	Vehicle findVehicle(String vin);
}

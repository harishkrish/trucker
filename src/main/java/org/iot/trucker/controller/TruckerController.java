package org.iot.trucker.controller;

import java.util.List;
import java.util.Map;

import org.iot.trucker.entity.Alert;
import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Vehicle;
import org.iot.trucker.service.TruckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for the trucker service
 * @author Harish Krishnamurthi
 *
 */
@RestController
@ResponseBody
public class TruckerController {
	@Autowired
	@Qualifier("truckerServiceImpl")
	private TruckerService truckerService;
	
	/**
	 * service landing for injesting vehicle details
	 * @param vehicles : list of vehicle details
	 */
	@CrossOrigin(origins = "http://mocker.egen.io")
	@RequestMapping(method = RequestMethod.PUT, value="/vehicles", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void createVehicle(@RequestBody List<Vehicle> vehicles) {
		 truckerService.createVehicle(vehicles);
	}
	
	/**
	 * service landing for ingesting readings of vehicles
	 * @param reading : the readings of a vehicle
	 */
	@CrossOrigin(origins = "http://mocker.egen.io")	
	@RequestMapping(method=RequestMethod.POST , value="/readings", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void createReading(@RequestBody Reading reading){
		 truckerService.createReading(reading);
	}
	
	/**
	 * service landing for finding all vehicle details
	 * @return List<Vehicle> : List of vehicles
	 */
	@RequestMapping(method=RequestMethod.GET , value="/vehicles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Vehicle> findAll(){
		return truckerService.findAllVehicles();
	}
	
	/**
	 * service landing for finding all alerts in last two hours sorted by time, given priority of alerts
	 * @param priority
	 * @return List<Alert> : list of alerts
	 */
	@RequestMapping(method=RequestMethod.GET , value="/allAlerts/{priority}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Alert> findAllAlerts(@PathVariable("priority") int priority){
		return truckerService.findAllAlerts(priority);
	}
	/**
	 * service landing for finding geo location of vehicle within last 30 minutes
	 * @param vin : vehicle vin
	 * @return Latitude,Longitude 
	 */
	@RequestMapping(method=RequestMethod.GET , value="/location/{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String,Double> findLocation(@PathVariable("vin") String vin){
		return truckerService.findLocation(vin);
	}
	
	/**
	 * service landing to get all historical alerts of a vehicle
	 * @param vin : vehicle vin
	 * @return List<Alert> : list of alerts
	 */
	@RequestMapping(method=RequestMethod.GET , value="/alerts/{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Alert> findVehicleAlert(@PathVariable("vin") String vin){
		return truckerService.findVehicleAlerts(vin);
	}
	
	/**
	 * service landing to get vehicle details
	 * @param vin : vehicle vin
	 * @return Vehicle : vehicle details, if present
	 */
	@RequestMapping(method=RequestMethod.GET , value="/vehicle/{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Vehicle findVehicle(@PathVariable("vin") String vin){
		return truckerService.findVehicle(vin);
	}
}

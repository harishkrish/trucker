package org.iot.trucker.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.iot.trucker.entity.Alert;
import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Vehicle;
import org.iot.trucker.exception.BadRequestException;
import org.iot.trucker.exception.ResourceNotFoundException;
import org.iot.trucker.repository.AlertRepository;
import org.iot.trucker.repository.ReadingRepository;
import org.iot.trucker.repository.TiresRepository;
import org.iot.trucker.repository.VehicleRepository;
import org.iot.trucker.rules.CheckRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Trucker service implementation
 * 
 * @author Harish Krishnamurthi
 *
 */
@Service
@Scope("prototype")
public class TruckerServiceImpl implements TruckerService {

	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private ReadingRepository readingRepository;
	@Autowired
	private TiresRepository tiresRepository;
	@Autowired
	@Lazy
	private AlertRepository alertRepository;

	@Autowired
	@Lazy
	private CheckRule checkRule;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iot.trucker.service.TruckerService#createVehicle(java.util.List)
	 */
	@Transactional
	public void createVehicle(List<Vehicle> vehicles) {
		vehicleRepository.saveAll(vehicles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.iot.trucker.service.TruckerService#createReading(org.iot.trucker.
	 * entity.Reading)
	 */
	@Transactional
	public void createReading(Reading reading) {
		tiresRepository.save(reading.getTires());
		readingRepository.save(reading);
		Optional<Vehicle> vehicle = vehicleRepository.findByVin(reading.getVin());
		// since we need vehicle entry to check thresholds
		if (vehicle.isPresent()) {
			checkRules(reading, vehicle.get());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iot.trucker.service.TruckerService#findAllVehicles()
	 */
	@Transactional
	public List<Vehicle> findAllVehicles() {
		List<Object[]> vehicles = vehicleRepository.findAllVehicles();
		if (vehicles.size() <= 0)
			return Collections.emptyList();
		List<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
		for (Object[] v : vehicles) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVin((String) v[0]);
			vehicle.setMake((String) v[1]);
			vehicle.setModel((String) v[2]);
			vehicle.setYear((int) v[3]);
			vehicle.setMaxFuelVolume((float) v[4]);
			vehicle.setRedlineRpm((float) v[5]);
			vehicle.setLastServiceDate((Timestamp) v[6]);
			listOfVehicles.add(vehicle);
		}
		return listOfVehicles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iot.trucker.service.TruckerService#findAlerts(int)
	 */
	@Transactional
	public List<Alert> findAllAlerts(int priority) {
		if (priority <= 0 || priority > 3)
			throw new BadRequestException("Use priorities between 1 & 3. 1 : HIGH, 2:MEDIUM, 3:LOW");
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Timestamp currentTime = new Timestamp(date.getTime());
		return alertRepository.findAlerts(currentTime, priority);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.iot.trucker.service.TruckerService#findLocation(java.lang.String)
	 */
	@Transactional
	public Map<String, Double> findLocation(String vin) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Timestamp currentTime = new Timestamp(date.getTime());
		List<Reading> reading = readingRepository.findByVin(vin);
		if (reading.size() == 0)
			throw new ResourceNotFoundException("Vehicle " + vin + "location not found");
		List<Object[]> obj = readingRepository.findLocation(currentTime, vin);
		if (obj.size() == 1) {
			Map<String, Double> geoLocation = new HashMap<>();
			for (Object[] o : obj) {
				geoLocation.put("latitude", (Double) o[0]);
				geoLocation.put("longitude", (Double) o[1]);
			}
			return geoLocation;
		} else
			return Collections.emptyMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iot.trucker.service.TruckerService#findVehicleAlerts(java.lang.
	 * String)
	 */
	@Transactional
	public List<Alert> findVehicleAlerts(String vin) {
		if(vehicleRepository.findVehicle(vin).size()==0)
			throw new ResourceNotFoundException("No vehicle with id :"+vin);
		return alertRepository.findVehicleAlerts(vin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iot.trucker.service.TruckerService#findVehicle(java.lang.String)
	 */
	public Vehicle findVehicle(String vin) {
		List<Object[]> obj = vehicleRepository.findVehicle(vin);
		if (obj.size() <= 0)
			throw new ResourceNotFoundException("Vehicle with vin :" + vin + "not present.");
		Vehicle vehicle = new Vehicle();
		for (Object[] v : obj) {
			vehicle.setVin((String) v[0]);
			vehicle.setMake((String) v[1]);
			vehicle.setModel((String) v[2]);
			vehicle.setYear((int) v[3]);
			vehicle.setRedlineRpm((float) v[4]);
			vehicle.setMaxFuelVolume((float) v[5]);
			vehicle.setLastServiceDate((Timestamp) v[6]);
		}
		return vehicle;
	}

	/**
	 * service for checking conditions to alert
	 * 
	 * @param reading
	 * @param vehicle
	 */
	@Async
	public void checkRules(Reading reading, Vehicle vehicle) {
		checkRule.checkComponents(reading, vehicle);
	}
}

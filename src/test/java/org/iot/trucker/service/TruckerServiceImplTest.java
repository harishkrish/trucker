package org.iot.trucker.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import org.iot.trucker.entity.Alert;
import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Vehicle;
import org.iot.trucker.exception.BadRequestException;
import org.iot.trucker.exception.ResourceNotFoundException;
import org.iot.trucker.repository.AlertRepository;
import org.iot.trucker.repository.ReadingRepository;
import org.iot.trucker.repository.TiresRepository;
import org.iot.trucker.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import junit.framework.Assert;

/**
 * JUnit Test for Trucker service
 * 
 * @author Harish Krishamurthi
 *
 */
@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
public class TruckerServiceImplTest {

	@TestConfiguration
	static class TruckerServiceImplTestConfiguration {
		@Bean
		public TruckerService getTruckerService() {
			return new TruckerServiceImpl();
		}
	}

	@Autowired
	private TruckerService truckerService;

	@MockBean
	private AlertRepository alertRepository;

	@MockBean
	private ReadingRepository readingRepository;

	@MockBean
	private TiresRepository tiresRepository;

	@MockBean
	private VehicleRepository vehicleRepository;

	private List<Alert> alerts;

	private List<Object[]> vehicles;

	private List<Vehicle> vehicleList;

	private Vehicle vehicle;

	private Reading reading;

	@Before
	public void setup() {
		Alert alert = new Alert();
		alert.setId("A123");
		alert.setVin("V123");
		alert.setMessage("Fuel is low");
		alert.setPriority(1);
		alert.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		alert.setType("FUEL");
		alerts = Collections.singletonList(alert);
		Mockito.when(alertRepository.findVehicleAlerts("V123")).thenReturn(alerts);
		vehicle = new Vehicle();
		vehicle.setVin("V123");
		vehicle.setMake("HONDA");
		vehicle.setModel("ACCORD");
		vehicle.setYear(2015);
		vehicle.setRedlineRpm(5500);
		vehicle.setMaxFuelVolume(15);
		vehicle.setLastServiceDate(new Timestamp(System.currentTimeMillis()));
		Object[] objects = new Object[7];
		objects[0] = vehicle.getVin();
		objects[1] = vehicle.getMake();
		objects[2] = vehicle.getModel();
		objects[3] = vehicle.getYear();
		objects[4] = vehicle.getRedlineRpm();
		objects[5] = vehicle.getMaxFuelVolume();
		objects[6] = vehicle.getLastServiceDate();
		vehicleList = Collections.singletonList(vehicle);
		vehicles = Collections.singletonList(objects);

		reading = new Reading();
		reading.setVin("V123");
		reading.setLatitude(41.803194);
		reading.setLongitude(-88.144406);
		reading.setTimestamp(new Timestamp(System.currentTimeMillis()));
		reading.setFuelVolume((float) 1.5);
		reading.setSpeed(85);
		reading.setEngineHp(240);
		reading.setCheckEngineLightOn(false);
		reading.setEngineCoolantLow(true);
		reading.setCruiseControlOn(true);
		reading.setEngineRpm(6300);

		Mockito.when(vehicleRepository.findVehicle("V123")).thenReturn(vehicles);
		Mockito.when(vehicleRepository.findAllVehicles()).thenReturn(vehicles);
		Mockito.when(vehicleRepository.saveAll(vehicleList)).thenReturn(vehicleList);
		Mockito.when(readingRepository.save(reading)).thenReturn(reading);
	}

	/**
	 * Test for creating vehicles
	 */
	@Test
	public void testCreateVehicles() {
		List<Vehicle> result = truckerService.createVehicle(vehicleList);
		Assert.assertEquals("Vehicle list should match", vehicleList.get(0).getVin(), result.get(0).getVin());
	}

	/**
	 * Test for creating reading
	 */
	@Test
	public void testCreateReading() {
		Reading result = truckerService.createReading(reading);
		Assert.assertEquals("Reading object should match", reading.getVin(), result.getVin());
	}

	/**
	 * Test for find All vehicles
	 */
	@Test
	public void testFindAllVehicles() {
		List<Vehicle> result = truckerService.findAllVehicles();
		Assert.assertEquals("Vehicle list should match", vehicleList.get(0).getVin(), result.get(0).getVin());
	}

	/**
	 * Test for Bad requesting alerts with wrong priority
	 */
	@Test(expected = BadRequestException.class)
	public void testFindAllAlerts() {
		truckerService.findAllAlerts(4);
	}

	/**
	 * Test for finding Location with bad vin
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void testFindLocation() {
		truckerService.findLocation("V12");
	}

	/**
	 * Test for finding historic vehicle alerts
	 */
	@Test
	public void testFindVehicleAlerts() {
		List<Alert> result = truckerService.findVehicleAlerts("V123");
		Assert.assertEquals("alert list should match", alerts, result);
	}

	/**
	 * Test for finding historic vehicle alerts (Negative)
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void testFindVehicleAlerts400() {
		truckerService.findVehicleAlerts("V12");
	}

	/**
	 * Test for finding vehicle details
	 */
	@Test
	public void testFindVehicle() {
		Vehicle result = truckerService.findVehicle("V123");
		Assert.assertEquals("Vehicle should match", vehicle.toString(), result.toString());
	}

	/**
	 * Test for finding vehicle details(Negative)
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void testFindVehicle400() {
		truckerService.findVehicle("V12");
	}

}

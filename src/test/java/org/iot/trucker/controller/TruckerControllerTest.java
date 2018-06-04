package org.iot.trucker.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.iot.trucker.entity.Alert;
import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Tires;
import org.iot.trucker.entity.Vehicle;
import org.iot.trucker.repository.AlertRepository;
import org.iot.trucker.repository.ReadingRepository;
import org.iot.trucker.repository.TiresRepository;
import org.iot.trucker.repository.VehicleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Trucker controller test (Integration test)
 * 
 * @author Harish Krishnamurthi
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
public class TruckerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private ReadingRepository readingRepository;

	@Autowired
	private AlertRepository alertRepository;

	@Autowired
	private TiresRepository tiresRepository;

	@Before
	public void setup() {
		List<Reading> readings = new ArrayList<>();
		List<Alert> alerts = new ArrayList<>();
		Vehicle vehicle = new Vehicle();
		vehicle.setVin("V123");
		vehicle.setMake("HONDA");
		vehicle.setModel("ACCORD");
		vehicle.setYear(2015);
		vehicle.setRedlineRpm(5500);
		vehicle.setMaxFuelVolume(15);
		vehicle.setLastServiceDate(new Timestamp(System.currentTimeMillis()));
		Alert alert = new Alert();
		alert.setId("A123");
		alert.setVin("V123");
		alert.setMessage("Fuel is low");
		alert.setPriority(1);
		alert.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		alert.setType("FUEL");
		Reading reading = new Reading();
		Tires tires = new Tires();
		tires.setId("A123");
		reading.setId("A123");
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
		tires.setFrontLeft(34);
		tires.setFrontRight(36);
		tires.setRearLeft(29);
		tires.setRearRight(34);
		reading.setTires(tires);
		vehicle.setReadings(readings);
		vehicle.setAlerts(alerts);
		vehicleRepository.save(vehicle);
		alertRepository.save(alert);
		tiresRepository.save(tires);
		readingRepository.save(reading);
	}

	@After
	public void cleanup() {
		alertRepository.deleteAll();
		readingRepository.deleteAll();
		tiresRepository.deleteAll();
		vehicleRepository.deleteAll();
	}

	/**
	 * Test for create vehicles
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateVehicle() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Vehicle> vehicles = new ArrayList<>();
		Vehicle vehicle = new Vehicle();
		vehicle.setVin("V123");
		vehicle.setMake("HONDA");
		vehicle.setModel("CIVIC");
		vehicle.setYear(2015);
		vehicle.setRedlineRpm(5500);
		vehicle.setMaxFuelVolume(15);
		vehicle.setLastServiceDate(new Timestamp(System.currentTimeMillis()));
		vehicles.add(vehicle);
		mvc.perform(MockMvcRequestBuilders.put("/vehicles").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(vehicles))).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
	}

	/**
	 * Test for create reading
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateReading() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Reading reading = new Reading();
		Tires tires = new Tires();
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
		tires.setFrontLeft(34);
		tires.setFrontRight(36);
		tires.setRearLeft(29);
		tires.setRearRight(34);
		reading.setTires(tires);
		mvc.perform(MockMvcRequestBuilders.post("/readings").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(reading))).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("V123")));

	}

	/**
	 * Test for find all vehicle details
	 */
	@Test
	public void testFindAllVehicles() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/vehicles")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("V123")));
	}

	/**
	 * Test for Bad request for find All alerts
	 */
	@Test
	public void testFindAllAlerts400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/allAlerts/4"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/**
	 * Test for finding alerts, given Vehicle's vin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindVehicleAlerts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/alerts/V123")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("V123")));
	}

	/**
	 * Test for finding alerts (Vin not found)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindVehicleAlerts400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/alerts/V12")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Test for finding vehicle details, given vin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindVehicle() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/vehicle/V123")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("V123")));
	}

	/**
	 * Test for finding vehicle details, given vin (400 not found)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindVehicle400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/vehicle/V12")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}

package org.iot.trucker.repository;

import java.util.List;
import java.util.Optional;

import org.iot.trucker.entity.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Repository for Vehicles
 * @author Harish Krishnamurthi
 *
 */
public interface VehicleRepository extends CrudRepository<Vehicle,String>{
	
	Optional<Vehicle> findByVin(String vin);
	
	/**
	 * Query to return all details of all vehicles
	 * JSON object not formed properly if List<Vehicle> is used, hence object[] was used
	 * @return List<Object[]>
	 */
	@Query("SELECT v.vin,v.make,v.model,v.year,v.maxFuelVolume,v.redlineRpm,v.lastServiceDate"
			+ " from Vehicle v")
	List<Object[]> findAllVehicles();
	
	
	/**
	 * Query to return vehicle details of given vin
	 * @param vin : vehicle vin
	 * @return List<Object[]> list of vehicle details
	 */
	@Query("SELECT v.vin,v.make,v.model,v.year,v.redlineRpm,v.maxFuelVolume,v.lastServiceDate FROM Vehicle v WHERE v.vin =:vin")
	List<Object[]> findVehicle(@Param("vin") String vin);
	
}

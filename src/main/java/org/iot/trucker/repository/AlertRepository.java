package org.iot.trucker.repository;

import java.sql.Timestamp;
import java.util.List;

import org.iot.trucker.entity.Alert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Repository for Alerts
 * @author Harish Krishnamurthi
 *
 */
public interface AlertRepository extends CrudRepository<Alert, String>{
	
	/**
	 * Query to find list of alerts that occurred 2 hours before the given time
	 * @param time : current time
	 * @param priority : alert priority
	 * @return List<Alert> : list of alerts
	 */
	@Query(nativeQuery=true, value= "select * "
			+ "from alert a "
			+ "where a.priority = :priority "
			+ "and a.time_stamp >= DATE_SUB(:currentTime, INTERVAL 2 HOUR) "
			+ "ORDER by a.time_stamp")
	public List<Alert> findAlerts(@Param("currentTime") Timestamp time,@Param("priority")int priority);
	
	
	/**
	 * Query to find historic alerts of vehicle
	 * @param vin : vehicle vin
	 * @return List<Alert> : list of alerts
	 */
	@Query("SELECT a from Alert a where a.vin = :vin")
	public List<Alert> findVehicleAlerts( @Param("vin") String vin);
}




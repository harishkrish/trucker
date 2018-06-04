package org.iot.trucker.repository;

import java.sql.Timestamp;
import java.util.List;
import org.iot.trucker.entity.Reading;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;



/**
 * Repository for Readings
 * @author Harish Krishnamurthi
 *
 */
public interface ReadingRepository extends CrudRepository<Reading, String>{

	/**
	 * Query to find readings by vehicle vin
	 * @param vin : vehicle vin
	 * @return List<Reading> : list of reading
	 */
	public List<Reading> findByVin(String vin);
	
	/**
	 * Query to find latest geo-location of past 30 minutes of the vehicle
	 * @param currentTime : given time
	 * @param vin : vehicle vin
	 * @return : latest location of the vehicle in past 30 minutes
	 */
	@Query(nativeQuery=true,value ="select r.latitude,r.longitude "
			+ "from reading r "
			+ "where r.timestamp >= DATE_SUB(:currentTime, INTERVAL 30 MINUTE) "
			+ "and r.vin =:vin ORDER BY r.timestamp DESC LIMIT 1")
	public List<Object[]> findLocation(@Param("currentTime") Timestamp currentTime,@Param("vin") String vin);
}






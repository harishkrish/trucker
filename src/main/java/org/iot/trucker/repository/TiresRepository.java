package org.iot.trucker.repository;

import org.iot.trucker.entity.Tires;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Tires
 * @author Harish Krishnamurthi
 *
 */
public interface TiresRepository extends CrudRepository<Tires,String>{
	
}

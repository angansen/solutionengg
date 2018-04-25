package oracle.solution.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import oracle.solution.app.beans.ResourceType;

public interface ResourseTypeRepository extends CrudRepository<ResourceType, Long> {

	/**
	 * Get the list of all Resource types
	 */
	public List<ResourceType> findAll();
	
	/**
	 * Get the resource type by id
	 */
	public Optional<ResourceType> findById(String id);
	
}

package oracle.solution.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import oracle.solution.app.beans.ResourceRole;

public interface ResourceRoleRepository extends CrudRepository<ResourceRole	, Long> {

	
	/**
	 * Get all roles
	 */
	public List<ResourceRole> findAll();
	

	/**
	 * Get Role by supplied id
	 * @param id
	 * @return
	 */
	public Optional<ResourceRole> findById(String id);
}

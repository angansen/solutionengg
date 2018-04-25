package oracle.solution.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import oracle.solution.app.beans.ResourceContent;

public interface ResourceContentRepository extends CrudRepository<ResourceContent, Long> {

	/**
	 * Get the entire list of courses
	 */
	public List<ResourceContent> findAll();

	/**
	 * Get the Course by id
	 */
	public Optional<ResourceContent> findById(Long id);

	/**
	 * Delete content from database
	 */
	public void deleteById(Long id);


}

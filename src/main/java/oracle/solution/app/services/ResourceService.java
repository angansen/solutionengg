package oracle.solution.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oracle.solution.app.beans.ResourceContent;
import oracle.solution.app.beans.ResourceRole;
import oracle.solution.app.beans.ResourceType;
import oracle.solution.app.repositories.ResourceContentRepository;
import oracle.solution.app.repositories.ResourceRoleRepository;
import oracle.solution.app.repositories.ResourseTypeRepository;
import oracle.solution.app.wrapper.RequestFilterWrapper;

@Service
public class ResourceService {

	@Autowired
	private ResourseTypeRepository typeRepos;

	@Autowired
	private ResourceContentRepository contentRepos;

	@Autowired
	private ResourceRoleRepository rolesRepos;

	/**
	 * Get all Types
	 * 
	 * @return
	 */
	public List<ResourceType> getAllTypes() {
		return typeRepos.findAll();
	}

	/**
	 * Convert the Resource Type list into map
	 * 
	 * @return
	 */
	public Map<String, ResourceType> getAllTypesAsMap() {

		Map<String, ResourceType> typeMap = new HashMap();

		for (ResourceType type : getAllTypes()) {
			typeMap.put(type.getId() + "", type);
		}

		return typeMap;
	}

	/**
	 * Get all Contents
	 * 
	 * @return
	 */
	public List<ResourceContent> getAllContents() {
		return contentRepos.findAll();
	}

	/**
	 * Get all Roles
	 * 
	 * @return
	 */
	public List<ResourceRole> getAllRoles() {
		return rolesRepos.findAll();
	}

	public boolean saveContent(ResourceContent content) {

		content = contentRepos.save(content);

		return content != null;
	}

	public RequestFilterWrapper getTypeAndRolesFilterList() {
		RequestFilterWrapper wrap = new RequestFilterWrapper();

		wrap.setRolesList(getAllRoles());
		wrap.setTypesList(getAllTypes());
		return wrap;
	}

	public Collection<ResourceType> getAllCourses() {

		List<ResourceContent> contents = getAllContents();
		List<ResourceType> filteredTypes = new ArrayList<>();
		// Get all Types into map format
		Map<String, ResourceType> typeMap = getAllTypesAsMap();

		for (int i = 0; i < contents.size(); i++) {

			ResourceContent content = contents.get(i);
			List<ResourceRole> roleslist = getRolesByIds(content.getMappedRolesIds());
			contents.get(i).setRoles(roleslist);

			if (content.getMappedCategoryIds().contains(",")) {
				for (String typeid : content.getMappedCategoryIds().split(",")) {

					ResourceType type = typeMap.get(typeid);
					type.getContents().add(content);
					typeMap.put(typeid, type);
				}
			} else {
				ResourceType type = typeMap.get(content.getMappedCategoryIds());
				type.getContents().add(content);
				typeMap.put(content.getMappedCategoryIds(), type);
			}

		}

		for (String id : typeMap.keySet()) {

			if (!typeMap.get(id).getContents().isEmpty()) {
				filteredTypes.add(typeMap.get(id));
			}
		}

		return filteredTypes;
	}

	public List<ResourceRole> getRolesByIds(String idsValue) {

		String[] ids = idsValue.split(",");
		List<ResourceRole> roles = new ArrayList();

		for (String id : ids) {
			roles.add(getRolesById(id));
		}

		return roles;
	}

	public ResourceType getTypesById(String id) {

		return typeRepos.findById(id).get();
	}

	public ResourceRole getRolesById(String id) {
		return rolesRepos.findById(id).get();
	}

	public Collection<ResourceType> findMatchingCources(String catId, String roleId, String freetext) {

		// If no Category id and role id is provided then return un-filterd list
		if (catId.isEmpty() && roleId.isEmpty()) {
			return getAllCourses();
		}

		List catIdList = catId.length() > 0 ? Arrays.asList(catId.split(",")) : new ArrayList<>();
		List roleIdList = roleId.length() > 0 ? Arrays.asList(roleId.split(",")) : new ArrayList<>();

		Collection<ResourceType> categoryList = getAllCourses();
		Collection<ResourceType> tempList = new ArrayList<>();

		for (ResourceType type : categoryList) {
			if (!catId.isEmpty()) {
				// Check for category id match
				if (catIdList.contains(type.getId() + "")) {

					// Filter the contents based on associated roles and matching searched text
					// in the content name and description
					if (roleIdList.size() > 0) {
						type = findMatchingContentsByRoles(type, roleIdList, freetext.toLowerCase());
						tempList.add(type);
					} else {
						tempList.add(type);
					}
				}
			} else {
				// Filter the contents based on associated roles and matching searched text
				// in the content name and description
				if (roleIdList.size() > 0) {
					type = findMatchingContentsByRoles(type, roleIdList, freetext.toLowerCase());
					tempList.add(type);
				} else {
					tempList.add(type);
				}
			}
		}

		return tempList;
	}

	public ResourceType findMatchingContentsByRoles(ResourceType type, List roleIdList, String freetext) {
		List<ResourceContent> contents = type.getContents();
		List<ResourceContent> tempContents = new ArrayList<>();

		for (ResourceContent content : contents) {

			// Filter contents by associated roles
			// Then filter by matching search text
			if (isRoleMatching(roleIdList, content.getMappedRolesIds().split(","))) {
				if (freetext.length() > 0) {
					if (content.getName().toLowerCase().contains(freetext)
							|| content.getDescription().toLowerCase().contains(freetext)) {
						tempContents.add(content);
					}
				} else {
					tempContents.add(content);
				}

			}
		}

		type.setContents(tempContents);
		return type;
	}

	public boolean isRoleMatching(List roleIdList, String[] contentRolesIds) {

		for (String roleid : contentRolesIds) {
			if (roleIdList.contains(roleid)) {
				return true;
			}
		}

		return false;
	}

	public boolean deleteResourceContent(String id) {

		try {

			ResourceContent contenttodelete = new ResourceContent();
			contenttodelete.setId(Long.valueOf(id));
			contentRepos.delete(contenttodelete);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
}

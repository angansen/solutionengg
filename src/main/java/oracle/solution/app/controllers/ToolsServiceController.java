package oracle.solution.app.controllers;

import java.util.Collection;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import oracle.solution.app.beans.RequestStatus;
import oracle.solution.app.beans.ResourceContent;
import oracle.solution.app.beans.ResourceType;
import oracle.solution.app.services.ResourceService;
import oracle.solution.app.wrapper.RequestFilterWrapper;

@RestController
@CrossOrigin
public class ToolsServiceController {

	@Autowired
	private ResourceService typeService;

	/**
	 * Get the list of Content types
	 * 
	 * @return
	 */
	@RequestMapping("/tools/filters")
	public RequestFilterWrapper getResourceTypes() {
		return typeService.getTypeAndRolesFilterList();
	}

	/**
	 * Get all the list of Contents
	 * 
	 * @return
	 */
	@RequestMapping("/tools/contents")
	public Collection<ResourceType> getResourceContents() {
		return typeService.getAllCourses();
	}

	@RequestMapping("/tools/contents/find")
	public Collection<ResourceType> findResourceContents(
			@RequestHeader(value = "categoryid", defaultValue = "") String catId,
			@RequestHeader(value = "roleid", defaultValue = "") String roleId,
			@RequestHeader(value = "freetext", defaultValue = "") String freetext) {

		System.out.println(catId + " - " + roleId + " - " + freetext + " - ");
		return typeService.findMatchingCources(catId, roleId, freetext);
	}

	/**
	 * Create contents
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/tools/contents", method = RequestMethod.POST)
	public RequestStatus addResourceContent(@RequestBody ResourceContent content) {

		if (typeService.saveContent(content)) {
			return new RequestStatus("SUCCESS", "SUCCESS");
		} else {
			return new RequestStatus("ERROR", "ERROR");
		}
	}

	@RequestMapping(value = "/tools/type/image/{id}", method = RequestMethod.GET)
	public ResponseEntity getImage(@PathVariable("id") String id) {

		byte[] imageData = null;

		for (ResourceType type : typeService.getAllTypes()) {
			if ((type.getId() + "").equals(id)) {
				imageData = type.getImg();
			}
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg").body(imageData);
	}

	/**
	 * Update contents
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/tools/contents", method = RequestMethod.PUT)
	public RequestStatus updateResourceContent(@RequestBody ResourceContent content) {

		typeService.saveContent(content);
		return new RequestStatus("SUCCESS", "SUCCESS");
	}

	/**
	 * Delete contents from by content id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/tools/contents/{id}", method = RequestMethod.DELETE)
	public RequestStatus removeContent(@PathVariable("id") String id) {

		if (typeService.deleteResourceContent(id)) {
			return new RequestStatus("SUCCESS", "SUCCESS");
		} else {
			return new RequestStatus("ERROR", "ERROR");
		}
	}

}

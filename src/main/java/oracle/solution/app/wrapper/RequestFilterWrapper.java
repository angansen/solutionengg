package oracle.solution.app.wrapper;

import java.util.List;

import oracle.solution.app.beans.ResourceRole;
import oracle.solution.app.beans.ResourceType;

public class RequestFilterWrapper {

	private List<ResourceType> typesList;

	private List<ResourceRole> rolesList;

	public List<ResourceType> getTypesList() {
		return typesList;
	}

	public void setTypesList(List<ResourceType> typesList) {
		this.typesList = typesList;
	}

	public List<ResourceRole> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<ResourceRole> rolesList) {
		this.rolesList = rolesList;
	}

}

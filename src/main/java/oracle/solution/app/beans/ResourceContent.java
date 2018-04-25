package oracle.solution.app.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RESOURCE_CONTENT")
public class ResourceContent {

	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "CONTENT_SEQ")
	// @SequenceGenerator(sequenceName = "content_seq", allocationSize = 1, name =
	// "CONTENT_SEQ")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String description;
	private String link;

	@Transient
	private List<ResourceRole> roles;

	@Column(name = "MAPPED_CATEGORY_IDS")
	private String mappedCategoryIds;

	@Column(name = "MAPPED_ROLES_IDS")
	private String mappedRolesIds;

	public void setRoles(List<ResourceRole> roles) {
		this.roles = roles;
	}

	public List<ResourceRole> getRoles() {
		return roles;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMappedCategoryIds() {
		return mappedCategoryIds;
	}

	public void setMappedCategoryIds(String mappedCategoryIds) {
		this.mappedCategoryIds = mappedCategoryIds;
	}

	public String getMappedRolesIds() {
		return mappedRolesIds;
	}

	public void setMappedRolesIds(String mappedRolesIds) {
		this.mappedRolesIds = mappedRolesIds;
	}

	@Override
	public String toString() {
		return "ResourceContent [id=" + id + ", name=" + name + ", description=" + description + ", link=" + link
				+ ", roles=" + roles + ", mappedCategoryIds=" + mappedCategoryIds + ", mappedRolesIds=" + mappedRolesIds
				+ "]";
	}
}

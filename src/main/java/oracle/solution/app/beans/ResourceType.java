package oracle.solution.app.beans;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "RESOURCE_TYPE")
public class ResourceType {

	@Id
	private Integer id;
	private String name;

	@Lob
	@JsonIgnore
	private byte[] img;

	@Transient
	private List<ResourceContent> contents = new ArrayList();

	public void setImg(byte[] img) {
		this.img = img;
	}

	public byte[] getImg() {
		return img;
	}

	public void setContents(List<ResourceContent> contents) {
		this.contents = contents;
	}

	public List<ResourceContent> getContents() {
		return contents;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		ResourceType ob = (ResourceType) obj;
		return this.id.equals(ob);
	}

}

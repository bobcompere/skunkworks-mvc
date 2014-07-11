package net.fourstrategery.cloud.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	protected Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	protected Date modifiedOn;
	
	/**
	 * Called before insert set active true as default, set the CreatedOn stamp
	 */
	@PrePersist
	protected void preInsert() {
		this.createdOn = new Date();
	}
	
	/**
	 * Called before update, set the Mod stamps
	 */
	@PreUpdate
	protected void preUpdate() {
		this.modifiedOn = new Date();
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}

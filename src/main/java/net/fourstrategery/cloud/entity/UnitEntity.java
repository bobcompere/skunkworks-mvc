package net.fourstrategery.cloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "unit")
public class UnitEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "unit_id_seq")
	@SequenceGenerator(name="unit_id_seq", sequenceName="unit_id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "troops")
	private int troops;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private VenueEntity location;
	
	@Column(name = "next_move_time")
	private Date nextMoveTime;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTroops() {
		return troops;
	}

	public void setTroops(int troops) {
		this.troops = troops;
	}

	public VenueEntity getLocation() {
		return location;
	}

	public void setLocation(VenueEntity location) {
		this.location = location;
	}

	public Date getNextMoveTime() {
		return nextMoveTime;
	}

	public void setNextMoveTime(Date nextMoveTime) {
		this.nextMoveTime = nextMoveTime;
	}
	
	
}


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
@Table(name = "game")
public class GameEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "game_id_seq")
	@SequenceGenerator(name="game_id_seq", sequenceName="game_id_seq", allocationSize=1)
	private int id;
	
	@Column(name = "description")
	private String description;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private PlayerEntity createdBy;
	
	@Column(name = "starts")
	private Date starts;
	
	@Column(name = "ends")
	private Date ends;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PlayerEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(PlayerEntity createdBy) {
		this.createdBy = createdBy;
	}

	public Date getStarts() {
		return starts;
	}

	public void setStarts(Date starts) {
		this.starts = starts;
	}

	public Date getEnds() {
		return ends;
	}

	public void setEnds(Date ends) {
		this.ends = ends;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

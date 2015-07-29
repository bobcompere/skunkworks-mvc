
package net.fourstrategery.cloud.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private PlayerEntity createdBy;
	
	@Column(name = "starts")
	private Date starts;
	
	@Column(name = "ends")
	private Date ends;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	@JsonIgnore
	List<GamePlayerEntity> players;

	@Column(name = "finish_email_sent")
	private Date finish_email_sent;
	
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

	public List<GamePlayerEntity> getPlayers() {
		return players;
	}

	public void setPlayers(List<GamePlayerEntity> players) {
		this.players = players;
	}

	public Date getFinish_email_sent() {
		return finish_email_sent;
	}

	public void setFinish_email_sent(Date finish_email_sent) {
		this.finish_email_sent = finish_email_sent;
	}


	
	
}

package net.fourstrategery.cloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "game_checkin")
@IdClass(GameCheckinKey.class)
public class GameCheckinEntity extends BaseEntity {
	
	@Id
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private GameEntity game;
	
	@Id
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checkin_id")
	private CheckinEntity checkin;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	private PlayerEntity player;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id")
	private UnitEntity unit;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_transaction_id")
	private UnitTransactionEntity unitTransaction;
	
	@Column(name="troops_earned")
	private int troopsEarned;

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public CheckinEntity getCheckin() {
		return checkin;
	}

	public void setCheckin(CheckinEntity checkin) {
		this.checkin = checkin;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}

	public UnitEntity getUnit() {
		return unit;
	}

	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}

	public int getTroopsEarned() {
		return troopsEarned;
	}

	public void setTroopsEarned(int troopsEarned) {
		this.troopsEarned = troopsEarned;
	}

	public UnitTransactionEntity getUnitTransaction() {
		return unitTransaction;
	}

	public void setUnitTransaction(UnitTransactionEntity unitTransaction) {
		this.unitTransaction = unitTransaction;
	}
	
	
}

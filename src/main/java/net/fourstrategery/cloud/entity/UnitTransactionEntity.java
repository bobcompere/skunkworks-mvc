package net.fourstrategery.cloud.entity;

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
@Table(name = "unit_transaction")
public class UnitTransactionEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "unit_transaction_id_seq")
	@SequenceGenerator(name="unit_transaction_id_seq", sequenceName="unit_transaction_id_seq", allocationSize=1)
	private Integer id;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id")
	private UnitEntity unit;
	
	@Column(name = "transaction_type")
	private UnitTransactionType transactionType;
	
	@Column(name = "troop_change")
	private Integer troopChange;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private VenueEntity location;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UnitEntity getUnit() {
		return unit;
	}

	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}


	public Integer getTroopChange() {
		return troopChange;
	}

	public void setTroopChange(Integer troopChange) {
		this.troopChange = troopChange;
	}

	public VenueEntity getLocation() {
		return location;
	}

	public void setLocation(VenueEntity location) {
		this.location = location;
	}

	public UnitTransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(UnitTransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	
}

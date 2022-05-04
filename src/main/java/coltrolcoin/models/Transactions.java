package coltrolcoin.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Transactions {
	
    @Id
    @GeneratedValue
    private int id;

	public float value;
	
	public boolean entry;
	
	public String description;
	
	@Column(name="user_id", nullable=false, insertable = false, updatable = false)
	public int userId;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	public LocalDateTime createdAt;

   

	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public float getValue() {
		return value;
	}



	public void setValue(float value) {
		this.value = value;
	}



	public boolean isEntry() {
		return entry;
	}



	public void setEntry(boolean entry) {
		this.entry = entry;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


}

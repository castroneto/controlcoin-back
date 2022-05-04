package coltrolcoin.models.dtos;

import javax.validation.constraints.NotNull;

public class CreateTransactionDTO {
	
	@NotNull()
	public float value;
	
	@NotNull()
	public boolean entry;
	
	@NotNull()
	public String description;

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
	
}

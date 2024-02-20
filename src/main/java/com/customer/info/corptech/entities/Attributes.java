package com.customer.info.corptech.entities;

import java.util.Objects;

public class Attributes {
	private String type="Customer__C";
	private String referenceId;

	public String getType() {
		return type;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(referenceId, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attributes other = (Attributes) obj;
		return Objects.equals(referenceId, other.referenceId) && Objects.equals(type, other.type);
	}

	public Attributes(String referenceId) {
		super();
		this.referenceId = referenceId;
	}

}

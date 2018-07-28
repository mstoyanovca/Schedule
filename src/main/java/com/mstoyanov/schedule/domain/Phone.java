package com.mstoyanov.schedule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Phone {

	private long phoneId;
	private int version;
	private String number;
	private String type;
	private Student student;

	@Id
	@JsonIgnore
	@Column(name = "phone_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(long phoneId) {
		this.phoneId = phoneId;
	}

	@Version
	@JsonIgnore
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	// @Pattern(regexp = "([1]-?)?\\d{3}-?\\d{3}-?\\d{4}", message = "Invalid
	// phone number.")
	@Size(min = 1, max = 30, message = "Invalid phone number.")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + (int) (phoneId ^ (phoneId >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (phoneId != other.phoneId)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Phone [phoneId=" + phoneId + ", version=" + version + ", number=" + number + ", type=" + type + "]";
	}
}

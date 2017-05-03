package com.schedule.model;

import org.springframework.stereotype.Component;

@Component
public class Profile {
	
	private int teacherId;
	private int studentId;
	private String teacherName;

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentId;
		result = prime * result + teacherId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Profile)) {
			return false;
		}
		Profile other = (Profile) obj;
		if (studentId != other.studentId) {
			return false;
		}
		if (teacherId != other.teacherId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Profile [teacherId=" + teacherId + ", studentId=" + studentId + ", teacherName=" + teacherName + "]";
	}
}

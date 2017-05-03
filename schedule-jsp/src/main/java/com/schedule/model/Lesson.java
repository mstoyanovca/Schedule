package com.schedule.model;

import java.time.DayOfWeek;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lesson")
@NamedQueries({
		@NamedQuery(name = "findAllLessonsByTeacherId", query = "select l from Lesson l join l.teacher t where t.teacherId like :teacherId"),
		@NamedQuery(name = "findAllLessonsByStudentId", query = "select l from Lesson l join l.student s where s.studentId like :studentId") })
public class Lesson {

	private int lessonId;
	private int version;
	private DayOfWeek dow;
	private Date startTime;
	private Date endTime;
	private String note;
	private Teacher teacher;
	private Student student;

	@Id
	@Column(name = "lesson_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getLessonId() {
		return lessonId;
	}

	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@NotNull
	@Enumerated(value = EnumType.STRING)
	public DayOfWeek getDow() {
		return dow;
	}

	public void setDow(DayOfWeek dow) {
		this.dow = dow;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Size(min = 0, max = 256, message = "Note is 256 characters max.")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne
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
		result = prime * result + lessonId;
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
		if (!(obj instanceof Lesson)) {
			return false;
		}
		Lesson other = (Lesson) obj;
		if (lessonId != other.lessonId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Lesson [lessonId=" + lessonId + ", dow=" + dow + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", note=" + note + ", teacher=" + teacher + ", student=" + student + "]";
	}
}

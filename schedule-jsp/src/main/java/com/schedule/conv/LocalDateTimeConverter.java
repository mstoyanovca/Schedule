package com.schedule.conv;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		Instant instant = Instant.from(localDateTime);
		return Timestamp.from(instant);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		Instant instant = timestamp.toInstant();
		return LocalDateTime.from(instant);
	}
}

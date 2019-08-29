package com.ifinrelax.configuration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

import static java.sql.Date.valueOf;

/**
 * The class is supposed to convert LocalDate to sql Date, because JPA 2.1 doesn't suppor java 8 time api.
 *
 * @author Timur Berezhnoi
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param localDate the entity attribute value to be converted
     * @return the converted data to be stored in the database column
     */
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return (localDate != null) ? valueOf(localDate) : null;
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct dbData type for the corresponding column
     * for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param sqlDate the data from the database column to be converted
     * @return the converted value to be stored in the entity attribute
     */
    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        return (sqlDate != null) ? sqlDate.toLocalDate() : null;
    }
}

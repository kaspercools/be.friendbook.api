package be.friendbook.repository.converters;
import java.time.LocalDate;
import java.sql.Date;
import javax.persistence.AttributeConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kaspercools
 */
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate,Date>{

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
    	return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
    	return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
    
}

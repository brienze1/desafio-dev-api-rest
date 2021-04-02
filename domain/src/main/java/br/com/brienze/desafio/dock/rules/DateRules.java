package br.com.brienze.desafio.dock.rules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class DateRules {

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
    public LocalDateTime validate(String dateStr) {
        LocalDate data = null;
    	try {
            data = LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
        	throw new DateTimeParseException("data_nascimento deve ser informada no padrao dd/MM/yyyy", dateStr, e.getErrorIndex());
        }
        return LocalDateTime.of(data.getYear(), data.getMonthValue(), data.getDayOfMonth(), 0, 0);
    }
    
}

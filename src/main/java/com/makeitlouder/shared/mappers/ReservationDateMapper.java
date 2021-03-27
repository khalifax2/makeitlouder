package com.makeitlouder.shared.mappers;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReservationDateMapper {

    public String asString(Date date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
                .format( date ) : null;
    }

    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
                    .parse( date ) : null;
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }
}

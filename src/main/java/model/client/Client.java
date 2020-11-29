package model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import util.DateUtils;

import java.util.Date;
import java.util.Objects;


/**
 * The type Client.
 */
@Data
@AllArgsConstructor
public class Client {
    /**
     * Id of client
     */
    private int id;
    /**
     * FIO of client
     */
    private String fio;
    /**
     * Date of client's birth
     */
    private Date birthDate;
    /**
     * Client sex
     */
    private Sex sex;
    /**
     * PassportSeries and number of client
     */
    private int passportSeriesNumber;

    @Override
    public boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass ( ) != o.getClass ( ) ) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public String toString ( ) {
        return id +
                ",\t" + fio +
                ",\t" + DateUtils.getString (birthDate )+
                ",\t" + sex.name () +
                ",\t" + passportSeriesNumber ;
    }

    @Override
    public int hashCode ( ) {
        return Objects.hash ( id );
    }
}

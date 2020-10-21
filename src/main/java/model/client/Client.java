package model.client;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


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

}

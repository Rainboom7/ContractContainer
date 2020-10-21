package model.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.client.Client;

import java.util.Date;

/**
 * The type Cellular contract.
 */
@Data
@AllArgsConstructor
public class CellularContract extends Contract {
    /**
     * Included minutes for calls
     */
    private int minutes;

    /**
     * Included megabytes for internet
     */
    private int megabytes;
    /**
     * Included amount  of sms
     */
    private int smsAmount;

    public CellularContract ( int id, Date beginningDate, Date endingDate, Client client, int minutes ) {
        super ( id, beginningDate, endingDate, client );
        this.minutes = minutes;
    }


}

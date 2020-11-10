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

    public CellularContract ( int id, Date beginningDate, Date endingDate, Client client, int minutes, int megabytes, int smsAmount ) {
        super ( id, beginningDate, endingDate, client );
        this.minutes = minutes;
        this.megabytes = megabytes;
        this.smsAmount = smsAmount;
    }

    @Override
    public String toString ( ) {
        return "CellularContract,\t" +
                super.toString ( ) + ",\t" +
                minutes + ",\t" +
                megabytes + ",\t" +
                smsAmount + "\n";
    }


}

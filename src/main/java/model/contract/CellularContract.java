package model.contract;

import lombok.Data;

/**
 * The type Cellular contract.
 */
@Data
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

}

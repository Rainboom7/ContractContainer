package model.contract;

import lombok.Data;
import model.client.Client;

import java.util.Date;

/**
 * The type Contract.
 */
@Data
public abstract class Contract {
    /**
     * Id of contract
     */
    private int id;
    /**
     * Date of beginning of contract
     */
    private Date beginningDate;
    /**
     * Date of ending of contract
     */
    private Date endingDate;
    /**
     * Contract client
     */
    private Client client;


}

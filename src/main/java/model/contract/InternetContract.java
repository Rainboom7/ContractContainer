package model.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.client.Client;

import java.util.Date;

/**
 * The type Internet contract.
 */
@Data
public class InternetContract extends Contract {
    public InternetContract ( int id, Date beginningDate, Date endingDate, Client client, int maxMegabytesSpeed ) {
        super ( id, beginningDate, endingDate, client );
        this.maxMegabytesSpeed = maxMegabytesSpeed;
    }

    /**
     * Max connection speed in mb/s
     */
    private int maxMegabytesSpeed;

}

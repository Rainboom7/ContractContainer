package model.contract;

import lombok.Data;

/**
 * The type Internet contract.
 */
@Data
public class InternetContract extends Contract {
    /**
     * Max connection speed in mb/s
     */
    private int maxMegabytesSpeed;

}

package model.contract;

import lombok.Data;

/**
 * The type Television contract.
 */
@Data
public class TelevisionContract extends Contract {
    /**
     * Type of channel package
     */
    private ChannelPackage channelPackage;
}

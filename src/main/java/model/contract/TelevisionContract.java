package model.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.client.Client;

import java.util.Date;

/**
 * The type Television contract.
 */
@Data
@AllArgsConstructor
public class TelevisionContract extends Contract {
    @Override
    public String toString ( ) {
        return "TelevisionContract,\t" +
                super.toString ()+",\t"+
                channelPackage +"\n";
    }

    /**
     * Type of channel package
     */
    private ChannelPackage channelPackage;

    public TelevisionContract ( int id, Date beginningDate, Date endingDate, Client client, ChannelPackage channelPackage ) {
        super ( id, beginningDate, endingDate, client );
        this.channelPackage = channelPackage;
    }
}

package model.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.client.Client;

import java.util.Date;
import java.util.Objects;

/**
 * The type Contract.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Contract implements Comparable<Contract> {
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

    @Override
    public boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass ( ) != o.getClass ( ) ) return false;
        Contract contract = (Contract) o;
        return id == contract.id;
    }

    @Override
    public int hashCode ( ) {
        return Objects.hash ( id );
    }


    @Override
    public int compareTo ( Contract o ) {
        if ( this.id == o.getId () )
            return 0;
        return this.id > o.getId () ? 1 : -1;
    }
}

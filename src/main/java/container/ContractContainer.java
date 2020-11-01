package container;

import lombok.var;
import model.client.Client;
import model.contract.Contract;

import java.util.*;

/**
 * The type Contract container.
 */
public class ContractContainer {
    /**
     * Contract container.
     */
    private Contract[] contracts;
    /**
     * Index of last contract.
     */
    private int lastContract;
    /**
     * capacity of contracts array.
     */
    private int capacity;
    /**
     * Value that specifies in how many times you want to increase the array.
     */
    private static final double SIZE_MULTIPLICAND = 1.5;
    /**
     * Capacity of array in the moment of creation.
     */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * Instantiates a new Contract container.
     */
    public ContractContainer ( ) {
        this.capacity = DEFAULT_CAPACITY;
        this.lastContract = -1;
        this.contracts = new Contract[ this.capacity ];
    }

    /**
     * Adds new contract and icreases the size if current capacity is not enough
     *
     * @param contract the contract you want to add
     */
    public void add ( Contract contract ) {
        if ( this.lastContract >= this.capacity - 1 ) {
            this.capacity *= SIZE_MULTIPLICAND;
            Contract[] resizedContracts = new Contract[ this.capacity ];
            System.arraycopy ( this.contracts, 0, resizedContracts, 0, lastContract + 1 );
            this.contracts = resizedContracts;
        }
        this.lastContract++;
        this.contracts[ lastContract ] = contract;
    }

    /**
     * Delete contract with given id
     *
     * @param searchId the id of contract you want to delete
     * @throws NoSuchElementException if contract with given id is not present
     */
    public void deleteAt ( int searchId ) {
        int index = findIndexOf ( searchId );
        for (int i = index; i < lastContract - 1; i++) {
            this.contracts[ i ] = this.contracts[ i + 1 ];
        }
        this.contracts[ lastContract ] = null;
        this.lastContract--;
    }
    /**
     * Gets all stored contracts
     *
     * @return optional of stored contracts
     */
    public Optional<Contract[]> getAll ( ) {
        return Optional.of (
                Arrays.stream ( this.contracts )
                        .filter ( Objects::nonNull ).toArray ( Contract[]::new ) );
    }

    /**
     * Gets by contract by id.
     *
     * @param searchId the id of contract you want to find
     * @return optional of found contract or null if not present
     */
    public Optional<Contract> getById ( int searchId ) {
        int index = findIndexOf ( searchId );
        Optional<Contract> contract = ( index == -1 ) ? Optional.empty ( ) : Optional.of ( this.contracts[ index ] );
        return contract;

    }

    /**
     * Gets contracts with beginning date after parameter date.
     *
     * @param begin the  date after which you want to search
     * @return optional of found contracts
     */
    public Optional<Contract[]> getContractsAfter ( Date begin ) {
        return Optional.of ( Arrays.stream ( this.contracts )
                .filter ( c ->
                        c != null
                                &&
                                c.getBeginningDate ( ).after ( begin ) )
                .toArray ( Contract[]::new ) );

    }

    /**
     * Gets cantracts which begin and end dates are between parameters
     *
     * @param begin the  date after which you want to search
     * @param end   the  date before which you want to search
     * @return optional of found contracts
     */
    public Optional<Contract[]> getContractsBetween ( Date begin, Date end ) {
        return Optional.of ( (Contract[]) Arrays.stream ( this.contracts )
                .filter ( c -> c != null
                        &&
                        c.getBeginningDate ( ).after ( begin )
                        &&
                        c.getEndingDate ( ).before ( end )
                ).toArray ( Contract[]::new ) );

    }

    /**
     * Gets cantracts for specific client
     *
     * @param client client whose contracts you want to search
     * @return optional of found contracts
     */
    public Optional<Contract[]> getByClient ( Client client ) {

        return Optional.of ( Arrays.stream ( this.contracts )
                .filter ( c ->
                        c != null
                                &&
                                c.getClient ( ).equals ( client )
                ).toArray ( Contract[]::new ) );
    }

    /**
     * Sorts contracts by id
     */
    public void sortById ( ) {

        this.contracts = Arrays.stream ( this.contracts ).sorted ( Comparator.nullsLast ( Comparator.comparing ( Contract::getId ) ) ).toArray ( Contract[]::new );
    }

    /**
     * Sorts contracts by client fio
     */
    public void sortByClientName ( ) {

        this.contracts = Arrays.stream ( this.contracts ).sorted ( Comparator.nullsLast ( Comparator.comparing ( c -> c.getClient ( ).getFio ( ), String::compareToIgnoreCase ) ) ).toArray ( Contract[]::new );
    }

    /**
     * Sorts contracts by client id
     */
    public void sortByClientId ( ) {

        this.contracts = Arrays.stream ( this.contracts ).sorted ( Comparator.nullsLast ( Comparator.comparing ( c -> c.getClient ( ).getId ( ), Integer::compareTo ) ) ).toArray ( Contract[]::new );
    }

    /**
     * Sorts contracts by beginning date
     */
    public void sortByBeginDate ( ) {
        this.contracts = Arrays.stream ( this.contracts ).sorted ( Comparator.nullsLast ( Comparator.comparing ( Contract::getBeginningDate, Date::compareTo ) ) ).toArray ( Contract[]::new );
    }

    /**
     * Sorts contracts by ending date
     */
    public void sortByEndDate ( ) {

        this.contracts = Arrays.stream ( this.contracts ).sorted ( Comparator.nullsLast ( Comparator.comparing ( Contract::getEndingDate, Date::compareTo ) ) ).toArray ( Contract[]::new );
    }

    /**
     * Util method to find contract with given id;
     *
     * @param searchId the id of contract you want to find
     * @return found contract index in array or -1 if not present
     */
    private int findIndexOf ( final int searchId ) {
        int index = -1;
        try {
            index = Arrays.binarySearch ( this.contracts, new Contract ( ) {
                @Override
                public int getId ( ) {
                    return searchId;
                }

            } );
        } catch ( NullPointerException e ) {
            return -1;
        }
        return index;
    }

    @Override
    public String toString ( ) {
        String value = "ContractContainer{" +
                "contracts=";
        for (int i = 0; i <= lastContract; i++)
            value += this.contracts[ i ].toString ( );
        value += '}';
        return value;
    }

    /**
     * Capacity of container.
     *
     * @return the int value of capacity
     */
    public int capacity ( ) {
        return this.lastContract;
    }
}

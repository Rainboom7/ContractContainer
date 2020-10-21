package container;

import model.contract.Contract;

import java.util.Arrays;
import java.util.NoSuchElementException;

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
     * Gets by contract by id.
     *
     * @param searchId the id of contract you want to find
     * @return found contract
     * @throws NoSuchElementException if contract with given id is not present
     */
    public Contract getById ( int searchId ) {
        int index = findIndexOf ( searchId );
        return this.contracts[ index ];

    }

    /**
     * Util method to find contract with given id;
     *
     * @param searchId the id of contract you want to find
     * @return found contract index in array
     * @throws NoSuchElementException if contract with given id is not present
     */
    private int findIndexOf ( final int searchId ) {
        int index=-1;
        try {
            index = Arrays.binarySearch ( this.contracts, new Contract ( ) {
                @Override
                public int getId ( ) {
                    return searchId;
                }

            } );
        } catch ( NullPointerException e ) {
            throw new NoSuchElementException ( "Contract with id " + searchId + " not found" );
        }
        if ( index == -1 )
            throw new NoSuchElementException ( "Contract with id " + searchId + " not found" );
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

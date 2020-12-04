package container;

import annotations.Inject;
import model.client.Client;
import model.contract.Contract;
import util.sorter.BubbleContractSorter;
import util.sorter.ContainerSorter;
import util.sorter.MergeContractSorter;

import java.util.*;
import java.util.function.Predicate;

/**
 * The type Contract container.
 */
public class ContractContainer implements Container<Contract> {
    /**
     * Contract container.
     */
    private Contract[] contracts;
    /**
     * Sorter that performs contracts' sorting.
     */
    @Inject
    private ContainerSorter<Contract> sorter;
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
     * Instantiates a new Contract container with default merge sorter;
     */
    public ContractContainer ( ) {
        this.capacity = DEFAULT_CAPACITY;
        this.lastContract = -1;
        this.contracts = new Contract[ this.capacity ];
    }
    /**
     * Instantiates a new Contract container with chosen sorter merge sorter;
     */
    public ContractContainer ( ContainerSorter<Contract> sorter ) {
        this.capacity = DEFAULT_CAPACITY;
        this.lastContract = -1;
        this.contracts = new Contract[ this.capacity ];
        this.sorter = sorter;

    }
    /**
     * Instantiates a new Contract container with default merge sorter and with given contracts by another container;
     */
    public ContractContainer ( ContractContainer container ) {
        this.capacity = container.capacity ( );
        this.lastContract = container.capacity ( );
        this.contracts = container.getAll ( );
    }
    /**
     * Instantiates a new Contract container with given sorter and with given contracts by another container;
     */
    public ContractContainer ( ContractContainer container,ContainerSorter<Contract> sorter ) {
        this.capacity = container.capacity ( );
        this.lastContract = container.capacity ( );
        this.contracts = container.getAll ( );
        this.sorter = sorter;
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
     * Gets by contract by predicate.
     *
     * @param predicate the condition to rely on while searching
     * @return new container with found contractis
     */
    @Override
    public Container<Contract> search ( Predicate<? super Contract> predicate ) {
        Container<Contract> found = new ContractContainer ( );
        for (var contract : this.contracts
        ) {
            if ( contract != null && predicate.test ( contract ) )
                found.add ( contract );
        }
        return found;
    }


    /**
     * Gets all  contracts
     *
     * @return optional of stored contracts
     */
    public Contract[] getAll ( ) {
        return this.contracts;
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

    /**
     * Sorts contracts with given sorter and comparator.
     *
     * @param sorter     the sorter which you want to use while sorting
     * @param comparator comparator which defines by which attribute you want to sort
     * @return the int value of capacity
     */
    @Override
    public void sort ( Comparator<? super Contract> comparator ) {
        this.sorter.sort ( this.contracts, comparator );
    }
}

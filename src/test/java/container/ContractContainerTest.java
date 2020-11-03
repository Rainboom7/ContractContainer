package container;

import lombok.var;
import model.client.Client;
import model.client.Sex;
import model.contract.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.ContractComparators;
import util.ContractPredicates;
import util.sorter.BubbleContractSorter;
import util.sorter.MergeContractSorter;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

public class ContractContainerTest {
    private ContractContainer container;
    private final Client firstClient = new Client ( 1, "Ivan Ivanovich Ivanov", new Date ( 2000 ), Sex.MALE, 13233123 );
    private final Client secondClient = new Client ( 2, "Olga Petrova Ivanovna", new Date ( 1980 ), Sex.FEMALE, 1232133 );

    private final Contract cellularContract = new CellularContract ( 1,
            new GregorianCalendar ( 2010, 0, 11 ).getTime ( ),
            new GregorianCalendar ( 2012, 0, 11 ).getTime ( ), firstClient, 132 );
    private final Contract internetContract = new InternetContract ( 2,
            new GregorianCalendar ( 2014, 0, 11 ).getTime ( ),
            new GregorianCalendar ( 2016, 0, 11 ).getTime ( ), firstClient, 75 );
    private final Contract televisionContract = new TelevisionContract ( 3,
            new GregorianCalendar ( 2016, 0, 11 ).getTime ( ),
            new GregorianCalendar ( 2018, 0, 11 ).getTime ( ), secondClient, ChannelPackage.MOVIES );


    @Before
    public void refillContainer ( ) {
        this.container = new ContractContainer ( );
        container.add ( cellularContract );
        container.add ( televisionContract );
        container.add ( internetContract );
    }

    @Test
    public void addingNewElementWorks ( ) {
        int beginCapacity = this.container.capacity ( );
        Contract newTelevisionContract = new TelevisionContract ( 2, new Date ( ), new Date ( ), secondClient, ChannelPackage.MOVIES );
        this.container.add ( newTelevisionContract );
        Assert.assertEquals ( this.container.capacity ( ), beginCapacity + 1 );
    }

    @Test
    public void gettingElementByIdWorks ( ) {
        int expectedId = 57;
        Contract newTelevisionContract = new TelevisionContract ( expectedId, new Date ( ), new Date ( ), secondClient, ChannelPackage.MOVIES );
        Assert.assertThrows ( NoSuchElementException.class, ( ) -> {
            this.container.getById ( expectedId ).get ( );
        } );
        this.container.add ( newTelevisionContract );
        Assert.assertEquals ( this.container.getById ( expectedId ).get ( ), newTelevisionContract );

    }

    @Test
    public void throwsExceptionWhenNoSuchElement ( ) {
        Assert.assertThrows ( NoSuchElementException.class, ( ) -> {
            this.container.getById ( 999 ).get ( );
        } );

    }

    @Test
    public void searchesByClientWorks ( ) {
        var contract = this.container.search ( ContractPredicates.byClient ( firstClient ) );
        Assert.assertEquals ( contract.capacity ( ), 1 );

    }

    @Test
    public void searchByDateWorks ( ) {
        var expectedContract = new TelevisionContract ( 4,
                new GregorianCalendar ( 2018, 8, 11 ).getTime ( ),
                new GregorianCalendar ( 2017, 0, 13 ).getTime ( ), secondClient,
                ChannelPackage.MOVIES );
        this.container.add ( expectedContract );
        var begin = new GregorianCalendar ( 2018, 0, 11 ).getTime ( );
        var end = new GregorianCalendar ( 2019, 0, 11 ).getTime ( );
        var foundAfter = this.container.search ( ContractPredicates.byDateAfter ( begin ) );
        var foundBetween = this.container.search ( ContractPredicates.byDateBetween ( begin, end ) );
        Assert.assertEquals ( expectedContract, foundAfter.getAll ( )[ 0 ] );
        Assert.assertEquals ( expectedContract, foundBetween.getAll ( )[ 0 ] );
    }

    @Test
    public void sortByDateWorks ( ) {
        var expectedContract = new TelevisionContract ( 4,
                new GregorianCalendar ( 1000, 8, 11 ).getTime ( ),
                new GregorianCalendar ( 2035, 0, 13 ).getTime ( ), secondClient,
                ChannelPackage.MOVIES );
        this.container.add ( expectedContract );
        MergeContractSorter mergeContractSorter = new MergeContractSorter ( );
        this.container.sort ( mergeContractSorter, ContractComparators.byBeginningDate ( ) );
        var contracts = this.container.getAll ( );
        Assert.assertEquals ( contracts[ 0 ], expectedContract );

        this.container.sort ( mergeContractSorter, ContractComparators.byEndingDate ( ) );
        contracts = this.container.getAll ( );
        var lastInd = this.container.capacity ( );
        Assert.assertEquals ( contracts[ lastInd ], expectedContract );

    }

    @Test
    public void sortByClientWorkds ( ) {
        var newClient = new Client ( 8, "Alexandra Petrova Ivanovna", new Date ( 1980 ), Sex.FEMALE, 1232133 );
        var expectedContract = new TelevisionContract ( 4,
                new GregorianCalendar ( 1000, 8, 11 ).getTime ( ),
                new GregorianCalendar ( 2017, 0, 13 ).getTime ( ), newClient,
                ChannelPackage.MOVIES );
        this.container.add ( expectedContract );
        BubbleContractSorter bubbleContractSorter = new BubbleContractSorter ( );
        this.container.sort ( bubbleContractSorter, ContractComparators.byClientName ( ) );
        var contracts = this.container.getAll ( );
        Assert.assertEquals ( contracts[ 0 ], expectedContract );
        var lastInd = this.container.capacity ( );
        Assert.assertEquals ( contracts[ lastInd ], expectedContract );
    }


    @Test
    public void deletingByIdWorks ( ) {
        int expectedId = 22;
        Contract newTelevisionContract = new TelevisionContract ( expectedId, new Date ( ), new Date ( ), secondClient, ChannelPackage.MOVIES );
        this.container.add ( newTelevisionContract );
        Assert.assertEquals ( this.container.getById ( expectedId ).get ( ), newTelevisionContract );
        int beginCapacity = this.container.capacity ( );
        this.container.deleteAt ( expectedId );
        Assert.assertEquals ( this.container.capacity ( ), beginCapacity - 1 );
        Assert.assertThrows ( NoSuchElementException.class, ( ) -> {
            this.container.getById ( expectedId ).get ( );
        } );
    }


}

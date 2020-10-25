package container;

import model.client.Client;
import model.client.Sex;
import model.contract.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.NoSuchElementException;

public class ContractContainerTest {
    private ContractContainer container;
    private final Client firstClient = new Client ( 1, "Ivan Ivanovich Ivanov", new Date ( 2000 ), Sex.MALE, 13233123 );
    private final Client secondClient = new Client ( 2, "Olga Petrova Ivanovna", new Date ( 1980 ), Sex.FEMALE, 1232133 );

    private final Contract cellularContract = new CellularContract ( 1, new Date ( ), new Date ( ), firstClient, 132 );
    private final Contract internetContract = new InternetContract ( 2, new Date ( ), new Date ( ), firstClient, 75 );
    private final Contract televisionContract = new TelevisionContract ( 3, new Date ( ), new Date ( ), secondClient, ChannelPackage.MOVIES );


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
            this.container.getById ( expectedId ).get ();
        } );
        this.container.add ( newTelevisionContract );
        Assert.assertEquals ( this.container.getById ( expectedId ).get (),newTelevisionContract );

    }

    @Test
    public void throwsExceptionWhenNoSuchElement ( ) {
        Assert.assertThrows ( NoSuchElementException.class, ( ) -> {
            this.container.getById ( 999 ).get ();
        } );

    }

    @Test
    public void deletingByIdWorks ( ) {
        int expectedId = 22;
        Contract newTelevisionContract = new TelevisionContract ( expectedId, new Date ( ), new Date ( ), secondClient, ChannelPackage.MOVIES );
        this.container.add ( newTelevisionContract );
        Assert.assertEquals ( this.container.getById ( expectedId ).get (),newTelevisionContract );
        int beginCapacity =this.container.capacity ();
        this.container.deleteAt ( expectedId );
        Assert.assertEquals ( this.container.capacity (),beginCapacity-1 );
        Assert.assertThrows ( NoSuchElementException.class, ( ) -> {
            this.container.getById ( expectedId ).get ();
        } );
    }


}

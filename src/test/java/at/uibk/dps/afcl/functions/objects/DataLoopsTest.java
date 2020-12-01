package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.Collections;

public class DataLoopsTest {
    /**
     * Test full construction of a dataLoop.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        DataLoops dataLoops = new DataLoops("name", "type");

        Assert.assertEquals("name", dataLoops.getName());
        Assert.assertEquals("type", dataLoops.getType());
    }

    /**
     * Test the empty construction of a dataLoop.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        DataLoops dataLoops = new DataLoops();

        Assert.assertNull(dataLoops.getName());
        Assert.assertNull(dataLoops.getType());
        Assert.assertNull(dataLoops.getConstraints());
        Assert.assertNull(dataLoops.getInitSource());
        Assert.assertNull(dataLoops.getLoopSource());
        Assert.assertNull(dataLoops.getPassing());
        Assert.assertNull(dataLoops.getProperties());
        Assert.assertNull(dataLoops.getValue());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(DataLoops.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        DataLoops dataLoops = new DataLoops("name", "type");

        Assert.assertEquals(dataLoops, dataLoops);
        Assert.assertEquals(dataLoops.hashCode(), dataLoops.hashCode());
        Assert.assertNotEquals(dataLoops, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(dataLoops, compound);

        DataLoops dataLoops2 = new DataLoops("name", "type");
        Assert.assertEquals(dataLoops, dataLoops2);
        Assert.assertEquals(dataLoops.hashCode(), dataLoops2.hashCode());

        DataLoops dataLoops3;
        dataLoops3 = new DataLoops("nameWrong", "type");
        Assert.assertNotEquals(dataLoops, dataLoops3);

        dataLoops3 = new DataLoops("name", "typeWrong");
        Assert.assertNotEquals(dataLoops, dataLoops3);

        dataLoops3 = new DataLoops("name", "type");
        dataLoops3.setInitSource("initSource");
        Assert.assertNotEquals(dataLoops, dataLoops3);

        dataLoops3 = new DataLoops("name", "type");
        dataLoops3.setLoopSource("loopSource");
        Assert.assertNotEquals(dataLoops, dataLoops3);

        dataLoops3 = new DataLoops("name", "type");
        dataLoops3.setValue("value");
        Assert.assertNotEquals(dataLoops, dataLoops3);

        dataLoops3 = new DataLoops("name", "type");
        dataLoops3.setPassing(true);
        Assert.assertNotEquals(dataLoops, dataLoops3);

        dataLoops3 = new DataLoops("name", "type");
        dataLoops3.setProperties(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataLoops, dataLoops3);

        dataLoops3 = new DataLoops("name", "type");
        dataLoops3.setConstraints(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataLoops, dataLoops3);
    }
}

package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.Collections;

/**
 * Test the functionality of a data ouput object.
 *
 * @author stefanpedratscher
 */
public class DataOutsTest {
    /**
     * Test full construction of a dataOuts.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        final DataOuts dataOuts = new DataOuts("name", "type", "source");

        Assert.assertEquals("name", dataOuts.getName());
        Assert.assertEquals("type", dataOuts.getType());
        Assert.assertEquals("source", dataOuts.getSource());
    }

    /**
     * Test the empty construction of a dataOuts.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final DataOuts dataOuts = new DataOuts();

        Assert.assertNull(dataOuts.getName());
        Assert.assertNull(dataOuts.getType());
        Assert.assertNull(dataOuts.getSource());
        Assert.assertNull(dataOuts.getConstraints());
        Assert.assertNull(dataOuts.getPassing());
        Assert.assertNull(dataOuts.getProperties());
        Assert.assertNull(dataOuts.getSaveto());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(DataOuts.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        final DataOuts dataOuts = new DataOuts("name", "type", "source");

        Assert.assertEquals(dataOuts, dataOuts);
        Assert.assertEquals(dataOuts.hashCode(), dataOuts.hashCode());
        Assert.assertNotEquals(dataOuts, null);

        final Compound compound = new Compound();
        Assert.assertNotEquals(dataOuts, compound);

        final DataOuts dataOuts2 = new DataOuts("name", "type", "source");
        Assert.assertEquals(dataOuts, dataOuts2);
        Assert.assertEquals(dataOuts.hashCode(), dataOuts2.hashCode());

        DataOuts dataOuts3;
        dataOuts3 = new DataOuts("nameWrong", "type", "source");
        Assert.assertNotEquals(dataOuts, dataOuts3);

        dataOuts3 = new DataOuts("name", "typeWrong", "source");
        Assert.assertNotEquals(dataOuts, dataOuts3);

        dataOuts3 = new DataOuts("name", "type", "sourceWrong");
        Assert.assertNotEquals(dataOuts, dataOuts3);

        dataOuts3 = new DataOuts("name", "type", "source");
        dataOuts3.setSaveto("file");
        Assert.assertNotEquals(dataOuts, dataOuts3);

        dataOuts3 = new DataOuts("name", "type", "source");
        dataOuts3.setPassing(true);
        Assert.assertNotEquals(dataOuts, dataOuts3);

        dataOuts3 = new DataOuts("name", "type", "source");
        dataOuts3.setProperties(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataOuts, dataOuts3);

        dataOuts3 = new DataOuts("name", "type", "source");
        dataOuts3.setConstraints(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataOuts, dataOuts3);
    }
}

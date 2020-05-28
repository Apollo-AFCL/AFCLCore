package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.Collections;

public class DataOutsAtomicTest {
    /**
     * Test full construction of a dataOutsAtomic.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        DataOutsAtomic dataOutsAtomic = new DataOutsAtomic("name", "type");

        Assert.assertEquals("name", dataOutsAtomic.getName());
        Assert.assertEquals("type", dataOutsAtomic.getType());
    }

    /**
     * Test the empty construction of a dataOutsAtomic.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        DataOutsAtomic dataOutsAtomic = new DataOutsAtomic();

        Assert.assertNull(dataOutsAtomic.getName());
        Assert.assertNull(dataOutsAtomic.getType());
        Assert.assertNull(dataOutsAtomic.getConstraints());
        Assert.assertNull(dataOutsAtomic.getPassing());
        Assert.assertNull(dataOutsAtomic.getProperties());
        Assert.assertNull(dataOutsAtomic.getSaveto());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(DataOutsAtomic.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        DataOutsAtomic dataOutsAtomic = new DataOutsAtomic("name", "type");

        Assert.assertEquals(dataOutsAtomic, dataOutsAtomic);
        Assert.assertEquals(dataOutsAtomic.hashCode(), dataOutsAtomic.hashCode());
        Assert.assertNotEquals(dataOutsAtomic, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(dataOutsAtomic, compound);

        DataOutsAtomic dataOutsAtomic2 = new DataOutsAtomic("name", "type");
        Assert.assertEquals(dataOutsAtomic, dataOutsAtomic2);
        Assert.assertEquals(dataOutsAtomic.hashCode(), dataOutsAtomic2.hashCode());

        DataOutsAtomic dataOutsAtomic3;
        dataOutsAtomic3 = new DataOutsAtomic("nameWrong", "type");
        Assert.assertNotEquals(dataOutsAtomic, dataOutsAtomic3);

        dataOutsAtomic3 = new DataOutsAtomic("name", "typeWrong");
        Assert.assertNotEquals(dataOutsAtomic, dataOutsAtomic3);

        dataOutsAtomic3 = new DataOutsAtomic("name", "type");
        dataOutsAtomic3.setSaveto("file");
        Assert.assertNotEquals(dataOutsAtomic, dataOutsAtomic3);

        dataOutsAtomic3 = new DataOutsAtomic("name", "type");
        dataOutsAtomic3.setPassing(true);
        Assert.assertNotEquals(dataOutsAtomic, dataOutsAtomic3);

        dataOutsAtomic3 = new DataOutsAtomic("name", "type");
        dataOutsAtomic3.setProperties(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataOutsAtomic, dataOutsAtomic3);

        dataOutsAtomic3 = new DataOutsAtomic("name", "type");
        dataOutsAtomic3.setConstraints(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataOutsAtomic, dataOutsAtomic3);
    }
}

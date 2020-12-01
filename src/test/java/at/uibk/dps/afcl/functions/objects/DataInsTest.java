package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.Collections;

public class DataInsTest {
    /**
     * Test full construction of a dataIns.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        DataIns dataIns = new DataIns("name", "type", "source");

        Assert.assertEquals("name", dataIns.getName());
        Assert.assertEquals("type", dataIns.getType());
        Assert.assertEquals("source", dataIns.getSource());
    }

    /**
     * Test the empty construction of a dataIns.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        DataIns dataIns = new DataIns();

        Assert.assertNull(dataIns.getName());
        Assert.assertNull(dataIns.getType());
        Assert.assertNull(dataIns.getSource());
        Assert.assertNull(dataIns.getConstraints());
        Assert.assertNull(dataIns.getPassing());
        Assert.assertNull(dataIns.getProperties());
        Assert.assertNull(dataIns.getValue());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(DataIns.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        DataIns dataIns = new DataIns("name", "type", "source");

        Assert.assertEquals(dataIns, dataIns);
        Assert.assertEquals(dataIns.hashCode(), dataIns.hashCode());
        Assert.assertNotEquals(dataIns, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(dataIns, compound);

        DataIns dataIns2 = new DataIns("name", "type", "source");
        Assert.assertEquals(dataIns, dataIns2);
        Assert.assertEquals(dataIns.hashCode(), dataIns2.hashCode());

        DataIns dataIns3;
        dataIns3 = new DataIns("nameWrong", "type", "source");
        Assert.assertNotEquals(dataIns, dataIns3);

        dataIns3 = new DataIns("name", "typeWrong", "source");
        Assert.assertNotEquals(dataIns, dataIns3);

        dataIns3 = new DataIns("name", "type", "sourceWrong");
        Assert.assertNotEquals(dataIns, dataIns3);

        dataIns3 = new DataIns("name", "type", "source");
        dataIns3.setValue("value");
        Assert.assertNotEquals(dataIns, dataIns3);

        dataIns3 = new DataIns("name", "type", "source");
        dataIns3.setPassing(true);
        Assert.assertNotEquals(dataIns, dataIns3);

        dataIns3 = new DataIns("name", "type", "source");
        dataIns3.setProperties(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataIns, dataIns3);

        dataIns3 = new DataIns("name", "type", "source");
        dataIns3.setConstraints(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(dataIns, dataIns3);
    }
}

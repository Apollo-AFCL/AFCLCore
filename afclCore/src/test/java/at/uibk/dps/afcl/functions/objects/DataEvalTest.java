package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

public class DataEvalTest {
    /**
     * Test full construction of a dataEval.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        DataEval dataEval = new DataEval("name", "type");

        Assert.assertEquals("name", dataEval.getName());
        Assert.assertEquals("type", dataEval.getType());
        Assert.assertEquals(0, dataEval.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a dataEval.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        DataEval dataEval = new DataEval();

        Assert.assertNull(dataEval.getName());
        Assert.assertNull(dataEval.getType());
        Assert.assertNull(dataEval.getSource());
        Assert.assertEquals(0, dataEval.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(DataEval.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        DataEval dataEval = new DataEval("name", "type");

        Assert.assertEquals(dataEval, dataEval);
        Assert.assertEquals(dataEval.hashCode(), dataEval.hashCode());
        Assert.assertNotEquals(dataEval, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(dataEval, compound);

        DataEval dataEval2 = new DataEval("name", "type");
        Assert.assertEquals(dataEval, dataEval2);
        Assert.assertEquals(dataEval.hashCode(), dataEval2.hashCode());
        dataEval2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(dataEval, dataEval2);

        DataEval dataEval3;
        dataEval3 = new DataEval("nameWrong", "type");
        Assert.assertNotEquals(dataEval, dataEval3);

        dataEval3 = new DataEval("name", "typeWrong");
        Assert.assertNotEquals(dataEval, dataEval3);

        dataEval3 = new DataEval("name", "type");
        dataEval3.setSource("source");
        Assert.assertNotEquals(dataEval, dataEval3);
    }
}

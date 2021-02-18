package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * Test the functionality of a condition object.
 *
 * @author stefanpedratscher
 */
public class AConditionTest {
    /**
     * Test full construction of an aCondition.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        final ACondition aCondition = new ACondition("1", "2", "==");

        Assert.assertEquals("1", aCondition.getData1());
        Assert.assertEquals("2", aCondition.getData2());
        Assert.assertEquals("==", aCondition.getOperator());
        Assert.assertEquals(0, aCondition.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of an aCondition.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final ACondition aCondition = new ACondition();

        Assert.assertNull(aCondition.getData1());
        Assert.assertNull(aCondition.getData2());
        Assert.assertNull(aCondition.getOperator());
        Assert.assertNull(aCondition.getNegation());
        Assert.assertEquals(0, aCondition.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(ACondition.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        final ACondition aCondition = new ACondition("1", "2", "!=");

        Assert.assertEquals(aCondition, aCondition);
        Assert.assertEquals(aCondition.hashCode(), aCondition.hashCode());
        Assert.assertNotEquals(aCondition, null);

        final Compound compound = new Compound();
        Assert.assertNotEquals(aCondition, compound);

        final ACondition aCondition2 = new ACondition("1", "2", "!=");
        Assert.assertEquals(aCondition, aCondition2);
        Assert.assertEquals(aCondition.hashCode(), aCondition2.hashCode());
        aCondition2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(aCondition, aCondition2);

        ACondition aCondition3;
        aCondition3 = new ACondition("5", "2", "!=");
        Assert.assertNotEquals(aCondition, aCondition3);

        aCondition3 = new ACondition("1", "5", "!=");
        Assert.assertNotEquals(aCondition, aCondition3);

        aCondition3 = new ACondition("1", "2", "==");
        Assert.assertNotEquals(aCondition, aCondition3);

        aCondition3 = new ACondition("1", "2", "!=");
        aCondition3.setNegation("negation");
        Assert.assertNotEquals(aCondition, aCondition3);
    }
}

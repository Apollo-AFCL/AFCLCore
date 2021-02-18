package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Test the functionality of a condition object.
 *
 * @author stefanpedratscher
 */
public class ConditionTest {
    /**
     * Test full construction of a condition.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        final ACondition aCondition = new ACondition("1", "2", "==");

        Condition condition = new Condition("AND", new ArrayList<>(Collections.singleton(aCondition)));

        Assert.assertEquals("AND", condition.getCombinedWith());

        Assert.assertEquals(1, condition.getConditions().size());
        Assert.assertEquals(aCondition, condition.getConditions().get(0));
        Assert.assertEquals(aCondition.hashCode(), condition.getConditions().get(0).hashCode());

        Assert.assertEquals(0, condition.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a condition.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final Condition condition = new Condition();

        Assert.assertNull(condition.getCombinedWith());
        Assert.assertNull(condition.getConditions());
        Assert.assertEquals(0, condition.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Condition.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        ACondition aCondition = new ACondition("1", "2", "==");

        Condition condition = new Condition("AND", new ArrayList<>(Collections.singleton(aCondition)));

        Assert.assertEquals(condition, condition);
        Assert.assertEquals(condition.hashCode(), condition.hashCode());
        Assert.assertNotEquals(condition, null);

        final Compound compound = new Compound();
        Assert.assertNotEquals(condition, compound);

        final Condition condition2 = new Condition("AND", new ArrayList<>(Collections.singleton(aCondition)));
        Assert.assertEquals(condition, condition2);
        Assert.assertEquals(condition.hashCode(), condition2.hashCode());
        condition2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(condition, condition2);

        Condition condition3;
        condition3 = new Condition("OR", new ArrayList<>(Collections.singleton(aCondition)));
        Assert.assertNotEquals(condition, condition3);

        condition3 = new Condition("AND", new ArrayList<>(Collections.singleton(new ACondition())));
        Assert.assertNotEquals(condition, condition3);
    }
}

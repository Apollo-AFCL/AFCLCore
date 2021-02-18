package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * Test the functionality of a loop counter object.
 *
 * @author stefanpedratscher
 */
public class LoopCounterTest {
    /**
     * Test full construction of a loopCounter.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        final LoopCounter loopCounter = new LoopCounter("name", "type", "0", "10");

        Assert.assertEquals("name", loopCounter.getName());
        Assert.assertEquals("type", loopCounter.getType());
        Assert.assertEquals("0", loopCounter.getFrom());
        Assert.assertEquals("10", loopCounter.getTo());
        Assert.assertEquals(0, loopCounter.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a loopCounter.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final LoopCounter loopCounter = new LoopCounter();

        Assert.assertNull(loopCounter.getName());
        Assert.assertNull(loopCounter.getType());
        Assert.assertNull(loopCounter.getFrom());
        Assert.assertNull(loopCounter.getTo());
        Assert.assertNull(loopCounter.getStep());
        Assert.assertEquals(0, loopCounter.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(LoopCounter.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        final LoopCounter loopCounter = new LoopCounter("name", "type", "0", "10");

        Assert.assertEquals(loopCounter, loopCounter);
        Assert.assertEquals(loopCounter.hashCode(), loopCounter.hashCode());
        Assert.assertNotEquals(loopCounter, null);

        final Compound compound = new Compound();
        Assert.assertNotEquals(loopCounter, compound);

        final LoopCounter loopCounter2 = new LoopCounter("name", "type", "0", "10");
        Assert.assertEquals(loopCounter, loopCounter2);
        Assert.assertEquals(loopCounter.hashCode(), loopCounter2.hashCode());
        loopCounter2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(loopCounter, loopCounter2);

        LoopCounter loopCounter3;
        loopCounter3 = new LoopCounter("nameWrong", "type", "0", "10");
        Assert.assertNotEquals(loopCounter, loopCounter3);

        loopCounter3 = new LoopCounter("name", "typeWrong", "0", "10");
        Assert.assertNotEquals(loopCounter, loopCounter3);

        loopCounter3 = new LoopCounter("name", "type", "1", "10");
        Assert.assertNotEquals(loopCounter, loopCounter3);

        loopCounter3 = new LoopCounter("name", "type", "0", "11");
        Assert.assertNotEquals(loopCounter, loopCounter3);

        loopCounter3 = new LoopCounter("name", "type", "0", "10");
        loopCounter3.setStep("4");
        Assert.assertNotEquals(loopCounter, loopCounter3);
    }
}

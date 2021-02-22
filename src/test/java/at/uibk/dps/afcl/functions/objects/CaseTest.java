package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.AtomicFunction;
import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Test the functionality of switch-case object.
 *
 * @author stefanpedratscher
 */
public class CaseTest {
    /**
     * Test full construction of a case.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        final AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);

        final Case aCase = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));

        Assert.assertEquals("1", aCase.getValue());

        Assert.assertEquals(1, aCase.getFunctions().size());
        Assert.assertEquals(atomicFunction, aCase.getFunctions().get(0));

        Assert.assertEquals(0, aCase.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a case.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final Case aCase = new Case();

        Assert.assertNull(aCase.getBreakCase());
        Assert.assertNull(aCase.getFunctions());
        Assert.assertNull(aCase.getValue());
        Assert.assertEquals(0, aCase.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Case.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        final AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);

        final Case aCase = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));

        Assert.assertEquals(aCase, aCase);
        Assert.assertEquals(aCase.hashCode(), aCase.hashCode());
        Assert.assertNotEquals(aCase, null);

        final Compound compound = new Compound();
        Assert.assertNotEquals(aCase, compound);

        final Case aCase2 = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));
        Assert.assertEquals(aCase, aCase2);
        Assert.assertEquals(aCase.hashCode(), aCase2.hashCode());
        aCase2.setAdditionalProperties("name", "type");
        Assert.assertNotEquals(aCase, aCase2);

        Case aCase3;
        aCase3 = new Case("5", new ArrayList<>(Collections.singleton(atomicFunction)));
        Assert.assertNotEquals(aCase, aCase3);

        aCase3 = new Case("1", new ArrayList<>(Collections.singleton(new AtomicFunction())));
        Assert.assertNotEquals(aCase, aCase3);

        aCase3 = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));
        aCase3.setBreakCase("break");
        Assert.assertNotEquals(aCase, aCase3);
    }
}

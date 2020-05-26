package afcl.functions.objects;

import afcl.functions.AtomicFunction;
import afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

public class CaseTest {
    /**
     * Test full construction of a case.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);

        Case aCase = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));

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
        Case aCase = new Case();

        Assert.assertNull(aCase.getBreak());
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
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);

        Case aCase = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));

        Assert.assertEquals(aCase, aCase);
        Assert.assertEquals(aCase.hashCode(), aCase.hashCode());
        Assert.assertNotEquals(aCase, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(aCase, compound);

        Case aCase2 = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));
        Assert.assertEquals(aCase, aCase2);
        Assert.assertEquals(aCase.hashCode(), aCase2.hashCode());
        aCase2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(aCase, aCase2);

        Case aCase3;
        aCase3 = new Case("5", new ArrayList<>(Collections.singleton(atomicFunction)));
        Assert.assertNotEquals(aCase, aCase3);

        aCase3 = new Case("1", new ArrayList<>(Collections.singleton(new AtomicFunction())));
        Assert.assertNotEquals(aCase, aCase3);

        aCase3 = new Case("1", new ArrayList<>(Collections.singleton(atomicFunction)));
        aCase3.setBreak("break");
        Assert.assertNotEquals(aCase, aCase3);
    }
}

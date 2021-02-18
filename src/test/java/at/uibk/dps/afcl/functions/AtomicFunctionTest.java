package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOutsAtomic;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import org.meanbean.test.BeanTester;

/**
 * Test the functionality of an atomic function object.
 *
 * @author stefanpedratscher
 */
public class AtomicFunctionTest {

    /**
     * Test full construction of an atomicFunction.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        final DataIns dataIns = new DataIns("inName", "inType");
        final DataOutsAtomic dataOutsAtomic = new DataOutsAtomic("outName", "outType");
        final AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType",
                new ArrayList<>(Collections.singleton(dataIns)),
                new ArrayList<>(Collections.singleton(dataOutsAtomic)));

        Assert.assertEquals("atomicFunction", atomicFunction.getName());
        Assert.assertEquals("atomicFunctionType", atomicFunction.getType());

        Assert.assertEquals(1, atomicFunction.getDataIns().size());
        Assert.assertEquals(atomicFunction.getDataIns().get(0), dataIns);
        Assert.assertEquals(atomicFunction.getDataIns().get(0).hashCode(), dataIns.hashCode());

        Assert.assertEquals(1, atomicFunction.getDataOuts().size());
        Assert.assertEquals(atomicFunction.getDataOuts().get(0), dataOutsAtomic);
        Assert.assertEquals(atomicFunction.getDataOuts().get(0).hashCode(), dataOutsAtomic.hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of an atomicFunction.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final AtomicFunction atomicFunction = new AtomicFunction();

        Assert.assertNull(atomicFunction.getName());
        Assert.assertNull(atomicFunction.getType());
        Assert.assertNull(atomicFunction.getDataIns());
        Assert.assertNull(atomicFunction.getDataOuts());
        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the additional properties functionality of an AtomicFunction.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testAdditionalProperties() {
        AtomicFunction atomicFunction = new AtomicFunction();
        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());

        atomicFunction.setAdditionalProperty("name", "value");
        Assert.assertEquals(1, atomicFunction.getAdditionalProperties().size());

        atomicFunction.setAdditionalProperty("name2", "value2");
        Assert.assertEquals(2, atomicFunction.getAdditionalProperties().size());

        Assert.assertEquals("value", atomicFunction.getAdditionalProperties().get("name"));
        Assert.assertEquals("value2", atomicFunction.getAdditionalProperties().get("name2"));
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(AtomicFunction.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        final AtomicFunction atomicFunction1 = new AtomicFunction("name", "type", null, null);
        Assert.assertEquals(atomicFunction1, atomicFunction1);
        Assert.assertEquals(atomicFunction1.hashCode(), atomicFunction1.hashCode());
        Assert.assertNotEquals(atomicFunction1, null);

        final Function function = new Function();
        Assert.assertNotEquals(atomicFunction1, function);

        final AtomicFunction atomicFunction2 = new AtomicFunction("name", "type", null, null);
        Assert.assertEquals(atomicFunction1, atomicFunction2);
        Assert.assertEquals(atomicFunction1.hashCode(), atomicFunction2.hashCode());

        atomicFunction2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(atomicFunction1, atomicFunction2);

        AtomicFunction atomicFunction3;
        atomicFunction3 = new AtomicFunction("nameWrong", "type", null, null);
        Assert.assertNotEquals(atomicFunction1, atomicFunction3);

        atomicFunction3 = new AtomicFunction("name", "typeWrong", null, null);
        Assert.assertNotEquals(atomicFunction1, atomicFunction3);

        atomicFunction3 = new AtomicFunction("name", "type",
                Collections.singletonList(new DataIns("name", "type", "source")), null);
        Assert.assertNotEquals(atomicFunction1, atomicFunction3);

        atomicFunction3 = new AtomicFunction("name", "type", null,
                Collections.singletonList(new DataOutsAtomic("name", "type")));
        Assert.assertNotEquals(atomicFunction1, atomicFunction3);
    }
}

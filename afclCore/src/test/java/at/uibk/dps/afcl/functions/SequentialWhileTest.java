package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.Condition;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataLoops;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

public class SequentialWhileTest {
    /**
     * Test full construction of a sequentialWhile.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        DataIns dataIns = new DataIns("inName", "inType");
        DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");
        DataLoops dataLoops = new DataLoops();
        Condition condition = new Condition();

        SequentialWhile sequentialWhile = new SequentialWhile("sequentialWhile",
                new ArrayList<>(Collections.singleton(dataIns)),
                new ArrayList<>(Collections.singleton(dataLoops)),
                condition,
                new ArrayList<>(Collections.singleton(atomicFunction)),
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("sequentialWhile", sequentialWhile.getName());

        Assert.assertEquals(1, sequentialWhile.getDataIns().size());
        Assert.assertEquals(dataIns, sequentialWhile.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), sequentialWhile.getDataIns().get(0).hashCode());

        Assert.assertEquals(1, sequentialWhile.getDataLoops().size());
        Assert.assertEquals(dataLoops, sequentialWhile.getDataLoops().get(0));
        Assert.assertEquals(dataLoops.hashCode(), sequentialWhile.getDataLoops().get(0).hashCode());

        Assert.assertEquals(condition, sequentialWhile.getCondition());
        Assert.assertEquals(condition.hashCode(), sequentialWhile.getCondition().hashCode());

        Assert.assertEquals(1, sequentialWhile.getLoopBody().size());
        Assert.assertEquals(atomicFunction, sequentialWhile.getLoopBody().get(0));
        Assert.assertEquals(atomicFunction.hashCode(), sequentialWhile.getLoopBody().get(0).hashCode());

        Assert.assertEquals(1, sequentialWhile.getDataOuts().size());
        Assert.assertEquals(dataOuts, sequentialWhile.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), sequentialWhile.getDataOuts().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a sequentialWhile.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        SequentialWhile sequentialWhile = new SequentialWhile();

        Assert.assertNull(sequentialWhile.getName());
        Assert.assertNull(sequentialWhile.getDataIns());
        Assert.assertNull(sequentialWhile.getDataLoops());
        Assert.assertNull(sequentialWhile.getCondition());
        Assert.assertNull(sequentialWhile.getLoopBody());
        Assert.assertNull(sequentialWhile.getDataOuts());
        Assert.assertEquals(0, sequentialWhile.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(SequentialWhile.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        SequentialWhile sequentialWhile = new SequentialWhile("name", null, null, null, null, null);

        Assert.assertEquals(sequentialWhile, sequentialWhile);
        Assert.assertEquals(sequentialWhile.hashCode(), sequentialWhile.hashCode());
        Assert.assertNotEquals(sequentialWhile, null);

        LoopCompound loopCompound = new LoopCompound();
        Assert.assertNotEquals(sequentialWhile, loopCompound);

        SequentialWhile sequentialWhile2 = new SequentialWhile("name", null, null, null, null, null);
        Assert.assertEquals(sequentialWhile, sequentialWhile2);
        Assert.assertEquals(sequentialWhile.hashCode(), sequentialWhile2.hashCode());
        sequentialWhile2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(sequentialWhile, sequentialWhile2);

        SequentialWhile sequentialWhile3;
        sequentialWhile3 = new SequentialWhile("nameWrong", null, null, null, null, null);
        Assert.assertNotEquals(sequentialWhile, sequentialWhile3);

        sequentialWhile3 = new SequentialWhile("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null, null, null);
        Assert.assertNotEquals(sequentialWhile, sequentialWhile3);

        sequentialWhile3 = new SequentialWhile("name", null, Collections.singletonList(new DataLoops()), null, null, null);
        Assert.assertNotEquals(sequentialWhile, sequentialWhile3);

        sequentialWhile3 = new SequentialWhile("name", null, null, new Condition(), null, null);
        Assert.assertNotEquals(sequentialWhile, sequentialWhile3);

        sequentialWhile3 = new SequentialWhile("name", null, null, null, Collections.singletonList(new AtomicFunction()), null);
        Assert.assertNotEquals(sequentialWhile, sequentialWhile3);

        sequentialWhile3 = new SequentialWhile("name", null, null, null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(sequentialWhile, sequentialWhile3);
    }
}

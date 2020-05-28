package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.LoopCounter;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

public class ParallelForTest {

    /**
     * Test full construction of a parallelFor.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {

        LoopCounter loopCounter = new LoopCounter("name", "type", "0", "10");
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        DataIns dataIns = new DataIns("inName", "inType");
        DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");

        ParallelFor parallelFor = new ParallelFor("parallelFor",
                new ArrayList<>(Collections.singleton(dataIns)),
                loopCounter,
                new ArrayList<>(Collections.singleton(atomicFunction)),
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("parallelFor", parallelFor.getName());

        Assert.assertEquals(1, parallelFor.getDataIns().size());
        Assert.assertEquals(dataIns, parallelFor.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), parallelFor.getDataIns().get(0).hashCode());

        Assert.assertEquals(loopCounter, parallelFor.getLoopCounter());
        Assert.assertEquals(loopCounter.hashCode(), parallelFor.getLoopCounter().hashCode());

        Assert.assertEquals(1, parallelFor.getLoopBody().size());
        Assert.assertEquals(atomicFunction, parallelFor.getLoopBody().get(0));
        Assert.assertEquals(atomicFunction.hashCode(), parallelFor.getLoopBody().get(0).hashCode());

        Assert.assertEquals(1, parallelFor.getDataOuts().size());
        Assert.assertEquals(dataOuts, parallelFor.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), parallelFor.getDataOuts().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a parallelFor.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        ParallelFor parallelFor = new ParallelFor();

        Assert.assertNull(parallelFor.getName());
        Assert.assertNull(parallelFor.getDataIns());
        Assert.assertNull(parallelFor.getLoopCounter());
        Assert.assertNull(parallelFor.getLoopBody());
        Assert.assertNull(parallelFor.getDataOuts());
        Assert.assertEquals(0, parallelFor.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(ParallelFor.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        ParallelFor parallelFor1 = new ParallelFor("name", null, null, null, null);
        Assert.assertEquals(parallelFor1, parallelFor1);
        Assert.assertEquals(parallelFor1.hashCode(), parallelFor1.hashCode());
        Assert.assertNotEquals(parallelFor1, null);

        LoopCompound loopCompound = new LoopCompound();
        Assert.assertNotEquals(parallelFor1, loopCompound);

        ParallelFor parallelFor2 = new ParallelFor("name", null, null, null, null);
        Assert.assertEquals(parallelFor1, parallelFor2);
        Assert.assertEquals(parallelFor1.hashCode(), parallelFor2.hashCode());
        parallelFor2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(parallelFor1, parallelFor2);

        ParallelFor parallelFor3;
        parallelFor3 = new ParallelFor("nameWrong", null, null, null, null);
        Assert.assertNotEquals(parallelFor1, parallelFor3);

        parallelFor3 = new ParallelFor("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null, null);
        Assert.assertNotEquals(parallelFor1, parallelFor3);

        parallelFor3 = new ParallelFor("name", null, new LoopCounter("name", "type", "0", "5"), null, null);
        Assert.assertNotEquals(parallelFor1, parallelFor3);

        parallelFor3 = new ParallelFor("name", null, null, Collections.singletonList(new AtomicFunction()), null);
        Assert.assertNotEquals(parallelFor1, parallelFor3);

        parallelFor3 = new ParallelFor("name", null, null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(parallelFor1, parallelFor3);
    }
}

package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.Section;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

public class ParallelTest {
    /**
     * Test full construction of a parallel.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        Section section = new Section(Collections.singletonList(atomicFunction));
        DataIns dataIns = new DataIns("inName", "inType");
        DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");

        Parallel parallel = new Parallel("parallel",
                new ArrayList<>(Collections.singleton(dataIns)),
                new ArrayList<>(Collections.singleton(section)),
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("parallel", parallel.getName());

        Assert.assertEquals(1, parallel.getDataIns().size());
        Assert.assertEquals(dataIns, parallel.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), parallel.getDataIns().get(0).hashCode());

        Assert.assertEquals(1, parallel.getParallelBody().size());
        Assert.assertEquals(section, parallel.getParallelBody().get(0));
        Assert.assertEquals(section.hashCode(), parallel.getParallelBody().get(0).hashCode());

        Assert.assertEquals(1, parallel.getDataOuts().size());
        Assert.assertEquals(dataOuts, parallel.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), parallel.getDataOuts().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a parallel.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        Parallel parallel = new Parallel();

        Assert.assertNull(parallel.getName());
        Assert.assertNull(parallel.getDataIns());
        Assert.assertNull(parallel.getParallelBody());
        Assert.assertNull(parallel.getDataOuts());
        Assert.assertEquals(0, parallel.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Parallel.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        Parallel parallel = new Parallel("name", null, null, null);

        Assert.assertEquals(parallel, parallel);
        Assert.assertEquals(parallel.hashCode(), parallel.hashCode());
        Assert.assertNotEquals(parallel, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(parallel, compound);

        Parallel parallel2 = new Parallel("name", null, null, null);
        Assert.assertEquals(parallel, parallel2);
        Assert.assertEquals(parallel.hashCode(), parallel2.hashCode());
        parallel2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(parallel, parallel2);

        Parallel parallel3;
        parallel3 = new Parallel("nameWrong", null, null, null);
        Assert.assertNotEquals(parallel, parallel3);

        parallel3 = new Parallel("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null);
        Assert.assertNotEquals(parallel, parallel3);

        parallel3 = new Parallel("name", null, Collections.singletonList(new Section()), null);
        Assert.assertNotEquals(parallel, parallel3);

        parallel3 = new Parallel("name", null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(parallel, parallel3);
    }
}

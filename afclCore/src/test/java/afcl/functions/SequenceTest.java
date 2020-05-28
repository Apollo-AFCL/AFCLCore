package afcl.functions;

import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

public class SequenceTest {
    /**
     * Test full construction of a sequence.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        DataIns dataIns = new DataIns("inName", "inType");
        DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");

        Sequence sequence = new Sequence("sequence",
                new ArrayList<>(Collections.singleton(dataIns)),
                new ArrayList<>(Collections.singleton(atomicFunction)),
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("sequence", sequence.getName());

        Assert.assertEquals(1, sequence.getDataIns().size());
        Assert.assertEquals(dataIns, sequence.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), sequence.getDataIns().get(0).hashCode());

        Assert.assertEquals(1, sequence.getSequenceBody().size());
        Assert.assertEquals(atomicFunction, sequence.getSequenceBody().get(0));
        Assert.assertEquals(atomicFunction.hashCode(), sequence.getSequenceBody().get(0).hashCode());

        Assert.assertEquals(1, sequence.getDataOuts().size());
        Assert.assertEquals(dataOuts, sequence.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), sequence.getDataOuts().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a sequence.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        Sequence sequence = new Sequence();

        Assert.assertNull(sequence.getName());
        Assert.assertNull(sequence.getDataIns());
        Assert.assertNull(sequence.getSequenceBody());
        Assert.assertNull(sequence.getDataOuts());
        Assert.assertEquals(0, sequence.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Sequence.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        Sequence sequence = new Sequence("name", null, null, null);

        Assert.assertEquals(sequence, sequence);
        Assert.assertEquals(sequence.hashCode(), sequence.hashCode());
        Assert.assertNotEquals(sequence, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(sequence, compound);

        Sequence sequence2 = new Sequence("name", null, null, null);
        Assert.assertEquals(sequence, sequence2);
        Assert.assertEquals(sequence.hashCode(), sequence2.hashCode());
        sequence2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(sequence, sequence2);

        Sequence sequence3;
        sequence3 = new Sequence("nameWrong", null, null, null);
        Assert.assertNotEquals(sequence, sequence3);

        sequence3 = new Sequence("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null);
        Assert.assertNotEquals(sequence, sequence3);

        sequence3 = new Sequence("name", null, Collections.singletonList(new AtomicFunction()), null);
        Assert.assertNotEquals(sequence, sequence3);

        sequence3 = new Sequence("name", null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(sequence, sequence3);
    }
}

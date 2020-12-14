package at.uibk.dps.afcl;

import at.uibk.dps.afcl.functions.AtomicFunction;
import at.uibk.dps.afcl.functions.Compound;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.PropertyConstraint;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Test the functionality of a sub-fc.
 *
 * @author stefanpedratscher
 */
public class SubFCTest {
    /**
     * Test full construction of a subFC.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        DataIns dataIns = new DataIns("inName", "inType");
        DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");

        final SubFC subFC = new SubFC("subFC",
                new ArrayList<>(Collections.singleton(dataIns)),
                new ArrayList<>(Collections.singleton(atomicFunction)),
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("subFC", subFC.getName());

        Assert.assertEquals(1, subFC.getDataIns().size());
        Assert.assertEquals(dataIns, subFC.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), subFC.getDataIns().get(0).hashCode());

        Assert.assertEquals(1, subFC.getSubFCBody().size());
        Assert.assertEquals(atomicFunction, subFC.getSubFCBody().get(0));
        Assert.assertEquals(atomicFunction.hashCode(), subFC.getSubFCBody().get(0).hashCode());

        Assert.assertEquals(1, subFC.getDataOuts().size());
        Assert.assertEquals(dataOuts, subFC.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), subFC.getDataOuts().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a subFC.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        SubFC subFC = new SubFC();

        Assert.assertNull(subFC.getName());
        Assert.assertNull(subFC.getDataIns());
        Assert.assertNull(subFC.getSubFCBody());
        Assert.assertNull(subFC.getDataOuts());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(SubFC.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        SubFC subFC = new SubFC("name", null, null, null);

        Assert.assertEquals(subFC, subFC);
        Assert.assertEquals(subFC.hashCode(), subFC.hashCode());
        Assert.assertNotEquals(subFC, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(subFC, compound);

        SubFC subFC2 = new SubFC("name", null, null, null);
        Assert.assertEquals(subFC, subFC2);
        Assert.assertEquals(subFC.hashCode(), subFC2.hashCode());

        SubFC subFC3;
        subFC3 = new SubFC("nameWrong", null, null, null);
        Assert.assertNotEquals(subFC, subFC3);

        subFC3 = new SubFC("name", null, null, null);
        subFC3.setConstraints(Collections.singletonList(new PropertyConstraint()));
        Assert.assertNotEquals(subFC, subFC3);

        subFC3 = new SubFC("name", null, null, null);
        subFC3.setProperties(Collections.singletonList(new PropertyConstraint()));
        Assert.assertNotEquals(subFC, subFC3);

        subFC3 = new SubFC("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null);
        Assert.assertNotEquals(subFC, subFC3);

        subFC3 = new SubFC("name", null, Collections.singletonList(new AtomicFunction()), null);
        Assert.assertNotEquals(subFC, subFC3);

        subFC3 = new SubFC("name", null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(subFC, subFC3);
    }
}

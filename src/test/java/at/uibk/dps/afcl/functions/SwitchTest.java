package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.Case;
import at.uibk.dps.afcl.functions.objects.DataEval;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

public class SwitchTest {
    /**
     * Test full construction of a switch.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {

        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        DataIns dataIns = new DataIns("inName", "inType");
        DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");
        Case caseCondition = new Case();
        DataEval dataEval = new DataEval();

        Switch switchCondition = new Switch("switch",
                new ArrayList<>(Collections.singleton(dataIns)),
                dataEval,
                new ArrayList<>(Collections.singleton(caseCondition)),
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("switch", switchCondition.getName());

        Assert.assertEquals(1, switchCondition.getDataIns().size());
        Assert.assertEquals(dataIns, switchCondition.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), switchCondition.getDataIns().get(0).hashCode());

        Assert.assertEquals(1, switchCondition.getDataOuts().size());
        Assert.assertEquals(dataOuts, switchCondition.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), switchCondition.getDataOuts().get(0).hashCode());

        Assert.assertEquals(dataEval, switchCondition.getDataEval());
        Assert.assertEquals(dataEval.hashCode(), switchCondition.getDataEval().hashCode());

        Assert.assertEquals(1, switchCondition.getCases().size());
        Assert.assertEquals(caseCondition, switchCondition.getCases().get(0));
        Assert.assertEquals(caseCondition.hashCode(), switchCondition.getCases().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a switch.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        Switch switchCondition = new Switch();

        Assert.assertNull(switchCondition.getName());
        Assert.assertNull(switchCondition.getDataIns());
        Assert.assertNull(switchCondition.getDataOuts());
        Assert.assertNull(switchCondition.getCases());
        Assert.assertNull(switchCondition.getDataEval());
        Assert.assertEquals(0, switchCondition.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Switch.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        Switch switchCondition = new Switch("name", null, null, null, null);
        Assert.assertEquals(switchCondition, switchCondition);
        Assert.assertEquals(switchCondition.hashCode(), switchCondition.hashCode());
        Assert.assertNotEquals(switchCondition, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(switchCondition, compound);

        Switch switchCondition2 = new Switch("name", null, null, null, null);
        Assert.assertEquals(switchCondition, switchCondition2);
        Assert.assertEquals(switchCondition.hashCode(), switchCondition2.hashCode());
        ;

        switchCondition2.setAdditionalProperty("name", "type");
        Assert.assertNotEquals(switchCondition, switchCondition2);

        Switch switchCondition3;
        switchCondition3 = new Switch("nameWrong", null, null, null, null);
        Assert.assertNotEquals(switchCondition, switchCondition3);

        switchCondition3 = new Switch("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null, null);
        Assert.assertNotEquals(switchCondition, switchCondition3);

        switchCondition3 = new Switch("name", null, new DataEval(), null, null);
        Assert.assertNotEquals(switchCondition, switchCondition3);

        switchCondition3 = new Switch("name", null, null, Collections.singletonList(new Case()), null);
        Assert.assertNotEquals(switchCondition, switchCondition3);

        switchCondition3 = new Switch("name", null, null, null, null);
        switchCondition3.setDefault(Collections.singletonList(new AtomicFunction()));
        Assert.assertNotEquals(switchCondition, switchCondition3);

        switchCondition3 = new Switch("name", null, null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(switchCondition, switchCondition3);
    }
}

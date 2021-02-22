package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.ACondition;
import at.uibk.dps.afcl.functions.objects.Condition;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Test the functionality of a if-then-else object.
 *
 * @author stefanpedratscher
 */
public class IfThenElseTest {
    /**
     * Test full construction of an ifThenElse.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {

        final Condition condition = new Condition("AND", new ArrayList<>(Arrays.asList(
                new ACondition("1", "2", "=="),
                new ACondition("1", "1", "=="))));
        final AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        final DataIns dataIns = new DataIns("inName", "inType");
        final DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");

        final IfThenElse ifThenElse = new IfThenElse("ifThenElse",
                new ArrayList<>(Collections.singleton(dataIns)),
                condition,
                new ArrayList<>(Arrays.asList(atomicFunction)),
                null,
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("ifThenElse", ifThenElse.getName());

        Assert.assertEquals(1, ifThenElse.getDataIns().size());
        Assert.assertEquals(dataIns, ifThenElse.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), ifThenElse.getDataIns().get(0).hashCode());

        Assert.assertEquals(1, ifThenElse.getDataOuts().size());
        Assert.assertEquals(dataOuts, ifThenElse.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), ifThenElse.getDataOuts().get(0).hashCode());

        Assert.assertEquals(condition, ifThenElse.getCondition());
        Assert.assertEquals(condition.hashCode(), ifThenElse.getCondition().hashCode());

        Assert.assertEquals(1, ifThenElse.getThenBranch().size());
        Assert.assertEquals(atomicFunction, ifThenElse.getThenBranch().get(0));
        Assert.assertEquals(atomicFunction.hashCode(), ifThenElse.getThenBranch().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of an ifThenElse.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final IfThenElse ifThenElse = new IfThenElse();

        Assert.assertNull(ifThenElse.getName());
        Assert.assertNull(ifThenElse.getDataIns());
        Assert.assertNull(ifThenElse.getDataOuts());
        Assert.assertNull(ifThenElse.getCondition());
        Assert.assertNull(ifThenElse.getThenBranch());
        Assert.assertNull(ifThenElse.getElseBranch());
        Assert.assertEquals(0, ifThenElse.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(IfThenElse.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        final  IfThenElse ifThenElse1 = new IfThenElse("name", null, null, null, null, null);
        Assert.assertEquals(ifThenElse1, ifThenElse1);
        Assert.assertEquals(ifThenElse1.hashCode(), ifThenElse1.hashCode());
        Assert.assertNotEquals(ifThenElse1, null);

        final Compound compound = new Compound();
        Assert.assertNotEquals(ifThenElse1, compound);

        final IfThenElse ifThenElse2 = new IfThenElse("name", null, null, null, null, null);
        Assert.assertEquals(ifThenElse1, ifThenElse2);
        Assert.assertEquals(ifThenElse1.hashCode(), ifThenElse2.hashCode());

        ifThenElse2.setAdditionalProperties("name", "type");
        Assert.assertNotEquals(ifThenElse1, ifThenElse2);

        IfThenElse ifThenElse3;
        ifThenElse3 = new IfThenElse("nameWrong", null, null, null, null, null);
        Assert.assertNotEquals(ifThenElse1, ifThenElse3);

        ifThenElse3 = new IfThenElse("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null, null, null);
        Assert.assertNotEquals(ifThenElse1, ifThenElse3);

        ifThenElse3 = new IfThenElse("name", null, new Condition("and", Collections.singletonList(new ACondition("1", "1", "=="))), null, null, null);
        Assert.assertNotEquals(ifThenElse1, ifThenElse3);

        ifThenElse3 = new IfThenElse("name", null, null, Collections.singletonList(new AtomicFunction()), null, null);
        Assert.assertNotEquals(ifThenElse1, ifThenElse3);

        ifThenElse3 = new IfThenElse("name", null, null, null, Collections.singletonList(new AtomicFunction()), null);
        Assert.assertNotEquals(ifThenElse1, ifThenElse3);

        ifThenElse3 = new IfThenElse("name", null, null, null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(ifThenElse1, ifThenElse3);
    }
}

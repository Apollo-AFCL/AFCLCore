package at.uibk.dps.afcl;

import at.uibk.dps.afcl.functions.AtomicFunction;
import at.uibk.dps.afcl.functions.Compound;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.ArrayList;
import java.util.Collections;

public class WorkflowTest {
    /**
     * Test full construction of a workflow.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        DataIns dataIns = new DataIns("inName", "inType");
        DataOuts dataOuts = new DataOuts("outName", "outType", "outSource");

        Workflow workflow = new Workflow("workflow",
                new ArrayList<>(Collections.singleton(dataIns)),
                new ArrayList<>(Collections.singleton(atomicFunction)),
                new ArrayList<>(Collections.singleton(dataOuts)));

        Assert.assertEquals("workflow", workflow.getName());

        Assert.assertEquals(1, workflow.getDataIns().size());
        Assert.assertEquals(dataIns, workflow.getDataIns().get(0));
        Assert.assertEquals(dataIns.hashCode(), workflow.getDataIns().get(0).hashCode());

        Assert.assertEquals(1, workflow.getWorkflowBody().size());
        Assert.assertEquals(atomicFunction, workflow.getWorkflowBody().get(0));
        Assert.assertEquals(atomicFunction.hashCode(), workflow.getWorkflowBody().get(0).hashCode());

        Assert.assertEquals(1, workflow.getDataOuts().size());
        Assert.assertEquals(dataOuts, workflow.getDataOuts().get(0));
        Assert.assertEquals(dataOuts.hashCode(), workflow.getDataOuts().get(0).hashCode());

        Assert.assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a workflow.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        Workflow workflow = new Workflow();

        Assert.assertNull(workflow.getName());
        Assert.assertNull(workflow.getDataIns());
        Assert.assertNull(workflow.getWorkflowBody());
        Assert.assertNull(workflow.getDataOuts());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Workflow.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        Workflow workflow = new Workflow("name", null, null, null);

        Assert.assertEquals(workflow, workflow);
        Assert.assertEquals(workflow.hashCode(), workflow.hashCode());
        Assert.assertNotEquals(workflow, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(workflow, compound);

        Workflow workflow2 = new Workflow("name", null, null, null);
        Assert.assertEquals(workflow, workflow2);
        Assert.assertEquals(workflow.hashCode(), workflow2.hashCode());

        Workflow workflow3;
        workflow3 = new Workflow("nameWrong", null, null, null);
        Assert.assertNotEquals(workflow, workflow3);

        workflow3 = new Workflow("name", null, null, null);
        workflow3.setSubFCs(Collections.singletonList(new SubFC()));
        Assert.assertNotEquals(workflow, workflow3);

        workflow3 = new Workflow("name", Collections.singletonList(new DataIns("name", "type", "source")), null, null);
        Assert.assertNotEquals(workflow, workflow3);

        workflow3 = new Workflow("name", null, Collections.singletonList(new AtomicFunction()), null);
        Assert.assertNotEquals(workflow, workflow3);

        workflow3 = new Workflow("name", null, null, Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(workflow, workflow3);
    }
}

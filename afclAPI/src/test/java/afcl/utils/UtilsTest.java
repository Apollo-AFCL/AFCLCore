package afcl.utils;

import afcl.Function;
import afcl.Workflow;
import afcl.functions.AtomicFunction;
import afcl.functions.ParallelFor;
import afcl.functions.objects.LoopCounter;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UtilsTest {

    /**
     * Creates an example of a simple workflow consisting of an
     * atomicFunction and a parallelFor loop.
     *
     * @return simple workflow
     */
    private Workflow getSimpleWorkflow(){

        List<Function> workflowBody = new ArrayList<>();

        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        workflowBody.add(atomicFunction);

        ParallelFor parallelFor = new ParallelFor("parallelFor", null,
                new LoopCounter("loopCounter", "loopCounterType", "0", "10"),
                new ArrayList<>(Collections.singleton(atomicFunction)),
                null);
        workflowBody.add(parallelFor);

        return new Workflow("workflow", null, workflowBody, null);
    }

    /**
     * Compare two atomic functions.
     *
     * @param atomicFunction1 to compare
     * @param atomicFunction2 to compare
     */
    private void compareAtomicFunctions(AtomicFunction atomicFunction1, AtomicFunction atomicFunction2){
        assertEquals(atomicFunction1.getName(), atomicFunction2.getName());
        assertEquals(atomicFunction1.getType(), atomicFunction2.getType());
        assertEquals(atomicFunction1.getDataIns(), atomicFunction2.getDataIns());
        assertEquals(atomicFunction1.getDataOuts(), atomicFunction2.getDataOuts());
    }

    /**
     * Compare two workflows.
     *
     * @param workflow1 to compare
     * @param workflow2 to compare
     */
    private void compareSimpleWorkflow(Workflow workflow1, Workflow workflow2){

        assertEquals(workflow1.getName(), workflow2.getName());
        assertEquals(workflow1.getDataIns(), workflow2.getDataIns());
        assertEquals(workflow1.getDataOuts(), workflow2.getDataOuts());

        AtomicFunction atomicFunction1 = (AtomicFunction) workflow1.getWorkflowBody().get(0);
        AtomicFunction atomicFunction2 = (AtomicFunction) workflow2.getWorkflowBody().get(0);
        compareAtomicFunctions(atomicFunction1, atomicFunction2);

        ParallelFor parallelFor1 = (ParallelFor) workflow1.getWorkflowBody().get(1);
        ParallelFor parallelFor2 = (ParallelFor) workflow2.getWorkflowBody().get(1);
        assertEquals(parallelFor1.getName(), parallelFor2.getName());
        assertEquals(parallelFor1.getDataIns(), parallelFor2.getDataIns());
        assertEquals(parallelFor1.getDataOuts(), parallelFor2.getDataOuts());
        assertEquals(parallelFor1.getDataOuts(), parallelFor2.getDataOuts());
        compareAtomicFunctions((AtomicFunction) parallelFor1.getLoopBody().get(0),
                (AtomicFunction) parallelFor2.getLoopBody().get(0));

        LoopCounter loopCounter1 = parallelFor1.getLoopCounter();
        LoopCounter loopCounter2 = parallelFor2.getLoopCounter();
        assertEquals(loopCounter1.getName(), loopCounter2.getName());
        assertEquals(loopCounter1.getFrom(), loopCounter2.getFrom());
        assertEquals(loopCounter1.getTo(), loopCounter2.getTo());
        assertEquals(loopCounter1.getStep(), loopCounter2.getStep());
        assertEquals(loopCounter1.getType(), loopCounter2.getType());
        assertEquals(loopCounter1.getAdditionalProperties().size(), loopCounter2.getAdditionalProperties().size());
    }

    /**
     * Test the reading and writing of a json workflow.
     */
    @Test
    public void writeReadJsonTest(){
        File workflowFile = new File("writeRead.json");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow1 = getSimpleWorkflow();

        Utils.writeJson(workflow1, workflowFile.getName(), schema.getAbsolutePath());
        Workflow workflow2 = Utils.readJSON(workflowFile.getName(), schema.getAbsolutePath());

        compareSimpleWorkflow(workflow1, workflow2);

        workflowFile.delete();
    }

    /**
     * Test the reading and writing of a yaml workflow.
     */
    @Test
    public void writeReadYamlTest(){
        File workflowFile = new File("writeRead.yaml");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow1 = getSimpleWorkflow();

        Utils.writeYaml(workflow1, workflowFile.getName(), schema.getAbsolutePath());
        Workflow workflow2 = Utils.readYAML(workflowFile.getName(), schema.getAbsolutePath());

        compareSimpleWorkflow(workflow1, workflow2);

        workflowFile.delete();
    }
}

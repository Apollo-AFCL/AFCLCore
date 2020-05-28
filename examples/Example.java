import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.Workflow;
import at.uibk.dps.afcl.functions.AtomicFunction;
import at.uibk.dps.afcl.functions.ParallelFor;
import at.uibk.dps.afcl.functions.objects.*;
import at.uibk.dps.afcl.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Create a simple workflow and parsing it again using AFCLCore.
 *
 * @author stefanpedratscher
 */
public class Example {

    public static void main(String[] args) {
        new Example().createMonteCarlo();
    }

    /**
     * Create monteCarlo workflow.
     */
    private void createMonteCarlo(){
        Workflow workflow = new Workflow();
        workflow.setName("MonteCarlo");
        workflow.setDataIns(Arrays.asList(new DataIns("array", "collection", "array"),
                new DataIns("total", "number", "total"),
                new DataIns("each", "number", "each")));

        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", "2", "1"));
        DataIns dataInsDataFlow = new DataIns("InVal", "number", workflow.getName() + "/" + workflow.getDataIns().get(0).getName());
        DataIns dataInsEach = new DataIns("each", "number", workflow.getName() + "/" + workflow.getDataIns().get(2).getName());
        dataInsEach.setPassing(true);
        DataIns dataInsTotal = new DataIns("total", "number", workflow.getName() + "/" + workflow.getDataIns().get(1).getName());
        dataInsTotal.setPassing(true);

        dataInsDataFlow.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(1)")));
        parallelFor.setDataIns(Arrays.asList(dataInsDataFlow, dataInsTotal, dataInsEach));

        DataIns inParallelDataIns = new DataIns("fraction", "number", parallelFor.getName() + "/" + parallelFor.getDataIns().get(0).getName());
        AtomicFunction monteCarlo = new AtomicFunction("monteCarlo", "monteCarloType", Arrays.asList(inParallelDataIns), Arrays.asList(new DataOutsAtomic("result", "number")));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("array", "collection",
                        monteCarlo.getName() + "/" + monteCarlo.getDataOuts().get(0).getName()),
                new DataOuts("total", "number", parallelFor.getDataIns().get(1).getName()),
                new DataOuts("each", "number", parallelFor.getDataIns().get(2).getName())));

        parallelFor.setLoopBody(new ArrayList<Function>(Collections.singleton(monteCarlo)));

        DataIns array = new DataIns("array", "collection", parallelFor.getName() + "/" + parallelFor.getDataOuts().get(0).getName());
        DataIns total = new DataIns("total", "number", parallelFor.getName() + "/" + parallelFor.getDataOuts().get(1).getName());
        DataIns each = new DataIns("each", "number", parallelFor.getName() + "/" + parallelFor.getDataOuts().get(2).getName());
        AtomicFunction summary = new AtomicFunction("summary", "summaryType", Arrays.asList(array, total, each), Arrays.asList(new DataOutsAtomic("pi", "number")));

        workflow.setWorkflowBody(Arrays.asList(parallelFor, summary));

        // CFCL
        monteCarlo.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:EE_exp_1_1_monteCarlo")));
        summary.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:EE_exp_1_1_monteCarlo_summary")));

        // Export / Write workflow
        try {
            Utils.writeYaml(workflow, "MonteCarlo_CFCL.yaml", "path/to/schema.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse / Read workflow
        Utils.writeYamlNoValidation(workflow, "MonteCarlo_CFCL.yaml");
    }
}

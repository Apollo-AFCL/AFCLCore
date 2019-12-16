import afcl.Function;
import afcl.Workflow;
import afcl.functions.*;
import afcl.functions.objects.*;
import afcl.functions.objects.dataflow.DataFlowBlock;
import afcl.functions.objects.dataflow.DataFlowElementIndex;
import afcl.functions.objects.dataflow.DataFlowReplication;
import afcl.functions.objects.dataflow.DataInsDataFlow;
import afcl.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author stefanpedratscher
 */
public class Tester {

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.createGateChangeAlert();
        tester.createGenome(true);
    }

    private void createGenome(boolean cfcl) {
        Workflow workflow = new Workflow();
        workflow.setName("genome");
        workflow.setDataIns(Arrays.asList(new DataIns("key", "string"), new DataIns("chromNr", "string"),
                new DataIns("keyInputFile", "string")));

        // Individuals
        ParallelFor individuals = new ParallelFor();
        individuals.setName("individuals");

        // Individual
        individuals.setLoopCounter(new LoopCounter("counter", "number", "1", "10000", "1000"));
        DataIns dataInsCounter = new DataIns("counter", "number", individuals.getName() + "/counter");
        DataIns dataInsAmount = new DataIns("amount", "number", individuals.getName() + "/step");
        AtomicFunction individual = new AtomicFunction("individual", "individualType",
                Arrays.asList(
                        getDataIns("keyInput", "string", workflow, 2),
                        getDataIns("chromNr", "string", workflow, 1),
                        dataInsCounter, dataInsAmount),
                Arrays.asList(
                        new DataOutsAtomic("fileName", "string")));

        individuals.setLoopBody(Arrays.asList(individual));
        individuals.setDataOuts(Arrays.asList(new DataOuts("fileNames", "collection", getDataOutsByIndex(individual, 0))));

        // individuals_merge
        AtomicFunction individualsMerge = new AtomicFunction("individualsMerge", "individualsMergeType", Arrays.asList(getDataOuts("InVal", "collection", individuals, 0)), Arrays.asList(new DataOutsAtomic("fileName", "string")));

        AtomicFunction sifting = new AtomicFunction("sifting", "siftingType", Arrays.asList(
                getDataIns("key", "string", workflow, 0),
                getDataIns("chromNr", "string", workflow, 1)),
                Arrays.asList(new DataOutsAtomic("fileName", "string")));

        Parallel parallel1 = new Parallel();
        parallel1.setName("parallelIndividualSifting");
        DataOuts dOuts1 = new DataOuts();
        dOuts1.setName("fileNames");
        dOuts1.setType("collection");
        dOuts1.setSource(individualsMerge.getName() + "/" + individual.getDataOuts().get(0).getName() + "," + sifting.getName() + "/" + sifting.getDataOuts().get(0).getName());
        parallel1.setDataOuts(Arrays.asList(dOuts1));
        Section s1 = new Section();
        s1.setSection(Arrays.asList(individuals, individualsMerge));
        Section s2 = new Section();
        s2.setSection(Arrays.asList(sifting));
        parallel1.setParallelBody(Arrays.asList(s1, s2));


        ParallelFor mutationalOverlaps = new ParallelFor();
        mutationalOverlaps.setName("mutationsOverlaps");
        mutationalOverlaps.setDataIns(Arrays.asList(
                getDataOutsDataFlow("InVal", "collection", parallel1, 0))
        );

        // mutational overlap
        mutationalOverlaps.setLoopCounter(new LoopCounter("counter", "number", "0", "7"));
        AtomicFunction mutationalOverlap = new AtomicFunction("mutationsOverlap", "mutationsOverlapType",
                Arrays.asList(
                        getDataInsDataFlow("chromNr", "string", workflow, 1),
                        new DataInsDataFlow("POP", "number", mutationalOverlaps.getName() + "/" + mutationalOverlaps.getLoopCounter().getName()),
                        getDataOutsDataFlow("files", "string", parallel1, 0)),
                null);
        mutationalOverlaps.setLoopBody(Arrays.asList(mutationalOverlap));


        ParallelFor frequencies = new ParallelFor();
        frequencies.setName("frequencies");
        frequencies.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "collection", parallel1, 0)));

        // frequency
        frequencies.setLoopCounter(new LoopCounter("counter", "number", "0", "7"));
        AtomicFunction frequency = new AtomicFunction("frequency", "frequencyType", Arrays.asList(
                getDataInsDataFlow("chromNr", "string", workflow, 1),
                new DataInsDataFlow("POP", "number", frequencies.getName() + "/" + frequencies.getLoopCounter().getName()),
                getDataOutsDataFlow("files", "string", parallel1, 0)),
                null);
        frequencies.setLoopBody(Arrays.asList(frequency));
        //frequencies.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", frequency.getName() + "/" +frequency.getDataOuts().get(0).getName())));


        Parallel parallel2 = new Parallel();
        parallel2.setName("parallelMutationsOverlapFrequency");
        parallel2.setDataOuts(null);
        Section s11 = new Section();
        s11.setSection(Arrays.asList(mutationalOverlaps));
        Section s22 = new Section();
        s22.setSection(Arrays.asList(frequencies));
        parallel2.setParallelBody(Arrays.asList(s11, s22));

        workflow.setWorkflowBody(Arrays.asList(parallel1, parallel2));
        if (cfcl) {
            individual.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:genome_individual")));
            individualsMerge.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:genome_individuals_merge")));
            sifting.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:genome_sifting")));
            mutationalOverlap.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:genome_mutual_overlap")));
            frequency.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:genome_frequency")));
            Utils.writeYaml(workflow, "genomeCFCL.yaml", "schema.json");
        } else {
            Utils.writeYaml(workflow, "genome.yaml", "schema.json");
        }
    }


    private void testDataFlow() {
        Workflow workflow = new Workflow();
        workflow.setName("anomalyDetection");

        Parallel p = new Parallel();
        p.setDataIns(Arrays.asList(new DataInsDataFlow("name", "type", "source", new DataFlowBlock(new DataFlowBlock("C", "L"), "L")),
                new DataInsDataFlow("name", "type", "source", new DataFlowReplication("S")),
                new DataInsDataFlow("name", "type", "source", new DataFlowElementIndex("1:2:3"))));

        /*Parallel p2 = new Parallel();
        p2.setDataIns(Arrays.asList(new DataInsDataFlow("name", "type", "source", new DataFlowObject(DataFlowType.BLOCK, Arrays.asList(new PropertyConstraint("S", "10")))),
                new DataInsDataFlow("name", "type", "source", new DataFlowReplication("S")),
                new DataInsDataFlow("name", "type", "source", new DataFlowElementIndex("1:2:3"))));

        workflow.setWorkflowBody(Arrays.asList(p2));*/
        workflow.setWorkflowBody(Arrays.asList(p));

        Utils.writeYamlNoValidation(workflow, "test.yaml");
        System.out.println("HES1");
        Workflow wf2 = Utils.readYAMLNoValidation("test.yaml");
        System.out.println("HES");
        Utils.writeYamlNoValidation(workflow, "test2.yaml");
    }

    private void createAnomalyDetection() {
        Workflow workflow = new Workflow();
        workflow.setName("anomalyDetection");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "number", "some source")));

        // f1
        AtomicFunction f1 = new AtomicFunction("f1", "f1Type", Arrays.asList(getDataIns("InVal", "number", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string"), new DataOutsAtomic("OutVal2", "string")));

        // switch (f2,f3,f4)
        Switch switchF2F3F4 = new Switch();
        switchF2F3F4.setName("switchF2F3F4");
        switchF2F3F4.setDataIns(Arrays.asList(getDataOuts("InVal", "string", f1, 0)));

        AtomicFunction f2 = new AtomicFunction("f2", "f2Type", Arrays.asList(getDataIns("InVal", "string", switchF2F3F4, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        AtomicFunction f3 = new AtomicFunction("f3", "f3Type", Arrays.asList(getDataIns("InVal", "string", switchF2F3F4, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        AtomicFunction f4 = new AtomicFunction("f4", "f4Type", Arrays.asList(getDataIns("InVal", "string", switchF2F3F4, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));

        DataEval switchF2F3F4DataEval = new DataEval("value", "string");
        switchF2F3F4DataEval.setSource(getDataOutsByIndex(f1, 1));
        switchF2F3F4.setDataEval(switchF2F3F4DataEval);

        List<Case> cases = Arrays.asList(new Case("two", Arrays.asList(f2)), new Case("three", Arrays.asList(f3)), new Case("four", Arrays.asList(f4)));
        switchF2F3F4.setCases(cases);

        switchF2F3F4.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(f2, 0) + "," + getDataOutsByIndex(f3, 0) + "," + getDataOutsByIndex(f4, 0) + ",NULL")));

        // f18
        AtomicFunction f18 = new AtomicFunction("f18", "f18Type", Arrays.asList(getDataOuts("InVal", "collection", switchF2F3F4, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string"), new DataOutsAtomic("OutVal2", "string")));

        // switch (f5,f6,f7)
        Switch switchF5F6F7 = new Switch();
        switchF5F6F7.setName("switchF5F6F7");
        switchF5F6F7.setDataIns(Arrays.asList(getDataOuts("InVal", "string", f18, 0)));

        AtomicFunction f5 = new AtomicFunction("f5", "f5Type", Arrays.asList(getDataIns("InVal", "string", switchF5F6F7, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f6 = new AtomicFunction("f6", "f6Type", Arrays.asList(getDataIns("InVal", "string", switchF5F6F7, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f7 = new AtomicFunction("f7", "f7Type", Arrays.asList(getDataIns("InVal", "string", switchF5F6F7, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        DataEval switchF5F6F7DataEval = new DataEval("value", "string");
        switchF5F6F7DataEval.setSource(getDataOutsByIndex(f18, 1));

        List<Case> casesSecondSwitch = new ArrayList<>();

        // -> parallel f8,f9,f10,f11
        Parallel parallelF8F9F10F11 = new Parallel();
        parallelF8F9F10F11.setName("parallelF8F9F10F11");
        parallelF8F9F10F11.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", f5, 0)));

        AtomicFunction f8 = new AtomicFunction("f8", "f8Type", Arrays.asList(getDataIns("InVal", "string", parallelF8F9F10F11, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f9 = new AtomicFunction("f9", "f9Type", Arrays.asList(getDataIns("InVal", "string", parallelF8F9F10F11, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f10 = new AtomicFunction("f10", "f10Type", Arrays.asList(getDataIns("InVal", "string", parallelF8F9F10F11, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f11 = new AtomicFunction("f11", "f11Type", Arrays.asList(getDataIns("InVal", "string", parallelF8F9F10F11, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        parallelF8F9F10F11.setParallelBody(Arrays.asList(new Section(Arrays.asList(f8)), new Section(Arrays.asList(f9)), new Section(Arrays.asList(f10)), new Section(Arrays.asList(f11))));

        parallelF8F9F10F11.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(f8, 0) + "," + getDataOutsByIndex(f9, 0) + "," + getDataOutsByIndex(f10, 0) + "," + getDataOutsByIndex(f11, 0))));
        casesSecondSwitch.add(new Case("five", Arrays.asList(f5, parallelF8F9F10F11)));

        // -> parallel f12,f13,f14
        Parallel parallelF12F13F14 = new Parallel();
        parallelF12F13F14.setName("parallelF12F13F14");
        parallelF12F13F14.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", f6, 0)));

        AtomicFunction f12 = new AtomicFunction("f12", "f12Type", Arrays.asList(getDataIns("InVal", "string", parallelF12F13F14, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f13 = new AtomicFunction("f13", "f13Type", Arrays.asList(getDataIns("InVal", "string", parallelF12F13F14, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f14 = new AtomicFunction("f14", "f14Type", Arrays.asList(getDataIns("InVal", "string", parallelF12F13F14, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        parallelF12F13F14.setParallelBody(Arrays.asList(new Section(Arrays.asList(f12)), new Section(Arrays.asList(f13)), new Section(Arrays.asList(f14))));

        parallelF12F13F14.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(f12, 0) + "," + getDataOutsByIndex(f13, 0) + "," + getDataOutsByIndex(f14, 0))));
        casesSecondSwitch.add(new Case("six", Arrays.asList(f6, parallelF12F13F14)));

        // -> parallel f15,f16
        Parallel parallelF15F16 = new Parallel();
        parallelF15F16.setName("parallelF15F16");
        parallelF15F16.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", f7, 0)));

        AtomicFunction f15 = new AtomicFunction("f15", "f15Type", Arrays.asList(getDataIns("InVal", "string", parallelF15F16, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction f16 = new AtomicFunction("f16", "f16Type", Arrays.asList(getDataIns("InVal", "string", parallelF15F16, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        parallelF15F16.setParallelBody(Arrays.asList(new Section(Arrays.asList(f15)), new Section(Arrays.asList(f16))));

        parallelF15F16.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(f15, 0) + "," + getDataOutsByIndex(f16, 0))));
        casesSecondSwitch.add(new Case("seven", Arrays.asList(f7, parallelF15F16)));

        // -> dummy function
        AtomicFunction dummy = new AtomicFunction();
        dummy.setName("dummy");
        dummy.setDataIns(Arrays.asList(new DataIns("InVal", "string", getDataInsByIndex(switchF5F6F7, 0))));
        dummy.setDataOuts(Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        casesSecondSwitch.add(new Case("empty", Arrays.asList(dummy)));

        switchF5F6F7.setDataEval(switchF5F6F7DataEval);
        switchF5F6F7.setCases(casesSecondSwitch);
        switchF5F6F7.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelF8F9F10F11, 0) + "," + getDataOutsByIndex(parallelF12F13F14, 0) + "," + getDataOutsByIndex(parallelF15F16, 0) + "," + getDataOutsByIndex(dummy, 0) + ",NULL")));

        // f17
        AtomicFunction f17 = new AtomicFunction("f17", "f17Type", Arrays.asList(getDataOuts("InVal", "collection", switchF5F6F7, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        workflow.setWorkflowBody(Arrays.asList(f1, switchF2F3F4, f18, switchF5F6F7, f17));
        DataOuts wfdataOuts = new DataOuts("OutVal", "string", getDataOutsByIndex(f17, 0));
        workflow.setDataOuts(Arrays.asList(wfdataOuts));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "anomalyDetection.yaml", "schema.json");
    }

    private void createGateChangeAlert() {
        Workflow workflow = new Workflow();
        workflow.setName("gateChangeAlert");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "number", "some source")));

        // getFlight
        AtomicFunction getFlight = new AtomicFunction("getFlight", "getFlightType", Arrays.asList(getDataIns("InVal", "number", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        // selectPassenger
        AtomicFunction selectPassenger = new AtomicFunction("selectPassenger", "selectPassengerType", Arrays.asList(getDataOuts("InVal", "string", getFlight, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "collection"), new DataOutsAtomic("OutVal2", "number")));

        // parallelFor
        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        DataInsDataFlow dataIns = getDataOutsDataFlow("InVal", "collection", selectPassenger, 0);
        //dataIns.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(5)")));
        dataIns.setDataFlow(new DataFlowBlock("5"));
        parallelFor.setDataIns(Arrays.asList(dataIns));
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", getDataOutsByIndex(selectPassenger, 1)));


        // -> parallel informPassenger,calculateTimeToGate
        Parallel parallelF3F4 = new Parallel();
        parallelF3F4.setName("parallelF3F4");
        DataInsDataFlow dataInsParallel = getDataInsDataFlow("InVal", "collection", parallelFor, 0);
        parallelF3F4.setDataIns(Arrays.asList(dataInsParallel));
        AtomicFunction informPassenger = new AtomicFunction("informPassenger", "informPassengerType", Arrays.asList(getDataIns("InVal", "string", parallelF3F4, 0)), null);
        AtomicFunction calculateTimeToGate = new AtomicFunction("calculateTimeToGate", "calculateTimeToGateType", Arrays.asList(getDataIns("InVal", "string", parallelF3F4, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));

        IfThenElse ifThenElse = new IfThenElse();
        ifThenElse.setName("ifThenElse");
        ifThenElse.setDataIns(Arrays.asList(getDataOuts("InVal", "number", calculateTimeToGate, 0)));
        ifThenElse.setCondition(new Condition("and", Arrays.asList(new ACondition(getDataInsByIndex(ifThenElse, 0), "20", ">"))));

        AtomicFunction recommendShop = new AtomicFunction("recommendShop", "recommendShopType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction informTimeCritical = new AtomicFunction("informTimeCritical", "informTimeCriticalType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        ifThenElse.setThen(Arrays.asList(recommendShop));
        ifThenElse.setElse(Arrays.asList(informTimeCritical));
        ifThenElse.setDataOuts(Arrays.asList(new DataOuts("OutVal", "string", getDataOutsByIndex(recommendShop, 0) + "," + getDataOutsByIndex(informTimeCritical, 0))));

        parallelF3F4.setParallelBody(Arrays.asList(new Section(Arrays.asList(informPassenger)), new Section(Arrays.asList(calculateTimeToGate, ifThenElse))));
        parallelF3F4.setDataOuts(Arrays.asList(new DataOuts("OutVal", "string", getDataOutsByIndex(ifThenElse, 0))));

        parallelFor.setLoopBody(Arrays.asList(parallelF3F4));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelF3F4, 0))));

        AtomicFunction log = new AtomicFunction("log", "logType", Arrays.asList(getDataOuts("InVal", "collection", parallelFor, 0)), null);

        workflow.setWorkflowBody(Arrays.asList(getFlight, selectPassenger, parallelFor, log));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "gateChangeAlert.yaml", "schema.json");
    }

    private void createAnomalyDetectionCFCLIBM() {

        Workflow workflow = new Workflow();
        workflow.setName("anomalyDetection");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // cleanData
        AtomicFunction cleanData = new AtomicFunction("cleanData", "cleanDataType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string"), new DataOutsAtomic("OutVal2", "number")));
        cleanData.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1CleanData.json")));

        // switch (selectSensors)
        Switch selectSensor = new Switch();
        selectSensor.setName("selectSensor");
        selectSensor.setDataIns(Arrays.asList(getDataOuts("InVal", "string", cleanData, 0)));

        AtomicFunction driveIntensity = new AtomicFunction("driveIntensity", "driveIntensityType", Arrays.asList(getDataIns("InVal", "string", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        driveIntensity.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1DriveIntensity.json")));
        AtomicFunction spindleVibration = new AtomicFunction("spindleVibration", "spindleVibrationType", Arrays.asList(getDataIns("InVal", "string", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        spindleVibration.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1SpindleVibration.json")));
        AtomicFunction temperature = new AtomicFunction("temperature", "temperatureType", Arrays.asList(getDataIns("InVal", "string", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        temperature.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1Temperature.json")));

        DataEval switchF2F3F4DataEval = new DataEval("value", "number");
        switchF2F3F4DataEval.setSource(getDataOutsByIndex(cleanData, 1));
        selectSensor.setDataEval(switchF2F3F4DataEval);

        List<Case> cases = Arrays.asList(new Case("73641", Arrays.asList(driveIntensity)), new Case("73642", Arrays.asList(spindleVibration)), new Case("73643", Arrays.asList(temperature)));
        selectSensor.setCases(cases);

        selectSensor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(driveIntensity, 0) + "," + getDataOutsByIndex(spindleVibration, 0) + "," + getDataOutsByIndex(temperature, 0) + ",NULL")));

        // normalizeData
        AtomicFunction normalizeData = new AtomicFunction("normalizeData", "normalizeDataType", Arrays.asList(getDataOuts("InVal", "collection", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string"), new DataOutsAtomic("OutVal2", "string")));
        normalizeData.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1NormalizeData.json")));


        // switch (renderCriticalReport,renderSevereReport,renderLightReport)
        Switch selectReport = new Switch();
        selectReport.setName("selectReport");
        selectReport.setDataIns(Arrays.asList(getDataOuts("InVal", "string", normalizeData, 0)));

        AtomicFunction renderCriticalReport = new AtomicFunction("renderCriticalReport", "renderCriticalReportType", Arrays.asList(getDataIns("InVal", "string", selectReport, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        renderCriticalReport.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1RenderCriticalReport.json")));
        AtomicFunction renderSevereReport = new AtomicFunction("renderSevereReport", "renderSevereReportType", Arrays.asList(getDataIns("InVal", "string", selectReport, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        renderSevereReport.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1RenderSevereReport.json")));
        AtomicFunction renderLightReport = new AtomicFunction("renderLightReport", "f7Type", Arrays.asList(getDataIns("InVal", "string", selectReport, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        renderLightReport.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1RenderLightReport.json")));

        DataEval switchF5F6F7DataEval = new DataEval("value", "string");
        switchF5F6F7DataEval.setSource(getDataOutsByIndex(normalizeData, 1));

        List<Case> casesSecondSwitch = new ArrayList<>();

        // -> parallel alertCustomer,alertMachineExpert,alertAdmin,logAtEdge
        Parallel parallelCriticalReport = new Parallel();
        parallelCriticalReport.setName("parallelCriticalReport");
        parallelCriticalReport.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", renderCriticalReport, 0)));

        AtomicFunction alertCustomer = new AtomicFunction("alertCustomer", "alertCustomerType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);
        alertCustomer.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1AlertCustomer.json")));
        AtomicFunction alertMachineExpert = new AtomicFunction("alertMachineExpert", "alertMachineExpertType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);
        alertMachineExpert.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1AlertMachineExpert.json")));
        AtomicFunction alertAdmin = new AtomicFunction("alertAdmin", "alertAdminType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);
        alertAdmin.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1AlertAdmin.json")));
        AtomicFunction logAtEdge = new AtomicFunction("logAtEdge", "logAtEdgeType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);
        logAtEdge.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1LogAtEdge.json")));

        parallelCriticalReport.setParallelBody(Arrays.asList(new Section(Arrays.asList(alertCustomer)), new Section(Arrays.asList(alertMachineExpert)), new Section(Arrays.asList(alertAdmin)), new Section(Arrays.asList(logAtEdge))));

        //parallelCriticalReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(alertCustomer,0)+","+getDataOutsByIndex(alertMachineExpert,0)+","+getDataOutsByIndex(alertAdmin,0)+","+getDataOutsByIndex(logAtEdge,0))));
        casesSecondSwitch.add(new Case("critical", Arrays.asList(renderCriticalReport, parallelCriticalReport)));

        // -> parallel alertMachineExpert2,alertAdmin2,logAtFog
        Parallel parallelSevereReport = new Parallel();
        parallelSevereReport.setName("parallelSevereReport");
        parallelSevereReport.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", renderSevereReport, 0)));

        AtomicFunction alertMachineExpert2 = new AtomicFunction("alertMachineExpert2", "alertMachineExpertType", Arrays.asList(getDataIns("InVal", "string", parallelSevereReport, 0)), null);
        alertMachineExpert2.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1AlertMachineExpert.json")));
        AtomicFunction alertAdmin2 = new AtomicFunction("alertAdmin2", "alertAdminType", Arrays.asList(getDataIns("InVal", "string", parallelSevereReport, 0)), null);
        alertAdmin2.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1AlertAdmin.json")));
        AtomicFunction logAtFog = new AtomicFunction("logAtFog", "flogAtFogType", Arrays.asList(getDataIns("InVal", "string", parallelSevereReport, 0)), null);
        logAtFog.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1LogAtFog.json")));

        parallelSevereReport.setParallelBody(Arrays.asList(new Section(Arrays.asList(alertMachineExpert2)), new Section(Arrays.asList(alertAdmin2)), new Section(Arrays.asList(logAtFog))));

//        parallelSevereReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(alertMachineExpert,0)+","+getDataOutsByIndex(alertAdmin,0)+","+getDataOutsByIndex(logAtFog,0))));
        casesSecondSwitch.add(new Case("severe", Arrays.asList(renderSevereReport, parallelSevereReport)));

        // -> parallel alertAdmin3,logAtFog3
        Parallel parallelLightReport = new Parallel();
        parallelLightReport.setName("parallelLightReport");
        parallelLightReport.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", renderLightReport, 0)));

        AtomicFunction alertAdmin3 = new AtomicFunction("alertAdmin3", "alertAdminType", Arrays.asList(getDataIns("InVal", "string", parallelLightReport, 0)), null);
        alertAdmin3.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1AlertAdmin.json")));
        AtomicFunction logAtFog3 = new AtomicFunction("logAtFog3", "logAtFogType", Arrays.asList(getDataIns("InVal", "string", parallelLightReport, 0)), null);
        logAtFog3.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1LogAtFog.json")));

        parallelLightReport.setParallelBody(Arrays.asList(new Section(Arrays.asList(alertAdmin3)), new Section(Arrays.asList(logAtFog3))));

//        parallelLightReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(alertAdmin,0)+","+getDataOutsByIndex(logAtFog,0))));
        casesSecondSwitch.add(new Case("light", Arrays.asList(renderLightReport, parallelLightReport)));

        // -> dummy function
        AtomicFunction dummy = new AtomicFunction();
        dummy.setName("dummy");
        dummy.setType("dummy");
        dummy.setDataIns(Arrays.asList(new DataIns("InVal", "string", getDataInsByIndex(selectReport, 0))));
        dummy.setDataOuts(Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        selectReport.setDefault(Arrays.asList(dummy));

        selectReport.setDataEval(switchF5F6F7DataEval);
        selectReport.setCases(casesSecondSwitch);
        selectReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "string", getDataOutsByIndex(normalizeData, 1))));

        // logCentral
        AtomicFunction logCentral = new AtomicFunction("logCentral", "logCentralType", Arrays.asList(getDataOuts("InVal", "string", selectReport, 0)), null);
        logCentral.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f1LogCentral.json")));

        workflow.setWorkflowBody(Arrays.asList(cleanData, selectSensor, normalizeData, selectReport, logCentral));
        //DataOuts wfdataOuts = new DataOuts("OutVal", "string", getDataOutsByIndex(logCentral,0));
        //workflow.setDataOuts(Arrays.asList(wfdataOuts));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "anomalyDetectionCFCL_IBM.yaml", "schema.json");
    }

    private void createAnomalyDetectionCFCLAWS() {

        Workflow workflow = new Workflow();
        workflow.setName("anomalyDetection");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // cleanData
        AtomicFunction cleanData = new AtomicFunction("cleanData", "cleanDataType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string"), new DataOutsAtomic("OutVal2", "number")));

        // switch (selectSensors)
        Switch selectSensor = new Switch();
        selectSensor.setName("selectSensor");
        selectSensor.setDataIns(Arrays.asList(getDataOuts("InVal", "string", cleanData, 0)));

        AtomicFunction driveIntensity = new AtomicFunction("driveIntensity", "driveIntensityType", Arrays.asList(getDataIns("InVal", "string", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        AtomicFunction spindleVibration = new AtomicFunction("spindleVibration", "spindleVibrationType", Arrays.asList(getDataIns("InVal", "string", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        AtomicFunction temperature = new AtomicFunction("temperature", "temperatureType", Arrays.asList(getDataIns("InVal", "string", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));

        DataEval switchF2F3F4DataEval = new DataEval("value", "number");
        switchF2F3F4DataEval.setSource(getDataOutsByIndex(cleanData, 1));
        selectSensor.setDataEval(switchF2F3F4DataEval);

        List<Case> cases = Arrays.asList(new Case("73641", Arrays.asList(driveIntensity)), new Case("73642", Arrays.asList(spindleVibration)), new Case("73643", Arrays.asList(temperature)));
        selectSensor.setCases(cases);

        selectSensor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(driveIntensity, 0) + "," + getDataOutsByIndex(spindleVibration, 0) + "," + getDataOutsByIndex(temperature, 0) + ",NULL")));

        // normalizeData
        AtomicFunction normalizeData = new AtomicFunction("normalizeData", "normalizeDataType", Arrays.asList(getDataOuts("InVal", "collection", selectSensor, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string"), new DataOutsAtomic("OutVal2", "string")));


        // switch (renderCriticalReport,renderSevereReport,renderLightReport)
        Switch selectReport = new Switch();
        selectReport.setName("selectReport");
        selectReport.setDataIns(Arrays.asList(getDataOuts("InVal", "string", normalizeData, 0)));

        AtomicFunction renderCriticalReport = new AtomicFunction("renderCriticalReport", "renderCriticalReportType", Arrays.asList(getDataIns("InVal", "string", selectReport, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction renderSevereReport = new AtomicFunction("renderSevereReport", "renderSevereReportType", Arrays.asList(getDataIns("InVal", "string", selectReport, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        AtomicFunction renderLightReport = new AtomicFunction("renderLightReport", "f7Type", Arrays.asList(getDataIns("InVal", "string", selectReport, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));

        DataEval switchF5F6F7DataEval = new DataEval("value", "string");
        switchF5F6F7DataEval.setSource(getDataOutsByIndex(normalizeData, 1));

        List<Case> casesSecondSwitch = new ArrayList<>();

        // -> parallel alertCustomer,alertMachineExpert,alertAdmin,logAtEdge
        Parallel parallelCriticalReport = new Parallel();
        parallelCriticalReport.setName("parallelCriticalReport");
        parallelCriticalReport.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", renderCriticalReport, 0)));

        AtomicFunction alertCustomer = new AtomicFunction("alertCustomer", "alertCustomerType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);
        AtomicFunction alertMachineExpert = new AtomicFunction("alertMachineExpert", "alertMachineExpertType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);
        AtomicFunction alertAdmin = new AtomicFunction("alertAdmin", "alertAdminType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);
        AtomicFunction logAtEdge = new AtomicFunction("logAtEdge", "logAtEdgeType", Arrays.asList(getDataIns("InVal", "string", parallelCriticalReport, 0)), null);

        parallelCriticalReport.setParallelBody(Arrays.asList(new Section(Arrays.asList(alertCustomer)), new Section(Arrays.asList(alertMachineExpert)), new Section(Arrays.asList(alertAdmin)), new Section(Arrays.asList(logAtEdge))));

        //parallelCriticalReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(alertCustomer,0)+","+getDataOutsByIndex(alertMachineExpert,0)+","+getDataOutsByIndex(alertAdmin,0)+","+getDataOutsByIndex(logAtEdge,0))));
        casesSecondSwitch.add(new Case("critical", Arrays.asList(renderCriticalReport, parallelCriticalReport)));

        // -> parallel alertMachineExpert2,alertAdmin2,logAtFog
        Parallel parallelSevereReport = new Parallel();
        parallelSevereReport.setName("parallelSevereReport");
        parallelSevereReport.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", renderSevereReport, 0)));

        AtomicFunction alertMachineExpert2 = new AtomicFunction("alertMachineExpert2", "alertMachineExpertType", Arrays.asList(getDataIns("InVal", "string", parallelSevereReport, 0)), null);
        AtomicFunction alertAdmin2 = new AtomicFunction("alertAdmin2", "alertAdminType", Arrays.asList(getDataIns("InVal", "string", parallelSevereReport, 0)), null);
        AtomicFunction logAtFog = new AtomicFunction("logAtFog", "flogAtFogType", Arrays.asList(getDataIns("InVal", "string", parallelSevereReport, 0)), null);

        parallelSevereReport.setParallelBody(Arrays.asList(new Section(Arrays.asList(alertMachineExpert2)), new Section(Arrays.asList(alertAdmin2)), new Section(Arrays.asList(logAtFog))));

//        parallelSevereReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(alertMachineExpert,0)+","+getDataOutsByIndex(alertAdmin,0)+","+getDataOutsByIndex(logAtFog,0))));
        casesSecondSwitch.add(new Case("severe", Arrays.asList(renderSevereReport, parallelSevereReport)));

        // -> parallel alertAdmin3,logAtFog3
        Parallel parallelLightReport = new Parallel();
        parallelLightReport.setName("parallelLightReport");
        parallelLightReport.setDataIns(Arrays.asList(getDataOutsDataFlow("InVal", "string", renderLightReport, 0)));

        AtomicFunction alertAdmin3 = new AtomicFunction("alertAdmin3", "alertAdminType", Arrays.asList(getDataIns("InVal", "string", parallelLightReport, 0)), null);
        AtomicFunction logAtFog3 = new AtomicFunction("logAtFog3", "logAtFogType", Arrays.asList(getDataIns("InVal", "string", parallelLightReport, 0)), null);

        parallelLightReport.setParallelBody(Arrays.asList(new Section(Arrays.asList(alertAdmin3)), new Section(Arrays.asList(logAtFog3))));

//        parallelLightReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(alertAdmin,0)+","+getDataOutsByIndex(logAtFog,0))));
        casesSecondSwitch.add(new Case("light", Arrays.asList(renderLightReport, parallelLightReport)));

        // -> dummy function
        AtomicFunction dummy = new AtomicFunction();
        dummy.setName("dummy");
        dummy.setType("dummy");
        dummy.setDataIns(Arrays.asList(new DataIns("InVal", "string", getDataInsByIndex(selectReport, 0))));
        dummy.setDataOuts(Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        selectReport.setDefault(Arrays.asList(dummy));

        selectReport.setDataEval(switchF5F6F7DataEval);
        selectReport.setCases(casesSecondSwitch);
        selectReport.setDataOuts(Arrays.asList(new DataOuts("OutVal", "string", getDataOutsByIndex(normalizeData, 1))));

        // logCentral
        AtomicFunction logCentral = new AtomicFunction("logCentral", "logCentralType", Arrays.asList(getDataOuts("InVal", "string", selectReport, 0)), null);

        cleanData.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1CleanData")));
        driveIntensity.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1DriveIntensity")));
        spindleVibration.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1SpindleVibration")));
        temperature.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1Temperature")));
        normalizeData.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1NormalizeData")));
        renderCriticalReport.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1RenderCriticalReport")));
        renderSevereReport.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1RenderSevereReport")));
        renderLightReport.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1RenderLightReport")));
        alertCustomer.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1AlertCustomer")));
        alertMachineExpert.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1AlertMachineExpert")));
        alertMachineExpert2.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1AlertMachineExpert")));
        alertAdmin.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1AlertAdmin")));
        alertAdmin2.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1AlertAdmin")));
        alertAdmin3.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1AlertAdmin")));
        logAtEdge.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1LogAtEdge")));
        logAtFog.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1LogAtFog")));
        logAtFog3.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1LogAtFog")));
        logCentral.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:f1LogCentral")));


        workflow.setWorkflowBody(Arrays.asList(cleanData, selectSensor, normalizeData, selectReport, logCentral));
        //DataOuts wfdataOuts = new DataOuts("OutVal", "string", getDataOutsByIndex(logCentral,0));
        //workflow.setDataOuts(Arrays.asList(wfdataOuts));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "anomalyDetectionCFCL_AWS.yaml", "schema.json");
    }

    private void createGateChangeAlertCFCLIBM10() {
        //.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:")));
        Workflow workflow = new Workflow();
        workflow.setName("gateChangeAlert");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // getFlight
        AtomicFunction getFlight = new AtomicFunction("getFlight", "getFlightType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        getFlight.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4GetFlight.json")));

        // selectPassenger
        AtomicFunction selectPassenger = new AtomicFunction("selectPassenger", "selectPassengerType", Arrays.asList(getDataOuts("InVal", "string", getFlight, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "collection"), new DataOutsAtomic("OutVal2", "number")));
        selectPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4SelectPassenger10.json")));

        // parallelFor
        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        DataInsDataFlow dataIns = getDataOutsDataFlow("InVal", "collection", selectPassenger, 0);
        //dataIns.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(5)")));
        dataIns.setDataFlow(new DataFlowBlock("5"));
        parallelFor.setDataIns(Arrays.asList(dataIns));
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", getDataOutsByIndex(selectPassenger, 1)));

        // -> parallel informPassenger,calculateTimeToGate
        Parallel parallelInformCalc = new Parallel();
        parallelInformCalc.setName("parallelInformCalc");
        DataInsDataFlow dataInsParallel = getDataInsDataFlow("InVal", "string", parallelFor, 0);
        parallelInformCalc.setDataIns(Arrays.asList(dataInsParallel));
        AtomicFunction informPassenger = new AtomicFunction("informPassenger", "informPassengerType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), null);
        informPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4InformPassenger.json")));
        AtomicFunction calculateTimeToGate = new AtomicFunction("calculateTimeToGate", "calculateTimeToGateType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        calculateTimeToGate.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4CalculateTimeToGate.json")));

        IfThenElse ifThenElse = new IfThenElse();
        ifThenElse.setName("checkTime");
        ifThenElse.setDataIns(Arrays.asList(getDataOuts("InVal", "number", calculateTimeToGate, 0)));
        ifThenElse.setCondition(new Condition("and", Arrays.asList(new ACondition(getDataInsByIndex(ifThenElse, 0), "20", ">"))));

        AtomicFunction recommendShop = new AtomicFunction("recommendShop", "recommendShopType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        recommendShop.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4RecommendShop.json")));
        AtomicFunction informTimeCritical = new AtomicFunction("informTimeCritical", "informTimeCriticalType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        informTimeCritical.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4InformCriticalTime.json")));

        ifThenElse.setThen(Arrays.asList(recommendShop));
        ifThenElse.setElse(Arrays.asList(informTimeCritical));

        parallelInformCalc.setParallelBody(Arrays.asList(new Section(Arrays.asList(informPassenger)), new Section(Arrays.asList(calculateTimeToGate, ifThenElse))));
        parallelInformCalc.setDataOuts(Arrays.asList(new DataOuts("OutVal", "number", getDataOutsByIndex(calculateTimeToGate, 0))));

        parallelFor.setLoopBody(Arrays.asList(parallelInformCalc));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelInformCalc, 0))));

        AtomicFunction log = new AtomicFunction("log", "logType", Arrays.asList(getDataOuts("InVal", "collection", parallelFor, 0)), null);
        log.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4Log.json")));

        workflow.setWorkflowBody(Arrays.asList(getFlight, selectPassenger, parallelFor, log));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "gateChangeAlertCFCL_IBM_10.yaml", "schema.json");
    }

    private void createGateChangeAlertCFCLIBM100() {
        //.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:")));
        Workflow workflow = new Workflow();
        workflow.setName("gateChangeAlert");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // getFlight
        AtomicFunction getFlight = new AtomicFunction("getFlight", "getFlightType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        getFlight.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4GetFlight.json")));

        // selectPassenger
        AtomicFunction selectPassenger = new AtomicFunction("selectPassenger", "selectPassengerType", Arrays.asList(getDataOuts("InVal", "string", getFlight, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "collection"), new DataOutsAtomic("OutVal2", "number")));
        selectPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4SelectPassenger100.json")));

        // parallelFor
        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        DataInsDataFlow dataIns = getDataOutsDataFlow("InVal", "collection", selectPassenger, 0);
        //dataIns.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(5)")));
        dataIns.setDataFlow(new DataFlowBlock("5"));
        parallelFor.setDataIns(Arrays.asList(dataIns));
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", getDataOutsByIndex(selectPassenger, 1)));

        // -> parallel informPassenger,calculateTimeToGate
        Parallel parallelInformCalc = new Parallel();
        parallelInformCalc.setName("parallelInformCalc");
        DataInsDataFlow dataInsParallel = getDataInsDataFlow("InVal", "string", parallelFor, 0);
        parallelInformCalc.setDataIns(Arrays.asList(dataInsParallel));
        AtomicFunction informPassenger = new AtomicFunction("informPassenger", "informPassengerType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), null);
        informPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4InformPassenger.json")));
        AtomicFunction calculateTimeToGate = new AtomicFunction("calculateTimeToGate", "calculateTimeToGateType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        calculateTimeToGate.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4CalculateTimeToGate.json")));

        IfThenElse ifThenElse = new IfThenElse();
        ifThenElse.setName("checkTime");
        ifThenElse.setDataIns(Arrays.asList(getDataOuts("InVal", "number", calculateTimeToGate, 0)));
        ifThenElse.setCondition(new Condition("and", Arrays.asList(new ACondition(getDataInsByIndex(ifThenElse, 0), "20", ">"))));

        AtomicFunction recommendShop = new AtomicFunction("recommendShop", "recommendShopType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        recommendShop.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4RecommendShop.json")));
        AtomicFunction informTimeCritical = new AtomicFunction("informTimeCritical", "informTimeCriticalType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        informTimeCritical.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4InformCriticalTime.json")));

        ifThenElse.setThen(Arrays.asList(recommendShop));
        ifThenElse.setElse(Arrays.asList(informTimeCritical));

        parallelInformCalc.setParallelBody(Arrays.asList(new Section(Arrays.asList(informPassenger)), new Section(Arrays.asList(calculateTimeToGate, ifThenElse))));
        parallelInformCalc.setDataOuts(Arrays.asList(new DataOuts("OutVal", "number", getDataOutsByIndex(calculateTimeToGate, 0))));

        parallelFor.setLoopBody(Arrays.asList(parallelInformCalc));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelInformCalc, 0))));

        AtomicFunction log = new AtomicFunction("log", "logType", Arrays.asList(getDataOuts("InVal", "collection", parallelFor, 0)), null);
        log.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4Log.json")));

        workflow.setWorkflowBody(Arrays.asList(getFlight, selectPassenger, parallelFor, log));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "gateChangeAlertCFCL_IBM_100.yaml", "schema.json");
    }

    private void createGateChangeAlertCFCLIBM1000() {
        //.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:")));
        Workflow workflow = new Workflow();
        workflow.setName("gateChangeAlert");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // getFlight
        AtomicFunction getFlight = new AtomicFunction("getFlight", "getFlightType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        getFlight.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4GetFlight.json")));

        // selectPassenger
        AtomicFunction selectPassenger = new AtomicFunction("selectPassenger", "selectPassengerType", Arrays.asList(getDataOuts("InVal", "string", getFlight, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "collection"), new DataOutsAtomic("OutVal2", "number")));
        selectPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4SelectPassenger1000.json")));

        // parallelFor
        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        DataInsDataFlow dataIns = getDataOutsDataFlow("InVal", "collection", selectPassenger, 0);
        //dataIns.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(5)")));
        dataIns.setDataFlow(new DataFlowBlock("5"));
        parallelFor.setDataIns(Arrays.asList(dataIns));
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", getDataOutsByIndex(selectPassenger, 1)));

        // -> parallel informPassenger,calculateTimeToGate
        Parallel parallelInformCalc = new Parallel();
        parallelInformCalc.setName("parallelInformCalc");
        DataInsDataFlow dataInsParallel = getDataInsDataFlow("InVal", "string", parallelFor, 0);
        parallelInformCalc.setDataIns(Arrays.asList(dataInsParallel));
        AtomicFunction informPassenger = new AtomicFunction("informPassenger", "informPassengerType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), null);
        informPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4InformPassenger.json")));
        AtomicFunction calculateTimeToGate = new AtomicFunction("calculateTimeToGate", "calculateTimeToGateType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        calculateTimeToGate.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4CalculateTimeToGate.json")));

        IfThenElse ifThenElse = new IfThenElse();
        ifThenElse.setName("checkTime");
        ifThenElse.setDataIns(Arrays.asList(getDataOuts("InVal", "number", calculateTimeToGate, 0)));
        ifThenElse.setCondition(new Condition("and", Arrays.asList(new ACondition(getDataInsByIndex(ifThenElse, 0), "20", ">"))));

        AtomicFunction recommendShop = new AtomicFunction("recommendShop", "recommendShopType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        recommendShop.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4RecommendShop.json")));
        AtomicFunction informTimeCritical = new AtomicFunction("informTimeCritical", "informTimeCriticalType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        informTimeCritical.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4InformCriticalTime.json")));

        ifThenElse.setThen(Arrays.asList(recommendShop));
        ifThenElse.setElse(Arrays.asList(informTimeCritical));

        parallelInformCalc.setParallelBody(Arrays.asList(new Section(Arrays.asList(informPassenger)), new Section(Arrays.asList(calculateTimeToGate, ifThenElse))));
        parallelInformCalc.setDataOuts(Arrays.asList(new DataOuts("OutVal", "number", getDataOutsByIndex(calculateTimeToGate, 0))));

        parallelFor.setLoopBody(Arrays.asList(parallelInformCalc));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelInformCalc, 0))));

        AtomicFunction log = new AtomicFunction("log", "logType", Arrays.asList(getDataOuts("InVal", "collection", parallelFor, 0)), null);
        log.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:https://eu-gb.functions.cloud.ibm.com/api/v1/web/sashko%40dps.uibk.ac.at_dev/default/f4Log.json")));

        workflow.setWorkflowBody(Arrays.asList(getFlight, selectPassenger, parallelFor, log));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "gateChangeAlertCFCL_IBM_1000.yaml", "schema.json");
    }

    private void createGateChangeAlertCFCLAWS10() {
        //.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:")));
        Workflow workflow = new Workflow();
        workflow.setName("gateChangeAlert");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // getFlight
        AtomicFunction getFlight = new AtomicFunction("getFlight", "getFlightType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        getFlight.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:getFlight")));

        // selectPassenger
        AtomicFunction selectPassenger = new AtomicFunction("selectPassenger", "selectPassengerType", Arrays.asList(getDataOuts("InVal", "string", getFlight, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "collection"), new DataOutsAtomic("OutVal2", "number")));
        selectPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:selectPassenger10")));

        // parallelFor
        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        DataInsDataFlow dataIns = getDataOutsDataFlow("InVal", "collection", selectPassenger, 0);
        //dataIns.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(5)")));
        dataIns.setDataFlow(new DataFlowBlock("5"));
        parallelFor.setDataIns(Arrays.asList(dataIns));
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", getDataOutsByIndex(selectPassenger, 1)));

        // -> parallel informPassenger,calculateTimeToGate
        Parallel parallelInformCalc = new Parallel();
        parallelInformCalc.setName("parallelInformCalc");
        DataInsDataFlow dataInsParallel = getDataInsDataFlow("InVal", "string", parallelFor, 0);
        parallelInformCalc.setDataIns(Arrays.asList(dataInsParallel));
        AtomicFunction informPassenger = new AtomicFunction("informPassenger", "informPassengerType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), null);
        informPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:informPassenger")));
        AtomicFunction calculateTimeToGate = new AtomicFunction("calculateTimeToGate", "calculateTimeToGateType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        calculateTimeToGate.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:calculateTimeToGate")));

        IfThenElse ifThenElse = new IfThenElse();
        ifThenElse.setName("checkTime");
        ifThenElse.setDataIns(Arrays.asList(getDataOuts("InVal", "number", calculateTimeToGate, 0)));
        ifThenElse.setCondition(new Condition("and", Arrays.asList(new ACondition(getDataInsByIndex(ifThenElse, 0), "20", ">"))));

        AtomicFunction recommendShop = new AtomicFunction("recommendShop", "recommendShopType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        recommendShop.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:recommendShop")));
        AtomicFunction informTimeCritical = new AtomicFunction("informTimeCritical", "informTimeCriticalType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        informTimeCritical.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:informCriticalTime")));

        ifThenElse.setThen(Arrays.asList(recommendShop));
        ifThenElse.setElse(Arrays.asList(informTimeCritical));

        parallelInformCalc.setParallelBody(Arrays.asList(new Section(Arrays.asList(informPassenger)), new Section(Arrays.asList(calculateTimeToGate, ifThenElse))));
        parallelInformCalc.setDataOuts(Arrays.asList(new DataOuts("OutVal", "number", getDataOutsByIndex(calculateTimeToGate, 0))));

        parallelFor.setLoopBody(Arrays.asList(parallelInformCalc));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelInformCalc, 0))));

        AtomicFunction log = new AtomicFunction("log", "logType", Arrays.asList(getDataOuts("InVal", "collection", parallelFor, 0)), null);
        log.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:log")));

        workflow.setWorkflowBody(Arrays.asList(getFlight, selectPassenger, parallelFor, log));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "gateChangeAlertCFCL_AWS_10.yaml", "schema.json");
    }

    private void createGateChangeAlertCFCLAWS100() {
        //.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:")));
        Workflow workflow = new Workflow();
        workflow.setName("gateChangeAlert");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // getFlight
        AtomicFunction getFlight = new AtomicFunction("getFlight", "getFlightType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        getFlight.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:getFlight")));

        // selectPassenger
        AtomicFunction selectPassenger = new AtomicFunction("selectPassenger", "selectPassengerType", Arrays.asList(getDataOuts("InVal", "string", getFlight, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "collection"), new DataOutsAtomic("OutVal2", "number")));
        selectPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:selectPassenger100")));

        // parallelFor
        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        DataInsDataFlow dataIns = getDataOutsDataFlow("InVal", "collection", selectPassenger, 0);
        //dataIns.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(5)")));
        dataIns.setDataFlow(new DataFlowBlock("5"));
        parallelFor.setDataIns(Arrays.asList(dataIns));
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", getDataOutsByIndex(selectPassenger, 1)));

        // -> parallel informPassenger,calculateTimeToGate
        Parallel parallelInformCalc = new Parallel();
        parallelInformCalc.setName("parallelInformCalc");
        DataInsDataFlow dataInsParallel = getDataInsDataFlow("InVal", "string", parallelFor, 0);
        parallelInformCalc.setDataIns(Arrays.asList(dataInsParallel));
        AtomicFunction informPassenger = new AtomicFunction("informPassenger", "informPassengerType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), null);
        informPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:informPassenger")));
        AtomicFunction calculateTimeToGate = new AtomicFunction("calculateTimeToGate", "calculateTimeToGateType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        calculateTimeToGate.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:calculateTimeToGate")));

        IfThenElse ifThenElse = new IfThenElse();
        ifThenElse.setName("checkTime");
        ifThenElse.setDataIns(Arrays.asList(getDataOuts("InVal", "number", calculateTimeToGate, 0)));
        ifThenElse.setCondition(new Condition("and", Arrays.asList(new ACondition(getDataInsByIndex(ifThenElse, 0), "20", ">"))));

        AtomicFunction recommendShop = new AtomicFunction("recommendShop", "recommendShopType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        recommendShop.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:recommendShop")));
        AtomicFunction informTimeCritical = new AtomicFunction("informTimeCritical", "informTimeCriticalType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        informTimeCritical.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:informCriticalTime")));

        ifThenElse.setThen(Arrays.asList(recommendShop));
        ifThenElse.setElse(Arrays.asList(informTimeCritical));

        parallelInformCalc.setParallelBody(Arrays.asList(new Section(Arrays.asList(informPassenger)), new Section(Arrays.asList(calculateTimeToGate, ifThenElse))));
        parallelInformCalc.setDataOuts(Arrays.asList(new DataOuts("OutVal", "number", getDataOutsByIndex(calculateTimeToGate, 0))));

        parallelFor.setLoopBody(Arrays.asList(parallelInformCalc));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelInformCalc, 0))));

        AtomicFunction log = new AtomicFunction("log", "logType", Arrays.asList(getDataOuts("InVal", "collection", parallelFor, 0)), null);
        log.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:log")));

        workflow.setWorkflowBody(Arrays.asList(getFlight, selectPassenger, parallelFor, log));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "gateChangeAlertCFCL_AWS_100.yaml", "schema.json");
    }

    private void createGateChangeAlertCFCLAWS1000() {
        //.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:")));
        Workflow workflow = new Workflow();
        workflow.setName("gateChangeAlert");
        workflow.setDataIns(Arrays.asList(new DataIns("InVal", "string", "some source")));

        // getFlight
        AtomicFunction getFlight = new AtomicFunction("getFlight", "getFlightType", Arrays.asList(getDataIns("InVal", "string", workflow, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "string")));
        getFlight.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:getFlight")));

        // selectPassenger
        AtomicFunction selectPassenger = new AtomicFunction("selectPassenger", "selectPassengerType", Arrays.asList(getDataOuts("InVal", "string", getFlight, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "collection"), new DataOutsAtomic("OutVal2", "number")));
        selectPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:selectPassenger1000")));

        // parallelFor
        ParallelFor parallelFor = new ParallelFor();
        parallelFor.setName("parallelFor");
        DataInsDataFlow dataIns = getDataOutsDataFlow("InVal", "collection", selectPassenger, 0);
        //dataIns.setConstraints(Arrays.asList(new PropertyConstraint("distribution", "BLOCK(5)")));
        dataIns.setDataFlow(new DataFlowBlock("5"));
        parallelFor.setDataIns(Arrays.asList(dataIns));
        parallelFor.setLoopCounter(new LoopCounter("counter", "number", "0", getDataOutsByIndex(selectPassenger, 1)));

        // -> parallel informPassenger,calculateTimeToGate
        Parallel parallelInformCalc = new Parallel();
        parallelInformCalc.setName("parallelInformCalc");
        DataInsDataFlow dataInsParallel = getDataInsDataFlow("InVal", "string", parallelFor, 0);
        parallelInformCalc.setDataIns(Arrays.asList(dataInsParallel));
        AtomicFunction informPassenger = new AtomicFunction("informPassenger", "informPassengerType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), null);
        informPassenger.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:informPassenger")));
        AtomicFunction calculateTimeToGate = new AtomicFunction("calculateTimeToGate", "calculateTimeToGateType", Arrays.asList(getDataIns("InVal", "string", parallelInformCalc, 0)), Arrays.asList(new DataOutsAtomic("OutVal", "number")));
        calculateTimeToGate.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:calculateTimeToGate")));

        IfThenElse ifThenElse = new IfThenElse();
        ifThenElse.setName("checkTime");
        ifThenElse.setDataIns(Arrays.asList(getDataOuts("InVal", "number", calculateTimeToGate, 0)));
        ifThenElse.setCondition(new Condition("and", Arrays.asList(new ACondition(getDataInsByIndex(ifThenElse, 0), "20", ">"))));

        AtomicFunction recommendShop = new AtomicFunction("recommendShop", "recommendShopType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        recommendShop.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:recommendShop")));
        AtomicFunction informTimeCritical = new AtomicFunction("informTimeCritical", "informTimeCriticalType", Arrays.asList(getDataIns("InVal", "number", ifThenElse, 0)), null);
        informTimeCritical.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:informCriticalTime")));

        ifThenElse.setThen(Arrays.asList(recommendShop));
        ifThenElse.setElse(Arrays.asList(informTimeCritical));

        parallelInformCalc.setParallelBody(Arrays.asList(new Section(Arrays.asList(informPassenger)), new Section(Arrays.asList(calculateTimeToGate, ifThenElse))));
        parallelInformCalc.setDataOuts(Arrays.asList(new DataOuts("OutVal", "number", getDataOutsByIndex(calculateTimeToGate, 0))));

        parallelFor.setLoopBody(Arrays.asList(parallelInformCalc));
        parallelFor.setDataOuts(Arrays.asList(new DataOuts("OutVal", "collection", getDataOutsByIndex(parallelInformCalc, 0))));

        AtomicFunction log = new AtomicFunction("log", "logType", Arrays.asList(getDataOuts("InVal", "collection", parallelFor, 0)), null);
        log.setProperties(Arrays.asList(new PropertyConstraint("resource", "python:arn:aws:lambda:us-east-1:170392512081:function:log")));

        workflow.setWorkflowBody(Arrays.asList(getFlight, selectPassenger, parallelFor, log));

        // Write workflow to YAML
        Utils.writeYaml(workflow, "gateChangeAlertCFCL_AWS_1000.yaml", "schema.json");
    }

    private void testRead() {
        Workflow wf = Utils.readYAML("gateChangeAlert.yaml", "schema.json");
        System.out.println(((ParallelFor) wf.getWorkflowBody().get(2)).getLoopBody().get(0));
    }

    private String getDataInsByIndex(Function function, int index) {
        if (function instanceof AtomicFunction) {
            AtomicFunction atomicFunction = (AtomicFunction) function;
            return atomicFunction.getName() + "/" + atomicFunction.getDataIns().get(index).getName();
        } else if (function instanceof CompoundSequential) {
            CompoundSequential compoundSequential = (CompoundSequential) function;
            return compoundSequential.getName() + "/" + compoundSequential.getDataIns().get(index).getName();
        } else if (function instanceof CompoundParallel) {
            CompoundParallel compoundParallel = (CompoundParallel) function;
            return compoundParallel.getName() + "/" + compoundParallel.getDataIns().get(index).getName();
        }
        System.err.println("not found " + function.getName());
        return null;
    }

    private String getDataOutsByIndex(Function function, int index) {
        if (function instanceof AtomicFunction) {
            AtomicFunction atomicFunction = (AtomicFunction) function;
            return atomicFunction.getName() + "/" + atomicFunction.getDataOuts().get(index).getName();
        } else if (function instanceof CompoundSequential) {
            CompoundSequential compoundSequential = (CompoundSequential) function;
            return compoundSequential.getName() + "/" + compoundSequential.getDataOuts().get(index).getName();
        } else if (function instanceof CompoundParallel) {
            CompoundParallel compoundParallel = (CompoundParallel) function;
            return compoundParallel.getName() + "/" + compoundParallel.getDataOuts().get(index).getName();
        }
        System.err.println("not found " + function.getName());
        return null;
    }

    private DataIns getDataOuts(String name, String type, Function function, int outParamIndex) {
        DataIns dataIns = new DataIns();
        if (function instanceof AtomicFunction) {
            AtomicFunction atomicFunction = ((AtomicFunction) function);
            dataIns.setSource(atomicFunction.getName() + "/" + atomicFunction.getDataOuts().get(outParamIndex).getName());
        } else if (function instanceof Switch) {
            Switch _switch = (Switch) function;
            dataIns.setSource(_switch.getName() + "/" + _switch.getDataOuts().get(outParamIndex).getName());
        } else if (function instanceof Parallel) {
            Parallel parallel = (Parallel) function;
            dataIns.setSource(parallel.getName() + "/" + parallel.getDataOuts().get(outParamIndex).getName());
        } else if (function instanceof ParallelFor) {
            ParallelFor parallelFor = (ParallelFor) function;
            dataIns.setSource(parallelFor.getName() + "/" + parallelFor.getDataOuts().get(outParamIndex).getName());
        } else {
            System.err.println("No allowed yet (DataOuts class)");
        }
        dataIns.setName(name);
        dataIns.setType(type);
        return dataIns;
    }

    private DataInsDataFlow getDataOutsDataFlow(String name, String type, Function function, int outParamIndex) {
        DataInsDataFlow dataIns = new DataInsDataFlow();
        if (function instanceof AtomicFunction) {
            AtomicFunction atomicFunction = ((AtomicFunction) function);
            dataIns.setSource(atomicFunction.getName() + "/" + atomicFunction.getDataOuts().get(outParamIndex).getName());
        } else if (function instanceof Switch) {
            Switch _switch = (Switch) function;
            dataIns.setSource(_switch.getName() + "/" + _switch.getDataOuts().get(outParamIndex).getName());
        } else if (function instanceof Parallel) {
            Parallel parallel = (Parallel) function;
            dataIns.setSource(parallel.getName() + "/" + parallel.getDataOuts().get(outParamIndex).getName());
        } else if (function instanceof ParallelFor) {
            ParallelFor parallelFor = (ParallelFor) function;
            dataIns.setSource(parallelFor.getName() + "/" + parallelFor.getDataOuts().get(outParamIndex).getName());
        } else {
            System.err.println("No allowed yet (DataOuts class)");
        }
        dataIns.setName(name);
        dataIns.setType(type);
        return dataIns;
    }

    private DataIns getDataIns(String name, String type, Workflow workflow, int inParamIndex) {
        DataIns dataIns = new DataIns();
        dataIns.setSource(workflow.getName() + "/" + workflow.getDataIns().get(inParamIndex).getName());
        dataIns.setName(name);
        dataIns.setType(type);
        return dataIns;
    }

    private DataIns getDataIns(String name, String type, Function function, int inParamIndex) {
        DataIns dataIns = new DataIns();
        if (function instanceof AtomicFunction) {
            AtomicFunction atomicFunction = ((AtomicFunction) function);
            dataIns.setSource(atomicFunction.getName() + "/" + atomicFunction.getDataIns().get(inParamIndex).getName());
        } else if (function instanceof CompoundSequential) {
            CompoundSequential compoundSequential = (CompoundSequential) function;
            dataIns.setSource(compoundSequential.getName() + "/" + compoundSequential.getDataIns().get(inParamIndex).getName());
        } else if (function instanceof CompoundParallel) {
            CompoundParallel compoundParallel = (CompoundParallel) function;
            dataIns.setSource(compoundParallel.getName() + "/" + compoundParallel.getDataIns().get(inParamIndex).getName());
        } else {
            System.err.println("No allowed yet (DataIns class)");
        }
        dataIns.setName(name);
        dataIns.setType(type);
        return dataIns;
    }

    private DataInsDataFlow getDataInsDataFlow(String name, String type, Workflow wf, int inParamIndex) {
        DataInsDataFlow dataIns = new DataInsDataFlow();
        dataIns.setSource(wf.getName() + "/" + wf.getDataIns().get(inParamIndex).getName());
        dataIns.setName(name);
        dataIns.setType(type);
        return dataIns;
    }

    private DataInsDataFlow getDataInsDataFlow(String name, String type, Function function, int inParamIndex) {
        DataInsDataFlow dataIns = new DataInsDataFlow();
        if (function instanceof AtomicFunction) {
            AtomicFunction atomicFunction = ((AtomicFunction) function);
            dataIns.setSource(atomicFunction.getName() + "/" + atomicFunction.getDataIns().get(inParamIndex).getName());
        } else if (function instanceof CompoundSequential) {
            CompoundSequential compoundSequential = (CompoundSequential) function;
            dataIns.setSource(compoundSequential.getName() + "/" + compoundSequential.getDataIns().get(inParamIndex).getName());
        } else if (function instanceof CompoundParallel) {
            CompoundParallel compoundParallel = (CompoundParallel) function;
            dataIns.setSource(compoundParallel.getName() + "/" + compoundParallel.getDataIns().get(inParamIndex).getName());
        } else {
            System.err.println("No allowed yet (DataIns class)");
        }
        dataIns.setName(name);
        dataIns.setType(type);
        return dataIns;
    }
}

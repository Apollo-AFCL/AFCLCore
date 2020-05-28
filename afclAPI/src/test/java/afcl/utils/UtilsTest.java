package afcl.utils;

import afcl.Function;
import afcl.Workflow;
import afcl.functions.AtomicFunction;
import afcl.functions.ParallelFor;
import afcl.functions.objects.*;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UtilsTest {

    /**
     * Creates an example of a simple workflow consisting of an
     * atomicFunction and a parallelFor loop.
     *
     * @return simple workflow
     */
    private Workflow getSimpleWorkflow() {

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
     * Creates an example of a simple workflow which is not
     * valid according to the schema.
     *
     * @return simple invalid workflow
     */
    private Workflow getSimpleInvalidWorkflow() {

        List<Function> workflowBody = new ArrayList<>();
        ParallelFor parallelFor = new ParallelFor();
        workflowBody.add(parallelFor);

        return new Workflow("workflow", null, workflowBody, null);
    }

    /**
     * Test the reading and writing of a json workflow.
     */
    @Test
    public void writeReadJsonTest() {
        File workflowFile = new File("writeRead.json");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow1 = getSimpleWorkflow();
        Workflow workflow2 = null;
        try {
            Utils.writeJson(workflow1, workflowFile.getName(), schema.getAbsolutePath());
            workflow2 = Utils.readJSON(workflowFile.getName(), schema.getAbsolutePath());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(workflow1, workflow2);

        workflowFile.delete();
    }

    /**
     * Test the reading and writing of a yaml workflow.
     */
    @Test
    public void writeReadYamlTest() {
        File workflowFile = new File("writeRead.yaml");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow1 = getSimpleWorkflow();
        Workflow workflow2 = null;
        try {
            Utils.writeYaml(workflow1, workflowFile.getName(), schema.getAbsolutePath());
            workflow2 = Utils.readYAML(workflowFile.getName(), schema.getAbsolutePath());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(workflow1, workflow2);

        workflowFile.delete();
    }

    /**
     * Test the reading of a invalid YAML workflow file.
     */
    @Test
    public void invalidReadYamlFile() {
        File workflowFile = new File("src/test/resources/invalid.yaml");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow2 = null;
        try {
            workflow2 = Utils.readYAML(workflowFile.getAbsolutePath(), schema.getAbsolutePath());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertNull(workflow2);
    }

    /**
     * Test the reading of a invalid JSON workflow file.
     */
    @Test
    public void invalidReadJsonFile() {
        File workflowFile = new File("src/test/resources/invalid.json");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow2 = null;
        try {
            workflow2 = Utils.readJSON(workflowFile.getAbsolutePath(), schema.getAbsolutePath());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertNull(workflow2);
    }

    /**
     * Test the writing of a invalid workflow file.
     * <p>
     * Expected: file will not be written.
     */
    @Test
    public void invalidWriteFile() {
        File workflowFile = new File("writeInvalid.yaml");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow1 = getSimpleInvalidWorkflow();
        try {
            Utils.writeYaml(workflow1, workflowFile.getName(), schema.getAbsolutePath());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(workflowFile.exists());
    }

    /**
     * Test the reading of a invalid workflow file which does not exist.
     */
    @Test
    public void fileNotExistRead() {
        File workflowFile = new File("wrong/path/to/workflow.yaml");
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());
        Workflow workflow2 = null;
        try {
            workflow2 = Utils.readYAML(workflowFile.getName(), schema.getAbsolutePath());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertNull(workflow2);

        workflowFile.delete();
    }

    /**
     * Test the reading of a json string.
     */
    @Test
    public void readJsonString() {
        String jsonString = "{\r\n  \"name\": \"workflow\",\r\n  \"workflowBody\": [\r\n    {\r\n      \"function\": {\r\n        \"name\": \"atomicFunction\",\r\n        " +
                "\"type\": \"atomicFunctionType\"\r\n      }\r\n    },\r\n    {\r\n      \"parallelFor\": {\r\n        \"name\": \"parallelFor\",\r\n        " +
                "\"loopCounter\": {\r\n          \"name\": \"loopCounter\",\r\n          \"type\": \"loopCounterType\",\r\n          \"from\": \"0\",\r\n          " +
                "\"to\": \"10\"\r\n        },\r\n        \"loopBody\": [\r\n          {\r\n            \"function\": {\r\n              " +
                "\"name\": \"atomicFunction\",\r\n              \"type\": \"atomicFunctionType\"\r\n            }\r\n          }\r\n        " +
                "]\r\n      }\r\n    }\r\n  ]\r\n}";
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());

        Workflow workflow1 = getSimpleWorkflow();
        Workflow workflow2 = null;
        try {
            workflow2 = Utils.readJSONString(jsonString, schema.getAbsolutePath());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(workflow1, workflow2);
    }

    /**
     * Test the reading of an invalid json string.
     */
    @Test
    public void readJsonStringInvalid() {
        String jsonString = "{\r\n  \"name\": \"workflow\",\r\n  \"workflowBody\": [\r\n    {\r\n      \"function\": {\r\n        \"name\": \"atomicFunction\",\r\n        " +
                "\"type\": \"atomicFunctionType\"\r\n      }\r\n    },\r\n    {\r\n      \"parallelFor\": {\r\n        \"name\": \"parallelFor\",\r\n        " +
                "\"loopCounter\": {\r\n          \"name\": \"loopCounter\",\r\n          \"type\": \"loopCounterType\",\r\n          \"from\": \"0\",\r\n          " +
                "\"to\": \"10\"\r\n        },\r\n        \"loopBody\": [\r\n          {\r\n            \"function\": {\r\n              " +
                "\"name\": \"atomicFunction\"            }\r\n          }\r\n        " +
                "]\r\n      }\r\n    }\r\n  ]\r\n}";

        Workflow workflow1 = getSimpleWorkflow();
        Workflow workflow2 = null;
        try {
            workflow2 = Utils.readJSONStringNoValidation(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotEquals(workflow1, workflow2);
    }

    /**
     * Test the validation of a Workflow.
     */
    @Test
    public void validateWorkflow() {
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());
        Workflow workflow1 = getSimpleWorkflow();
        try {
            Assert.assertTrue(Utils.validate(workflow1, schema.getAbsolutePath()));
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the validation of an invalid Workflow.
     */
    @Test
    public void validateInvalidWorkflow() {
        File schema = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json")).getFile());
        Workflow workflow1 = getSimpleInvalidWorkflow();
        try {
            Assert.assertFalse(Utils.validate(workflow1, schema.getAbsolutePath()));
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the reading and writing of a yaml workflow.
     */
    @Test
    public void writeReadYamlNoValidationTest() {
        File workflowFile = new File("writeReadNoValidation.yaml");

        Workflow workflow1 = getSimpleInvalidWorkflow();
        Workflow workflow2 = null;
        try {
            Utils.writeYamlNoValidation(workflow1, workflowFile.getName());
            workflow2 = Utils.readYAMLNoValidation(workflowFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(workflow1, workflow2);

        workflowFile.delete();
    }

    /**
     * Test the writing of a yaml workflow with missing permissions.
     */
    @Test
    public void writeFileInvalidPermissions() {
        File workflowFile = new File("invalidPermissions.yaml");

        if(workflowFile.exists()){
            workflowFile.delete();
        }

        try {
            workflowFile.createNewFile();
            workflowFile.setWritable(false, false);

            Workflow workflow1 = getSimpleWorkflow();
            Utils.writeYamlNoValidation(workflow1, workflowFile.getName());

            //Assert.assertEquals(0, workflowFile.length());

        } catch (IOException ignored) {
        } finally {
            workflowFile.delete();
        }
    }

}

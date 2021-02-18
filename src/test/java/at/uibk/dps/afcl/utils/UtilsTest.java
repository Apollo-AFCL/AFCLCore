package at.uibk.dps.afcl.utils;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.Workflow;
import at.uibk.dps.afcl.functions.AtomicFunction;
import at.uibk.dps.afcl.functions.ParallelFor;
import at.uibk.dps.afcl.functions.objects.LoopCounter;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Test the functionality of the utility class.
 *
 * @author stefanpedratscher
 */
public class UtilsTest {

    /**
     * Creates an example of a simple workflow consisting of an
     * atomicFunction and a parallelFor loop.
     *
     * @return simple workflow
     */
    private Workflow getSimpleWorkflow() {

        final List<Function> workflowBody = new ArrayList<>();

        final AtomicFunction atomicFunction =
            new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);
        workflowBody.add(atomicFunction);

        final ParallelFor parallelFor = new ParallelFor("parallelFor", null,
            new LoopCounter("loopCounter", "loopCounterType", "0", "10"),
            new ArrayList<>(Collections.singleton(atomicFunction)), null);
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
        final ParallelFor parallelFor = new ParallelFor();
        workflowBody.add(parallelFor);

        return new Workflow("workflow", null, workflowBody, null);
    }

    /**
     * Test the reading and writing of a json workflow.
     */
    @Test public void writeReadJsonTest() {
        final File workflowFile = new File("writeRead.json");
        final File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());

        final Workflow workflow1 = getSimpleWorkflow();
        Workflow workflow2 = null;
        try {
            Utils.writeJson(workflow1, workflowFile.getName(), schema.getAbsolutePath());
            workflow2 = Utils.readJSON(workflowFile.getName(), schema.getAbsolutePath());
        } catch (IOException |
            ProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(workflow1, workflow2);

        workflowFile.delete();
    }

    /**
     * Test the reading and writing of a yaml workflow.
     */
    @Test public void writeReadYamlTest() {
        final File workflowFile = new File("writeRead.yaml");

        final File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());

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
    @Test public void invalidReadYamlFile() {
        final File workflowFile = new File("src/test/resources/invalid.yaml");
        final File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());

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
    @Test public void invalidReadJsonFile() {
        final File workflowFile = new File("src/test/resources/invalid.json");
        final File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());

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
    @Test public void invalidWriteFile() {
        final File workflowFile = new File("writeInvalid.yaml");
        final File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());

        final Workflow workflow1 = getSimpleInvalidWorkflow();
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
    @Test public void fileNotExistRead() {
        final File workflowFile = new File("wrong/path/to/workflow.yaml");
        final File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());
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
    @Test public void readJsonString() {
        final String jsonString =
            "{\r\n  \"name\": \"workflow\",\r\n  \"workflowBody\": [\r\n    {\r\n      \"function\": {\r\n        \"name\": \"atomicFunction\",\r\n        "
                + "\"type\": \"atomicFunctionType\"\r\n      }\r\n    },\r\n    {\r\n      \"parallelFor\": {\r\n        \"name\": \"parallelFor\",\r\n        "
                + "\"loopCounter\": {\r\n          \"name\": \"loopCounter\",\r\n          \"type\": \"loopCounterType\",\r\n          \"from\": \"0\",\r\n          "
                + "\"to\": \"10\"\r\n        },\r\n        \"loopBody\": [\r\n          {\r\n            \"function\": {\r\n              "
                + "\"name\": \"atomicFunction\",\r\n              \"type\": \"atomicFunctionType\"\r\n            }\r\n          }\r\n        "
                + "]\r\n      }\r\n    }\r\n  ]\r\n}";
        File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());

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
    @Test public void readJsonStringInvalid() {
        String jsonString =
            "{\r\n  \"name\": \"workflow\",\r\n  \"workflowBody\": [\r\n    {\r\n      \"function\": {\r\n        \"name\": \"atomicFunction\",\r\n        "
                + "\"type\": \"atomicFunctionType\"\r\n      }\r\n    },\r\n    {\r\n      \"parallelFor\": {\r\n        \"name\": \"parallelFor\",\r\n        "
                + "\"loopCounter\": {\r\n          \"name\": \"loopCounter\",\r\n          \"type\": \"loopCounterType\",\r\n          \"from\": \"0\",\r\n          "
                + "\"to\": \"10\"\r\n        },\r\n        \"loopBody\": [\r\n          {\r\n            \"function\": {\r\n              "
                + "\"name\": \"atomicFunction\"            }\r\n          }\r\n        "
                + "]\r\n      }\r\n    }\r\n  ]\r\n}";

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
    @Test public void validateWorkflow() {
        File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());
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
    @Test public void validateInvalidWorkflow() {
        File schema = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("schema.json"))
                .getFile());
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
    @Test public void writeReadYamlNoValidationTest() {
        final File workflowFile = new File("writeReadNoValidation.yaml");

        final Workflow workflow1 = getSimpleInvalidWorkflow();
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
     * Test the reading and writing of a yaml workflow.
     */
    @Test public void writeReadYamlNoValidationByteArrayTest() {
        final File workflowFile = new File("writeReadNoValidation.yaml");

        final Workflow workflow1 = getSimpleInvalidWorkflow();
        Workflow workflow2 = null;
        try {
            Utils.writeYamlNoValidation(workflow1, workflowFile.getName());
            workflow2 = Utils.readYAMLNoValidation(FileUtils.readFileToByteArray(workflowFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(workflow1, workflow2);

        workflowFile.delete();
    }

    /**
     * Test the writing of a yaml workflow with missing permissions.
     */
    @Test public void fileNotExistWrite() {
        final File invalidFile = new File("path/to/invalidName.yaml");

        try {
            Workflow workflow1 = getSimpleWorkflow();
            Utils.writeYamlNoValidation(workflow1, invalidFile.getAbsolutePath());

            Assert.assertFalse(invalidFile.exists());

        } catch (Exception ignored) {
        }
    }
}

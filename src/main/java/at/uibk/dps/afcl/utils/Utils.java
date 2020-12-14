package at.uibk.dps.afcl.utils;

import at.uibk.dps.afcl.Workflow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to write and read a workflow
 * @author stefanpedratscher
 */
public class Utils {

    private Utils(){}

    private static final String PREFIX_ERROR = "ERROR: ";

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    /**
     * Parse a workflow
     *
     * @param workflow object to parse
     * @param yf yaml factory used for parsing
     * @param jsonSchema to validate
     * @param objectMapper to map objects
     * @return workflow
     * @throws IOException on failure
     * @throws ProcessingException on failure
     */
    private static Workflow parseWorkflow(Workflow workflow, YAMLFactory yf, String jsonSchema, ObjectMapper objectMapper) throws IOException, ProcessingException {
        byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        JsonNode workflowNode = objectMapper.readTree(yf.createParser(bytes));
        ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            return workflow;
        }else{
            LOGGER.log(Level.SEVERE, PREFIX_ERROR + "{0}", processingReport);
            return null;
        }
    }

    /**
     * Validate a workflowNode with a given schema
     *
     * @param jsonSchema used for validation
     * @param workflowNode to validate
     * @return processing report with results
     * @throws ProcessingException on failure
     * @throws IOException on failure
     */
    private static ProcessingReport validateWorkflowNode(String jsonSchema, JsonNode workflowNode) throws ProcessingException, IOException {
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
        JsonSchema schema = schemaFactory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
        return schema.validate(workflowNode);
    }

    /**
     * Write workflow to file if validation succeeds
     *
     * @param workflowNode workflow to write
     * @param bytes to write to file
     * @param destination of the file
     * @param jsonSchema used for validation
     * @throws IOException on failure
     * @throws ProcessingException on failure
     */
    private static void validateAndWriteFile(JsonNode workflowNode, byte[] bytes, String destination, String jsonSchema) throws IOException, ProcessingException {
        ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            File file = new File(destination);
            writeBytes(file, bytes);
        }else{
            LOGGER.log(Level.SEVERE, PREFIX_ERROR + "{0}", processingReport);
        }
    }

    /**
     * Write byte[] to file
     *
     * @param file on which byte[] should be written
     * @param bytes to write
     */
    private static void writeBytes(File file, byte[] bytes){
        try (OutputStream fileOutputStream = Files.newOutputStream(Paths.get(file.getName()))) {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Write workflow to Json File
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeJson(Workflow workflow, String destination, String jsonSchema) throws IOException, ProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        JsonNode workflowNode = objectMapper.readTree(bytes);
        validateAndWriteFile(workflowNode, bytes, destination, jsonSchema);
    }

    /**
     * Write workflow to Yaml File
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeYaml(Workflow workflow, String destination, String jsonSchema) throws IOException, ProcessingException {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        JsonNode workflowNode = objectMapper.readTree(yf.createParser(bytes));
        validateAndWriteFile(workflowNode, bytes, destination, jsonSchema);

    }

    /**
     * Read Json file to get workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSON(String origin, String jsonSchema) throws IOException, ProcessingException {
        File file = new File(origin);
        ObjectMapper objectMapper = new ObjectMapper();
        Workflow workflow = objectMapper.readValue(file, Workflow.class);

        byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        JsonNode workflowNode = objectMapper.readTree(bytes);

        ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            return workflow;
        }
        LOGGER.log(Level.SEVERE, PREFIX_ERROR + "{0}", processingReport);
        return null;
    }

    /**
     * Read Yaml file to get workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readYAML(String origin, String jsonSchema) throws IOException, ProcessingException {
        File file = new File(origin);
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);

        ObjectMapper objectMapper = new ObjectMapper(yf);
        Workflow workflow = objectMapper.readValue(file, Workflow.class);
        return parseWorkflow(workflow, yf, jsonSchema, objectMapper);

    }

    /**
     * Read a json string and convert to workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSONString(String origin, String jsonSchema) throws IOException, ProcessingException {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);

        ObjectMapper objectMapper = new ObjectMapper(yf);
        Workflow workflow = objectMapper.readValue(origin, Workflow.class);
        return parseWorkflow(workflow, yf, jsonSchema, objectMapper);
    }

    /**
     * Read a json string and convert to workflow without validation
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSONStringNoValidation(final String origin) throws IOException {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        return objectMapper.readValue(origin, Workflow.class);
    }

    /**
     * Validate a workflow according to the schema
     *
     * @return true if validation was successful
     */
    public static boolean validate(Workflow workflow, String jsonSchema) throws IOException, ProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        JsonNode workflowNode = objectMapper.readTree(bytes);

        ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            return true;
        }else{
            LOGGER.log(Level.SEVERE, PREFIX_ERROR + "{0}", processingReport);
            return false;
        }
    }

    /**
     * Write workflow to Yaml File and do not validate
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeYamlNoValidation(Workflow workflow, final String destination) throws JsonProcessingException {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        byte[] bytes = objectMapper.writeValueAsBytes(workflow);

        File file = new File(destination);
        writeBytes(file, bytes);
    }

    /**
     * Read Yaml file to get workflow and do not validate
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readYAMLNoValidation(String origin) throws IOException {
        File file = new File(origin);
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        return objectMapper.readValue(file, Workflow.class);
    }

    /**
     * Read Yaml byte[] to get workflow and do not validate
     *
     * @param origin byte[] origin to read from
     * @return workflow
     */
    public static Workflow readYAMLNoValidation(byte[] origin) throws IOException {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        return objectMapper.readValue(origin, Workflow.class);
    }
}

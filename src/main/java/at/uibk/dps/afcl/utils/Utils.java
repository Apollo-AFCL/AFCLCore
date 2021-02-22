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
public final class Utils {

    /**
     * Prefix for the error output.
     */
    private static final String PREFIX_ERROR = "ERROR: {0}";

    /**
     * Logger for the Utils class.
     */
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    /**
     * Default constructor.
     */
    private Utils() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Parse a workflow
     *
     * @param workflow object to parse
     * @param yamlFactory yaml factory used for parsing
     * @param jsonSchema to validate
     * @param objectMapper to map objects
     * @return workflow
     * @throws IOException on failure
     * @throws ProcessingException on failure
     */
    private static Workflow parseWorkflow(final Workflow workflow, final YAMLFactory yamlFactory, final String jsonSchema, final ObjectMapper objectMapper) throws IOException, ProcessingException {
        final byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        final JsonNode workflowNode = objectMapper.readTree(yamlFactory.createParser(bytes));
        final ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            return workflow;
        }else{
            LOGGER.log(Level.SEVERE, PREFIX_ERROR, processingReport);
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
    private static ProcessingReport validateWorkflowNode(final String jsonSchema, final JsonNode workflowNode) throws ProcessingException, IOException {
        final JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
        final JsonSchema schema = schemaFactory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
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
    private static void validateAndWriteFile(final JsonNode workflowNode, final byte[] bytes, final String destination, final String jsonSchema) throws IOException, ProcessingException {
        final ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            final File file = new File(destination);
            writeBytes(file, bytes);
        }else{
            LOGGER.log(Level.SEVERE, PREFIX_ERROR, processingReport);
        }
    }

    /**
     * Write byte[] to file
     *
     * @param file on which byte[] should be written
     * @param bytes to write
     */
    private static void writeBytes(final File file, final byte[] bytes){
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
    public static void writeJson(final Workflow workflow, final String destination, final String jsonSchema) throws IOException, ProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        final JsonNode workflowNode = objectMapper.readTree(bytes);
        validateAndWriteFile(workflowNode, bytes, destination, jsonSchema);
    }

    /**
     * Write workflow to Yaml File
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeYaml(final Workflow workflow, final String destination, final String jsonSchema) throws IOException, ProcessingException {
        final YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        final ObjectMapper objectMapper = new ObjectMapper(yamlFactory);
        final byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        final JsonNode workflowNode = objectMapper.readTree(yamlFactory.createParser(bytes));
        validateAndWriteFile(workflowNode, bytes, destination, jsonSchema);

    }

    /**
     * Read Json file to get workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSON(final String origin, final String jsonSchema) throws IOException, ProcessingException {
        final File file = new File(origin);
        final ObjectMapper objectMapper = new ObjectMapper();
        final Workflow workflow = objectMapper.readValue(file, Workflow.class);

        final byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        final JsonNode workflowNode = objectMapper.readTree(bytes);

        final ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            return workflow;
        }
        LOGGER.log(Level.SEVERE, PREFIX_ERROR, processingReport);
        return null;
    }

    /**
     * Read Yaml file to get workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readYAML(final String origin, final String jsonSchema) throws IOException, ProcessingException {
        final File file = new File(origin);
        final YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);

        final ObjectMapper objectMapper = new ObjectMapper(yamlFactory);
        final Workflow workflow = objectMapper.readValue(file, Workflow.class);
        return parseWorkflow(workflow, yamlFactory, jsonSchema, objectMapper);

    }

    /**
     * Read a json string and convert to workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSONString(final String origin, final String jsonSchema) throws IOException, ProcessingException {
        final YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);

        final ObjectMapper objectMapper = new ObjectMapper(yf);
        final Workflow workflow = objectMapper.readValue(origin, Workflow.class);
        return parseWorkflow(workflow, yf, jsonSchema, objectMapper);
    }

    /**
     * Read a json string and convert to workflow without validation
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSONStringNoValidation(final String origin) throws IOException {
        final YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        final ObjectMapper objectMapper = new ObjectMapper(yf);
        return objectMapper.readValue(origin, Workflow.class);
    }

    /**
     * Validate a workflow according to the schema
     *
     * @return true if validation was successful
     */
    public static boolean validate(final Workflow workflow, final String jsonSchema) throws IOException, ProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final byte[] bytes = objectMapper.writeValueAsBytes(workflow);
        final JsonNode workflowNode = objectMapper.readTree(bytes);

        final ProcessingReport processingReport = validateWorkflowNode(jsonSchema, workflowNode);
        if(processingReport.isSuccess()){
            return true;
        }else{
            LOGGER.log(Level.SEVERE, PREFIX_ERROR, processingReport);
            return false;
        }
    }

    /**
     * Write workflow to Yaml File and do not validate
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeYamlNoValidation(final Workflow workflow, final String destination) throws JsonProcessingException {
        final YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        final ObjectMapper objectMapper = new ObjectMapper(yf);
        final byte[] bytes = objectMapper.writeValueAsBytes(workflow);

        final File file = new File(destination);
        writeBytes(file, bytes);
    }

    /**
     * Read Yaml file to get workflow and do not validate
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readYAMLNoValidation(final String origin) throws IOException {
        final File file = new File(origin);
        final YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        final ObjectMapper objectMapper = new ObjectMapper(yf);
        return objectMapper.readValue(file, Workflow.class);
    }

    /**
     * Read Yaml byte[] to get workflow and do not validate
     *
     * @param origin byte[] origin to read from
     * @return workflow
     */
    public static Workflow readYAMLNoValidation(final byte[] origin) throws IOException {
        final YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        final ObjectMapper objectMapper = new ObjectMapper(yf);
        return objectMapper.readValue(origin, Workflow.class);
    }
}

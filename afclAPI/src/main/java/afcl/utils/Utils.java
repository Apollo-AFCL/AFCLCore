package afcl.utils;

import afcl.Workflow;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to write and read a workflow
 * @author stefanpedratscher
 */
public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
    //private static final URL JSON_SCHEMA_LOCATION =  Utils.class.getResource("schema.json");

    /**
     * Write workflow to Json File
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeJson(Workflow workflow, String destination, String jsonSchema) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(workflow);
            JsonNode workflowNode = objectMapper.readTree(bytes);

            JsonSchemaFactory schema_factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = schema_factory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
            ProcessingReport processingReport = schema.validate(workflowNode);
            if(processingReport.isSuccess()){
                File file = new File(destination);
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                LOGGER.log(Level.SEVERE, processingReport.toString());
            }
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Write workflow to Yaml File
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeYaml(Workflow workflow, String destination, String jsonSchema) {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(workflow);
            JsonNode workflowNode = objectMapper.readTree(yf.createParser(bytes));

            JsonSchemaFactory schema_factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = schema_factory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
            ProcessingReport processingReport = schema.validate(workflowNode);
            if(processingReport.isSuccess()){
                File file = new File(destination);
                try (FileOutputStream fileOuputStream = new FileOutputStream(file)) {
                    fileOuputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                LOGGER.log(Level.SEVERE, processingReport.toString());
            }
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read Json file to get workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSON(String origin, String jsonSchema) {
        File file = new File(origin);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Workflow workflow = objectMapper.readValue(file, Workflow.class);

            byte[] bytes = objectMapper.writeValueAsBytes(workflow);
            JsonNode workflowNode = objectMapper.readTree(bytes);

            JsonSchemaFactory schema_factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = schema_factory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
            ProcessingReport processingReport = schema.validate(workflowNode);
            if(processingReport.isSuccess()){
                return workflow;
            }else{
                LOGGER.log(Level.SEVERE, processingReport.toString());
                return null;
            }
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read Yaml file to get workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readYAML(String origin, String jsonSchema) {
        File file = new File(origin);
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        try {
            Workflow workflow = objectMapper.readValue(file, Workflow.class);

            byte[] bytes = objectMapper.writeValueAsBytes(workflow);
            JsonNode workflowNode = objectMapper.readTree(yf.createParser(bytes));

            JsonSchemaFactory schema_factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = schema_factory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
            ProcessingReport processingReport = schema.validate(workflowNode);
            if(processingReport.isSuccess()){
                return workflow;
            }else{
                LOGGER.log(Level.SEVERE, processingReport.toString());
                return null;
            }
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read a json string and convert to workflow
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSONString(String origin, String jsonSchema) {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        try {
            Workflow workflow = objectMapper.readValue(origin, Workflow.class);

            byte[] bytes = objectMapper.writeValueAsBytes(workflow);
            JsonNode workflowNode = objectMapper.readTree(yf.createParser(bytes));

            JsonSchemaFactory schema_factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = schema_factory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
            ProcessingReport processingReport = schema.validate(workflowNode);
            if(processingReport.isSuccess()){
                return workflow;
            }else{
                LOGGER.log(Level.SEVERE, processingReport.toString());
                return null;
            }
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read a json string and convert to workflow without validation
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readJSONStringNoValidation(String origin) {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        try {
            return objectMapper.readValue(origin, Workflow.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Validate a workflow according to the schema
     *
     * @return true if validation was successful
     */
    public static boolean validate(Workflow workflow, String jsonSchema){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(workflow);
            JsonNode workflowNode = objectMapper.readTree(bytes);

            JsonSchemaFactory schema_factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = schema_factory.getJsonSchema(JsonLoader.fromFile(new File(jsonSchema)));
            ProcessingReport processingReport = schema.validate(workflowNode);
            if(processingReport.isSuccess()){
                return true;
            }else{
                LOGGER.log(Level.SEVERE, processingReport.toString());
                return false;
            }
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Write workflow to Yaml File and do not validate
     *
     * @param workflow    to write
     * @param destination file to write (i.e. workflow.json)
     */
    public static void writeYamlNoValidation(Workflow workflow, String destination) {
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(workflow);

            File file = new File(destination);
            try (FileOutputStream fileOuputStream = new FileOutputStream(file)) {
                fileOuputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Read Yaml file to get workflow and do not validate
     *
     * @param origin file origin to read from
     * @return workflow
     */
    public static Workflow readYAMLNoValidation(String origin) {
        File file = new File(origin);
        YAMLFactory yf = new YAMLFactory();
        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(yf);
        try {
            return objectMapper.readValue(file, Workflow.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

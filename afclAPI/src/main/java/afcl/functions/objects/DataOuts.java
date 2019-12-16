
package afcl.functions.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * This class describes the data output ports of
 * an compound function
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "source",
        "saveto",
        "properties",
        "constraints"
})
public class DataOuts {

    /**
     * Unique identifier for the data output port
     */
    @JsonProperty("name")
    private String name;

    /**
     * Indicates the data type of the data port
     */
    @JsonProperty("type")
    private String type;

    /**
     * Represents where the data comes from
     */
    @JsonProperty("source")
    private String source;

    /**
     * To specify the output of the data output
     * port
     */
    @JsonProperty("saveto")
    private String saveto;

    /**
     * Tells whether value will be passed to other
     * function
     */
    @JsonProperty("passing")
    private Boolean passing;

    /**
     * {@link PropertyConstraint} (information about the
     * behaviour of functions)
     */
    @JsonProperty("properties")
    private List<Object> properties = null;

    /**
     * {@link PropertyConstraint} (which must be fulfilled
     * by underlying workflow runtime environment)
     */
    @JsonProperty("constraints")
    private List<Object> constraints = null;

    public DataOuts() {
    }

    /**
     * Constructor for data output port of compound function
     *
     * @param name   Unique identifier for the data output port
     * @param type   Data type of the data port
     * @param source Source location of the data port
     */
    public DataOuts(String name, String type, String source) {
        this.name = name;
        this.type = type;
        this.source = source;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("saveto")
    public String getSaveto() {
        return saveto;
    }

    @JsonProperty("saveto")
    public void setSaveto(String saveto) {
        this.saveto = saveto;
    }

    @JsonProperty("passing")
    public Boolean getPassing() {
        return passing;
    }

    @JsonProperty("passing")
    public void setPassing(Boolean passing) {
        this.passing = passing;
    }

    @JsonProperty("properties")
    public List<Object> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<Object> properties) {
        this.properties = properties;
    }

    @JsonProperty("constraints")
    public List<Object> getConstraints() {
        return constraints;
    }

    @JsonProperty("constraints")
    public void setConstraints(List<Object> constraints) {
        this.constraints = constraints;
    }
}

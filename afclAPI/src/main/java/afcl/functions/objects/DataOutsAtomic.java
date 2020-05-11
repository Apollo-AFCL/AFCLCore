
package afcl.functions.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * This class describes the data output ports of
 * an atomic function
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
public class DataOutsAtomic {

    /**
     * Unique identifier for the data output port
     */
    @JsonProperty("name")
    private String atomicOutputName;

    /**
     * Indicates the data type of the data port
     */
    @JsonProperty("type")
    private String atomicOutputType;

    /**
     * To specify the output of the data output
     * port
     */
    @JsonProperty("saveto")
    private String atomicOutputSaveTo;

    /**
     * Tells whether value will be passed to other
     * function
     */
    @JsonProperty("passing")
    private Boolean passingDataOutsAtomic;

    /**
     * {@link PropertyConstraint} (information about the
     * behaviour of functions)
     */
    @JsonProperty("properties")
    private List<Object> propertiesDataOutsAtomic = null;

    /**
     * {@link PropertyConstraint} (which must be fulfilled
     * by underlying workflow runtime environment)
     */
    @JsonProperty("constraints")
    private List<Object> constraintsDataOutsAtomic = null;

    public DataOutsAtomic() {
    }

    /**
     * Constructor for data output port of atomic function
     *
     * @param atomicOutputName Unique identifier for the data output port
     * @param atomicOutputType Data type of the data port
     */
    public DataOutsAtomic(String atomicOutputName, String atomicOutputType) {
        this.atomicOutputName = atomicOutputName;
        this.atomicOutputType = atomicOutputType;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return atomicOutputName;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.atomicOutputName = name;
    }

    @JsonProperty("type")
    public String getType() {
        return atomicOutputType;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.atomicOutputType = type;
    }

    @JsonProperty("saveto")
    public String getSaveto() {
        return atomicOutputSaveTo;
    }

    @JsonProperty("saveto")
    public void setSaveto(String saveto) {
        this.atomicOutputSaveTo = saveto;
    }

    @JsonProperty("passing")
    public Boolean getPassing() {
        return passingDataOutsAtomic;
    }

    @JsonProperty("passing")
    public void setPassing(Boolean passing) {
        this.passingDataOutsAtomic = passing;
    }

    @JsonProperty("properties")
    public List<Object> getProperties() {
        return propertiesDataOutsAtomic;
    }

    @JsonProperty("properties")
    public void setProperties(List<Object> properties) {
        this.propertiesDataOutsAtomic = properties;
    }

    @JsonProperty("constraints")
    public List<Object> getConstraints() {
        return constraintsDataOutsAtomic;
    }

    @JsonProperty("constraints")
    public void setConstraints(List<Object> constraints) {
        this.constraintsDataOutsAtomic = constraints;
    }

}


package at.uibk.dps.afcl.functions.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

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
    private String outputName;

    /**
     * Indicates the data type of the data port
     */
    @JsonProperty("type")
    private String outputType;

    /**
     * Represents where the data comes from
     */
    @JsonProperty("source")
    private String outputSource;

    /**
     * To specify the output of the data output
     * port
     */
    @JsonProperty("saveto")
    private String outputSaveTo;

    /**
     * Tells whether value will be passed to other
     * function
     */
    @JsonProperty("passing")
    private Boolean passingDataOuts;

    /**
     * {@link PropertyConstraint} (information about the
     * behaviour of functions)
     */
    @JsonProperty("properties")
    private List<PropertyConstraint> propertiesDataOuts = null;

    /**
     * {@link PropertyConstraint} (which must be fulfilled
     * by underlying workflow runtime environment)
     */
    @JsonProperty("constraints")
    private List<PropertyConstraint> constraintsDataOuts = null;

    public DataOuts() {
    }

    /**
     * Constructor for data output port of compound function
     *
     * @param outputName   Unique identifier for the data output port
     * @param outputType   Data type of the data port
     * @param outputSource Source location of the data port
     */
    public DataOuts(String outputName, String outputType, String outputSource) {
        this.outputName = outputName;
        this.outputType = outputType;
        this.outputSource = outputSource;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return outputName;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.outputName = name;
    }

    @JsonProperty("type")
    public String getType() {
        return outputType;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.outputType = type;
    }

    @JsonProperty("source")
    public String getSource() {
        return outputSource;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.outputSource = source;
    }

    @JsonProperty("saveto")
    public String getSaveto() {
        return outputSaveTo;
    }

    @JsonProperty("saveto")
    public void setSaveto(String saveto) {
        this.outputSaveTo = saveto;
    }

    @JsonProperty("passing")
    public Boolean getPassing() {
        return passingDataOuts;
    }

    @JsonProperty("passing")
    public void setPassing(Boolean passing) {
        this.passingDataOuts = passing;
    }

    @JsonProperty("properties")
    public List<PropertyConstraint> getProperties() {
        return propertiesDataOuts;
    }

    @JsonProperty("properties")
    public void setProperties(List<PropertyConstraint> properties) {
        this.propertiesDataOuts = properties;
    }

    @JsonProperty("constraints")
    public List<PropertyConstraint> getConstraints() {
        return constraintsDataOuts;
    }

    @JsonProperty("constraints")
    public void setConstraints(List<PropertyConstraint> constraints) {
        this.constraintsDataOuts = constraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataOuts dataOuts = (DataOuts) o;
        return Objects.equals(outputName, dataOuts.outputName) &&
                Objects.equals(outputType, dataOuts.outputType) &&
                Objects.equals(outputSource, dataOuts.outputSource) &&
                Objects.equals(outputSaveTo, dataOuts.outputSaveTo) &&
                Objects.equals(passingDataOuts, dataOuts.passingDataOuts) &&
                Objects.equals(propertiesDataOuts, dataOuts.propertiesDataOuts) &&
                Objects.equals(constraintsDataOuts, dataOuts.constraintsDataOuts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputName, outputType, outputSource, outputSaveTo, passingDataOuts, propertiesDataOuts, constraintsDataOuts);
    }
}

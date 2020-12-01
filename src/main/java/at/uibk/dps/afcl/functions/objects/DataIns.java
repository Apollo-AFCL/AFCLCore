
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.Function;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

/**
 * This class describes the data input ports of
 * a {@link Function}
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "source",
        "value",
        "properties",
        "constraints"
})
public class DataIns {

    /**
     * Unique identifier for the data input port
     */
    @JsonProperty("name")
    private String inputName;

    /**
     * Indicates the data type of the data port
     */
    @JsonProperty("type")
    private String inputType;

    /**
     * Represents where the data comes from
     */
    @JsonProperty("source")
    private String inputSource;

    /**
     * Represents a constant value
     */
    @JsonProperty("value")
    private String inputValue;

    /**
     * Tells whether value will be passed to other
     * function
     */
    @JsonProperty("passing")
    private Boolean passingDataIns;

    /**
     * {@link PropertyConstraint} (information about the
     * behaviour of functions)
     */
    @JsonProperty("properties")
    private List<PropertyConstraint> propertiesDataIns;

    /**
     * {@link PropertyConstraint} (which must be fulfilled
     * by underlying workflow runtime environment)
     */
    @JsonProperty("constraints")
    private List<PropertyConstraint> constraintsDataIns;

    public DataIns() {
    }

    /**
     * Constructor for data input ports
     *
     * @param inputName Unique identifier for the data input port
     * @param inputType Data type of the data port
     */
    public DataIns(String inputName, String inputType) {
        this(inputName, inputType, null);
    }

    /**
     * Constructor for data input ports
     *
     * @param inputName   Unique identifier for the data input port
     * @param inputType   Data type of the data port
     * @param inputSource Specifies where the data comes from
     */
    public DataIns(String inputName, String inputType, String inputSource) {
        this.inputName = inputName;
        this.inputType = inputType;
        this.inputSource = inputSource;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return inputName;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.inputName = name;
    }

    @JsonProperty("type")
    public String getType() {
        return inputType;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.inputType = type;
    }

    @JsonProperty("source")
    public String getSource() {
        return inputSource;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.inputSource = source;
    }

    @JsonProperty("value")
    public String getValue() {
        return inputValue;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.inputValue = value;
    }

    @JsonProperty("passing")
    public Boolean getPassing() {
        return passingDataIns;
    }

    @JsonProperty("passing")
    public void setPassing(Boolean passing) {
        this.passingDataIns = passing;
    }

    @JsonProperty("properties")
    public List<PropertyConstraint> getProperties() {
        return propertiesDataIns;
    }

    @JsonProperty("properties")
    public void setProperties(List<PropertyConstraint> properties) {
        this.propertiesDataIns = properties;
    }

    @JsonProperty("constraints")
    public List<PropertyConstraint> getConstraints() {
        return constraintsDataIns;
    }

    @JsonProperty("constraints")
    public void setConstraints(List<PropertyConstraint> constraints) {
        this.constraintsDataIns = constraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataIns dataIns = (DataIns) o;
        return Objects.equals(inputName, dataIns.inputName) &&
                Objects.equals(inputType, dataIns.inputType) &&
                Objects.equals(inputSource, dataIns.inputSource) &&
                Objects.equals(inputValue, dataIns.inputValue) &&
                Objects.equals(passingDataIns, dataIns.passingDataIns) &&
                Objects.equals(propertiesDataIns, dataIns.propertiesDataIns) &&
                Objects.equals(constraintsDataIns, dataIns.constraintsDataIns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputName, inputType, inputSource, inputValue, passingDataIns, propertiesDataIns, constraintsDataIns);
    }
}

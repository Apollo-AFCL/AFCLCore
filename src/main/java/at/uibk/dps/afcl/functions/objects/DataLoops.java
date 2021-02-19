package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.SequentialFor;
import at.uibk.dps.afcl.functions.SequentialWhile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

/**
 * This class describes the data loop ports of
 * a {@link SequentialFor} or
 * {@link SequentialWhile}
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "initSource",
        "value",
        "properties",
        "constraints"
})
public class DataLoops {

    /**
     * Unique identifier for the data input port
     */
    @JsonProperty("name")
    private String dataLoopName;

    /**
     * Indicates the data type of the data port
     */
    @JsonProperty("type")
    private String dataLoopType;

    /**
     * Represents the initial value of the variable
     */
    @JsonProperty("initSource")
    private String initSource;

    /**
     * Represents a cyclic data flow inside
     * a loop body
     */
    @JsonProperty("loopSource")
    private String loopSource;

    /**
     * Represents a constant value
     */
    @JsonProperty("value")
    private String value;

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
    private List<PropertyConstraint> properties;

    /**
     * {@link PropertyConstraint} (which must be fulfilled
     * by underlying workflow runtime environment)
     */
    @JsonProperty("constraints")
    private List<PropertyConstraint> constraints;

    /**
     * Default constructor.
     */
    public DataLoops() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor for data input ports
     *
     * @param dataLoopName Unique identifier for the data input port
     * @param dataLoopType Data type of the data port
     */
    public DataLoops(final String dataLoopName, final String dataLoopType) {
        this(dataLoopName, dataLoopType, null, null);
    }

    /**
     * Constructor for data input ports
     *
     * @param dataLoopName   Unique identifier for the data input port
     * @param dataLoopType   Data type of the data port
     * @param initSource Specifies where the data comes from
     */
    public DataLoops(final String dataLoopName, final String dataLoopType, final String initSource, final String loopSource) {
        this.dataLoopName = dataLoopName;
        this.dataLoopType = dataLoopType;
        this.initSource = initSource;
        this.loopSource = loopSource;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return dataLoopName;
    }

    @JsonProperty("name")
    public void setName(final String name) {
        this.dataLoopName = name;
    }

    @JsonProperty("type")
    public String getType() {
        return dataLoopType;
    }

    @JsonProperty("type")
    public void setType(final String type) {
        this.dataLoopType = type;
    }

    @JsonProperty("loopSource")
    public String getLoopSource() { return loopSource; }

    @JsonProperty("loopSource")
    public void setLoopSource(final String loopSource) { this.loopSource = loopSource; }

    @JsonProperty("initSource")
    public String getInitSource() {
        return initSource;
    }

    @JsonProperty("initSource")
    public void setInitSource(final String initSource) {
        this.initSource = initSource;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(final String value) {
        this.value = value;
    }

    @JsonProperty("passing")
    public Boolean getPassing() {
        return passing;
    }

    @JsonProperty("passing")
    public void setPassing(final Boolean passing) {
        this.passing = passing;
    }

    @JsonProperty("properties")
    public List<PropertyConstraint> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<PropertyConstraint> properties) {
        this.properties = properties;
    }

    @JsonProperty("constraints")
    public List<PropertyConstraint> getConstraints() {
        return constraints;
    }

    @JsonProperty("constraints")
    public void setConstraints(final List<PropertyConstraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final DataLoops dataLoops = (DataLoops) object;
        return Objects.equals(dataLoopName, dataLoops.dataLoopName) &&
                Objects.equals(dataLoopType, dataLoops.dataLoopType) &&
                Objects.equals(initSource, dataLoops.initSource) &&
                Objects.equals(loopSource, dataLoops.loopSource) &&
                Objects.equals(value, dataLoops.value) &&
                Objects.equals(passing, dataLoops.passing) &&
                Objects.equals(properties, dataLoops.properties) &&
                Objects.equals(constraints, dataLoops.constraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataLoopName, dataLoopType, initSource, loopSource, value, passing, properties, constraints);
    }
}

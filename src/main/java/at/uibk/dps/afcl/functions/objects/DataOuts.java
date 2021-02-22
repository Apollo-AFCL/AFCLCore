
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
    public DataOuts() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor for data output port of compound function
     *
     * @param name   Unique identifier for the data output port
     * @param type   Data type of the data port
     * @param source Source location of the data port
     */
    public DataOuts(final String name, final String type, final String source) {
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
    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(final String type) {
        this.type = type;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(final String source) {
        this.source = source;
    }

    @JsonProperty("saveto")
    public String getSaveto() {
        return saveto;
    }

    @JsonProperty("saveto")
    public void setSaveto(final String saveto) {
        this.saveto = saveto;
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
    public void setProperties(final List<PropertyConstraint> properties) {
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
        final DataOuts dataOuts = (DataOuts) object;
        return Objects.equals(name, dataOuts.name) &&
                Objects.equals(type, dataOuts.type) &&
                Objects.equals(source, dataOuts.source) &&
                Objects.equals(saveto, dataOuts.saveto) &&
                Objects.equals(passing, dataOuts.passing) &&
                Objects.equals(properties, dataOuts.properties) &&
                Objects.equals(constraints, dataOuts.constraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, source, saveto, passing, properties, constraints);
    }
}

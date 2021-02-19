
package at.uibk.dps.afcl;

import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.PropertyConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

/**
 * This class describes sub function choreographies
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "subFCBody",
        "dataOuts",
        "properties",
        "constraints"
})
public class SubFC {

    /**
     * Unique identifier for the sub function choreography
     */
    @JsonProperty("name")
    private String name;

    /**
     * Data input ports ({@link DataIns})
     */
    @JsonProperty("dataIns")
    private List<DataIns> dataIns;

    /**
     * Body of the sub function choreography containing {@link Function}s
     */
    @JsonProperty("subFCBody")
    private List<Function> subFCBody;

    /**
     * Data output ports ({@link DataOuts})
     */
    @JsonProperty("dataOuts")
    private List<DataOuts> dataOuts;

    /**
     * {@link PropertyConstraint} (information about the behaviour of functions)
     */
    @JsonProperty("properties")
    private List<PropertyConstraint> properties;

    /**
     * {@link PropertyConstraint} (which must be fulfilled by underlying workflow
     * runtime environment)
     */
    @JsonProperty("constraints")
    private List<PropertyConstraint> constraints;

    /**
     * Constructor for sub function choreographies
     *
     * @param name      Unique identifier for the sub function
     *                  choreography
     * @param dataIns   Data input ports ({@link DataIns})
     * @param subFCBody Body of the sub function choreography
     *                  containing {@link Function}s
     * @param dataOuts  Data output ports ({@link DataOuts})
     */
    public SubFC(final String name, final List<DataIns> dataIns, final List<Function> subFCBody, final List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.subFCBody = subFCBody;
        this.dataOuts = dataOuts;
    }

    /**
     * Empty constructor for sub function choreographies
     *
     */
    public SubFC() {
        // This constructor is intentionally empty. Nothing special is needed here.
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

    @JsonProperty("dataIns")
    public List<DataIns> getDataIns() {
        return dataIns;
    }

    @JsonProperty("dataIns")
    public void setDataIns(final List<DataIns> dataIns) {
        this.dataIns = dataIns;
    }

    @JsonProperty("subFCBody")
    public List<Function> getSubFCBody() {
        return subFCBody;
    }

    @JsonProperty("subFCBody")
    public void setSubFCBody(final List<Function> subFCBody) {
        this.subFCBody = subFCBody;
    }

    @JsonProperty("dataOuts")
    public List<DataOuts> getDataOuts() {
        return dataOuts;
    }

    @JsonProperty("dataOuts")
    public void setDataOuts(final List<DataOuts> dataOuts) {
        this.dataOuts = dataOuts;
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
        final SubFC subFC = (SubFC) object;
        return Objects.equals(name, subFC.name) &&
                Objects.equals(dataIns, subFC.dataIns) &&
                Objects.equals(subFCBody, subFC.subFCBody) &&
                Objects.equals(dataOuts, subFC.dataOuts) &&
                Objects.equals(properties, subFC.properties) &&
                Objects.equals(constraints, subFC.constraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataIns, subFCBody, dataOuts, properties, constraints);
    }
}

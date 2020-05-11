
package afcl;

import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import afcl.functions.objects.PropertyConstraint;
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
    private DataIns dataIns;

    /**
     * Body of the sub function choreography containing {@link Function}s
     */
    @JsonProperty("subFCBody")
    private List<Function> subFCBody = null;

    /**
     * Data output ports ({@link DataOuts})
     */
    @JsonProperty("dataOuts")
    private DataOuts dataOuts;

    /**
     * {@link PropertyConstraint} (information about the behaviour of functions)
     */
    @JsonProperty("properties")
    private List<Object> propertiesSubFC = null;

    /**
     * {@link PropertyConstraint} (which must be fulfilled by underlying workflow
     * runtime environment)
     */
    @JsonProperty("constraints")
    private List<Object> constraintsSubFC = null;

    /**
     * Constructor for sub function choreographies
     *
     * @param name      Unique identifier for the sub function choreography
     * @param dataIns   Data input ports ({@link DataIns})
     * @param subFCBody Body of the sub function choreography containing {@link Function}s
     * @param dataOuts  Data output ports ({@link DataOuts})
     */
    public SubFC(String name, DataIns dataIns, List<Function> subFCBody, DataOuts dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.subFCBody = subFCBody;
        this.dataOuts = dataOuts;
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

    @JsonProperty("dataIns")
    public DataIns getDataIns() {
        return dataIns;
    }

    @JsonProperty("dataIns")
    public void setDataIns(DataIns dataIns) {
        this.dataIns = dataIns;
    }

    @JsonProperty("subFCBody")
    public List<Function> getSubFCBody() {
        return subFCBody;
    }

    @JsonProperty("subFCBody")
    public void setSubFCBody(List<Function> subFCBody) {
        this.subFCBody = subFCBody;
    }

    @JsonProperty("dataOuts")
    public DataOuts getDataOuts() {
        return dataOuts;
    }

    @JsonProperty("dataOuts")
    public void setDataOuts(DataOuts dataOuts) {
        this.dataOuts = dataOuts;
    }

    @JsonProperty("properties")
    public List<Object> getProperties() {
        return propertiesSubFC;
    }

    @JsonProperty("properties")
    public void setProperties(List<Object> propertiesSubFC) {
        this.propertiesSubFC = propertiesSubFC;
    }

    @JsonProperty("constraints")
    public List<Object> getConstraints() {
        return constraintsSubFC;
    }

    @JsonProperty("constraints")
    public void setConstraints(List<Object> constraintsSubFC) {
        this.constraintsSubFC = constraintsSubFC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubFC subFC = (SubFC) o;
        return Objects.equals(name, subFC.name) &&
                Objects.equals(dataIns, subFC.dataIns) &&
                Objects.equals(subFCBody, subFC.subFCBody) &&
                Objects.equals(dataOuts, subFC.dataOuts) &&
                Objects.equals(propertiesSubFC, subFC.propertiesSubFC) &&
                Objects.equals(constraintsSubFC, subFC.constraintsSubFC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataIns, subFCBody, dataOuts, propertiesSubFC, constraintsSubFC);
    }
}

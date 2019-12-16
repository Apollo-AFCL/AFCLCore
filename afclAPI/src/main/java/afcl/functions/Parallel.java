
package afcl.functions;

import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import afcl.functions.objects.PropertyConstraint;
import afcl.functions.objects.Section;
import afcl.functions.objects.dataflow.DataInsDataFlow;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class describes the parallel compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "parallelBody",
        "dataOuts"
})
@JsonTypeName("parallel")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Parallel extends CompoundParallel {


    /**
     * List of sections which can run in parallel
     */
    @JsonProperty("parallelBody")
    private List<Section> parallelBody = null;

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

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Parallel() {
    }

    /**
     * Constructor for function compound parallel
     *
     * @param name         Unique identifier of the compound
     * @param dataIns      Data input ports ({@link DataIns})
     * @param parallelBody sections which can run in parallel
     * @param dataOuts     Data output ports ({@link DataOuts})
     */
    public Parallel(String name, List<DataInsDataFlow> dataIns, List<Section> parallelBody, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.parallelBody = parallelBody;
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("parallelBody")
    public List<Section> getParallelBody() {
        return parallelBody;
    }

    @JsonProperty("parallelBody")
    public void setParallelBody(List<Section> parallelBody) {
        this.parallelBody = parallelBody;
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
    public void setConstraints(List<PropertyConstraint> constraints) {
        this.constraints = constraints;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

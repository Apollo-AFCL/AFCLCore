
package afcl.functions;

import afcl.Function;
import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import afcl.functions.objects.LoopCounter;
import afcl.functions.objects.PropertyConstraint;
import afcl.functions.objects.dataflow.DataInsDataFlow;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class describes the parallelFor compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "loopCounter",
        "loopBody",
        "dataOuts"
})
@JsonTypeName("parallelFor")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ParallelFor extends CompoundParallel {

    /**
     * Contains needed information about the number of (parallel) loop iterations
     */
    @JsonProperty("loopCounter")
    private LoopCounter loopCounter;

    /**
     * Contains {@link Function}s which should be executed in each iteration
     */
    @JsonProperty("loopBody")
    private List<Function> loopBody = null;

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

    public ParallelFor() {
    }

    /**
     * Constructor for function compound parallelFor
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param loopCounter information about the number of (parallel) loop iterations
     * @param loopBody    functions which should be executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public ParallelFor(String name, List<DataInsDataFlow> dataIns, LoopCounter loopCounter, List<Function> loopBody, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.loopCounter = loopCounter;
        this.loopBody = loopBody;
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("loopCounter")
    public LoopCounter getLoopCounter() {
        return loopCounter;
    }

    @JsonProperty("loopCounter")
    public void setLoopCounter(LoopCounter loopCounter) {
        this.loopCounter = loopCounter;
    }

    @JsonProperty("loopBody")
    public List<Function> getLoopBody() {
        return loopBody;
    }

    @JsonProperty("loopBody")
    public void setLoopBody(List<Function> loopBody) {
        this.loopBody = loopBody;
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

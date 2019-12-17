package afcl.functions;

import afcl.Function;
import afcl.functions.objects.*;
import afcl.functions.objects.dataflow.DataInsDataFlow;
import com.fasterxml.jackson.annotation.*;
import afcl.functions.objects.Condition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class describes the sequential While compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "dataLoops",
        "condition",
        "loopBody",
        "dataOuts"
})
@JsonTypeName("while")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class SequentialWhile extends CompoundAdvancedDataFlow {

    /**
     * Contains needed information about potential loop data flow
     */
    @JsonProperty("dataLoops")
    private List<DataLoops> dataLoops;

    /**
     * Contains needed information about the number of (sequential) loop iterations
     */
    @JsonProperty("condition")
    private Condition condition;

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

    public SequentialWhile() {
    }

    /**
     * Constructor for function compound parallelFor
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param condition   while loop condition
     * @param loopBody    functions which should be executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public SequentialWhile(String name, List<DataInsDataFlow> dataIns, List<DataLoops> dataLoops, Condition condition, List<Function> loopBody, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.dataLoops = dataLoops;
        this.condition = condition;
        this.loopBody = loopBody;
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("dataLoops")
    public List<DataLoops> getDataLoops() { return dataLoops; }

    @JsonProperty("dataLoops")
    public void setDataLoops(List<DataLoops> dataLoops) { this.dataLoops = dataLoops; }

    @JsonProperty("condition")
    public Condition getCondition() {
        return condition;
    }

    @JsonProperty("condition")
    public void setCondition(Condition loopCounter) {
        this.condition = loopCounter;
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

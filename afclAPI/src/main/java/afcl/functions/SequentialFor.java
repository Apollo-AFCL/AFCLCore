package afcl.functions;

import afcl.Function;
import afcl.functions.objects.*;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class describes the sequential for compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "dataLoops",
        "loopCounter",
        "loopBody",
        "dataOuts"
})
@JsonTypeName("for")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class SequentialFor extends Compound {

    /**
     * Contains needed information about potential loop data flow
     */
    @JsonProperty("dataLoops")
    private List<DataLoops> dataLoops;

    /**
     * Contains needed information about the number of (sequential) loop iterations
     */
    @JsonProperty("loopCounter")
    private LoopCounter loopCounter;

    /**
     * Contains {@link Function}s which should be executed in each iteration
     */
    @JsonProperty("loopBody")
    private List<Function> loopBody = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public SequentialFor() {
    }

    /**
     * Constructor for function compound for
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param loopCounter information about the number of (sequential) loop iterations
     * @param loopBody    functions which should be executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public SequentialFor(String name, List<DataIns> dataIns, List<DataLoops> dataLoops, LoopCounter loopCounter, List<Function> loopBody, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.dataLoops = dataLoops;
        this.loopCounter = loopCounter;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

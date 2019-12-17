
package afcl.functions;

import afcl.Function;
import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import afcl.functions.objects.LoopCounter;
import afcl.functions.objects.PropertyConstraint;
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
public class ParallelFor extends Compound {

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
    public ParallelFor(String name, List<DataIns> dataIns, LoopCounter loopCounter, List<Function> loopBody, List<DataOuts> dataOuts) {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

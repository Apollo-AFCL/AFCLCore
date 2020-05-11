
package afcl.functions;

import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import afcl.functions.objects.Section;
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
public class Parallel extends Compound {


    /**
     * List of sections which can run in parallel
     */
    @JsonProperty("parallelBody")
    private List<Section> parallelBody = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

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
    public Parallel(String name, List<DataIns> dataIns, List<Section> parallelBody, List<DataOuts> dataOuts) {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.Section;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private Map<String, Object> additionalPropertiesParallel = new HashMap<>();

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
        return this.additionalPropertiesParallel;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesParallel.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Parallel parallel = (Parallel) o;
        return Objects.equals(parallelBody, parallel.parallelBody) &&
                Objects.equals(additionalPropertiesParallel, parallel.additionalPropertiesParallel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parallelBody, additionalPropertiesParallel);
    }
}


package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.Section;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    private List<Section> parallelBody;

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Default constructor.
     */
    public Parallel() {
        super();
    }

    /**
     * Constructor for function compound parallel
     *
     * @param name         Unique identifier of the compound
     * @param dataIns      Data input ports ({@link DataIns})
     * @param parallelBody sections which can run in parallel
     * @param dataOuts     Data output ports ({@link DataOuts})
     */
    public Parallel(final String name, final List<DataIns> dataIns, final List<Section> parallelBody, final List<DataOuts> dataOuts) {
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
    public void setParallelBody(final List<Section> parallelBody) {
        this.parallelBody = parallelBody;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(final String name, final Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        final Parallel parallel = (Parallel) object;
        return Objects.equals(parallelBody, parallel.parallelBody) &&
                Objects.equals(additionalProperties, parallel.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parallelBody, additionalProperties);
    }
}

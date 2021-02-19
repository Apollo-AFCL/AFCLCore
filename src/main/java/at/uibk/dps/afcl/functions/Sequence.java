
package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

/**
 * This class describes the sequence compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "sequenceBody",
        "dataOuts"
})
@JsonTypeName("sequence")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Sequence extends Compound {

    /**
     * List of {@link Function}s to be executed sequentially
     */
    @JsonProperty("sequenceBody")
    private List<Function> sequenceBody;

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Empty constructor for sequence construct.
     */
    public Sequence() {
        super();
    }

    /**
     * Constructor for function compound sequence
     *
     * @param name         Unique identifier of the compound
     * @param dataIns      Data input ports ({@link DataIns})
     * @param sequenceBody List of {@link Function}s to be executed sequentially
     * @param dataOuts     Data output ports ({@link DataOuts})
     */
    public Sequence(final String name, final List<DataIns> dataIns, final List<Function> sequenceBody, final List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.sequenceBody = sequenceBody;
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("sequenceBody")
    public List<Function> getSequenceBody() {
        return sequenceBody;
    }

    @JsonProperty("sequenceBody")
    public void setSequenceBody(final List<Function> sequenceBody) {
        this.sequenceBody = sequenceBody;
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
        final Sequence sequence = (Sequence) object;
        return Objects.equals(sequenceBody, sequence.sequenceBody) &&
                Objects.equals(additionalProperties, sequence.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sequenceBody, additionalProperties);
    }
}

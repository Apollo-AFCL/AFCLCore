package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.DataOutsAtomic;
import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

/**
 * This class describes an atomic function
 *
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "dataIns",
        "dataOuts"
})
@JsonTypeName("function")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AtomicFunction extends Function {

    /**
     * Function type of the Atomic Function. Represents an abstract description of
     * a group of function implementations.
     */
    @JsonProperty("type")
    private String type;

    /**
     * Data input ports of the atomic function ({@link DataIns})
     */
    @JsonProperty("dataIns")
    private List<DataIns> dataIns;

    /**
     * Data output ports of the atomic function ({@link DataOuts})
     */
    @JsonProperty("dataOuts")
    private List<DataOutsAtomic> dataOuts;

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Default constructor.
     */
    public AtomicFunction() {
        super();
    }

    /**
     * Constructor for atomic function
     *
     * @param name     Unique identifier of the compound
     * @param type     Function type of the Atomic Function
     * @param dataIns  Data input ports ({@link DataIns})
     * @param dataOuts Data output ports ({@link DataOuts})
     */
    public AtomicFunction(final String name, final String type, final List<DataIns> dataIns, final List<DataOutsAtomic> dataOuts) {
        this.name = name;
        this.type = type;
        this.dataIns = dataIns;
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(final String type) {
        this.type = type;
    }

    @JsonProperty("dataIns")
    public List<DataIns> getDataIns() {
        return dataIns;
    }

    @JsonProperty("dataIns")
    public void setDataIns(final List<DataIns> dataIns) {
        this.dataIns = dataIns;
    }

    @JsonProperty("dataOuts")
    public List<DataOutsAtomic> getDataOuts() {
        return dataOuts;
    }

    @JsonProperty("dataOuts")
    public void setDataOuts(final List<DataOutsAtomic> dataOuts) {
        this.dataOuts = dataOuts;
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
        final AtomicFunction that = (AtomicFunction) object;
        return super.equals(object) &&
                Objects.equals(type, that.type) &&
                Objects.equals(dataIns, that.dataIns) &&
                Objects.equals(dataOuts, that.dataOuts) &&
                Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, dataIns, dataOuts, additionalProperties);
    }
}

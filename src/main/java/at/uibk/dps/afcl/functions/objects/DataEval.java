
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Switch;
import com.fasterxml.jackson.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

/**
 * This class describes the data to be compared among the
 * different ({@link Switch}) cases
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type"
})
public class DataEval {

    /**
     * Unique identifier for the data eval
     */
    @JsonProperty("name")
    private String name;

    /**
     * Indicates the data type
     */
    @JsonProperty("type")
    private String type;

    /**
     * Represents where the data comes from
     */
    @JsonProperty("source")
    private String source;

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Empty constructor for a data evaluation object.
     */
    public DataEval() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor for data eval
     *
     * @param name Unique identifier for the data eval
     * @param type Data type
     */
    public DataEval(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(final String type) {
        this.type = type;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(final String source) {
        this.source = source;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(final String name, final Object value) {
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
        final DataEval dataEval = (DataEval) object;
        return Objects.equals(name, dataEval.name) &&
                Objects.equals(type, dataEval.type) &&
                Objects.equals(source, dataEval.source) &&
                Objects.equals(additionalProperties, dataEval.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, source, additionalProperties);
    }
}

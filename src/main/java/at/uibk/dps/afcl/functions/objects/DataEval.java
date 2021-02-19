
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Switch;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
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
    private String evaluatorName;

    /**
     * Indicates the data type
     */
    @JsonProperty("type")
    private String evaluatorType;

    /**
     * Represents where the data comes from
     */
    @JsonProperty("source")
    private String evaluatorSource;

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
     * @param evaluatorName Unique identifier for the data eval
     * @param evaluatorType Data type
     */
    public DataEval(final String evaluatorName, final String evaluatorType) {
        this.evaluatorName = evaluatorName;
        this.evaluatorType = evaluatorType;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return evaluatorName;
    }

    @JsonProperty("name")
    public void setName(final String name) {
        this.evaluatorName = name;
    }

    @JsonProperty("type")
    public String getType() {
        return evaluatorType;
    }

    @JsonProperty("type")
    public void setType(final String type) {
        this.evaluatorType = type;
    }

    @JsonProperty("source")
    public String getSource() {
        return evaluatorSource;
    }

    @JsonProperty("source")
    public void setSource(final String source) {
        this.evaluatorSource = source;
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
        final DataEval dataEval = (DataEval) object;
        return Objects.equals(evaluatorName, dataEval.evaluatorName) &&
                Objects.equals(evaluatorType, dataEval.evaluatorType) &&
                Objects.equals(evaluatorSource, dataEval.evaluatorSource) &&
                Objects.equals(additionalProperties, dataEval.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evaluatorName, evaluatorType, evaluatorSource, additionalProperties);
    }
}

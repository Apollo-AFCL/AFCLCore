
package afcl.functions.objects;

import afcl.functions.Switch;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    @JsonIgnore
    private Map<String, Object> additionalPropertiesEvaluator = new HashMap<>();

    public DataEval() {
    }

    /**
     * Constructor for data eval
     *
     * @param evaluatorName Unique identifier for the data eval
     * @param evaluatorType Data type
     */
    public DataEval(String evaluatorName, String evaluatorType) {
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
    public void setName(String name) {
        this.evaluatorName = name;
    }

    @JsonProperty("type")
    public String getType() {
        return evaluatorType;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.evaluatorType = type;
    }

    @JsonProperty("source")
    public String getSource() {
        return evaluatorSource;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.evaluatorSource = source;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalPropertiesEvaluator;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesEvaluator.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataEval dataEval = (DataEval) o;
        return Objects.equals(evaluatorName, dataEval.evaluatorName) &&
                Objects.equals(evaluatorType, dataEval.evaluatorType) &&
                Objects.equals(evaluatorSource, dataEval.evaluatorSource) &&
                Objects.equals(additionalPropertiesEvaluator, dataEval.additionalPropertiesEvaluator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evaluatorName, evaluatorType, evaluatorSource, additionalPropertiesEvaluator);
    }
}

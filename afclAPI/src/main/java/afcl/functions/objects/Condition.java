
package afcl.functions.objects;

import afcl.functions.IfThenElse;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the condition which should be fulfilled to execute
 * the {@link IfThenElse} then functions. If the condition is not
 * fulfilled {@link IfThenElse} else will be executed.
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "combinedWith",
        "conditions"
})
public class Condition {

    /**
     * Represents how the different conditions should be
     * combined (AND or OR)
     */
    @JsonProperty("combinedWith")
    private String combinedWith;

    /**
     * List of conditions
     */
    @JsonProperty("conditions")
    private List<ACondition> conditions = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Condition() {
    }

    /**
     * Constructor for Condition
     *
     * @param combinedWith Combination of the different conditions (AND or OR)
     * @param conditions   List of conditions
     */
    public Condition(String combinedWith, List<ACondition> conditions) {
        this.combinedWith = combinedWith;
        this.conditions = conditions;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("combinedWith")
    public String getCombinedWith() {
        return combinedWith;
    }

    @JsonProperty("combinedWith")
    public void setCombinedWith(String combinedWith) {
        this.combinedWith = combinedWith;
    }

    @JsonProperty("conditions")
    public List<ACondition> getConditions() {
        return conditions;
    }

    @JsonProperty("conditions")
    public void setConditions(List<ACondition> conditions) {
        this.conditions = conditions;
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

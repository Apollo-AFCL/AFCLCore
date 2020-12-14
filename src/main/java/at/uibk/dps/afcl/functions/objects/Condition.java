
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.IfThenElse;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private List<ACondition> conditions;

    @JsonIgnore
    private Map<String, Object> additionalPropertiesCondition = new HashMap<>();

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
        return this.additionalPropertiesCondition;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesCondition.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Condition condition = (Condition) o;
        return Objects.equals(combinedWith, condition.combinedWith) &&
                Objects.equals(conditions, condition.conditions) &&
                Objects.equals(additionalPropertiesCondition, condition.additionalPropertiesCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combinedWith, conditions, additionalPropertiesCondition);
    }
}


package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.IfThenElse;
import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Empty constructor for a condition.
     */
    public Condition() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor for Condition
     *
     * @param combinedWith Combination of the different conditions (AND or OR)
     * @param conditions   List of conditions
     */
    public Condition(final String combinedWith, final List<ACondition> conditions) {
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
    public void setCombinedWith(final String combinedWith) {
        this.combinedWith = combinedWith;
    }

    @JsonProperty("conditions")
    public List<ACondition> getConditions() {
        return conditions;
    }

    @JsonProperty("conditions")
    public void setConditions(final List<ACondition> conditions) {
        this.conditions = conditions;
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
        final Condition condition = (Condition) object;
        return Objects.equals(combinedWith, condition.combinedWith) &&
                Objects.equals(conditions, condition.conditions) &&
                Objects.equals(additionalProperties, condition.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combinedWith, conditions, additionalProperties);
    }
}


package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.Condition;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

/**
 * This class describes the if-then-else compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "condition",
        "then",
        "else",
        "dataOuts"
})
@JsonTypeName("if")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class IfThenElse extends Compound {

    /**
     * Represents the condition which should be fulfilled to execute
     * the {@link IfThenElse#thenBranch} functions. If the condition is not
     * fulfilled {@link IfThenElse#elseBranch} will be executed.
     */
    @JsonProperty("condition")
    private Condition condition;

    /**
     * List of functions that will be executed if {@link IfThenElse#condition}
     * is fulfilled.
     */
    @JsonProperty("then")
    private List<Function> thenBranch;

    /**
     * List of functions that will be executed if {@link IfThenElse#condition}
     * is not fulfilled.
     */
    @JsonProperty("else")
    private List<Function> elseBranch;

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Default constructor.
     */
    public IfThenElse() {
        super();
    }

    /**
     * Constructor for if-then-else compound
     *
     * @param name      Unique identifier of the compound
     * @param dataIns   Data input ports ({@link DataIns})
     * @param condition Represents the condition which should be fulfilled to execute
     *                  the {@link IfThenElse#thenBranch} functions. If the condition is not
     *                  fulfilled {@link IfThenElse#elseBranch} will be executed.
     * @param thenBranch List of functions that will be executed if {@link IfThenElse#condition}
     *                  is fulfilled.
     * @param elseBranch     List of functions that will be executed if {@link IfThenElse#condition}
     *                  is not fulfilled.
     * @param dataOuts  Data output ports ({@link DataOuts})
     */
    public IfThenElse(final String name, final List<DataIns> dataIns, final Condition condition, final List<Function> thenBranch, final List<Function> elseBranch, final List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("condition")
    public Condition getCondition() {
        return condition;
    }

    @JsonProperty("condition")
    public void setCondition(final Condition condition) {
        this.condition = condition;
    }

    @JsonProperty("then")
    public List<Function> getThen() {
        return thenBranch;
    }

    @JsonProperty("then")
    public void setThen(final List<Function> thenBranch) {
        this.thenBranch = thenBranch;
    }

    @JsonProperty("else")
    public List<Function> getElse() {
        return elseBranch;
    }

    @JsonProperty("else")
    public void setElse(final List<Function> elseBranch) {
        this.elseBranch = elseBranch;
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
        final IfThenElse that = (IfThenElse) object;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(thenBranch, that.thenBranch) &&
                Objects.equals(elseBranch, that.elseBranch) &&
                Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), condition, thenBranch, elseBranch, additionalProperties);
    }
}

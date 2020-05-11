
package afcl.functions;

import afcl.Function;
import afcl.functions.objects.Condition;
import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Function> thenBranch = null;

    /**
     * List of functions that will be executed if {@link IfThenElse#condition}
     * is not fulfilled.
     */
    @JsonProperty("else")
    private List<Function> elseBranch = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public IfThenElse() {
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
    public IfThenElse(String name, List<DataIns> dataIns, Condition condition, List<Function> thenBranch, List<Function> elseBranch, List<DataOuts> dataOuts) {
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
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @JsonProperty("then")
    public List<Function> getThen() {
        return thenBranch;
    }

    @JsonProperty("then")
    public void setThen(List<Function> thenBranch) {
        this.thenBranch = thenBranch;
    }

    @JsonProperty("else")
    public List<Function> getElse() {
        return elseBranch;
    }

    @JsonProperty("else")
    public void setElse(List<Function> elseBranch) {
        this.elseBranch = elseBranch;
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

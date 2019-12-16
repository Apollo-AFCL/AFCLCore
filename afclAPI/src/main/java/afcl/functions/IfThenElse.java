
package afcl.functions;

import afcl.Function;
import afcl.functions.objects.Condition;
import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import afcl.functions.objects.PropertyConstraint;
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
public class IfThenElse extends CompoundSequential {

    /**
     * Represents the condition which should be fulfilled to execute
     * the {@link IfThenElse#then} functions. If the condition is not
     * fulfilled {@link IfThenElse#_else} will be executed.
     */
    @JsonProperty("condition")
    private Condition condition;

    /**
     * List of functions that will be executed if {@link IfThenElse#condition}
     * is fulfilled.
     */
    @JsonProperty("then")
    private List<Function> then = null;

    /**
     * List of functions that will be executed if {@link IfThenElse#condition}
     * is not fulfilled.
     */
    @JsonProperty("else")
    private List<Function> _else = null;

    /**
     * {@link PropertyConstraint} (information about the
     * behaviour of functions)
     */
    @JsonProperty("properties")
    private List<PropertyConstraint> properties;

    /**
     * {@link PropertyConstraint} (which must be fulfilled
     * by underlying workflow runtime environment)
     */
    @JsonProperty("constraints")
    private List<PropertyConstraint> constraints;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public IfThenElse() {
    }

    /**
     * Constructor for if-then-else compound
     *
     * @param name      Unique identifier of the compound
     * @param dataIns   Data input ports ({@link DataIns})
     * @param condition Represents the condition which should be fulfilled to execute
     *                  the {@link IfThenElse#then} functions. If the condition is not
     *                  fulfilled {@link IfThenElse#_else} will be executed.
     * @param then      List of functions that will be executed if {@link IfThenElse#condition}
     *                  is fulfilled.
     * @param _else     List of functions that will be executed if {@link IfThenElse#condition}
     *                  is not fulfilled.
     * @param dataOuts  Data output ports ({@link DataOuts})
     */
    public IfThenElse(String name, List<DataIns> dataIns, Condition condition, List<Function> then, List<Function> _else, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.condition = condition;
        this.then = then;
        this._else = _else;
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
        return then;
    }

    @JsonProperty("then")
    public void setThen(List<Function> then) {
        this.then = then;
    }

    @JsonProperty("else")
    public List<Function> getElse() {
        return _else;
    }

    @JsonProperty("else")
    public void setElse(List<Function> _else) {
        this._else = _else;
    }

    @JsonProperty("properties")
    public List<PropertyConstraint> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<PropertyConstraint> properties) {
        this.properties = properties;
    }

    @JsonProperty("constraints")
    public List<PropertyConstraint> getConstraints() {
        return constraints;
    }

    @JsonProperty("constraints")
    public void setConstraints(List<PropertyConstraint> constraints) {
        this.constraints = constraints;
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

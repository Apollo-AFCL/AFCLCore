
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.Switch;
import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

/**
 * This class represents a case of the {@link Switch} compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value",
        "break",
        "functions"
})
public class Case {

    /**
     * If this value is equal to the one in {@link DataEval} the case will be executed.
     */
    @JsonProperty("value")
    private String value;

    /**
     * Terminated the enclosing switch.
     */
    @JsonProperty("break")
    private String breakCase;

    /**
     * List of functions to be executed if the cases matches {@link DataEval}
     */
    @JsonProperty("functions")
    private List<Function> functions;

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Default constructor.
     */
    public Case() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor for case
     *
     * @param value     If this value is equal to the one in
     *                  {@link DataEval} the case will be executed.
     * @param functions List of functions to be executed
     */
    public Case(final String value, final List<Function> functions) {
        this.value = value;
        this.functions = functions;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(final String value) {
        this.value = value;
    }

    @JsonProperty("break")
    public String getBreakCase() {
        return breakCase;
    }

    @JsonProperty("break")
    public void setBreakCase(final String breakCase) {
        this.breakCase = breakCase;
    }

    @JsonProperty("functions")
    public List<Function> getFunctions() {
        return functions;
    }

    @JsonProperty("functions")
    public void setFunctions(final List<Function> functions) {
        this.functions = functions;
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
        final Case aCase = (Case) object;
        return Objects.equals(value, aCase.value) &&
                Objects.equals(breakCase, aCase.breakCase) &&
                Objects.equals(functions, aCase.functions) &&
                Objects.equals(additionalProperties, aCase.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, breakCase, functions, additionalProperties);
    }
}

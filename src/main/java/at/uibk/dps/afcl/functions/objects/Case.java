
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.Switch;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<Function> functions = null;

    @JsonIgnore
    private Map<String, Object> additionalPropertiesCase = new HashMap<>();

    public Case() {
    }

    /**
     * Constructor for case
     *
     * @param value     If this value is equal to the one in {@link DataEval} the case will be executed.
     * @param functions List of functions to be executed
     */
    public Case(String value, List<Function> functions) {
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
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("break")
    public String getBreak() {
        return breakCase;
    }

    @JsonProperty("break")
    public void setBreak(String breakCase) {
        this.breakCase = breakCase;
    }

    @JsonProperty("functions")
    public List<Function> getFunctions() {
        return functions;
    }

    @JsonProperty("functions")
    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalPropertiesCase;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesCase.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(value, aCase.value) &&
                Objects.equals(breakCase, aCase.breakCase) &&
                Objects.equals(functions, aCase.functions) &&
                Objects.equals(additionalPropertiesCase, aCase.additionalPropertiesCase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, breakCase, functions, additionalPropertiesCase);
    }
}

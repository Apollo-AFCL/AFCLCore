
package afcl.functions.objects;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a condition of {@link Condition}
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data1",
        "data2",
        "operator"
})
public class ACondition {

    /**
     * Left operand
     */
    @JsonProperty("data1")
    private String data1;

    /**
     * Right operand
     */
    @JsonProperty("data2")
    private String data2;

    /**
     * Operand (e.g. ==, contains, ...)
     */
    @JsonProperty("operator")
    private String operator;

    /**
     * Negate the condition
     */
    @JsonProperty("negation")
    private String negation;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ACondition() {
    }

    /**
     * Constructor for ACondition
     *
     * @param data1    Left operand
     * @param data2    Right operand
     * @param operator Operand (e.g. ==, contains, ...)
     */
    public ACondition(String data1, String data2, String operator) {
        this.data1 = data1;
        this.data2 = data2;
        this.operator = operator;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("data1")
    public String getData1() {
        return data1;
    }

    @JsonProperty("data1")
    public void setData1(String data1) {
        this.data1 = data1;
    }

    @JsonProperty("data2")
    public String getData2() {
        return data2;
    }

    @JsonProperty("data2")
    public void setData2(String data2) {
        this.data2 = data2;
    }

    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonProperty("negation")
    public String getNegation() {
        return negation;
    }

    @JsonProperty("negation")
    public void setNegation(String negation) {
        this.negation = negation;
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

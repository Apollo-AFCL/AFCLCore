
package afcl.functions.objects;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides information about the number
 * of (parallel) loop iterations
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "type",
    "from",
    "to",
    "step"
})
public class LoopCounter {

    /**
     * Unique identifier for the loop counter
     */
    @JsonProperty("name")
    private String name;

    /**
     * Type of the counter variable (e.g. integer)
     */
    @JsonProperty("type")
    private String type;

    /**
     * Initial assignment of the counter variable
     */
    @JsonProperty("from")
    private String from;

    /**
     * If this value is reached or exceeded the iterations stops
     */
    @JsonProperty("to")
    private String to;

    /**
     * The counter variable will be increased step times in
     * each iteration.
     */
    @JsonProperty("step")
    private String step;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public LoopCounter() {
    }

    /**
     * Constructor for loop counter
     *
     * @param name Unique identifier for the loop counter
     * @param type Type of the counter variable (e.g. integer)
     * @param from Initial assignment of the counter variable
     * @param to If this value is reached or exceeded the iterations stops
     */
    public LoopCounter(String name, String type, String from, String to) {
        this(name, type, from, to, null);
    }

    /**
     * Constructor for loop counter
     *
     * @param name Unique identifier for the loop counter
     * @param type Type of the counter variable (e.g. integer)
     * @param from Initial assignment of the counter variable
     * @param to If this value is reached or exceeded the iterations stops
     * @param step The counter variable will be increased step times in each iteration.
     */
    public LoopCounter(String name, String type, String from, String to, String step) {
        this.name = name;
        this.type = type;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    @JsonProperty("step")
    public String getStep() {
        return step;
    }

    @JsonProperty("step")
    public void setStep(String step) {
        this.step = step;
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


package at.uibk.dps.afcl.functions.objects;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private String loopCounterName;

    /**
     * Type of the counter variable (e.g. integer)
     */
    @JsonProperty("type")
    private String loopCounterType;

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
    private Map<String, Object> additionalPropertiesLoopCounter = new HashMap<>();

    public LoopCounter() {
    }

    /**
     * Constructor for loop counter
     *
     * @param loopCounterName Unique identifier for the loop counter
     * @param loopCounterType Type of the counter variable (e.g. integer)
     * @param from Initial assignment of the counter variable
     * @param to If this value is reached or exceeded the iterations stops
     */
    public LoopCounter(String loopCounterName, String loopCounterType, String from, String to) {
        this(loopCounterName, loopCounterType, from, to, null);
    }

    /**
     * Constructor for loop counter
     *
     * @param loopCounterName Unique identifier for the loop counter
     * @param loopCounterType Type of the counter variable (e.g. integer)
     * @param from Initial assignment of the counter variable
     * @param to If this value is reached or exceeded the iterations stops
     * @param step The counter variable will be increased step times in each iteration.
     */
    public LoopCounter(String loopCounterName, String loopCounterType, String from, String to, String step) {
        this.loopCounterName = loopCounterName;
        this.loopCounterType = loopCounterType;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return loopCounterName;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.loopCounterName = name;
    }

    @JsonProperty("type")
    public String getType() {
        return loopCounterType;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.loopCounterType = type;
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
        return this.additionalPropertiesLoopCounter;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesLoopCounter.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoopCounter that = (LoopCounter) o;
        return Objects.equals(loopCounterName, that.loopCounterName) &&
                Objects.equals(loopCounterType, that.loopCounterType) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(step, that.step) &&
                Objects.equals(additionalPropertiesLoopCounter, that.additionalPropertiesLoopCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loopCounterName, loopCounterType, from, to, step, additionalPropertiesLoopCounter);
    }
}

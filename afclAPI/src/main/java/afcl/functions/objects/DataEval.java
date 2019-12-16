
package afcl.functions.objects;

import afcl.functions.Switch;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class describes the data to be compared among the
 * different ({@link Switch}) cases
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type"
})
public class DataEval {

    /**
     * Unique identifier for the data eval
     */
    @JsonProperty("name")
    private String name;

    /**
     * Indicates the data type
     */
    @JsonProperty("type")
    private String type;

    /**
     * Represents where the data comes from
     */
    @JsonProperty("source")
    private String source;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public DataEval() {
    }

    /**
     * Constructor for data eval
     *
     * @param name Unique identifier for the data eval
     * @param type Data type
     */
    public DataEval(String name, String type) {
        this.name = name;
        this.type = type;
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

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
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

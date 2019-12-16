package afcl.functions.objects.dataflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents a simple string value
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value"
})
public class DataFlowString extends DataFlowObject {

    /**
     * String value
     */
    @JsonProperty("value")
    String str;

    /**
     * Constructor
     *
     * @param str value
     */
    public DataFlowString(String str) {
        this.str = str;
    }

    public DataFlowString() {
    }


    /**
     * Getter and Setter
     */

    @JsonProperty("value")
    public String getStr() {
        return str;
    }

    @JsonProperty("value")
    public void setStr(String str) {
        this.str = str;
    }
}

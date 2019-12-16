package afcl.functions.objects.dataflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents the element index data flow
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "index"
})
public class DataFlowElementIndex extends DataFlowObject {

    /**
     * Index of the element index data flow
     */
    @JsonProperty("index")
    private DataFlowObject index;


    public DataFlowElementIndex() {
    }

    /**
     * Constructor of element index data flow
     *
     * @param index of the element index data flow
     */
    public DataFlowElementIndex(String index) {
        this.index = new DataFlowString(index);
    }

    /**
     * Constructor of element index data flow
     *
     * @param index of the element index data flow
     */
    public DataFlowElementIndex(DataFlowObject index) {
        this.index = index;
    }


    /**
     * Getter and Setter
     */

    @JsonProperty("index")
    public DataFlowObject getIndex() {
        return index;
    }

    @JsonProperty("index")
    public void setIndex(DataFlowObject index) {
        this.index = index;
    }
}

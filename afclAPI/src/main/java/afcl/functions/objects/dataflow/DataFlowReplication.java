package afcl.functions.objects.dataflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents the replication data flow
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "times"
})
public class DataFlowReplication extends DataFlowObject{

    /**
     * Number of replications
     */
    @JsonProperty("times")
    private DataFlowObject times;

    public DataFlowReplication() {
    }

    /**
     * Constructor for replication data flow
     *
     * @param times number of replications
     */
    public DataFlowReplication(DataFlowObject times) {
        //.type = DataFlowType.REPLICATION;
        this.times = times;
    }

    /**
     * Constructor for replication data flow
     *
     * @param times number of replications
     */
    public DataFlowReplication(String times) {
        //.type = DataFlowType.REPLICATION;
        this.times = new DataFlowString(times);
    }


    /**
     * Getter and Setter
     */

    @JsonProperty("times")
    public DataFlowObject getTimes() {
        return times;
    }

    @JsonProperty("times")
    public void setTimes(DataFlowObject times) {
        this.times = times;
    }
}

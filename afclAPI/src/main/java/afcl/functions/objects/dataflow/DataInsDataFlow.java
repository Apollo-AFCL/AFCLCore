package afcl.functions.objects.dataflow;

import afcl.functions.CompoundParallel;
import afcl.functions.objects.DataIns;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class describes the data input port of {@link CompoundParallel}
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "source",
        "value",
        "dataFlow",
        "properties",
        "constraints"
})
public class DataInsDataFlow extends DataIns {

    /**
     * Data flow of the input port
     */
    @JsonProperty("dataFlow")
    private DataFlowObject dataFlow;

    public DataInsDataFlow() {}

    /**
     * Constructor for data flow of data input ports
     *
     * @param name of the data input port
     * @param type of the data input port
     * @param source of the data input port
     */
    public DataInsDataFlow(String name, String type, String source) {
        super(name, type, source);
        this.dataFlow = dataFlow;
    }

    /**
     * Constructor for data flow of data input ports
     *
     * @param name of the data input port
     * @param type of the data input port
     * @param source of the data input port
     * @param dataFlow of the data input port
     */
    public DataInsDataFlow(String name, String type, String source, DataFlowObject dataFlow) {
        super(name, type, source);
        this.dataFlow = dataFlow;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("dataFlow")
    public DataFlowObject getDataFlow() {
        return dataFlow;
    }

    @JsonProperty("dataFlow")
    public void setDataFlow(DataFlowObject dataFlow) {
        this.dataFlow = dataFlow;
    }
}

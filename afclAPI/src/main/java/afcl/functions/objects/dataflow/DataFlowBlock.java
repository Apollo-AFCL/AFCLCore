package afcl.functions.objects.dataflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents the block data flow
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "size",
        "overlap"
})
public class DataFlowBlock extends DataFlowObject {

    /**
     * Size of the block data flow
     */
    @JsonProperty("size")
    private DataFlowObject size;

    /**
     * Overlap of the block data flow
     */
    @JsonProperty("overlap")
    private DataFlowObject overlap;

    public DataFlowBlock() {
    }

    /**
     * Constructor for block dataflow
     *
     * @param size    of the block data flow
     */
    public DataFlowBlock(String size) {
        this.size = new DataFlowString(size);
    }

    /**
     * Constructor for block dataflow
     *
     * @param size    of the block data flow
     */
    public DataFlowBlock(DataFlowObject size) {
        this.size = size;
    }

    /**
     * Constructor for block dataflow
     *
     * @param size    of the block data flow
     * @param overlap of the block data flow
     */
    public DataFlowBlock(DataFlowObject size, DataFlowObject overlap) {
        this.size = size;
        this.overlap = overlap;
    }

    /**
     * Constructor for block dataflow
     *
     * @param size    of the block data flow
     * @param overlap of the block data flow
     */
    public DataFlowBlock(String size, DataFlowObject overlap) {
        this.size = new DataFlowString(size);
        this.overlap = overlap;
    }

    /**
     * Constructor for block dataflow
     *
     * @param size    of the block data flow
     * @param overlap of the block data flow
     */
    public DataFlowBlock(DataFlowObject size, String overlap) {
        this.size = size;
        this.overlap = new DataFlowString(overlap);
    }

    /**
     * Constructor for block dataflow
     *
     * @param size    of the block data flow
     * @param overlap of the block data flow
     */
    public DataFlowBlock(String size, String overlap) {
        this.size = new DataFlowString(size);
        this.overlap = new DataFlowString(overlap);
    }


    /**
     * Getter and Setter
     */

    @JsonProperty("size")
    public DataFlowObject getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(DataFlowObject size) {
        this.size = size;
    }

    @JsonProperty("overlap")
    public DataFlowObject getOverlap() {
        return overlap;
    }

    @JsonProperty("overlap")
    public void setOverlap(DataFlowObject overlap) {
        this.overlap = overlap;
    }
}

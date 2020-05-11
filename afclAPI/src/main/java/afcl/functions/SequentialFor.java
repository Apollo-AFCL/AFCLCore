package afcl.functions;

import afcl.Function;
import afcl.functions.objects.*;
import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Objects;

/**
 * This class describes the sequential for compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "dataLoops",
        "loopCounter",
        "loopBody",
        "dataOuts"
})
@JsonTypeName("for")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class SequentialFor extends LoopCompound {

    /**
     * Contains needed information about potential loop data flow
     */
    @JsonProperty("dataLoops")
    private List<DataLoops> dataLoopsSequentialFor;

    /**
     * Contains needed information about the number of (sequential) loop iterations
     */
    @JsonProperty("loopCounter")
    private LoopCounter loopCounterSequentialFor;

    public SequentialFor() {
    }

    /**
     * Constructor for function compound for
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param loopCounterSequentialFor information about the number of (sequential) loop iterations
     * @param loopBodySequentialFor    functions which should be executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public SequentialFor(String name, List<DataIns> dataIns, List<DataLoops> dataLoopsSequentialFor, LoopCounter loopCounterSequentialFor, List<Function> loopBodySequentialFor, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.dataLoopsSequentialFor = dataLoopsSequentialFor;
        this.loopCounterSequentialFor = loopCounterSequentialFor;
        this.setLoopBody(loopBodySequentialFor);
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("dataLoops")
    public List<DataLoops> getDataLoops() { return dataLoopsSequentialFor; }

    @JsonProperty("dataLoops")
    public void setDataLoops(List<DataLoops> dataLoops) { this.dataLoopsSequentialFor = dataLoops; }

    @JsonProperty("loopCounter")
    public LoopCounter getLoopCounter() {
        return loopCounterSequentialFor;
    }

    @JsonProperty("loopCounter")
    public void setLoopCounter(LoopCounter loopCounterSequentialFor) {
        this.loopCounterSequentialFor = loopCounterSequentialFor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SequentialFor that = (SequentialFor) o;
        return Objects.equals(dataLoopsSequentialFor, that.dataLoopsSequentialFor) &&
                Objects.equals(loopCounterSequentialFor, that.loopCounterSequentialFor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dataLoopsSequentialFor, loopCounterSequentialFor);
    }
}

package afcl.functions;

import afcl.Function;
import afcl.functions.objects.*;
import com.fasterxml.jackson.annotation.*;
import afcl.functions.objects.Condition;
import java.util.List;
import java.util.Objects;

/**
 * This class describes the sequential while compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "dataLoops",
        "condition",
        "loopBody",
        "dataOuts"
})
@JsonTypeName("while")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class SequentialWhile extends LoopCompound {

    /**
     * Contains needed information about potential loop data flow
     */
    @JsonProperty("dataLoops")
    private List<DataLoops> dataLoopsSequentialWhile;

    /**
     * Contains needed information about the number of (sequential) loop iterations
     */
    @JsonProperty("condition")
    private Condition conditionSequentialWhile;

    public SequentialWhile() {
    }

    /**
     * Constructor for function compound while
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param conditionSequentialWhile   while loop condition
     * @param loopBodySequentialWhile    functions which should be executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public SequentialWhile(String name, List<DataIns> dataIns, List<DataLoops> dataLoopsSequentialWhile, Condition conditionSequentialWhile, List<Function> loopBodySequentialWhile, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.dataLoopsSequentialWhile = dataLoopsSequentialWhile;
        this.conditionSequentialWhile = conditionSequentialWhile;
        this.setLoopBody(loopBodySequentialWhile);
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("dataLoops")
    public List<DataLoops> getDataLoops() { return dataLoopsSequentialWhile; }

    @JsonProperty("dataLoops")
    public void setDataLoops(List<DataLoops> dataLoops) { this.dataLoopsSequentialWhile = dataLoops; }

    @JsonProperty("condition")
    public Condition getCondition() {
        return conditionSequentialWhile;
    }

    @JsonProperty("condition")
    public void setCondition(Condition loopCounter) {
        this.conditionSequentialWhile = loopCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SequentialWhile that = (SequentialWhile) o;
        return Objects.equals(dataLoopsSequentialWhile, that.dataLoopsSequentialWhile) &&
                Objects.equals(conditionSequentialWhile, that.conditionSequentialWhile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dataLoopsSequentialWhile, conditionSequentialWhile);
    }
}

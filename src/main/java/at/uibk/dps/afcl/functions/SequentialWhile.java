package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.Condition;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataLoops;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.*;

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
    private List<DataLoops> dataLoops;

    /**
     * Contains needed information about the number of (sequential) loop iterations
     */
    @JsonProperty("condition")
    private Condition condition;

    /**
     * Default constructor.
     */
    public SequentialWhile() {
        super();
    }

    /**
     * Constructor for function compound while
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param condition   while loop condition
     * @param loopBodySequentialWhile    functions which should be
     *                                   executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public SequentialWhile(final String name, final List<DataIns> dataIns, final List<DataLoops> dataLoops, final Condition condition,
                           final List<Function> loopBodySequentialWhile, final List<DataOuts> dataOuts) {
        this();
        this.name = name;
        this.dataIns = dataIns;
        this.dataLoops = dataLoops;
        this.condition = condition;
        this.setLoopBody(loopBodySequentialWhile);
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("dataLoops")
    public List<DataLoops> getDataLoops() { return dataLoops; }

    @JsonProperty("dataLoops")
    public void setDataLoops(final List<DataLoops> dataLoops) { this.dataLoops = dataLoops; }

    @JsonProperty("condition")
    public Condition getCondition() {
        return condition;
    }

    @JsonProperty("condition")
    public void setCondition(final Condition loopCounter) {
        this.condition = loopCounter;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        final SequentialWhile that = (SequentialWhile) object;
        return Objects.equals(dataLoops, that.dataLoops) &&
                Objects.equals(condition, that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dataLoops, condition);
    }
}

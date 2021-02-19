package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataLoops;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.LoopCounter;
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
    private List<DataLoops> dataLoops;

    /**
     * Contains needed information about the number of (sequential) loop iterations
     */
    @JsonProperty("loopCounter")
    private LoopCounter loopCounter;

    /**
     * Default constructor.
     */
    public SequentialFor() {
        super();
    }

    /**
     * Constructor for function compound for
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param loopCounter information about the number of
     *                    (sequential) loop iterations
     * @param loopBodySequentialFor    functions which should be
     *                                 executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public SequentialFor(final String name, final List<DataIns> dataIns, final List<DataLoops> dataLoops, final LoopCounter loopCounter,
                         final List<Function> loopBodySequentialFor, final List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.dataLoops = dataLoops;
        this.loopCounter = loopCounter;
        this.setLoopBody(loopBodySequentialFor);
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("dataLoops")
    public List<DataLoops> getDataLoops() { return dataLoops; }

    @JsonProperty("dataLoops")
    public void setDataLoops(final List<DataLoops> dataLoops) { this.dataLoops = dataLoops; }

    @JsonProperty("loopCounter")
    public LoopCounter getLoopCounter() {
        return loopCounter;
    }

    @JsonProperty("loopCounter")
    public void setLoopCounter(final LoopCounter loopCounterSequentialFor) {
        this.loopCounter = loopCounterSequentialFor;
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
        final SequentialFor that = (SequentialFor) object;
        return Objects.equals(dataLoops, that.dataLoops) &&
                Objects.equals(loopCounter, that.loopCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dataLoops, loopCounter);
    }
}

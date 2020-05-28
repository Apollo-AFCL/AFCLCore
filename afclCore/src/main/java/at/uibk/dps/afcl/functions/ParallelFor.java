
package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import at.uibk.dps.afcl.functions.objects.LoopCounter;
import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Objects;

/**
 * This class describes the parallelFor compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "loopCounter",
        "loopBody",
        "dataOuts"
})
@JsonTypeName("parallelFor")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ParallelFor extends LoopCompound {

    /**
     * Contains needed information about the number of (parallel) loop iterations
     */
    @JsonProperty("loopCounter")
    private LoopCounter loopCounterParallelFor;

    public ParallelFor() {
    }

    /**
     * Constructor for function compound parallelFor
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param loopCounterParallelFor information about the number of (parallel) loop iterations
     * @param loopBodyParallelFor    functions which should be executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public ParallelFor(String name, List<DataIns> dataIns, LoopCounter loopCounterParallelFor, List<Function> loopBodyParallelFor, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.loopCounterParallelFor = loopCounterParallelFor;
        this.setLoopBody(loopBodyParallelFor);
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("loopCounter")
    public LoopCounter getLoopCounter() {
        return loopCounterParallelFor;
    }

    @JsonProperty("loopCounter")
    public void setLoopCounter(LoopCounter loopCounterParallelFor) {
        this.loopCounterParallelFor = loopCounterParallelFor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ParallelFor that = (ParallelFor) o;
        return Objects.equals(loopCounterParallelFor, that.loopCounterParallelFor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loopCounterParallelFor);
    }
}

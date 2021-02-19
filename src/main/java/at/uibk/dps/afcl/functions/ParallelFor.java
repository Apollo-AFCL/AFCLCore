
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
    private LoopCounter loopCounter;

    /**
     * Default constructor.
     */
    public ParallelFor() {
        super();
    }

    /**
     * Constructor for function compound parallelFor
     *
     * @param name        Unique identifier of the compound
     * @param dataIns     Data input ports ({@link DataIns})
     * @param loopCounter information about the number of
     *                    (parallel) loop iterations
     * @param loopBodyParallelFor    functions which should be
     *                               executed in each iteration
     * @param dataOuts    Data output ports ({@link DataOuts})
     */
    public ParallelFor(final String name, final List<DataIns> dataIns, final LoopCounter loopCounter, final List<Function> loopBodyParallelFor, final List<DataOuts> dataOuts) {
        this();
        this.name = name;
        this.dataIns = dataIns;
        this.loopCounter = loopCounter;
        this.setLoopBody(loopBodyParallelFor);
        this.dataOuts = dataOuts;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("loopCounter")
    public LoopCounter getLoopCounter() {
        return loopCounter;
    }

    @JsonProperty("loopCounter")
    public void setLoopCounter(final LoopCounter loopCounter) {
        this.loopCounter = loopCounter;
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
        final ParallelFor that = (ParallelFor) object;
        return Objects.equals(loopCounter, that.loopCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loopCounter);
    }
}

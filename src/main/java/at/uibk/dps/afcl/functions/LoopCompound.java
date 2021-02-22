package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * This class describes a loop compound
 * @author stefanpedratscher
 */
public class LoopCompound extends Compound {
    /**
     * Contains {@link Function}s which should be executed in each iteration
     */
    @JsonProperty("loopBody")
    private List<Function> loopBody;

    /**
     * Default constructor.
     */
    public LoopCompound() {
        super();
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("loopBody")
    public List<Function> getLoopBody() {
        return loopBody;
    }

    @JsonProperty("loopBody")
    public void setLoopBody(final List<Function> loopBodyParallelFor) {
        this.loopBody = loopBodyParallelFor;
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
        final LoopCompound that = (LoopCompound) object;
        return Objects.equals(loopBody, that.loopBody) &&
                Objects.equals(getAdditionalProperties(), that.getAdditionalProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loopBody, getAdditionalProperties());
    }
}

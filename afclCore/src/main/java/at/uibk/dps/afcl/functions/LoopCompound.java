package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<Function> loopBody = null;

    @JsonIgnore
    private Map<String, Object> additionalPropertiesLoopCompound = new HashMap<>();

    /**
     * Getter and Setter
     */

    @JsonProperty("loopBody")
    public List<Function> getLoopBody() {
        return loopBody;
    }

    @JsonProperty("loopBody")
    public void setLoopBody(List<Function> loopBodyParallelFor) {
        this.loopBody = loopBodyParallelFor;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalPropertiesLoopCompound;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesLoopCompound.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LoopCompound that = (LoopCompound) o;
        return Objects.equals(loopBody, that.loopBody) &&
                Objects.equals(additionalPropertiesLoopCompound, that.additionalPropertiesLoopCompound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loopBody, additionalPropertiesLoopCompound);
    }
}

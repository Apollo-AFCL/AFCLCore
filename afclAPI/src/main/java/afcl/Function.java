package afcl;

import afcl.functions.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This class describes an abstract function ({@link AtomicFunction} or
 * {@link CompoundSimpleDataFlow})
 * @author stefanpedratscher
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AtomicFunction.class, name = "function"),
        @JsonSubTypes.Type(value = IfThenElse.class, name = "if"),
        @JsonSubTypes.Type(value = Parallel.class, name = "parallel"),
        @JsonSubTypes.Type(value = ParallelFor.class, name = "parallelFor"),
        @JsonSubTypes.Type(value = Sequence.class, name = "sequence"),
        @JsonSubTypes.Type(value = Switch.class, name = "switch")
})
public class Function {

    /**
     * Unique name of the function
     */
    @JsonProperty("name")
    protected String name;


    /**
     * Getter and Setter
     */

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}

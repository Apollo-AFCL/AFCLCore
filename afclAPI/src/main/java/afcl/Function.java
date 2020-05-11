package afcl;

import afcl.functions.*;
import afcl.functions.objects.PropertyConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * This class describes an abstract function ({@link AtomicFunction} or
 * {@link Compound})
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
     * {@link PropertyConstraint} (information about the
     * behaviour of functions)
     */
    @JsonProperty("properties")
    private List<PropertyConstraint> propertiesFunction;

    /**
     * {@link PropertyConstraint} (which must be fulfilled
     * by underlying workflow runtime environment)
     */
    @JsonProperty("constraints")
    private List<PropertyConstraint> constraintsFunction;


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

    @JsonProperty("properties")
    public List<PropertyConstraint> getProperties() {
        return propertiesFunction;
    }

    @JsonProperty("properties")
    public void setProperties(List<PropertyConstraint> propertiesFunction) {
        this.propertiesFunction = propertiesFunction;
    }

    @JsonProperty("constraints")
    public List<PropertyConstraint> getConstraints() {
        return constraintsFunction;
    }

    @JsonProperty("constraints")
    public void setConstraints(List<PropertyConstraint> constraintsFunction) {
        this.constraintsFunction = constraintsFunction;
    }
}

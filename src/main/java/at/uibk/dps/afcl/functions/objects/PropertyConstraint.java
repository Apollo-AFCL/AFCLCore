
package at.uibk.dps.afcl.functions.objects;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class describes a property or constraint which
 * provide additional information to a workflow runtime.
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "value"
})
public class PropertyConstraint {

    /**
     * Name of the property or constraint
     */
    @JsonProperty("name")
    private String name;

    /**
     * Value of the property or constraint regarding
     * its {@link PropertyConstraint#name}
     */
    @JsonProperty("value")
    private String value;
    @JsonIgnore
    private Map<String, Object> additionalPropertiesPropertiesConstraint = new HashMap<>();

    public PropertyConstraint() {
    }

    /**
     * Constructor for property or constraint
     *
     * @param name  Name of the property or constraint
     * @param value Value of the property or constraint regarding
     *              its {@link PropertyConstraint#name}
     */
    public PropertyConstraint(String name, String value) {
        this.name = name;
        this.value = value;
    }

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

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalPropertiesPropertiesConstraint;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesPropertiesConstraint.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PropertyConstraint that = (PropertyConstraint) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(additionalPropertiesPropertiesConstraint, that.additionalPropertiesPropertiesConstraint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, additionalPropertiesPropertiesConstraint);
    }
}

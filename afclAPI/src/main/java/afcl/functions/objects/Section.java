
package afcl.functions.objects;

import afcl.Function;
import afcl.functions.Parallel;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class describes a section of a
 * {@link Parallel} compound
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "section"
})
public class Section {

    /**
     * List of {@link Function}s within one section
     */
    @JsonProperty("section")
    private List<Function> section = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Section() {
    }

    /**
     * Constructor for section
     *
     * @param section List of {@link Function}s within one section
     */
    public Section(List<Function> section) {
        this.section = section;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("section")
    public List<Function> getSection() {
        return section;
    }

    @JsonProperty("section")
    public void setSection(List<Function> section) {
        this.section = section;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

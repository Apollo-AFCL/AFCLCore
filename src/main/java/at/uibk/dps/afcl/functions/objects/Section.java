
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.Parallel;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private List<Function> sectionBody;

    @JsonIgnore
    private Map<String, Object> additionalPropertiesSection = new HashMap<>();

    public Section() {
    }

    /**
     * Constructor for section
     *
     * @param section List of {@link Function}s within one section
     */
    public Section(List<Function> section) {
        this.sectionBody = section;
    }

    /**
     * Getter and Setter
     */

    @JsonProperty("section")
    public List<Function> getSection() {
        return sectionBody;
    }

    @JsonProperty("section")
    public void setSection(List<Function> section) {
        this.sectionBody = section;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalPropertiesSection;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalPropertiesSection.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        return Objects.equals(sectionBody, section.sectionBody) &&
                Objects.equals(additionalPropertiesSection, section.additionalPropertiesSection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionBody, additionalPropertiesSection);
    }
}

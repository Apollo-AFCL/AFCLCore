
package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.Parallel;
import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    /**
     * Optional additional json properties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Default constructor.
     */
    public Section() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor for section
     *
     * @param section List of {@link Function}s within one section
     */
    public Section(final List<Function> section) {
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
    public void setSection(final List<Function> section) {
        this.sectionBody = section;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(final String name, final Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Section section = (Section) object;
        return Objects.equals(sectionBody, section.sectionBody) &&
                Objects.equals(additionalProperties, section.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionBody, additionalProperties);
    }
}

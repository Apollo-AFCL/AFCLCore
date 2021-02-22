
package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.Case;
import at.uibk.dps.afcl.functions.objects.DataEval;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

/**
 * This class describes the compound function switch
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "dataIns",
        "dataEval",
        "cases",
        "default",
        "dataOuts"
})
@JsonTypeName("switch")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Switch extends Compound {

    /**
     * Represents the data to be compared among the different ({@link Switch#cases})
     */
    @JsonProperty("dataEval")
    private DataEval dataEval;

    /**
     * List of cases within the switch compound
     */
    @JsonProperty("cases")
    private List<Case> cases;

    /**
     * Default case (if no other case in {@link Switch#cases} matches the
     * criterion of {@link Switch#dataEval}
     */
    @JsonProperty("default")
    private List<Function> defaultBranch;

    /**
     * Default constructor.
     */
    public Switch() {
        super();
    }

    /**
     * Constructor for function compound switch
     *
     * @param name     Unique identifier of the compound
     * @param dataIns  Data input ports ({@link DataIns})
     * @param dataEval data to be compared among the different cases
     * @param cases    List of cases within the switch compound
     * @param dataOuts Data output ports ({@link DataOuts})
     */
    public Switch(final String name, final List<DataIns> dataIns, final DataEval dataEval, final List<Case> cases, final List<DataOuts> dataOuts) {
        this();
        this.name = name;
        this.dataIns = dataIns;
        this.dataEval = dataEval;
        this.cases = cases;
        this.dataOuts = dataOuts;
    }


    /**
     * Getter and Setter
     */

    @JsonProperty("dataEval")
    public DataEval getDataEval() {
        return dataEval;
    }

    @JsonProperty("dataEval")
    public void setDataEval(final DataEval dataEval) {
        this.dataEval = dataEval;
    }

    @JsonProperty("cases")
    public List<Case> getCases() {
        return cases;
    }

    @JsonProperty("cases")
    public void setCases(final List<Case> cases) {
        this.cases = cases;
    }

    @JsonProperty("default")
    public List<Function> getDefaultBranch() {
        return defaultBranch;
    }

    @JsonProperty("default")
    public void setDefaultBranch(final List<Function> defaultBranch) {
        this.defaultBranch = defaultBranch;
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
        final Switch aSwitch = (Switch) object;
        return Objects.equals(dataEval, aSwitch.dataEval) &&
                Objects.equals(cases, aSwitch.cases) &&
                Objects.equals(defaultBranch, aSwitch.defaultBranch) &&
                Objects.equals(getAdditionalProperties(), aSwitch.getAdditionalProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dataEval, cases, defaultBranch, getAdditionalProperties());
    }
}

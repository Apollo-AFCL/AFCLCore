
package afcl.functions;

import afcl.Function;
import afcl.functions.objects.*;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Case> cases = null;

    /**
     * Default case (if no other case in {@link Switch#cases} matches the
     * criterion of {@link Switch#dataEval}
     */
    @JsonProperty("default")
    private List<Function> defaultBranch = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Switch() {
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
    public Switch(String name, List<DataIns> dataIns, DataEval dataEval, List<Case> cases, List<DataOuts> dataOuts) {
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
    public void setDataEval(DataEval dataEval) {
        this.dataEval = dataEval;
    }

    @JsonProperty("cases")
    public List<Case> getCases() {
        return cases;
    }

    @JsonProperty("cases")
    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    @JsonProperty("default")
    public List<Function> getDefault() {
        return defaultBranch;
    }

    @JsonProperty("default")
    public void setDefault(List<Function> defaultBranch) {
        this.defaultBranch = defaultBranch;
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

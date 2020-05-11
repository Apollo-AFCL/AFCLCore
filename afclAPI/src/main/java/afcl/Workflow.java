
package afcl;

import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * This class describes a workflow
 * @author stefanpedratscher
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "subFCs",
        "dataIns",
        "workflowBody",
        "dataOuts"
})
public class Workflow {

    /**
     * Unique name of the workflow
     */
    @JsonProperty("name")
    private String name;

    /**
     * List of sub function choreographies ({@link SubFC})
     */
    @JsonProperty("subFCs")
    private List<SubFC> subFCs = null;

    /**
     * Data input ports ({@link DataIns})
     */
    @JsonProperty("dataIns")
    private List<DataIns> dataIns;

    /**
     * Workflow body containing {@link Function}s
     */
    @JsonProperty("workflowBody")
    private List<Function> workflowBody = null;

    /**
     * Data output ports ({@link DataOuts})
     */
    @JsonProperty("dataOuts")
    private List<DataOuts> dataOuts;

    public Workflow() {
    }

    /**
     * Constructor for a workflow
     *
     * @param name         Unique name of the workflow
     * @param dataIns      Data input ports ({@link DataIns})
     * @param workflowBody Workflow body containing {@link Function}s
     * @param dataOuts     Data output ports ({@link DataOuts})
     */
    public Workflow(String name, List<DataIns> dataIns, List<Function> workflowBody, List<DataOuts> dataOuts) {
        this.name = name;
        this.dataIns = dataIns;
        this.workflowBody = workflowBody;
        this.dataOuts = dataOuts;
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

    @JsonProperty("subFCs")
    public List<SubFC> getSubFCs() {
        return subFCs;
    }

    @JsonProperty("subFCs")
    public void setSubFCs(List<SubFC> subFCs) {
        this.subFCs = subFCs;
    }

    @JsonProperty("dataIns")
    public List<DataIns> getDataIns() {
        return dataIns;
    }

    @JsonProperty("dataIns")
    public void setDataIns(List<DataIns> dataIns) {
        this.dataIns = dataIns;
    }

    @JsonProperty("workflowBody")
    public List<Function> getWorkflowBody() {
        return workflowBody;
    }

    @JsonProperty("workflowBody")
    public void setWorkflowBody(List<Function> workflowBody) {
        this.workflowBody = workflowBody;
    }

    @JsonProperty("dataOuts")
    public List<DataOuts> getDataOuts() {
        return dataOuts;
    }

    @JsonProperty("dataOuts")
    public void setDataOuts(List<DataOuts> dataOuts) {
        this.dataOuts = dataOuts;
    }
}

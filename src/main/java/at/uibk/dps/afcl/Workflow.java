
package at.uibk.dps.afcl;

import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

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
    private List<SubFC> subFCs;

    /**
     * Data input ports ({@link DataIns})
     */
    @JsonProperty("dataIns")
    private List<DataIns> dataIns;

    /**
     * Workflow body containing {@link Function}s
     */
    @JsonProperty("workflowBody")
    private List<Function> workflowBody;

    /**
     * Data output ports ({@link DataOuts})
     */
    @JsonProperty("dataOuts")
    private List<DataOuts> dataOuts;

    /**
     * Default constructor.
     */
    public Workflow() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor for a workflow
     *
     * @param name         Unique name of the workflow
     * @param dataIns      Data input ports ({@link DataIns})
     * @param workflowBody Workflow body containing {@link Function}s
     * @param dataOuts     Data output ports ({@link DataOuts})
     */
    public Workflow(final String name, final List<DataIns> dataIns, final List<Function> workflowBody, final List<DataOuts> dataOuts) {
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
    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("dataOuts")
    public List<DataOuts> getDataOuts() {
        return dataOuts;
    }

    @JsonProperty("dataOuts")
    public void setDataOuts(final List<DataOuts> dataOuts) {
        this.dataOuts = dataOuts;
    }

    @JsonProperty("subFCs")
    public List<SubFC> getSubFCs() {
        return subFCs;
    }

    @JsonProperty("subFCs")
    public void setSubFCs(final List<SubFC> subFCs) {
        this.subFCs = subFCs;
    }

    @JsonProperty("dataIns")
    public List<DataIns> getDataIns() {
        return dataIns;
    }

    @JsonProperty("dataIns")
    public void setDataIns(final List<DataIns> dataIns) {
        this.dataIns = dataIns;
    }

    @JsonProperty("workflowBody")
    public List<Function> getWorkflowBody() {
        return workflowBody;
    }

    @JsonProperty("workflowBody")
    public void setWorkflowBody(final List<Function> workflowBody) {
        this.workflowBody = workflowBody;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Workflow workflow = (Workflow) object;
        return Objects.equals(name, workflow.name) &&
                Objects.equals(subFCs, workflow.subFCs) &&
                Objects.equals(dataIns, workflow.dataIns) &&
                Objects.equals(workflowBody, workflow.workflowBody) &&
                Objects.equals(dataOuts, workflow.dataOuts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subFCs, dataIns, workflowBody, dataOuts);
    }
}

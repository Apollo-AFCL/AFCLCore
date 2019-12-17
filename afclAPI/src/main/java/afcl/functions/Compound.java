package afcl.functions;

import afcl.Function;
import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This class describes a sequential compound function
 * @author stefanpedratscher
 */
public class Compound extends Function {

    /**
     * Data input ports of the compound ({@link DataIns})
     */
    @JsonProperty("dataIns")
    protected List<DataIns> dataIns;

    /**
     * Data output ports of the compound ({@link DataOuts})
     */
    @JsonProperty("dataOuts")
    protected List<DataOuts> dataOuts;


    /**
     * Getter and Setter
     */

    @JsonProperty("dataIns")
    public List<DataIns> getDataIns() {
        return dataIns;
    }

    @JsonProperty("dataIns")
    public void setDataIns(List<DataIns> dataIns) {
        this.dataIns = dataIns;
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

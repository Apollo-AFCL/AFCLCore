package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Compound compound = (Compound) o;
        return super.equals(o) &&
                Objects.equals(dataIns, compound.dataIns) &&
                Objects.equals(dataOuts, compound.dataOuts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataIns, dataOuts);
    }
}

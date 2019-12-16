package afcl.functions.objects.dataflow;

import afcl.functions.Switch;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This class represents a case of the {@link Switch} compound
 * @author stefanpedratscher
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
})
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataFlowBlock.class, name = "BLOCK"),
        @JsonSubTypes.Type(value = DataFlowReplication.class, name = "REPLICATE"),
        @JsonSubTypes.Type(value = DataFlowElementIndex.class, name = "ELEMENT_INDEX"),
        @JsonSubTypes.Type(value = DataFlowString.class, name = "SINGLE"),
})
public class DataFlowObject {
}

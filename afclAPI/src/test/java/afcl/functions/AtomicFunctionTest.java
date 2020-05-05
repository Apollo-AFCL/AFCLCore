package afcl.functions;

import static org.junit.Assert.*;

import afcl.functions.objects.DataIns;
import afcl.functions.objects.DataOutsAtomic;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class AtomicFunctionTest {

    /**
     * Test full construction of an AtomicFunction.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction("atomicFunction", "atomicFunctionType", null, null);

        assertNull(atomicFunction.getDataIns());
        assertNull(atomicFunction.getDataOuts());
        assertEquals("atomicFunction", atomicFunction.getName());
        assertEquals("atomicFunctionType", atomicFunction.getType());
        assertEquals(0, atomicFunction.getAdditionalProperties().size());
    }

    /**
     * Test the later creation of an atomicFunction.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testLateConstruction() {
        AtomicFunction atomicFunction = new AtomicFunction();

        assertNull(atomicFunction.getDataIns());
        assertNull(atomicFunction.getDataOuts());
        assertNull(atomicFunction.getName());
        assertNull(atomicFunction.getName());
        assertEquals(0, atomicFunction.getAdditionalProperties().size());

        atomicFunction.setName("atomicFunction");
        assertEquals("atomicFunction", atomicFunction.getName());

        atomicFunction.setType("atomicFunctionType");
        assertEquals("atomicFunctionType", atomicFunction.getType());

        atomicFunction.setDataIns(new ArrayList<>(Collections.singleton(new DataIns("inName", "inType"))));
        assertEquals("inName", atomicFunction.getDataIns().get(0).getName());
        assertEquals("inType", atomicFunction.getDataIns().get(0).getType());

        atomicFunction.setDataOuts(new ArrayList<>(Collections.singleton(new DataOutsAtomic("outName", "outType"))));
        assertEquals("outName", atomicFunction.getDataOuts().get(0).getName());
        assertEquals("outType", atomicFunction.getDataOuts().get(0).getType());
    }

    /**
     * Test the additional properties functionality of an AtomicFunction.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testAdditionalProperties() {
        AtomicFunction atomicFunction = new AtomicFunction();
        assertEquals(0, atomicFunction.getAdditionalProperties().size());

        atomicFunction.setAdditionalProperty("name", "value");
        assertEquals(1, atomicFunction.getAdditionalProperties().size());

        atomicFunction.setAdditionalProperty("name2", "value2");
        assertEquals(2, atomicFunction.getAdditionalProperties().size());

        assertEquals("value", atomicFunction.getAdditionalProperties().get("name"));
        assertEquals("value2", atomicFunction.getAdditionalProperties().get("name2"));
    }
}

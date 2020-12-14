package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.Collections;

public class LoopCompoundTest {

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(LoopCompound.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        LoopCompound loopCompound1 = new LoopCompound();
        Assert.assertEquals(loopCompound1, loopCompound1);
        Assert.assertEquals(loopCompound1.hashCode(), loopCompound1.hashCode());
        Assert.assertNotEquals(loopCompound1, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(loopCompound1, compound);

        final LoopCompound loopCompound2 = new LoopCompound();
        Assert.assertEquals(loopCompound1, loopCompound2);
        Assert.assertEquals(loopCompound1.hashCode(), loopCompound2.hashCode());

        LoopCompound loopCompound3 = new LoopCompound();
        loopCompound3.setDataIns(Collections.singletonList(new DataIns("name", "type", "source")));
        Assert.assertNotEquals(loopCompound1, loopCompound3);

        loopCompound3 = new LoopCompound();
        loopCompound3.setDataOuts(Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(loopCompound1, loopCompound3);

        loopCompound3 = new LoopCompound();
        loopCompound3.setName("nameWrong");
        Assert.assertNotEquals(loopCompound1, loopCompound3);
    }
}

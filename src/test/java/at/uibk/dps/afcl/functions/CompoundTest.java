package at.uibk.dps.afcl.functions;

import at.uibk.dps.afcl.Function;
import at.uibk.dps.afcl.functions.objects.DataIns;
import at.uibk.dps.afcl.functions.objects.DataOuts;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.Collections;

public class CompoundTest {

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Compound.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        Compound compound1 = new Compound();
        Assert.assertEquals(compound1, compound1);
        Assert.assertEquals(compound1.hashCode(), compound1.hashCode());
        Assert.assertNotEquals(compound1, null);

        Function function = new Function();
        Assert.assertNotEquals(compound1, function);

        Compound compound2 = new Compound();
        Assert.assertEquals(compound1, compound2);
        Assert.assertEquals(compound1.hashCode(), compound2.hashCode());

        Compound compound3 = new Compound();
        compound3.setDataIns(Collections.singletonList(new DataIns("name", "type", "source")));
        Assert.assertNotEquals(compound1, compound3);

        compound3 = new Compound();
        compound3.setDataOuts(Collections.singletonList(new DataOuts("name", "type", "source")));
        Assert.assertNotEquals(compound1, compound3);

        compound3 = new Compound();
        compound3.setName("nameWrong");
        Assert.assertNotEquals(compound1, compound3);
    }
}

package at.uibk.dps.afcl;

import at.uibk.dps.afcl.functions.Compound;
import at.uibk.dps.afcl.functions.objects.PropertyConstraint;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import java.util.Collections;

/**
 * Test the functionality of a function object.
 *
 * @author stefanpedratscher
 */
public class FunctionTest {

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(Function.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        Function function1 = new Function();
        Assert.assertEquals(function1, function1);
        Assert.assertEquals(function1.hashCode(), function1.hashCode());
        Assert.assertNotEquals(function1, null);

        Compound compound = new Compound();
        Assert.assertNotEquals(function1, compound);

        final Function function2 = new Function();
        Assert.assertEquals(function1, function2);
        Assert.assertEquals(function1.hashCode(), function2.hashCode());

        Function function3 = new Function();
        function3.setName("name");
        Assert.assertNotEquals(function1, function3);

        function3 = new Function();
        function3.setConstraints(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(function1, function3);

        function3 = new Function();
        function3.setProperties(Collections.singletonList(new PropertyConstraint("name", "value")));
        Assert.assertNotEquals(function1, function3);
    }
}

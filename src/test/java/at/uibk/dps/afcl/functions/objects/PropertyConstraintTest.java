package at.uibk.dps.afcl.functions.objects;

import at.uibk.dps.afcl.functions.Compound;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * Test the functionality of a property or constraint.
 *
 * @author stefanpedratscher
 */
public class PropertyConstraintTest {
    /**
     * Test full construction of a propertyConstraint.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testFullConstruction() {
        final PropertyConstraint propertyConstraint = new PropertyConstraint("name", "value");

        Assert.assertEquals("name", propertyConstraint.getName());
        Assert.assertEquals("value", propertyConstraint.getValue());
        Assert.assertEquals(0, propertyConstraint.getAdditionalProperties().size());
    }

    /**
     * Test the empty construction of a propertyConstraint.
     *
     * @author stefanpedratscher
     */
    @Test
    public void testEmptyConstruction() {
        final PropertyConstraint propertyConstraint = new PropertyConstraint();

        Assert.assertNull(propertyConstraint.getName());
        Assert.assertNull(propertyConstraint.getValue());
        Assert.assertEquals(0, propertyConstraint.getAdditionalProperties().size());
    }

    /**
     * Test getter and setter
     *
     * @author stefanpedratscher
     */
    @Test
    public void testGetterAndSetter() {
        new BeanTester().testBean(PropertyConstraint.class);
    }

    /**
     * Test hashCode and equals
     *
     * @author stefanpedratscher
     */
    @Test
    public void testHashEquals() {
        final PropertyConstraint propertyConstraint = new PropertyConstraint("name", "value");

        Assert.assertEquals(propertyConstraint, propertyConstraint);
        Assert.assertEquals(propertyConstraint.hashCode(), propertyConstraint.hashCode());
        Assert.assertNotEquals(propertyConstraint, null);

        final Compound compound = new Compound();
        Assert.assertNotEquals(propertyConstraint, compound);

        final PropertyConstraint propertyConstraint2 = new PropertyConstraint("name", "value");
        Assert.assertEquals(propertyConstraint, propertyConstraint2);
        Assert.assertEquals(propertyConstraint.hashCode(), propertyConstraint2.hashCode());
        propertyConstraint2.setAdditionalProperties("name", "type");
        Assert.assertNotEquals(propertyConstraint, propertyConstraint2);

        PropertyConstraint propertyConstraint3;
        propertyConstraint3 = new PropertyConstraint("nameWrong", "value");
        Assert.assertNotEquals(propertyConstraint, propertyConstraint3);

        propertyConstraint3 = new PropertyConstraint("name", "valueWrong");
        Assert.assertNotEquals(propertyConstraint, propertyConstraint3);
    }
}

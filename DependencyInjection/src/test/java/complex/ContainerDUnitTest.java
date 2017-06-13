/*
 * Copyright (C) 2017 naluem
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package complex;

import common.DependencyException;
import implementations.ImplementationD1;
import interfaces.InterfaceA;
import interfaces.InterfaceD;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author naluem
 */
public class ContainerDUnitTest {

    private Injector injector;

    @Before
    public void setUp() {
        this.injector = new Container();
    }

    @Test
    public void getObjectTest() throws DependencyException {
        injector= new Container();
        injector.registerConstant(Integer.class, 42);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        InterfaceD d = injector.getObject(InterfaceD.class);
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }
    @Test(expected = DependencyException.class)
    public void getObjectUnderUnregisteredNameTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        InterfaceD d = (InterfaceD) injector.getObject(InterfaceA.class);
    }

    @Test(expected = DependencyException.class)
    public void getObjectWithoutAllDependenciesTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        //injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject(InterfaceD.class);
    }

    @Test(expected = DependencyException.class)
    public void registerConstantWithRegisteredNameTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(Integer.class, 42);
    }

    @Test(expected = DependencyException.class)
    public void registerIncorrectConstantTest() throws DependencyException {
        injector.registerConstant(String.class, "42");
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        InterfaceD d = (InterfaceD) injector.getObject(InterfaceD.class);
    }

    @Test(expected = DependencyException.class)
    public void registerFactoryWithRegisteredNameTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
    }

    @Test(expected = DependencyException.class)
    public void registerIncorrectFactoryTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), Integer.class);
        InterfaceD d = (InterfaceD) injector.getObject(InterfaceD.class);
    }

}

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
package simple;

import common.DependencyException;
import implementations.ImplementationD1;
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
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test(expected = DependencyException.class)
    public void getObjectUnderUnregisteredNameTest() throws DependencyException {
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("H");
    }

    @Test(expected = DependencyException.class)
    public void getObjectWithoutAllDependenciesTest() throws DependencyException {
        injector.registerConstant("I", 42);
        //injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }

    @Test(expected = DependencyException.class)
    public void registerConstantWithRegisteredNameTest() throws DependencyException {
        injector.registerConstant("I", 42);
        injector.registerConstant("I", 42);
    }

    @Test(expected = DependencyException.class)
    public void registerIncorrectConstantTest() throws DependencyException {
        injector.registerConstant("I", "42");
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }

    @Test(expected = DependencyException.class)
    public void registerFactoryWithRegisteredNameTest() throws DependencyException {
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        injector.registerFactory("D", new FactoryD1(), "I");
    }

    @Test(expected = DependencyException.class)
    public void registerIncorrectFactoryTest() throws DependencyException {
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryA1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }

}

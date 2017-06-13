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
import implementations.ImplementationA1;
import implementations.ImplementationB1;
import implementations.ImplementationC1;
import implementations.ImplementationD1;
import interfaces.InterfaceA;
import interfaces.InterfaceB;
import interfaces.InterfaceC;
import interfaces.InterfaceD;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author naluem
 */
public class ContainerAUnitTest {

    public ContainerAUnitTest() {
    }

    private Injector injector;

    @Before
    public void setUp() {
        injector = new Container();
    }

    @Test
    public void getObjectTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "patata");
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class,
                InterfaceC.class);
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);

        assertThat(a, is(instanceOf(ImplementationA1.class)));

        ImplementationA1 a1 = (ImplementationA1) a;
        ImplementationB1 b1 = (ImplementationB1) a1.getB();
        ImplementationC1 c1 = (ImplementationC1) a1.getC();
        ImplementationD1 d1 = (ImplementationD1) b1.getD();

        assertThat(c1.getS(), is("patata"));
        assertThat(d1.getI(), is(42));
    }

    @Test(expected = DependencyException.class)
    public void getObjectUnderUnregisteredNameTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "patata");
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class, InterfaceC.class);
        InterfaceA a = (InterfaceA) injector.getObject(Object.class);
    }

    @Test(expected = DependencyException.class)
    public void getObjectWithoutAllDependenciesTest() throws DependencyException {
        //injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "patata");
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class,
                InterfaceC.class);
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
    }

    @Test(expected = DependencyException.class)
    public void registerConstantWithRegisteredNameTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "patata");
        injector.registerConstant(String.class, "patata");
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class,
                InterfaceC.class);
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
    }

    @Test(expected = DependencyException.class)
    public void registerIncorrectConstantTest() throws DependencyException {
        injector.registerConstant(String.class, "42");
        injector.registerConstant(String.class, "patata");
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class,
                InterfaceC.class);
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
    }

    @Test(expected = DependencyException.class)
    public void registerFactoryWithRegisteredNameTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "patata");
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class,
                InterfaceC.class);
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
    }

    @Test(expected = DependencyException.class)
    public void registerIncorrectFactoryTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "patata");
        injector.registerFactory(InterfaceA.class, new FactoryA1(), Integer.class);
        injector.registerFactory(InterfaceD.class, new FactoryD1(),Integer.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class,
                InterfaceC.class);
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
    }

}

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
package com.mdt3.dependencyinjection.simple;

import com.mdt3.dependencyinjection.Implementations.ImplementationD1;
import com.mdt3.dependencyinjection.Interfaces.InterfaceD;
import com.mdt3.dependencyinjection.common.DependencyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author naluem
 */
public class ContainerImplementationA1Test {

    public ContainerImplementationA1Test() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getObjectImplementationA1Test() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test(expected = DependencyException.class)
    public void getObjectUnderUnregisteredNameTest() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("H");
    }

    @Test(expected = DependencyException.class)
    public void getObjectWithoutAllDependenciesTest() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        //injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }

    @Test(expected = DependencyException.class)
    public void registerConstantWithRegisteredNameTest() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerConstant("I", 42);
    }
    
    @Test(expected = DependencyException.class)
    public void registerIncorrectConstantTest() throws DependencyException{
        Injector injector = new Container();
        injector.registerConstant("I", "42");
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }

    @Test(expected = DependencyException.class)
    public void registerFactoryWithRegisteredNameTest() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        injector.registerFactory("D", new FactoryD1(), "I");
    }

    @Test(expected = DependencyException.class)
    public void registerIncorrectFactoryTest() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryA1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
    }
}

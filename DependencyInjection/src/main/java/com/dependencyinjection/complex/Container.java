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
package com.dependencyinjection.complex;

import com.dependencyinjection.common.DependencyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author naluem
 */
public class Container implements Injector {

    private final Map<Class<?>, Object> constants;
    private final Map<Class<?>, Factory<?>> factories;
    private final Map<Class<?>, Class<?>[]> parameters;

    public Container() {
        constants = new HashMap<>();
        factories = new HashMap<>();
        parameters = new HashMap<>();
    }

    @Override
    public <E> void registerConstant(Class<E> name, E value)
            throws DependencyException {
        if (constants.containsKey(name) || !name.isInstance(value)) {
            throw new DependencyException("");
        }
        constants.put(name, (Object) value);
    }

    @Override
    public <E> void registerFactory(Class<E> name, Factory<? extends E> creator,
            Class<?>... parameters) throws DependencyException {
        if (factories.containsKey(name) || this.parameters.containsKey(name)) {
            throw new DependencyException("");
        }
        factories.put(name, creator);
        this.parameters.put(name, parameters);
    }

    @Override
    public <E> E getObject(Class<E> name) throws DependencyException {
        if (!factories.containsKey(name)) {
            throw new DependencyException("");
        }
        for (Class<?> parameterKey : parameters.get(name)) {
            if (!constants.containsKey(parameterKey)) {
                throw new DependencyException("");
            }
        }
        return (E) factories.get(name).create(_getParameters(parameters.get(name)));
    }

    private Object[] _getParameters(Class<?>[] keys) {
        List<Object> prms = new ArrayList<>();
        for (Class<?> key : keys) {
            prms.add(constants.get(key));
        }
        return prms.toArray();
    }

}

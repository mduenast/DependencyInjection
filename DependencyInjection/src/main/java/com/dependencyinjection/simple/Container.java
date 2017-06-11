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
package com.dependencyinjection.simple;

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

    private final Map<String, Object> constants;
    private final Map<String, Factory> factories;
    private final Map<String, String[]> parameters;

    public Container() {
        constants = new HashMap<>();
        factories = new HashMap<>();
        parameters = new HashMap<>();
    }

    @Override
    public void registerConstant(String name, Object value)
            throws DependencyException {
        if (constants.containsKey(name)) {
            throw new DependencyException("");
        }
        constants.put(name, value);
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters)
            throws DependencyException {
        if (factories.containsKey(name) || this.parameters.containsKey(name)) {
            throw new DependencyException("");
        }
        factories.put(name, creator);
        this.parameters.put(name, parameters);
    }

    @Override
    public Object getObject(String name) throws DependencyException {
        if (!factories.containsKey(name)) {
            throw new DependencyException("");
        }
        for (String parameterKey : parameters.get(name)) {
            if (!constants.containsKey(parameterKey)) {
                throw new DependencyException("");
            }
        }
        return factories.get(name).create(_getParameters(parameters.get(name)));
    }

    private Object[] _getParameters(String[] keys) {
        List<Object> prms = new ArrayList<>();
        for (String key : keys) {
            prms.add(constants.get(key));
        }
        return prms.toArray();
    }

}

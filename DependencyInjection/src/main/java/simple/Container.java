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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author naluem
 */
public class Container implements Injector {

    private final Map<String, Object> constants = new HashMap<>();
    private final Map<String, DependencyEntry> factories = new HashMap<>();

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if (this.constants.containsKey(name)) {
            throw new DependencyException("Unexpected entry : Constant was registered");
        }
        this.constants.put(name, value);
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if (this.factories.containsKey(name)) {
            throw new DependencyException("Unexpected entry : Factory was registered");
        }
        this.factories.put(name, new DependencyEntry(creator, parameters));
    }

    @Override
    public Object getObject(String name) throws DependencyException {
        if (!this.factories.containsKey(name)) {
            throw new DependencyException("Illegal state. Object that you want to create hasn't a factory to create it.");
        }
        DependencyEntry dependencyEntry = this.factories.get(name);
        List<Object> parameters = new ArrayList<>();
        for (String parameter : dependencyEntry.parameters) {
            if (this.factories.containsKey(parameter)) {
                parameters.add(this.getObject(parameter));
            } else if (this.constants.containsKey(parameter)) {
                parameters.add(this.constants.get(parameter));
            } else {
                throw new DependencyException("Object that you want to create hasn't some of its dependencies to create it.");
            }
        }
        return dependencyEntry.factory.create(parameters.toArray());
    }

    private static class DependencyEntry {

        private final Factory factory;
        private final String[] parameters;

        public DependencyEntry(Factory factory, String[] parameters) {
            this.factory = factory;
            this.parameters = parameters;
        }

    }

}

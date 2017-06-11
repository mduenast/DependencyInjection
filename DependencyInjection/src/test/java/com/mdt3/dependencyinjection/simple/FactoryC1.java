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

import com.mdt3.dependencyinjection.Implementations.ImplementationC1;
import com.mdt3.dependencyinjection.common.DependencyException;

/**
 *
 * @author naluem
 */
public class FactoryC1 implements Factory {

    @Override
    public ImplementationC1 create(Object... parameters)
            throws DependencyException {
        String s;
        try {
            s = (String) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationC1(s);
    }
}

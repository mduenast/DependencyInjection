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
package implementations;

import interfaces.InterfaceA;
import interfaces.InterfaceB;
import interfaces.InterfaceC;

/**
 *
 * @author naluem
 */
public class ImplementationA1 implements InterfaceA {

    private final InterfaceB b;
    private final InterfaceC c;

    public ImplementationA1(InterfaceB b, InterfaceC c) {
        this.b = b;
        this.c = c;
    }

    public InterfaceB getB() {
        return b;
    }

    public InterfaceC getC() {
        return c;
    }

}

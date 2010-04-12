/**
 * Copyright (C) 2003 - 2009
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package net.sourceforge.cilib.functions.continuous.unconstrained;

import static org.junit.Assert.assertEquals;
import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.Vector;

import org.junit.Before;
import org.junit.Test;

public class ZakharovTest {
    private ContinuousFunction function;

    @Before
    public void instantiate() {
        this.function = new Zakharov();
    }

    /** Test of evaluate method, of class cilib.functions.unconstrained.Zakharov. */
    @Test
    public void testEvaluate() {
        function.setDomain("R(-5, 10)^2");

        Vector x = new Vector();
        x.add(Real.valueOf(0.0));
        x.add(Real.valueOf(0.0));

        assertEquals(0.0, function.evaluate(x), 0.0);

        x.setReal(0, 1.0);
        x.setReal(1, 2.0);

        assertEquals(50.3125, function.evaluate(x), 0.0000000001);
    }

    @Test
    public void minimum() {
        assertEquals(0.0, function.getMinimum().doubleValue(), 0.0);
    }
}
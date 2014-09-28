/* Copyright (c) 2014 Luke Quinane
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.tmyroadctfig.jsteer2d;

import com.badlogic.gdx.math.Vector2;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.number.IsCloseTo.closeTo;

/**
 * Tests for {@link Vector2Utils}.
 */
@RunWith(JUnitParamsRunner.class)
public class TestVector2Utils
{
    public Object[][] findAngleBetweenTwoVectorsParam()
    {
        return new Object[][]
            {
                // Description, v1, v2, angle (degrees)
                { "Same vector", new Vector2(0, 1), new Vector2(0, 1), 0 },

                { "Right angle r1 cw", new Vector2(1, 0), new Vector2(0, -1), 90 },
                { "Right angle r2 cw", new Vector2(0, -1), new Vector2(-1, 0), 90 },
                { "Right angle r3 cw", new Vector2(-1, 0), new Vector2(0, 1), 90 },
                { "Right angle r4 cw", new Vector2(0, 1), new Vector2(1, 0), 90 },

                { "Right angle r1 ccw", new Vector2(1, 0), new Vector2(0, 1), -90 },
                { "Right angle r2 ccw", new Vector2(0, 1), new Vector2(-1, 0), -90 },
                { "Right angle r3 ccw", new Vector2(-1, 0), new Vector2(0, -1), -90 },
                { "Right angle r4 ccw", new Vector2(0, -1), new Vector2(1, 0), -90 },

                { "180 degrees", new Vector2(0, 1), new Vector2(0, -1), 180 },
                { "45 degrees", new Vector2(0, 1), new Vector2(1, 1), 45 },
                { "135 degrees", new Vector2(0, 1), new Vector2(-1, -1), -135 },
            };
    }

    @Test
    @Parameters(method = "findAngleBetweenTwoVectorsParam")
    public void testFindAngleBetweenTwoVectors(String description, Vector2 v1, Vector2 v2, double angle)
    {
        float angleInRad = Vector2Utils.findAngleBetweenTwoVectors(v1, v2);
        double angleInDeg = Math.toDegrees(angleInRad);
        String message = String.format("%s {%s - %s}: %.2f", description, v1, v2, angle);

        assertThat(message, angleInDeg, is(closeTo(angle, 0.001)));
    }
}

/* Copyright (c) 2013 Luke Quinane
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

/**
 * {@link Vector2} utilities.
 */
public class Vector2Utils
{
    public static float findAngleBetweenTwoVectors(Vector2 v1, Vector2 v2)
    {
        // from: http://forums.xna.com/forums/p/6035/31831.aspx#31831

        // turn vectors into unit vectors
        v1 = v1.cpy().nor();
        v2 = v2.cpy().nor();

        double angle = Math.acos(v1.dot(v2));

        // if no noticable rotation is available return zero rotation this way we avoid Cross product artifacts
        if (Math.abs(angle) < 0.0001)
        {
            return 0;
        }

        angle *= signal(v1, v2);

        return (float)angle;
    }

    private static int signal(Vector2 v1, Vector2 v2)
    {
        return (v1.y * v2.x - v2.y * v1.x) > 0 ? 1 : -1;
    }

    public static boolean equalsWithin(Vector2 v1, Vector2 v2, float margin)
    {
        return (v1.cpy().sub(v2).len()) < margin;
    }
}

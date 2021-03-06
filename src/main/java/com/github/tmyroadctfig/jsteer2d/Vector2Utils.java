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
        // turn vectors into unit vectors
        v1 = v1.cpy().nor();
        v2 = v2.cpy().nor();

        float angle1 = v1.angle();
        float angle2 = v2.angle();

        float result = angle1 - angle2;

        if (result > 180)
        {
            result -= 360;
        }
        else if (result <= -180)
        {
            result += 360;
        }

        return (float) Math.toRadians(result);
    }

    public static boolean equalsWithin(Vector2 v1, Vector2 v2, float margin)
    {
        return (v1.cpy().sub(v2).len()) < margin;
    }
}

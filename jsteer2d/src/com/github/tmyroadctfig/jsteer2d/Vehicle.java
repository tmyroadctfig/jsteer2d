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
 * The interface for a vehicle that steering can direct.
 */
public interface Vehicle extends MovingObstacle
{
    /**
     * Gets the current direction of the vehicle (as a unit vector).
     *
     * @return the direction.
     */
    Vector2 getDirection();

    /**
     * Gets the maximum forward thrust.
     *
     * @return the maximum thrust.
     */
    float getMaximumThrust();

    /**
     * Gets the maximum reverse thrust.
     *
     * @return the maximum reverse thrust.
     */
    float getMaximumReverseThrust();

    /**
     * Gets the rotation rate in radians / second.
     *
     * @return the rotation rate in radians / second.
     */
    float getRotationRate();

    /**
     * Get the maximum speed the vehicle can move at.
     *
     * @return the maximum speed the vehicle can move at, or {@code null} for no maximum.
     */
    Float getMaximumSpeed();
}
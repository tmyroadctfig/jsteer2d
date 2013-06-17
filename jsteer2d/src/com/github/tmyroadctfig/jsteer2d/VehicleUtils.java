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
 * {@link Vehicle} utilities.
 */
public class VehicleUtils
{
    /**
     * Gets the stopping distance for the vehicle.
     *
     * @param vehicle the vehicle to get the stopping distance for.
     * @return the stopping distance.
     */
    public static float getStoppingDistance(Vehicle vehicle)
    {
        // From: v^2 = u^2 + 2as, and since v = 0:
        // s = u^2 / 2a
        return vehicle.getVelocity().len2() / 2 * vehicle.getMaximumReverseThrust();
    }

    /**
     * Checks if the target position is ahead of the vehicle.
     *
     * @param vehicle the vehicle to check.
     * @param target the target to check.
     * @return {@code true} if in front.
     */
    public static boolean isAhead(Vehicle vehicle, Vector2 target)
    {
        return isAhead(vehicle, target, 0.707f);
    }

    /**
     * Checks if the target position is ahead of the vehicle.
     *
     * @param vehicle the vehicle to check.
     * @param target the target to check.
     * @param cosineThreshold the threshold to check within.
     * @return {@code true} if in front.
     */
    public static boolean isAhead(Vehicle vehicle, Vector2 target, float cosineThreshold)
    {
        Vector2 targetDirection = target.cpy().sub(vehicle.getPosition());
        targetDirection.nor();
        return vehicle.getDirection().dot(targetDirection) > cosineThreshold;
    }

    /**
     * Checks if the target position is behind the vehicle.
     *
     * @param vehicle the vehicle to check.
     * @param target the target to check.
     * @return {@code true} if behind.
     */
    public static boolean isBehind(Vehicle vehicle, Vector2 target)
    {
        return isBehind(vehicle, target, -0.707f);
    }

    /**
     * Checks if the target position is behind the vehicle.
     *
     * @param vehicle the vehicle to check.
     * @param target the target to check.
     * @param cosineThreshold the threshold to check within.
     * @return {@code true} if behind.
     */
    public static boolean isBehind(Vehicle vehicle, Vector2 target, float cosineThreshold)
    {
        Vector2 targetDirection = target.cpy().sub(vehicle.getPosition());
        targetDirection.nor();
        return vehicle.getDirection().dot(targetDirection) < cosineThreshold;
    }
}

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
 * A class for helping calculate steering related things.
 */
public class SteeringHelper
{
    public static Vector2 pursue(MovingObstacle vehicle, MovingObstacle quarry)
    {
        // How parallel is the quarry velocity compared to the vehicle velocity
        // 1 is parallel, 0 is perpendicular, -1 anti-parallel
        float parallel = quarry.getVelocity().dot(vehicle.getVelocity());

        if (parallel < -0.5)
        {
            return seek(vehicle.getPosition(), quarry.getPosition());
        }

        float predictionTime = 1; // MathHelper.SmoothStep(-1, 1, parallel);

        Vector2 predictedQuarryPosition = quarry.getPosition().cpy().add(quarry.getVelocity().cpy().mul(predictionTime));

        float distanceToQuarry = vehicle.getPosition().cpy().sub(quarry.getPosition()).len();

        //if (vehicle.Velocity.Length() > distanceToQuarry)
        //{
        //    predictedQuarryPosition = quarry.Position;
        //}

        return seek(vehicle.getPosition(), predictedQuarryPosition);
    }

    /**
     * Steers towards a target.
     *
     * @param position the current position.
     * @param target the target position.
     * @return the direction.
     */
    public static Vector2 seek(Vector2 position, Vector2 target)
    {
        Vector2 desiredDirection = target.cpy().sub(position);
        return desiredDirection;
    }

    /**
     * Steers away from a target.
     *
     * @param position the current position.
     * @param target the target position.
     * @return the direction.
     */
    public static Vector2 flee(Vector2 position, Vector2 target)
    {
        Vector2 desiredDirection = seek(position, target).mul(-1);
        return desiredDirection;
    }
}

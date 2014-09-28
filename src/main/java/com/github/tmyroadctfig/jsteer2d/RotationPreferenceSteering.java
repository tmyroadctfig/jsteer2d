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
 * A steering strategy that preferences rotation over thrust.
 */
public class RotationPreferenceSteering implements GetSteeringComponents
{
    /**
     * The angle allowed before only rotation will be applied (in radians). E.g. don't only rotate if +/- 15 deg. of the
     * target.
     */
    private float nonRotationWindow;

    public RotationPreferenceSteering()
    {
        nonRotationWindow = (float) Math.toRadians(15);
    }

    @Override
    public SteeringComponents getComponents(Vehicle vehicle, String steeringObjective, Vector2 steeringForce,
                                            float elapsedTime)
    {
        float rotation = Vector2Utils.findAngleBetweenTwoVectors(vehicle.getDirection(), steeringForce);

        float maxRotation = vehicle.getRotationRate() * elapsedTime;
        float clampedRotation = rotation;

        if (Math.abs(rotation) > maxRotation)
        {
            clampedRotation = MathHelper.clamp(rotation, -maxRotation, maxRotation);
        }

        Vector2 normalizedSteeringForce = steeringForce.cpy();
        normalizedSteeringForce.nor();

        float thrust = 0;

        float nonRotationWindowFraction = nonRotationWindow * elapsedTime;
        if (Math.abs(rotation) < nonRotationWindowFraction || Math.abs(rotation - Math.PI) < nonRotationWindowFraction)
        {
            thrust = vehicle.getDirection().dot(normalizedSteeringForce);

            // How parallel is the steering force compared to the vehicle direction
            // 1 is parallel, 0 is perpendicular, -1 anti-parallel
            float parallel = vehicle.getDirection().dot(normalizedSteeringForce);

            if (parallel > 0)
            {
                thrust *= vehicle.getMaximumThrust();
            }
            else
            {
                thrust *= vehicle.getMaximumReverseThrust();
            }

            thrust *= elapsedTime;
        }
        return new SteeringComponents(steeringObjective, steeringForce, normalizedSteeringForce, clampedRotation,
                thrust);
    }

    @Override
    public SteeringComponents arriveAtImpl(Vehicle vehicle, float distanceToTarget, float stoppingDistance,
                                           Vector2 steeringForce, float elapsedTime)
    {
        SteeringComponents components = getComponents(vehicle, "Arrive at", steeringForce, elapsedTime);

        float rampedSpeed = vehicle.getMaximumSpeed() * distanceToTarget / stoppingDistance;

        steeringForce = steeringForce.cpy().nor();
        if (vehicle.getVelocity().dot(steeringForce) > rampedSpeed)
        {
            components.setThrust(-0.01f);
        }

        return components;
    }
}

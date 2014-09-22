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
 * The components returned from a steering operation.
 */
public class SteeringComponents
{
    ///////////////////////////////////////////////////////////////////////////
    // Fields

    /**
     * A instance to represent no steering force.
     */
    public static SteeringComponents NO_STEERING = new SteeringComponents("No steering", Float.NaN, Float.NaN);

    /**
     * A tag describing the objective of the steering force (for debugging). E.g. "Flock: cohesion", "No steering", etc.
     */
    private String steeringObjective;

    /**
     * The target position that relates to the steering force. E.g. the destination point the steering is aiming for.
     */
    private Vector2 steeringTarget;

    /**
     * The original steering force.
     */
    private Vector2 steeringForce;

    /**
     * The thrust component. If the value is negative then deceleration is required.
     */
    private float thrust;

    /**
     * The rotation quantity.
     */
    private float rotation;

    ///////////////////////////////////////////////////////////////////////////
    // Constructors

    public SteeringComponents(String steeringObjective, float rotation, float  thrust)
    {
        this(steeringObjective, null, null, rotation, thrust);
    }

    public SteeringComponents(String steeringObjective, Vector2 steeringTarget, Vector2 steeringForce, float rotation,
                              float thrust)
    {
        this.steeringObjective = steeringObjective;
        this.steeringTarget = steeringTarget;
        this.steeringForce = steeringForce;
        this.thrust = thrust;
        this.rotation = rotation;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods

    /**
     * Checks whether the values of the steering components are valid.
     *
     * @return {@code true} if valid.
     */
    public boolean isValid()
    {
        return !Float.isNaN(thrust) && !Float.isNaN(rotation);
    }

    public float getThrust()
    {
        return thrust;
    }

    public void setThrust(float thrust)
    {
        this.thrust = thrust;
    }

    public float getRotation()
    {
        return rotation;
    }

    public Vector2 getSteeringTarget()
    {
        return steeringTarget;
    }

    @Override
    public String toString()
    {
        return String.format("steering:[rotation:%.2f thrust:%.2f]  - %s", rotation, thrust, steeringObjective);
    }
}
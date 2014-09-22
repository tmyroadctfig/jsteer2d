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

/**
 * The interface for a potential collision detector.
 */
public interface PotentialCollisionDetector
{
    /**
     * Finds the nearest obstacle that will potentially collide with the vehicle.
     *
     * @param vehicle the vehicle that detection is being performed for.
     * @param obstacles the possible obstacles.
     * @param detectionPeriod the time window to perform detection in (in milliseconds). E.g. 500ms from current position.
     * @return the nearest potential obstacle or null if no obstacles are in the vehicle's path.
     */
    Obstacle findNearestPotentialCollision(Vehicle vehicle, Iterable<Obstacle> obstacles, float detectionPeriod);
}
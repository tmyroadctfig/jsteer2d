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
 * Provides vehicle steering.
 */
public class Steering
{
    /**
     * The vehicle steering is working on.
     */
    protected Vehicle vehicle;

    /**
     * The utility for getting the steering components.
     */
    protected GetSteeringComponents getSteeringComponents;

    /**
     * The potential collision detector. Used for obstacle avoidance.
     */
    protected PotentialCollisionDetector potentialCollisionDetector;

    /**
     * The maximum distance between this vehicle and another before the other is not considered in flocking
     * calculations.
     */
    protected float maximumBoidDistance;

    /**
     * The minimum distance between this vehicle and another before this vehicle will steer away slightly.
     */
    protected float minimumBoidDistance;

    /**
     * The distance between this vehicle and another before this vehicle will steer towards the other slightly.
     */
    protected float boidCohesionDistance;

    /**
     *  The avoidance factor. If set to 1.1f then the steering will attempt to place 10% of the ship radius between the
     *  vehicle and the obstacle.
     */
    protected float avoidanceFactor;

    /**
     * Creates a new instance.
     *
     * @param vehicle the vehicle to use.
     * @param getSteeringComponents the utility for getting the steering components.
     */
    public Steering(Vehicle vehicle, GetSteeringComponents getSteeringComponents)
    {
        this(vehicle, getSteeringComponents, null);
    }

    /**
     * Creates a new instance.
     *
     * @param vehicle the vehicle to use.
     * @param getSteeringComponents the utility for getting the steering components.
     * @param potentialCollisionDetector the potential collision detector.
     */
    public Steering(Vehicle vehicle, GetSteeringComponents getSteeringComponents,
                    PotentialCollisionDetector potentialCollisionDetector)
    {
        this.vehicle = vehicle;
        this.getSteeringComponents = getSteeringComponents;
        this.potentialCollisionDetector = potentialCollisionDetector;

        avoidanceFactor = 1.1f;

        maximumBoidDistance = 400;
        minimumBoidDistance = 50;
        boidCohesionDistance = 100;
    }

    /**
     * Seeks to a target point.
     *
     * @param target the target to seek to.
     * @param elapsedTime the elapsed time.
     * @return the steering components.
     */
    public SteeringComponents seek(Vector2 target, float elapsedTime)
    {
        Vector2 estimatedPosition = vehicle.getPosition().cpy().add(vehicle.getVelocity().cpy().mul(elapsedTime));
        Vector2 steeringForce = SteeringHelper.seek(estimatedPosition, target);

        return getComponents("Seek", steeringForce, elapsedTime);
    }

    /**
     * Pursues another vehicle.
     *
     * @param target the target to pursue.
     * @param elapsedTime the elapsed time.
     * @return the steering components.
     */
    public SteeringComponents pursue(Vehicle target, float elapsedTime)
    {
        Vector2 estimatedPosition = vehicle.getPosition().cpy().add(vehicle.getVelocity().cpy().mul(elapsedTime));
        Vector2 estimatedTargetPosition = vehicle.getPosition().cpy().add(target.getVelocity().cpy().mul(elapsedTime));
        Vector2 steeringForce = SteeringHelper.seek(estimatedPosition, estimatedTargetPosition);

        return getComponents("Pursue", steeringForce, elapsedTime);
    }

    /**
     * Evades another vehicle.
     *
     * @param target the target to evade.
     * @param elapsedTime the elapsed time.
     * @return the steering components.
     */
    public SteeringComponents evade(Vehicle target, float elapsedTime)
    {
        Vector2 estimatedPosition = vehicle.getPosition().cpy().add(vehicle.getVelocity().cpy().mul(elapsedTime));
        Vector2 estimatedTargetPosition = vehicle.getPosition().cpy().add(target.getVelocity().cpy().mul(elapsedTime));
        Vector2 steeringForce = SteeringHelper.flee(estimatedPosition, estimatedTargetPosition);

        return getComponents("Evade", steeringForce, elapsedTime);
    }

    /**
     * Steers to avoid the given obstacles.
     *
     * @param obstacles the obstacles to avoid.
     * @param detectionPeriod The time window to perform detection in (in milliseconds). E.g. 500ms from current position.
     * @param elapsedTime the elapsed time.
     * @return the steering components.
     */
    public SteeringComponents avoidObstacles(Iterable<Obstacle> obstacles, float detectionPeriod, float elapsedTime)
    {
        Obstacle nearestObstacle = potentialCollisionDetector
                .findNearestPotentialCollision(vehicle, obstacles, detectionPeriod);

        if (nearestObstacle != null)
        {
            // Find the steering direction
            Vector2 obstacleOffset = vehicle.getPosition().cpy().sub(nearestObstacle.getPosition());
            Vector2 parallel = vehicle.getDirection().cpy().mul(obstacleOffset.dot(vehicle.getDirection()));
            Vector2 perpendicular = obstacleOffset.sub(parallel);

            // Offset to be past the obstacle's edge
            perpendicular.nor();
            Vector2 seekTo = nearestObstacle.getPosition().cpy().add(perpendicular.mul(nearestObstacle.getRadius() + (vehicle.getRadius() * avoidanceFactor)));

            return getComponents("Avoid obstacle", seekTo, elapsedTime);
        }

        return SteeringComponents.NO_STEERING;
    }

//    /// <summary>
//    /// Steers to stay aligned and cohesive to the flock.
//    /// </summary>
//    /// <param name="flock">The flock of vehicles to stay with.</param>
//    /// <param name="elapsedTime">The elapsed time.</param>
//    /// <returns>The steering force.</returns>
//    public SteeringComponents flock(Iterable<Vehicle> flock, float elapsedTime)
//    {
//        var closeBoids = FindCloseBoids(flock);
//
//        if (!closeBoids.Any())
//        {
//            // Found no other boids!
//            return SteeringComponents.NoSteering;
//        }
//
//        if (closeBoids.First().DistanceSquared < MinimumBoidDistance)
//        {
//            // Steer to separate
//            return SeparationImpl(closeBoids, elapsedTime);
//        }
//        else if (closeBoids.First().DistanceSquared > BoidCohesionDistance)
//        {
//            // Steer for cohension
//            return CohesionImpl(closeBoids, elapsedTime);
//        }
//        else
//        {
//            // Steer for alignment
//            return AlignmentImpl(closeBoids, elapsedTime);
//        }
//    }
//
//    /// <summary>
//    /// Steers to separate from neighbours.
//    /// </summary>
//    /// <param name="neighbours">The vehicles to separate from.</param>
//    /// <param name="elapsedTime">The elapsed time.</param>
//    /// <returns>The steering force.</returns>
//    public SteeringComponents separate(Iterable<Vehicle> neighbours, float elapsedTime)
//    {
//        var closeBoids = FindCloseBoids(neighbours);
//
//        if (closeBoids.Any() && closeBoids.First().DistanceSquared < MinimumBoidDistance)
//        {
//            // Steer to separate
//            return SeparationImpl(closeBoids, elapsedTime);
//        }
//
//        return SteeringComponents.NO_STEERING;
//    }
//
//    /// <summary>
//    /// Separates from nearby neighbours.
//    /// </summary>
//    /// <param name="closeBoids">The close boids.</param>
//    /// <param name="elapsedTime">The elapsed time.</param>
//    /// <returns>The steering force.</returns>
//    protected SteeringComponents separationImpl(Iterable<BoidDistance> closeBoids, float elapsedTime)
//    {
//        Vector2 direction = closeBoids.Aggregate(Vector2.Zero, (p, b) => p + b.Boid.Position);
//        direction /= closeBoids.Count();
//        direction.Normalize();
//
//        return getComponents("Flocking: separation", direction, elapsedTime);
//    }
//
//    /// <summary>
//    /// Steers for cohesion.
//    /// </summary>
//    /// <param name="closeBoids">The close boids.</param>
//    /// <param name="elapsedTime">The elapsed time.</param>
//    /// <returns>The steering force.</returns>
//    protected SteeringComponents cohesionImpl(Iterable<BoidDistance> closeBoids, float elapsedTime)
//    {
//        Vector2 direction = closeBoids.Aggregate(Vector2.Zero, (p, b) => p + b.Boid.Position);
//
//        direction /= closeBoids.Count();
//        direction -= vehicle.Position;
//        direction.Normalize();
//
//        return getComponents("Flocking: cohesion", direction, elapsedTime);
//    }
//
//    /// <summary>
//    /// Steers for alignment.
//    /// </summary>
//    /// <param name="closeBoids">The close boids.</param>
//    /// <param name="elapsedTime">The elapsed time.</param>
//    /// <returns>The steering force.</returns>
//    protected SteeringComponents alignmentImpl(Iterable<BoidDistance> closeBoids, float elapsedTime)
//    {
//        Vector2 direction = closeBoids.Aggregate(Vector2.Zero, (p, b) => p + b.Boid.Direction);
//
//        direction /= closeBoids.Count();
//        direction -= vehicle.Direction;
//        direction.Normalize();
//
//        return getComponents("Flocking: alignment", direction, elapsedTime);
//    }
//
//    /// <summary>
//    /// Finds close boids for flocking. Any boids further than "MaximumBoidDistance" will be filtered.
//    /// </summary>
//    /// <param name="flock">The flock to find close boids in.</param>
//    /// <returns>The close boids ordered by closest boid first.</returns>
//    public IEnumerable<BoidDistance> findCloseBoids(IEnumerable<IVehicle> flock)
//    {
//        // Filter out any boids that are too distance
//        var maxBoidDistanceSquared = MaximumBoidDistance * MaximumBoidDistance;
//
//        var closeBoids = flock
//                .Where(b => !vehicle.Equals(b))
//        .Select(b => new BoidDistance
//        {
//            Boid = b,
//                    DistanceSquared = (b.Position - vehicle.Position).LengthSquared()
//        });
//
//        closeBoids = closeBoids
//                .OrderBy(bd => bd.DistanceSquared);
//
//        closeBoids = closeBoids
//                .Where(bd => bd.DistanceSquared < MaximumBoidDistance);
//
//        closeBoids = closeBoids
//                .Take(5);
//
//        return closeBoids;
//    }

    /**
     * Arrives at a target point, stopping on arrival.
     *
     * @param target the target to arrive at.
     * @param elapsedTime the elapsed time.
     * @return the steering components.
     */
    public SteeringComponents arriveAt(Vector2 target, float elapsedTime)
    {
        Vector2 estimatedPosition = vehicle.getPosition().cpy().add(vehicle.getVelocity().cpy().mul(elapsedTime));
        float distanceToTarget = target.cpy().sub(estimatedPosition).len();
        float stoppingDistance = VehicleUtils.getStoppingDistance(vehicle);

        if (distanceToTarget > stoppingDistance)
        {
            Vector2 steeringForce = SteeringHelper.seek(estimatedPosition, target);
            return getComponents("Arrive at: seek", steeringForce, elapsedTime);
        }
        else
        {
            return getSteeringComponents.arriveAtImpl(vehicle, distanceToTarget, stoppingDistance,
                    SteeringHelper.seek(estimatedPosition, target), elapsedTime);
        }
    }

    /**
     * Gets the steering components for the steering force and vehicle.
     *
     * @param steeringObjective a string describing the steering force.
     * @param steeringForce the steering force.
     * @param elapsedTime the elapsed time.
     * @return the steering components.
     */
    protected SteeringComponents getComponents(String steeringObjective, Vector2 steeringForce, float elapsedTime)
    {
        return getSteeringComponents.getComponents(vehicle, steeringObjective, steeringForce, elapsedTime);
    }
}

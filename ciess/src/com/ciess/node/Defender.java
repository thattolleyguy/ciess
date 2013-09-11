package com.ciess.node;

import com.badlogic.gdx.math.Vector2;

public class Defender {

	private final float speed;
	private final float initialSize;
	private final Node node;
	private final Vector2[] patrolTargets;

	Vector2 location;
	Vector2 velocity;
	private float currentSize;

	int currentPatrolTargetIndex;
	boolean isHacked;
	Defender hackedTarget;
	int growthSpeed;
	Vector2 targetLocation;
	boolean dead;

	public Defender(Node node, float size, float speed, Vector2... targets) {
		this.patrolTargets = targets;
		this.currentSize = size;
		this.initialSize = size;
		this.speed = speed;
		this.node = node;
		reset();

	}

	public void reset() {
		this.location = patrolTargets[patrolTargets.length - 1].cpy();
		this.currentSize = initialSize;
		this.currentPatrolTargetIndex = 0;
		this.isHacked = false;
		this.hackedTarget = null;
		this.growthSpeed = 2;
		this.dead = false;

		changeTarget();
		updateTargetLocation();
	}

	public float getSize() {
		return currentSize;
	}

	public void update(float deltaTime) {
		handleMovement();
		handleCollision();

		updateTargetLocation();
	}

	/**
	 * Handle collisions which result in changes of directions
	 */
	private void handleCollision() {
		// Check for collision with hacked target.
		if (hackedTarget != null) {
			if (hitHackedTarget()) {
				hackedTarget.currentSize -= growthSpeed;
				this.currentSize--;

			}
			// Find next target
			if (hackedTarget.currentSize <= 0) {
				this.hackedTarget.dead = true;
				this.hackedTarget = null;
				findNextClosestHackedDefender();
			}
			if (this.currentSize <= 0) {
				this.dead = true;
			}

		}
		// Check to see if we should target the next patrol point
		else {
			if (hitPatrolTarget()) {
				changeTarget();
			}
		}
	}

	/**
	 * If we're hacked, attempt to grow. Otherwise move to target;
	 */
	private void handleMovement() {
		if (isHacked && !dead)
			currentSize+=growthSpeed;
		else
			location = location.add(velocity);
	}

	private void updateTargetLocation() {
		if (isHacked)
			targetLocation = location;
		else if (hackedTarget == null)
			targetLocation = patrolTargets[currentPatrolTargetIndex];
		else
			targetLocation = hackedTarget.location;

		setVelocityTarget(targetLocation);
	}

	public void findNextClosestHackedDefender() {
		for (Defender d : node.defenders) {
			if (d.isHacked && d.currentSize > 0)
				this.setDefenderTarget(d);
		}
	}

	private void setVelocityTarget(Vector2 target) {
		velocity = target.cpy().sub(location).nor().scl(speed);
	}

	private void changeTarget() {
		currentPatrolTargetIndex++;
		if (currentPatrolTargetIndex >= patrolTargets.length)
			currentPatrolTargetIndex = 0;
	}

	private boolean hitHackedTarget() {
		if ((Math.abs(hackedTarget.location.x - location.x) < ((this.currentSize + hackedTarget.currentSize) / 2))
				&& (Math.abs(hackedTarget.location.y - location.y) < ((this.currentSize + hackedTarget.currentSize) / 2)))
			return true;
		return false;

	}

	private boolean hitPatrolTarget() {
		if (hackedTarget == null
				&& (Math.abs(patrolTargets[currentPatrolTargetIndex].x
						- location.x) < currentSize / 2)
				&& (Math.abs(patrolTargets[currentPatrolTargetIndex].y
						- location.y) < currentSize / 2))
			return true;
		return false;
	}

	public void setDefenderTarget(Defender closestToTouch) {
		float distToTarget = closestToTouch.location.dst(this.location);
		if (this.hackedTarget == null
				|| this.hackedTarget.location.dst(this.location) > distToTarget) {
			this.hackedTarget = closestToTouch;
			setVelocityTarget(this.hackedTarget.location);
		}

	}

	public void hack() {
		this.isHacked = true;
		this.hackedTarget = null;

	}

}

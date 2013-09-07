package com.ciess.node;

import com.badlogic.gdx.math.Vector2;

public class Defender {
	Vector2 location;
	int currentTarget;
	Vector2[] patrolTargets;
	Vector2 velocity;
	float speed;
	private float size;
	boolean isHacked;
	Defender hackedTarget;
	int growthSpeed = 2;

	public Defender(float size, float speed, Vector2... targets) {
		this.patrolTargets = targets;
		this.location = targets[targets.length - 1].cpy();
		this.size = size;
		this.speed = speed;
		changeTarget();
	}

	public float getSize() {
		return size;
	}

	public void update(float deltaTime) {
		location = location.add(velocity);
		// If this defender is hacked, grow
		if (isHacked)
			size++;
		// If this defender has a target, then check for collision
		else if (hitHackedTarget()) {
			hackedTarget.size-=growthSpeed;
			this.size--;
			if (hackedTarget.size < 0) {
				hackedTarget = null;
				changeTarget();
			}
		}
		// If this defender isn't hacked and has no target, check to see if we
		// need to change target
		else if (hitPatrolTarget()) {
			changeTarget();
		}

		else if (hackedTarget != null)
			setVelocityTarget(hackedTarget.location);

	}

	private void setVelocityTarget(Vector2 target) {
		velocity = target.cpy().sub(location).nor().scl(speed);
	}

	private void changeTarget() {
		currentTarget++;
		if (currentTarget >= patrolTargets.length)
			currentTarget = 0;
		setVelocityTarget(patrolTargets[currentTarget]);
	}

	private boolean hitHackedTarget() {
		if (hackedTarget != null
				&& (Math.abs(hackedTarget.location.x - location.x) < ((this.size + hackedTarget.size) / 2))
				&& (Math.abs(hackedTarget.location.y - location.y) < ((this.size + hackedTarget.size) / 2)))
			return true;
		return false;

	}

	private boolean hitPatrolTarget() {
		if (hackedTarget == null
				&& (Math.abs(patrolTargets[currentTarget].x - location.x) < size / 2)
				&& (Math.abs(patrolTargets[currentTarget].y - location.y) < size / 2))
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
		this.velocity = Vector2.Zero;

	}
}

package com.ciess.node;

import com.badlogic.gdx.math.Vector2;

public class Defender {
	Vector2 location;
	int currentTarget;
	Vector2[] patrolTargets;
	Vector2 velocity;
	float speed;
	private final float size;
	boolean isHacked;
	Defender hackedTarget;

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
		if (hitTarget()) {
			if (hackedTarget != null) {
				hackedTarget = null;
				changeTarget();
			} else {

				changeTarget();
			}

		}

	}

	private void changeTarget() {
		currentTarget++;
		if (currentTarget >= patrolTargets.length)
			currentTarget = 0;
		velocity = patrolTargets[currentTarget].cpy().sub(location).nor()
				.scl(speed);
	}

	private boolean hitTarget() {
		if (hackedTarget != null
				&& (Math.abs(hackedTarget.location.x - location.x) < ((this.size + hackedTarget.size) / 2))
				&& (Math.abs(hackedTarget.location.y - location.y) < ((this.size + hackedTarget.size) / 2)))
			return true;
		if ((Math.abs(patrolTargets[currentTarget].x - location.x) < size / 2)
				&& (Math.abs(patrolTargets[currentTarget].y - location.y) < size / 2))
			return true;
		return false;
	}

	public void setDefenderTarget(Defender closestToTouch) {
		this.hackedTarget = closestToTouch;
		velocity = this.hackedTarget.location.cpy().sub(location).nor()
				.scl(speed);

	}

	public void hack() {
		this.isHacked=true;
		this.velocity=Vector2.Zero;
		
	}
}

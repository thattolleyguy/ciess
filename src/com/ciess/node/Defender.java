package com.ciess.node;

import com.badlogic.gdx.math.Vector2;

public class Defender {
	Vector2 location;
	int currentTarget;
	Vector2[] targets;
	Vector2 velocity;
	float speed = 2.0f;
	private final float size;

	public Defender() {
		location = new Vector2(0, 0);
		targets = new Vector2[] { new Vector2(40, 40), new Vector2(-40, -40),
				new Vector2(-40, 40), new Vector2(40, -40) };
		this.size = 8.0f;

		currentTarget = targets.length;
		changeTarget();
	}

	public Defender(float size, Vector2... targets) {
		this.targets = targets;
		this.location = targets[targets.length-1].cpy();
		this.size = size;
		changeTarget();
	}

	public float getSize() {
		return size;
	}

	public void update(float deltaTime) {
		location = location.add(velocity);
		if (hitTarget()) {
			changeTarget();
		}

	}

	private void changeTarget() {
		currentTarget++;
		if (currentTarget >= targets.length)
			currentTarget = 0;
		velocity = targets[currentTarget].cpy().sub(location).nor().scl(speed);
	}

	private boolean hitTarget() {
		if ((Math.abs(targets[currentTarget].x - location.x) < size / 2)
				&& (Math.abs(targets[currentTarget].y - location.y) < size / 2))
			return true;
		return false;
	}
}

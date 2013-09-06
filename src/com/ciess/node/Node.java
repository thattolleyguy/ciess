package com.ciess.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Node {
	Collection<Defender> defenders;
	public static final float NODE_SIZE = 1000f;

	public Node(int numDefenders, float defenderSize, float defenderSpeed,
			int maxTargets) {
		this.defenders = new ArrayList<Defender>(numDefenders);
		Random r = new Random();
		float defenderLocationSize = NODE_SIZE - defenderSize;
		for (int i = 0; i < numDefenders; i++) {

			int numTargets = maxTargets > 2 ? (r.nextInt(maxTargets - 2) + 2)
					: 2;
			Vector2[] targets = new Vector2[numTargets];
			for (int j = 0; j < numTargets;) {
				Vector2 potentialTarget = new Vector2(
						((r.nextFloat() * defenderLocationSize) - defenderLocationSize / 2),
						((r.nextFloat() * defenderLocationSize) - defenderLocationSize / 2));

				targets[j] = potentialTarget;
				if (j == 0
						|| targets[j - 1].dst2(potentialTarget) > 3 * defenderSize)
					j++;
			}
			this.defenders.add(new Defender(defenderSize, defenderSpeed,
					targets));
		}
	}

	public void update(float deltaTime) {

		// Find hacked defender
		Defender closestToTouch = null;
		if (Gdx.input.justTouched()) {
			Vector2 touchPoint = new Vector2(Gdx.input.getX()
					- Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2
					- Gdx.input.getY());

			float closestDist = 0.0f;
			for (Defender d : defenders) {
				if (touchPoint != null && !d.isHacked) {
					float dist = d.location.dst(touchPoint);
					if (closestToTouch == null || closestDist > dist) {
						closestToTouch = d;
						closestDist = dist;

					}
				}
			}
		}
		if (closestToTouch != null)
			closestToTouch.hack();
		for (Defender d : defenders) {
			d.update(deltaTime);
			if (!d.isHacked && closestToTouch != null)
				d.setDefenderTarget(closestToTouch);
		}

	}
}

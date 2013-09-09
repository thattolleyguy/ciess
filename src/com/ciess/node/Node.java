package com.ciess.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Node {
	Collection<Defender> defenders;
	public static final float GRID_SIZE = 1000f;
	public boolean hasUnhackedDefender = true;
	public boolean hasHackedDefender = false;
	final int initialHacks;
	public int remainingHacks;

	public NodeState nodeState;

	public Node(int numDefenders, float defenderSize, float defenderSpeed,
			int maxTargets) {
		this.defenders = new ArrayList<Defender>(numDefenders);
		this.initialHacks = 4;
		generateNode(numDefenders, defenderSize, defenderSpeed, maxTargets);

		resetNode();
	}

	public void reset() {
		resetDefenders();
		resetNode();
	}

	private void resetNode() {

		remainingHacks = initialHacks;
		nodeState = NodeState.DEFENDING;
		hasUnhackedDefender = true;
		hasHackedDefender = false;
	}

	private void resetDefenders() {
		for (Defender d : defenders) {
			d.reset();
		}
	}

	private void generateNode(int numDefenders, float defenderSize,
			float defenderSpeed, int maxTargets) {
		Random r = new Random();
		float defenderLocationSize = GRID_SIZE - defenderSize;
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
			this.defenders.add(new Defender(this, defenderSize, defenderSpeed,
					targets));
		}
	}

	public void update(float deltaTime) {

		// Find hacked defender
		Defender hackedDefender = hackClosestDefender();
		hasUnhackedDefender = false;
		hasHackedDefender = false;
		for (Defender d : defenders) {
			if (!d.dead) {
				d.update(deltaTime);
				if (!d.isHacked) {
					if (hackedDefender != null) {
						d.setDefenderTarget(hackedDefender);
					}
				}
				hasHackedDefender = hasHackedDefender || d.isHacked;
				hasUnhackedDefender = hasUnhackedDefender || !d.isHacked;
			}
		}
		if (hasUnhackedDefender && !hasHackedDefender && remainingHacks <= 0)
			nodeState = NodeState.DEFENDED;
		if (hasHackedDefender && !hasUnhackedDefender)
			nodeState = NodeState.DEFEATED;

	}

	private Defender hackClosestDefender() {

		if (Gdx.input.justTouched() && remainingHacks > 0) {
			Defender closestToTouch = null;
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
			if (closestToTouch != null) {
				closestToTouch.hack();
				remainingHacks--;
			}
			return closestToTouch;
		}
		return null;
	}

	public static enum NodeState {
		DEFENDING, DEFENDED, DEFEATED, CAPTURED
	}

}

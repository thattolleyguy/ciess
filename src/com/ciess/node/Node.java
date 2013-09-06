package com.ciess.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Node {
	Collection<Defender> defenders;
	public static final float NODE_SIZE = 1000f;

	public Node() {
		defenders = new ArrayList<Defender>();
		defenders.add(new Defender());
	}

	public Node(int numDefenders, float defenderSize, int maxTargets) {
		this.defenders = new ArrayList<Defender>(numDefenders);
		Random r = new Random();
		for (int i = 0; i < numDefenders; i++) {

			int numTargets = maxTargets > 2 ? (r.nextInt(maxTargets - 2) + 2)
					: 2;
			Vector2[] targets = new Vector2[numTargets];
			for (int j = 0; j < numTargets;) {
				Vector2 potentialTarget = new Vector2(
						((r.nextFloat() * NODE_SIZE) - NODE_SIZE / 2),
						((r.nextFloat() * NODE_SIZE) - NODE_SIZE / 2));

				targets[j] = potentialTarget;
				if (j == 0
						|| targets[j - 1].dst2(potentialTarget) > 3 * defenderSize)
					j++;
			}
			this.defenders.add(new Defender(defenderSize, targets));
		}
	}

	public void update(float deltaTime) {
		for (Defender d : defenders) {
			d.update(deltaTime);
		}
	}
}

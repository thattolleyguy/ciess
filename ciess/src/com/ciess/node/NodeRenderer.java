package com.ciess.node;

import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class NodeRenderer {
	OrthographicCamera cam;
	Node node;
	private static final float hackSize = 30f;

	public NodeRenderer(Node node) {
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(0, 0, 0);
		this.node = node;

	}

	public void render(float deltaTime) {
		cam.update();
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(cam.combined);
		renderGrid(renderer);
		renderDefenders(renderer, node.defenders);
	}

	private void renderGrid(ShapeRenderer renderer) {
		float gridWidth = Node.GRID_SIZE / 10;
		float gridRadius = Node.GRID_SIZE / 2;

		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.DARK_GRAY);
		for (float y = -gridRadius; y <= gridRadius; y += gridWidth) {
			renderer.line(-gridRadius, y, gridRadius, y);
			renderer.line(y, -gridRadius, y, gridRadius);
		}
		renderer.end();
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.RED);
		float hackDist = Node.GRID_SIZE / (node.initialHacks - 1);
		float hackX = -(((Gdx.graphics.getWidth() / 2) - gridRadius) / 2)
				- gridRadius;
		for (int i = 0; i < node.remainingHacks; i++) {
			renderer.circle(hackX, gridRadius - (i * hackDist), hackSize);
		}
		renderer.end();

	}

	private void renderDefenders(ShapeRenderer renderer,
			Collection<Defender> defenders) {

		renderer.begin(ShapeType.Filled);

		for (Defender d : defenders) {
			if (!d.dead) {
				float offset = d.getSize() / 2;
				if (d.isHacked) {
					float minX = Math.max(-Node.GRID_SIZE / 2, d.location.x
							- offset);
					float minY = Math.max(-Node.GRID_SIZE / 2, d.location.y
							- offset) + 1;
					float maxX = Math.min(Node.GRID_SIZE / 2, d.location.x
							+ offset) - 1;
					float maxY = Math.min(Node.GRID_SIZE / 2, d.location.y
							+ offset);
					renderer.setColor(Color.RED);
					renderer.triangle(minX, minY, minX, maxY, maxX, maxY);
					renderer.triangle(minX, minY, maxX, maxY, maxX, minY);
				} else {
					renderer.setColor(Color.GRAY);
					renderer.rect(d.location.x - offset, d.location.y - offset,
							d.getSize(), d.getSize());
				}
			}
		}
		renderer.end();
	}

}

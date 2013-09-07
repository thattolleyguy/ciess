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
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.WHITE);

		renderer.rect(-Node.GRID_SIZE / 2, -Node.GRID_SIZE / 2, Node.GRID_SIZE,
				Node.GRID_SIZE);
		renderer.end();
		renderDefenders(renderer, node.defenders);
	}

	private void renderDefenders(ShapeRenderer renderer,
			Collection<Defender> defenders) {

		renderer.begin(ShapeType.Filled);

		for (Defender d : defenders) {
			float offset = d.getSize() / 2;
			if (d.isHacked) {
				float minX = Math.max(-Node.GRID_SIZE / 2, d.location.x
						- offset);
				float minY = Math.max(-Node.GRID_SIZE / 2, d.location.y
						- offset + 1);
				float maxX = Math.min(Node.GRID_SIZE / 2, d.location.x + offset
						- 1);
				float maxY = Math
						.min(Node.GRID_SIZE / 2, d.location.y + offset);
				renderer.setColor(Color.RED);
				renderer.triangle(minX, minY, minX, maxY, maxX, maxY);
				renderer.triangle(minX, minY, maxX, maxY, maxX, minY);
			} else {
				renderer.setColor(Color.GRAY);
				renderer.rect(d.location.x - offset, d.location.y - offset,
						d.getSize(), d.getSize());
			}
		}
		renderer.end();
	}

}

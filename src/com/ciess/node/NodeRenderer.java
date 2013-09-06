package com.ciess.node;

import java.util.Collection;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class NodeRenderer {
	OrthographicCamera cam;
	Node node;

	public NodeRenderer(Node node) {

		cam = new OrthographicCamera(480, 320);
		cam.position.set(0, 0, 0);
		this.node = node;

	}

	public void render(float deltaTime) {
		cam.update();
		renderDefenders(node.defenders);
	}

	private void renderDefenders(Collection<Defender> defenders) {
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(cam.combined);
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.RED);
		for (Defender d : defenders) {
			renderer.rect(d.location.x, d.location.y, d.getSize(), d.getSize());
		}
		renderer.end();
	}
}

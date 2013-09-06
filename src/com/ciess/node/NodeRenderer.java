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

		renderer.rect(-Node.NODE_SIZE / 2, -Node.NODE_SIZE / 2, Node.NODE_SIZE,
				Node.NODE_SIZE);
		renderer.end();
		renderDefenders(renderer, node.defenders);
	}


	private void renderDefenders(ShapeRenderer renderer,
			Collection<Defender> defenders) {

		renderer.begin(ShapeType.Filled);

		for (Defender d : defenders) {
			float offset = d.getSize() / 2;
			renderer.setColor(d.isHacked ? Color.RED : Color.GRAY);
			renderer.rect(d.location.x - offset, d.location.y - offset,
					d.getSize(), d.getSize());
		}
		renderer.end();
	}

}

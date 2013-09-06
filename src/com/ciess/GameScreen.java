package com.ciess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.ciess.node.Node;
import com.ciess.node.NodeRenderer;

public class GameScreen implements Screen {

	Color color = Color.BLACK;
	Node node;
	NodeRenderer renderer;

	public GameScreen() {
		node = new Node(120, 30f, 2f, 8);
		renderer = new NodeRenderer(node);
	}

	@Override
	public void render(float delta) {
		node.update(delta);
		Gdx.graphics.getGL20().glClearColor(color.r, color.g, color.b, 1);
		Gdx.graphics.getGL20().glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		renderer.render(delta);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

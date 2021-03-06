package com.ciess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.ciess.node.Node;
import com.ciess.node.Node.NodeState;
import com.ciess.node.NodeRenderer;

public class GameScreen implements Screen {

	Color color = Color.BLACK;
	Node node;
	NodeRenderer renderer;
	boolean resetPressed = false;
	private final GLCommon gl;

	public GameScreen() {
		node = new Node(16, 60f, 2f, 8);
		renderer = new NodeRenderer(node);

		if (Gdx.graphics.isGL20Available())
			gl = Gdx.graphics.getGL20();
		else if (Gdx.graphics.isGL11Available())
			gl = Gdx.graphics.getGL11();
		else
			gl = Gdx.graphics.getGL10();
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.F5)) {
			resetPressed = true;
		} else if (resetPressed) {
			node.reset();
			color = Color.BLACK;
			resetPressed = false;
		}
		node.update(delta);
		if (node.nodeState == NodeState.DEFEATED)
			color = Color.ORANGE;
		else if (node.nodeState == NodeState.DEFENDED)
			color = Color.BLUE;

		gl.glClearColor(color.r, color.g, color.b, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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

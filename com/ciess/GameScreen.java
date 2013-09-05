package com.ciess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen{

	Color color = Color.BLUE;
	boolean wasTouched = false;
	@Override
	public void render(float delta) {
		
		if(wasTouched!=Gdx.input.isTouched())
		{
			if(wasTouched)
				if(color == Color.BLUE)
					color = Color.BLACK;
				else
					color = Color.BLUE;
			wasTouched = !wasTouched;
		}
		Gdx.graphics.getGL20().glClearColor(color.r, color.g, color.b, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		
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

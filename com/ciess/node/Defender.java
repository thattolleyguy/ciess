package com.ciess.node;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Defender {
	Vector2 location;
	int currentTarget;
	List<Vector2> targets;
	Vector2 velocity;
	
	float size;
	
	public Defender() {
		location = new Vector2(0, 0);
		
		size = 50f;
	}
	
	public void update(float deltaTime)
	{
		location = location.add(velocity);
		
	}
	
}

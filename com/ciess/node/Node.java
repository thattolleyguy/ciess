package com.ciess.node;

import java.util.ArrayList;
import java.util.Collection;

public class Node {
	Collection<Defender> defenders;
		
	public Node() {
		defenders = new ArrayList<Defender>();
		defenders.add(new Defender());
	}
	
	public void update(float deltaTime)
	{
		for(Defender d : defenders)
		{
			d.update(deltaTime);
		}
	}
}

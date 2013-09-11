package com.ciess.client;

import com.ciess.Ciess;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1920, 1080);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new Ciess();
	}
}
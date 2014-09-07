package io.github.deathsbreedgames.spacerun.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import io.github.deathsbreedgames.spacerun.SpaceRun;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(400, 600);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new SpaceRun();
        }
}

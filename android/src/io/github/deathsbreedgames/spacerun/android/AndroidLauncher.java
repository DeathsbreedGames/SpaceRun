package io.github.deathsbreedgames.spacerun.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import io.github.deathsbreedgames.spacerun.SpaceRun;
import io.github.deathsbreedgames.spacerun.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, ActionResolver {
	private GameHelper gameHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useWakelock = true;
		initialize(new SpaceRun(this), config);
		if(gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		gameHelper.onStop();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public boolean getSignedInGPGS() { return gameHelper.isSignedIn(); }
	
	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch(final Exception ex) {
			System.out.println("Error starting login runnable.");
		}
	}
	
	@Override
	public void submitScoreGPGS(int score) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkI74GFyZkUEAIQAQ", score);
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}
	
	@Override
	public void getLeaderboardGPGS(String type) {
		if(gameHelper.isSignedIn()) {
			if(type.equals("highscore")) {
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkI74GFyZkUEAIQAQ"), 100);
			} else if(type.equals("kills")) {
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkI74GFyZkUEAIQAg"), 100);
			}
		} else if(!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}
	
	@Override
	public void getAchievementsGPGS() {
		if(gameHelper.isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
		} else if(!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}
	
	@Override
	public void onSignInFailed() { }
	
	@Override
	public void onSignInSucceeded() { }
}

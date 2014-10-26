package io.github.deathsbreedgames.spacerun.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import io.github.deathsbreedgames.spacerun.SpaceRun;
import io.github.deathsbreedgames.spacerun.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, ActionResolver {
	private GameHelper gameHelper;
	
	public AndroidLauncher() {
		gameHelper = new GameHelper(this);
		gameHelper.enableDebugLog(true, "GPGS");
	}
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useWakelock = true;
		initialize(new SpaceRun(this), config);
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
	public void onActivityResult(int request, int respose, Intent data) {
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
	public void submitScoreGPGS(String type, int score) {
		if(type.equals("highscore") {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkI74GFyZkUEAIQAQ", score);
		} else if(type.equals("kills")) {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkI74GFyZkUEAIQAg", score);
		}
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}
	
	@Override
	public void getLeaderboardGPGS(String type) {
		if(gameHelper.isSignedIn()) {
			if(type.equals("highscore")) {
				startActivityForResult(Games.Leaderboards.getLeadboardIntent(gameHelper.getApiClient(), "CgkI74GFyZkUEAIQAQ"), 100);
			} else if(type.equals("kills")) {
				startActivityForResult(Games.Leaderboards.getLeadboardIntent(gameHelper.getApiClient(), "CgkI74GFyZkUEAIQAg"), 100);
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

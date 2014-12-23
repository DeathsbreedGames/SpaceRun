package io.github.deathsbreedgames.spacerun;

public interface ActionResolver {
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(int score);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS(String type);
	public void getAchievementsGPGS();
}

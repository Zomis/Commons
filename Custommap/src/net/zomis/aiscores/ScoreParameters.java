package net.zomis.aiscores;

public interface ScoreParameters<Params> {
	<E> E getAnalyze(Class<E> clazz);
	Params getParameters();
//	MinesweeperWeapon getWeapon();
//	MinesweeperPlayingPlayer getPlayer();
}

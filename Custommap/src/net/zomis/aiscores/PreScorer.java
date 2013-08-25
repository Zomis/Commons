package net.zomis.aiscores;


public interface PreScorer<Params> {
	Object analyze(Params params);
	void scoringComplete();
}

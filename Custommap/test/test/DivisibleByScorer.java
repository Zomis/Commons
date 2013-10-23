package net.zomis.aiscores.test;

import net.zomis.aiscores.AbstractScorer;
import net.zomis.aiscores.ScoreParameters;
import net.zomis.aiscores.test.TestScore.Params;

public class DivisibleByScorer extends AbstractScorer<Params, Integer> {

	private int mod;

	public DivisibleByScorer(int mod) {
		this.mod = mod;
	}
	@Override
	public boolean workWith(ScoreParameters<Params> scores) {
		return true;
	}

	@Override
	public double getScoreFor(Integer field, ScoreParameters<Params> scores) {
		return (field % this.mod == 0 ? 1 : 0);
	}

	@Override
	public String getName() {
		return super.getName() + this.mod;
	}
}

package net.zomis.aiscores.test;

import net.zomis.aiscores.AbstractScorer;
import net.zomis.aiscores.ScoreParameters;
import net.zomis.aiscores.test.TestScore.Params;

public class CloseToScorer extends AbstractScorer<Params, Integer> {

	private final int value;
	public CloseToScorer(int value) {
		this.value = value;
	}
	@Override
	public boolean workWith(ScoreParameters<Params> scores) {
		return true;
	}

	@Override
	public double getScoreFor(Integer field, ScoreParameters<Params> scores) {
		return -Math.abs(field - this.value);
	}
	@Override
	public String getName() {
		return super.getName() + value;
	}

}

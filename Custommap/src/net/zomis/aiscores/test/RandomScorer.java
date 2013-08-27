package net.zomis.aiscores.test;

import java.util.Random;

import net.zomis.aiscores.AbstractScorer;
import net.zomis.aiscores.ScoreParameters;
import net.zomis.aiscores.test.TestScore.Params;

public class RandomScorer extends AbstractScorer<Params, Integer> {

	private Random	random;

	public RandomScorer() {
		this.random = new Random();
	}
	@Override
	public boolean workWith(ScoreParameters<Params> scores) {
		return true;
	}

	@Override
	public double getScoreFor(Integer field, ScoreParameters<Params> scores) {
		return random.nextDouble();
	}

}

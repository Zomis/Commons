package net.zomis.aiscores;


public abstract class AbstractScorer<Params, Field> extends Scorer {
	public String getName() {
		return this.getClass().getSimpleName();
	}

	public abstract boolean workWith(ScoreParameters<Params> scores);
	public abstract double getScoreFor(Field field, ScoreParameters<Params> scores);
	
	protected static boolean hasProbability(double probability, double d) {
		return Math.abs(probability - d) < 0.00001;
	}
	@Override
	public String toString() {
		return this.getName();
	}
}

package net.zomis.aiscores;


public abstract class AbstractScorer<Params, Field> extends Scorer {
	public String getName() {
		return this.getClass().getSimpleName();
	}

	public abstract boolean workWith(ScoreParameters<Params> scores);
	public abstract double getScoreFor(Field field, ScoreParameters<Params> scores);
	
	/**
	 * Returns the value of getName()
	 * 
	 * @see #getName()
	 */
	@Override
	public final String toString() {
		return this.getName();
	}
}

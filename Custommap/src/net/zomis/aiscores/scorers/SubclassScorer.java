package net.zomis.aiscores.scorers;

import net.zomis.ZomisUtils;
import net.zomis.aiscores.AbstractScorer;
import net.zomis.aiscores.ScoreParameters;

public abstract class SubclassScorer<Params, A, B extends A> extends AbstractScorer<Params, A> {
	
	private final Class<? extends B> clazz;

	public SubclassScorer(Class<? extends B> bClass) {
		this.clazz = bClass;
	}

	@Override
	public final double getScoreFor(A field, ScoreParameters<Params> scores) {
		Class<?> clz = ZomisUtils.classFor(field);
		if (clz == null)
			return 0;
		if (clazz.isAssignableFrom(clz)) {
			double value = scoreSubclass(clazz.cast(field), scores);
//			CustomFacade.getLog().i("Subclass scorer: " + value + " for " + field + ", " + scores);
			return value;
		}
		else return 0;
	}

	@Override
	public boolean workWith(ScoreParameters<Params> scores) {
		return true;
	}
	
	public abstract double scoreSubclass(B cast, ScoreParameters<Params> scores);

}

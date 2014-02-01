package net.zomis.aiscores.scorers;

import net.zomis.ZomisUtils;
import net.zomis.aiscores.AbstractScorer;
import net.zomis.aiscores.ScoreParameters;

public final class IsSubclassScorer<Params, A> extends AbstractScorer<Params, A> {
	public IsSubclassScorer(Class<? extends A> bClass) {
		this.clazz = bClass;
	}

	private final Class<? extends A> clazz;

	@Override
	public final double getScoreFor(A field, ScoreParameters<Params> scores) {
		Class<?> clz = ZomisUtils.classFor(field);
		if (clz == null)
			return clazz == null ? 1 : 0;
		return clazz.isAssignableFrom(clz) ? 1 : 0;
	}

	public String getName() {
		return this.getClass().getSimpleName() + "-" + clazz.getSimpleName();
	}
}

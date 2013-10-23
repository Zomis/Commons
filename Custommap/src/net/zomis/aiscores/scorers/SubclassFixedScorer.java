package net.zomis.aiscores.scorers;

import net.zomis.aiscores.ScoreParameters;

public class SubclassFixedScorer<Params, A, B extends A> extends SubclassScorer<Params, A, B> {
	public SubclassFixedScorer(Class<? extends B> bClass) {
		super(bClass);
	}

	public double scoreSubclass(B cast, ScoreParameters<Params> scores) {
		return 1;
	}

}

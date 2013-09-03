package net.zomis.aiscores;

import java.util.Collection;

public interface ScoreStrategy<Params, Field> {
	Collection<Field> getFieldsToScore(Params params);

	boolean canScoreField(ScoreParameters<Params> parameters, Field field);

}

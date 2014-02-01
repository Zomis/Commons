package net.zomis.aiscores.scorers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.zomis.aiscores.AbstractScorer;
import net.zomis.aiscores.ScoreParameters;

import com.google.common.collect.Lists;

public class MultiplicationScorer<Params, Field> extends AbstractScorer<Params, Field> {

	private Collection<AbstractScorer<Params, Field>> scorers;

	public MultiplicationScorer(AbstractScorer<Params, Field>... scorers) {
		this(Lists.newArrayList(scorers));
	}
	public MultiplicationScorer(List<AbstractScorer<Params, Field>> scorers) {
		this.scorers = new ArrayList<AbstractScorer<Params,Field>>(scorers);
	}
	@SuppressWarnings("unchecked")
	public MultiplicationScorer(AbstractScorer<Params, Field> scorerA, AbstractScorer<Params, Field> scorerB) {
		this(Lists.newArrayList(scorerA, scorerB));
	}
	
	@Override
	public boolean workWith(ScoreParameters<Params> scores) {
		boolean b = true;
		for (AbstractScorer<Params, Field> scorer : scorers)
			b = b && scorer.workWith(scores);
		return b;
	}
	
	@Override
	public double getScoreFor(Field field, ScoreParameters<Params> scores) {
		double ret = 1;
		for (AbstractScorer<Params, Field> scorer : scorers)
			ret = ret * scorer.getScoreFor(field, scores);
		return ret;
	}

}

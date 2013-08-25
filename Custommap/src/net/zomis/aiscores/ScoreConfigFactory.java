package net.zomis.aiscores;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ScoreConfigFactory<Params, Field> {
	private ScoreSet<Params, Field>	scoreSet;
	private final List<PostScorer<Params, Field>> postScorers;
	private final List<PreScorer<Params>> preScorers;

	public ScoreConfigFactory() {
		this.scoreSet = new ScoreSet<Params, Field>();
		this.postScorers = new LinkedList<PostScorer<Params, Field>>();
		this.preScorers = new LinkedList<PreScorer<Params>>();
	}

	public ScoreConfigFactory<Params, Field> withScorer(AbstractScorer<Params, Field> scorer) {
		scoreSet.put(scorer, 1.0);
		return this;
	}
	public ScoreConfigFactory<Params, Field> withScorer(AbstractScorer<Params, Field> scorer, double weight) {
		scoreSet.put(scorer, weight);
		return this;
	}
	public ScoreConfigFactory<Params, Field> multiplyAll(double value) {
		ScoreSet<Params, Field> oldScoreSet = scoreSet;
		scoreSet = new ScoreSet<Params, Field>();
		for (Map.Entry<AbstractScorer<Params, Field>, Double> ee : oldScoreSet.entrySet()) {
			scoreSet.put(ee.getKey(), ee.getValue() * value);
		}

		return this;
	}

	public ScoreConfigFactory<Params, Field> withPost(PostScorer<Params, Field> post) {
		postScorers.add(post);
		return this;
	}

	public ScoreConfig<Params, Field> build() {
		return new ScoreConfig<Params, Field>(this.preScorers, this.postScorers, this.scoreSet);
	}

	public ScoreConfigFactory<Params, Field> withPreScorer(PreScorer<Params> analyzer) {
		this.preScorers.add(analyzer);
		return this;
	}
	
	
	
}

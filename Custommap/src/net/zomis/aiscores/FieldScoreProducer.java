package net.zomis.aiscores;

import java.util.HashMap;
import java.util.Map;

public class FieldScoreProducer<Params, Field> {
	private final ScoreConfig<Params, Field> config;

	private final Map<Class<?>, Object> analyzes;
	private boolean analyzed;

	private ScoreStrategy<Params, Field>	strat;

	public FieldScoreProducer(ScoreConfig<Params, Field> config, ScoreStrategy<Params, Field> strat) {
		this.config = config;
		this.analyzes = new HashMap<Class<?>, Object>();
		this.analyzed = false;
		this.strat = strat;
	}
	
	public synchronized FieldScores<Params, Field> score(Params params) {
		if (!this.analyzed) return null;
		
		FieldScores<Params, Field> scores = new FieldScores<Params, Field>(params, config, strat);
		scores.setAnalyzes(this.analyzes);
		
		scores.determineActiveScorers();
		scores.calculateMoveScores();
//		scores.calculateRankings();
		scores.rankScores();
		scores.postHandle();
		
		for (PreScorer<Params> prescore : config.getPreScorers()) {
			prescore.scoringComplete();
		}
		
		return scores;
	}
	
	public synchronized Map<Params, FieldScores<Params, Field>> scoreAll(Params... params) {
		// TODO: Find the best score for each param, then return the best rank and the param that produced the best rank. Intended to be used for: MFE AI make move (Weapon is part of param)
		Map<Params, FieldScores<Params, Field>> allScores = new HashMap<Params, FieldScores<Params,Field>>();
		for (Params param : params) {
			this.analyze(param); // TODO: Make it possible to only analyze once
			allScores.put(param, this.score(param));
		}
		return allScores;
	}
	
	
	public synchronized boolean analyze(Params param) {
		this.analyzed = false;
		this.analyzes.clear();
		for (PreScorer<Params> preScorers : this.config.getPreScorers()) {
			Object data = preScorers.analyze(param);
			if (data == null) return false;
			this.analyzes.put(data.getClass(), data);
		}
		
		this.analyzed = true;
		return true;
	}

	public ScoreConfig<Params, Field> getConfig() {
		return this.config;
	}

	public synchronized FieldScores<Params, Field> analyzeAndScore(Params params) {
		this.analyze(params);
		return this.score(params);
	}
}

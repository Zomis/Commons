package net.zomis.aiscores;

import java.util.HashMap;
import java.util.Map;

public abstract class FieldScoreProducer<Field, Params> {
	private final ScoreConfig<Params, Field> config;

	private final Map<Class<?>, Object> analyzes;
	private boolean analyzed;

	private Params	params;
	
	public FieldScoreProducer(Params params, ScoreConfig<Params, Field> config) {
		this.params = params;
		this.config = config;
		this.analyzes = new HashMap<Class<?>, Object>();
		this.analyzed = false;
	}
	
	public FieldScores<Params, Field> score() {
		if (!this.analyzed) return null;
		
		FieldScores<Params, Field> scores = newFieldScores(this.params, config);
		scores.setAnalyzes(this.analyzes);
		
		scores.determineActiveScorers();
		scores.calculateMoveScores();
//		scores.calculateRankings();
		scores.rankScores();
		scores.postHandle();
		
		// TODO: Call prescorer scoringComplete method to cleanup
		
		return scores;
	}
	protected abstract FieldScores<Params, Field> newFieldScores(Params params, ScoreConfig<Params, Field> config);

	public boolean analyze() {
		this.analyzed = false;
		for (PreScorer<Params> preScorers : this.config.getPreScorers()) {
			Object data = preScorers.analyze(this.params);
			if (data == null) return false;
			this.analyzes.put(data.getClass(), data);
//			Logger.getLogger(getClass()).info("Analyze data: " + data + " size is now " + this.analyzes.size(), new Exception());
		}
		
//		if (this.config.getAnalyzeMethod().isZomisBasic()) {
//			this.analyze = AnalyzeFactory.analyze(this.player.getMap(), this.config.getAnalyzeMethod().isZomisAdvanced());
//			if (this.analyze == null) return false;
//		}
		this.analyzed = true;
		return true;
	}

	public ScoreConfig<Params, Field> getConfig() {
		return this.config;
	}
}

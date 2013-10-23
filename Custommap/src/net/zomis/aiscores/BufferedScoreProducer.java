package net.zomis.aiscores;

import java.util.Map;

public class BufferedScoreProducer<Params, Field> extends FieldScoreProducer<Params, Field> {

	private FieldScores<Params, Field>	savedScores;
	private Map<Params, FieldScores<Params, Field>>	savedScoreMap;

	public BufferedScoreProducer(ScoreConfig<Params, Field> config,	ScoreStrategy<Params, Field> strat) {
		super(config, strat);
	}

	@Override
	public synchronized FieldScores<Params, Field> score(Params params) {
		savedScores = super.score(params);
		return savedScores;
	}
	
	@Override
	public synchronized Map<Params, FieldScores<Params, Field>> scoreAll(Params... params) {
		savedScoreMap = super.scoreAll(params);
		return savedScoreMap;
	}
	
	public Map<Params, FieldScores<Params, Field>> getSavedScoreMap() {
		return savedScoreMap;
	}
	public FieldScores<Params, Field> getSavedScores() {
		return savedScores;
	}
	
}

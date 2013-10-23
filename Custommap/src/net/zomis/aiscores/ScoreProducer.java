package net.zomis.aiscores;



public interface ScoreProducer<Params, Field> {
	// TODO: Deprecate interface ScoreProducer
	ScoreConfig<Params, Field> getConfig();
	FieldScoreProducer<Params, Field> createScoreProvider();
	ParamAndField<Params, Field> play();
}

package net.zomis.aiscores;



public interface ScoreProducer<Params, Field> {
	ScoreConfig<Params, Field> getConfig();
	FieldScoreProducer<Params, Field> createScoreProvider();
	Params getParams();
	Field play();
}

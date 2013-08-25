package net.zomis.aiscores;


public abstract class PostScorer<Params, Field> extends Scorer {
	public String getName() {
		return "Post-" + this.getClass().getSimpleName();
	}

	public abstract void handle(FieldScores<Params, Field> scores);
	
	protected void force(FieldScore<Field> fscore, double score) {
		if (fscore == null) throw new AssertionError();
		fscore.giveExtraScore(this, score);
	}
	protected void force(FieldScores<Params, Field> scores, Field field, double force) {
		FieldScore<Field> score = scores.getScores().get(field);
		this.force(score, force);
	}
	protected void forceSet(FieldScores<Params, Field> scores, Field field, double force) {
		FieldScore<Field> score = scores.getScores().get(field);
		if (score == null) throw new AssertionError();
		score.giveExtraScore(this, force - score.getScore());
	}
}

package net.zomis.aiscores;

import java.util.HashMap;
import java.util.Map;


public class FieldScore<ScoreField> implements Comparable<FieldScore<ScoreField>> {
	private int rank;
	private double score;
	private final ScoreField field; // should this one exist?
	private Map<Scorer, Double> specificScorers; // TODO: Only use specific scorers if the fieldscores is detailed.
	private double	normalized;
	
	public FieldScore(ScoreField field) {
		this.field = field;
	}

	public FieldScore(FieldScore<ScoreField> copy) {
		this.field = copy.field;
		if (copy.specificScorers != null)
			this.specificScorers = new HashMap<Scorer, Double>(copy.specificScorers);
		this.score = copy.score;
	}
	
	boolean removeScore(Scorer scorer) {
		if (this.specificScorers != null) {
			Double value = this.specificScorers.remove(scorer);
			if (value != null)
				this.score -= value;
			return value != null;
		}
		throw new IllegalStateException();
	}
	
	void addScore(AbstractScorer<?, ?> scorer, double score, double weight) {
		double add = score * weight;
		this.saveScore(scorer, add);
	}
	
	private void saveScore(Scorer scorer, double score) {
		this.score += score;
		if (scorer != null) {
			if (this.specificScorers == null) this.specificScorers = new HashMap<Scorer, Double>();
			this.specificScorers.put(scorer, score);
		}
	}

	void setRank(int rank) {
		this.rank = rank;
	}
	void setNormalized(double normalized) {
		this.normalized = normalized;
	}

	@Override
	public int compareTo(FieldScore<ScoreField> other) {
		return Double.compare(this.score, other.score);
	}

	public double getScore() {
		return this.score;
	}

	public ScoreField getField() {
		return this.field;
	}

	void giveExtraScore(PostScorer<?, ScoreField> scorer, double bonus) {
		this.saveScore(scorer, bonus);
	}
	
	public int getRank() {
		return rank;
	}

	public double getNormalized() {
		return this.normalized;
	}

	public Map<Scorer, Double> getScoreMap() {
		return this.specificScorers;
	}
	
	@Override
	public String toString() {
		return String.format("(%s score %f)", this.field, this.score);
	}
}

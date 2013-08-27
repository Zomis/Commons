package net.zomis.aiscores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import net.zomis.ZomisList;
import net.zomis.ZomisUtils;

public class FieldScores<Params, Field> implements ScoreParameters<Params> {
	private List<AbstractScorer<Params, Field>> activeScorers;
	private final ScoreConfig<Params, Field>	config;
	private Map<Class<?>, Object> analyzes;

	private final Map<Field, FieldScore<Field>> scores = new HashMap<Field, FieldScore<Field>>();
//	@Deprecated
//	private Field[][] ranks;
	private Params	params;
	private ScoreStrategy<Params, Field>	strat;
	
	@SuppressWarnings("unchecked")
	public <E> E getAnalyze(Class<E> clazz) {
//		Logger.getLogger(getClass()).info("Getting analyze for class " + clazz + " size is " + this.analyzes.size());
		return (E) this.analyzes.get(clazz);
	}
	
	FieldScores(Params params, ScoreConfig<Params, Field> config, ScoreStrategy<Params, Field> strat) {
		this.params = params;
		this.config = config;
		this.strat = strat;
	}

	void determineActiveScorers() {
		if (activeScorers != null) throw new IllegalStateException();
		
		activeScorers = new ArrayList<AbstractScorer<Params, Field>>();
			
		for (AbstractScorer<Params, Field> scorer : config.getScorers().keySet()) {
			if (scorer.workWith(this)) {
				activeScorers.add(scorer);
			}
		}
	}

	void calculateMoveScores() {
		for (Field field : this.strat.getFieldsToScore()) {
			if (!this.strat.canScoreField(this, field))
				continue;
			
			FieldScore<Field> fscore = new FieldScore<Field>(field);
//			ProbabilityKnowledge<Field> data = this.analyze == null ? null : this.analyze.getKnowledgeFor(field);
			for (AbstractScorer<Params, Field> scorer : activeScorers) {
//				double lastScore = scorer.getScoreFor(field, data, this);
				double lastScore = scorer.getScoreFor(field, this);
				fscore.addScore(scorer, lastScore, this.config.getScorers().get(scorer));
			}
			scores.put(field, fscore);
		}
	}

	void postHandle() {
		for (PostScorer<Params, Field> post : this.config.getPostScorers()) {
			post.handle(this);
		}
	}

	@Override
	public Params getParameters() {
		return this.params;
	}

//	public Field[][] getRankings() {
//		return this.ranks;
//	}
	public List<List<FieldScore<Field>>> getRankedScores() {
		return rankedScores;
	}

	public Map<Field, FieldScore<Field>> getScores() {
		return this.scores;
	}

	public List<FieldScore<Field>> getBestFields() {
		return ZomisList.getAllExtreme(this.scores.values(), Integer.MIN_VALUE, new _GetValueFieldScore());
	}
	
	private class _GetValueFieldScore implements ZomisList.GetValueInterface<FieldScore<Field>> {
		@Override
		public double getValue(FieldScore<Field> obj) {
			return obj.getScore();
		}
	}

	public FieldScore<Field> getScoreFor(Field field) {
		return this.scores.get(field);
	}

	public double getNormalized(Field field) {
		if (scores.get(field) == null)
			throw new IllegalArgumentException("Field does not exist among scores: " + field);
		SortedSet<Entry<Field, FieldScore<Field>>> sorted = ZomisList.entriesSortedByValues(scores, true);
		double max = sorted.first().getValue().getScore();
		double min = sorted.last().getValue().getScore();
		double range = max - min;
		return ZomisUtils.normalized(scores.get(field).getScore(), min, range);
	}
	
	@Deprecated
	public void normalize() {
	}
	
	public static <ScoreField> boolean listsEquals(List<FieldScore<ScoreField>> a, List<FieldScore<ScoreField>> b) {
		List<ScoreField> temp;
		temp = scoresToFields(a);
		temp.removeAll(b);
		if (!temp.isEmpty()) return false;
		
		temp = scoresToFields(b);
		temp.removeAll(a);
		if (!temp.isEmpty()) return false;
		
		return true;
	}

	public static <ScoreField> List<ScoreField> scoresToFields(List<FieldScore<ScoreField>> scoreList) {
		List<ScoreField> list = new ArrayList<ScoreField>();
		for (FieldScore<ScoreField> score : scoreList) list.add(score.getField());
		return list;
	}

	private List<List<FieldScore<Field>>> rankedScores;
	
	public void rankScores() {
		SortedSet<Entry<Field, FieldScore<Field>>> sorted = ZomisList.entriesSortedByValues(this.scores, true);
		rankedScores = new LinkedList<List<FieldScore<Field>>>();
		if (sorted.isEmpty())
			return;
		double minScore = sorted.last().getValue().getScore();
		double maxScore = sorted.first().getValue().getScore();
		double lastScore = maxScore + 1;
		
//		Logger.getLogger(FieldScores.class).info("Normalizing called, " + lastScore + ", " + minScore + "--" + maxScore + ": " + this, new Exception());
		
		int rank = 0;
		List<FieldScore<Field>> currentRank = new LinkedList<FieldScore<Field>>();
		for (Entry<Field, FieldScore<Field>> score : sorted) {
			if (lastScore != score.getValue().getScore()) {
				lastScore = score.getValue().getScore();
				rank++;
				currentRank = new LinkedList<FieldScore<Field>>();
				rankedScores.add(currentRank);
			}
			score.getValue().setRank(rank);
			double normalized = ZomisUtils.normalized(score.getValue().getScore(), minScore, maxScore - minScore);
//			if (currentRank.size() == 0) Logger.getLogger(FieldScores.class).info("Normalizing " + score.getValue().getScore() + ", " + minScore + "--" + maxScore + ": " + normalized);

			score.getValue().setNormalized(normalized);
			currentRank.add(score.getValue());
		}
	}
	/**
	 * 
	 * @param rank From 1 to getRankLength() 
	 * @return
	 */
	public List<FieldScore<Field>> getRank(int rank) {
		return rankedScores.get(rank - 1);
	}

	public int getRankCount() {
		return rankedScores.size();
	}

	public ScoreConfig<Params, Field> getConfig() {
		return this.config;
	}

	public void setAnalyzes(Map<Class<?>, Object> analyzes) {
//		Logger.getLogger(getClass()).info("Copying analyzes: " + analyzes + " size " + analyzes.size());
		this.analyzes = new HashMap<Class<?>, Object>(analyzes);
	}
}

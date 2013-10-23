package net.zomis.aiscores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.zomis.ZomisList;

public class ScoreUtils {

	public static <A, B> ParamAndField<A, B> pickBest(Map<A, FieldScores<A, B>> scores, Random random) {
		double max = Integer.MIN_VALUE;
		List<FieldScore<B>> maxValues = null;
		A maxParams = null;
		
		for (Entry<A, FieldScores<A, B>> ee : scores.entrySet()) {
			List<FieldScore<B>> rank1 = ee.getValue().getRank(1);
			if (rank1 == null) {
//				CustomFacade.getLog().w("Scores has no rank 1: " + ee.getKey() + " with values " + ee.getValue().getScores());
				continue;
			}
			double score = rank1.get(0).getScore();
			if (score > max) {
				maxValues = rank1;
				max = score;
				maxParams = ee.getKey();
			}
		}
		if (maxValues == null) 
			return null;
		FieldScore<B> chosen = ZomisList.getRandom(maxValues, random);
		return new ParamAndField<A, B>(maxParams, chosen);
	}
	/**
	 * Pick a random best {@link FieldScore} when scoring.<br>
	 * NOTE: Because an underlying {@link HashMap} is used, to guarantee the same randomization, the B parameter must implement hashCode properly!
	 * @param producer The producer for the {@link FieldScores}
	 * @param params The parameter to pass the producer
	 * @param random Random object to use
	 * @return A randomly selected best tuple of the parameter and the {@link FieldScore} for the chosen field.
	 */
	public static <A, B> ParamAndField<A, B> pickBest(FieldScoreProducer<A, B> producer, A params, Random random) {
		FieldScores<A, B> scores = producer.analyzeAndScore(params);
		Map<A, FieldScores<A, B>> map = new HashMap<A, FieldScores<A, B>>();
		map.put(params, scores);
		return pickBest(map, random);
	}
	/**
	 * Pick a random best {@link FieldScore} when scoring.<br>
	 * NOTE: Because an underlying {@link HashMap} is used, to guarantee the same randomization, both the A and B classes must implement hashCode properly!<br>
	 * NOTE: When scoring multiple parameters, the second must exceed in score in order to be chosen, if all scores are equal the first parameter will be selected.
	 * @param producer The producer for the {@link FieldScores}
	 * @param params The parameters to pass the producer
	 * @param random Random object to use
	 * @return A randomly selected best tuple of {@link FieldScore} for the chosen field, connected to the parameter that produced the best score.
	 */
	public static <A, B> ParamAndField<A, B> pickBest(FieldScoreProducer<A, B> producer, A[] params, Random random) {
		Map<A, FieldScores<A, B>> scores = producer.scoreAll(params);
		double max = Integer.MIN_VALUE;
		List<FieldScore<B>> maxValues = null;
		A maxParams = null;
		
		for (Entry<A, FieldScores<A, B>> ee : scores.entrySet()) {
			List<FieldScore<B>> rank1 = ee.getValue().getRank(1);
			if (rank1 == null)
				continue;
			double score = rank1.get(0).getScore();
			if (score > max) {
				maxValues = rank1;
				max = score;
				maxParams = ee.getKey();
			}
		}
//		if (max < MIN_SCORE)
//			return null;
		if (maxValues == null) 
			return null;
		FieldScore<B> chosen = ZomisList.getRandom(maxValues, random);
		return new ParamAndField<A, B>(maxParams, chosen);
	}

}

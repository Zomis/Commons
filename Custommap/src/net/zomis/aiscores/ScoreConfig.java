package net.zomis.aiscores;

import java.util.Collections;
import java.util.List;


public class ScoreConfig<Params, Field> {
	private final ScoreSet<Params, Field> scorers;
	private final List<PostScorer<Params, Field>> postScorers;
	private boolean detailed;
	private final List<PreScorer<Params>> preScorers;
	
	public ScoreConfig(List<PreScorer<Params>> preScorers, List<PostScorer<Params, Field>> postScorers, ScoreSet<Params, Field> scorers) {
		this.postScorers = Collections.unmodifiableList(postScorers);
		this.preScorers = Collections.unmodifiableList(preScorers);
		this.scorers = scorers;
	}

	public List<PostScorer<Params, Field>> getPostScorers() {
		return postScorers;
	}

	public ScoreSet<Params, Field> getScorers() {
		return scorers;
	}

	public boolean isDetailed() {
		return detailed;
	}
	/**
	 * Set whether or not each FieldScore should contain detailed information about how much score the field got from all different scorers (including post scorers)
	 * @param detailed True if detailed, false otherwise.
	 */
	public void setDetailed(boolean detailed) {
		this.detailed = detailed;
	}
	public List<PreScorer<Params>> getPreScorers() {
		return preScorers;
	}
}

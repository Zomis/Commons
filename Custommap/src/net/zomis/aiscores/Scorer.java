package net.zomis.aiscores;

/**
 * Marker for both {@link PostScorer} and {@link AbstractScorer}
 * @author Zomis
 */
public abstract class Scorer {
	public abstract String getName();
	@Override
	public String toString() {
		return this.getName();
	}
}

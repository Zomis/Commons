package net.zomis.aiscores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;


public class ScoreSet<Params, Field> extends TreeMap<AbstractScorer<Params, Field>, Double> {
	private static final long	serialVersionUID	= 5924233965213820945L;

	ScoreSet() {
		super(new ScoreSetComparator());
	}
	
	private static class ScoreSetComparator implements Comparator<AbstractScorer<?, ?>> {
		@Override
		public int compare(AbstractScorer<?, ?> o1, AbstractScorer<?, ?> o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
	
	public List<AbstractScorer<Params, Field>> keysToList() {
		return new ArrayList<AbstractScorer<Params, Field>>(this.keySet()); // Arrays.asList(this.mapKeys);
	}
}

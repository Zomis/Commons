package net.zomis.replays;


public abstract class Replay<T, M> {
	
	protected final T game;
	
	private String initialization;
	private StringBuilder moves;

	private final String moveSep;
	
	public Replay(T game, String moveSeparator) {
		if (game == null)
			throw new NullPointerException("Although tempting, game cannot be null");
		this.game = game;
		this.moveSep = moveSeparator;
		this.moves = new StringBuilder();
	}
	
	public void addMove(M move) {
		if (moves.length() > 0)
			moves.append(moveSep);
		moves.append(getMoveData(move));
	}
	
	public abstract String getMoveData(M move);
	public abstract M getMoveData(String data);
	public abstract String[] extractMoves(String data);
	
	public abstract String getInitialization();
	public abstract void applyInitialization(String data);
	
	public void initialize() {
		this.initialization = getInitialization();
	}
	
	public void initialize(String init) {
		this.initialization = init;
		this.applyInitialization(init);
	}
	
	public void performMoves(String movesData) {
		this.moves = new StringBuilder(movesData);
		for (String str : extractMoves(movesData)) {
			M move = getMoveData(str);
			performMove(move);
		}
	}

	protected abstract void performMove(M move);
	
	/*
	 * MFE
	 * 		Init: Mine positions, starting board, player score
	 * 		Moves: Player + Weapon + Field (x, y)
	 * Cards
	 * 		Init: ResMaps, All locations of all cards
	 * 		Moves: Player + Card (zone x, card index y)
	 * UTTT
	 * 		Init: Starting board (2d array of TTPlayer enum)
	 * 		Moves: Field (x, y)
	 * */
	
	public String getMoveString() {
		return this.moves.toString();
	}
	
	public String getInit() {
		return this.initialization;
	}

}

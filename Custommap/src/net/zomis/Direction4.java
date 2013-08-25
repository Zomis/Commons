package net.zomis;


public enum Direction4 {
	LEFT, RIGHT, UP, DOWN;
	
	public static Direction4 direction(int oldX, int oldY, int newX, int newY) {
		if (oldX > newX) return LEFT;
		else if (oldX < newX) return RIGHT;
		else if (oldY > newY) return UP;
		else if (oldY < newY) return DOWN;
		else return null;
	}

	public char toChar() {
		return this.toString().charAt(0);
	}
}

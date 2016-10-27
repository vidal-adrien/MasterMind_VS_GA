

public enum PegColor {
	
/**
 * Enum to represent possible colors to include in mastermind codes.
 * Only the n first colors can be played in a code that may contain n colors. max 6.
 */

	
//Constants(rank):
RED(1),
BLUE(2),
YELLOW(3),
ORANGE(4),
GREEN(5),
PURPLE(6);

	//Fields:
	private final int rank;

	//Constructor:
	PegColor(int rank) { this.rank = rank; }
	
	//Methods:
	public int getRank() { return rank; }
	
	
	public static PegColor withRank(int rank){
		return PegColor.values()[rank-1];
	}
	
	
	public String toString() {
		switch(this){
			case RED:
				return "R";
			case BLUE:
				return "B";
			case YELLOW:
				return "Y";
			case ORANGE:
				return "O";
			case GREEN:
				return "G";
			case PURPLE:
				return "P";
			default:
				return "Ø";
		}
	}
	
}

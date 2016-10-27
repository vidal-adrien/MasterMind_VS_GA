import java.util.ArrayList;


public class GuessCode extends Code{
	
	/**
	 * Code subclass to be used for the codebreaker's guesses. 	
	 */
	
	//Fields:
	protected int colorScore;
	protected int positionScore;
	
	//Constructors:

	public GuessCode() { 
		super();
		this.colorScore = 0;
		this.positionScore = 0;
	}
	
	public GuessCode(Code code) {
		this.numColors = code.getNumColors();
		this.numPegs = code.getNumPegs();
		this.colors = code.getColors();
	}

	public GuessCode(int numColors, int numPegs) { 
		super(numColors, numPegs);
		this.colorScore = 0;
		this.positionScore = 0;
	}

	public GuessCode(int numColors, int numPegs, ArrayList<PegColor> colors) { 
		super(numColors, numPegs, colors);
		this.colorScore = 0;
		this.positionScore = 0;
	}
	
	public GuessCode(int numColors, int numPegs, ArrayList<PegColor> colors, int colorScore, int positionScore) { 
		super(numColors, numPegs, colors);
		this.colorScore = colorScore;
		this.positionScore = positionScore;
	}
	
	//Methods:
	
	public String toString(){
		return "Code with " + String.valueOf(this.numColors) + " colors and " + String.valueOf(this.numPegs) + " pegs:\n" 
				+ this.colors.toString() + '\n'
				+ "Correct colors: " + this.colorScore + "| Correct positions: " + this.positionScore;
	}
	
	public int getColorScore() { return this.colorScore; }
	
	public void setColorScore(int colorScore) {
		this.colorScore = colorScore;
	}
	
	public int getPositionScore() { return this.positionScore; }

	public void setPositionScore(int positionScore) {
		this.positionScore = positionScore;
	}

}

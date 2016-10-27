import java.util.ArrayList;
import java.util.ListIterator;


public class Code { 
	
	/**
	* Class to create mastermind codes.
	*Codes may be from 4 to 8 pegs long and be composed of 3 to 6 colors.
	*Available colors are the first n colors of enum Color where n is the number of colors to compose with.
	*/	
	
	//Fields:
	protected int numColors;
	protected int numPegs;
	protected ArrayList<PegColor> colors;

	
	//Constructors:

	public Code(){
		this.numPegs = 4;
		this.numColors = 3;
		this.colors = null;	
	}
	
	public Code(int numColors, int numPegs){
		if (numPegs <= 4){ this.numPegs = 4; }
		else if (numPegs >= 8){ this.numPegs = 8; }
		else{ this.numPegs = numPegs; }
		if (numColors <= 3){ this.numColors = 3; }
		else if (numColors >= 6){ this.numColors = 6; }
		else{ this.numColors = numColors; }
		this.colors = null;
	}
	
	public Code(int numColors, int numPegs, ArrayList<PegColor> colors){
		if (numPegs <= 4){ this.numPegs = 4; }
		else if (numPegs >= 8){ this.numPegs = 8; }
		else{ this.numPegs = numPegs; }
		if (numColors <= 3){ this.numColors = 3; }
		else if (numColors >= 6){ this.numColors = 6; }
		else{ this.numColors = numColors; }
		this.colors = new ArrayList<PegColor>();
		ListIterator<PegColor> i = colors.listIterator();
		while(i.hasNext() && i.nextIndex() <= numPegs){
			PegColor tmp = i.next();
			if (tmp.getRank() <= numColors){
				this.colors.add(tmp);
			}
			else{
				this.colors.add(PegColor.withRank(this.numColors));
			}
		}
	}
	
	
	
	//Methods:
	
	public String toString(){
		return "Code with " + String.valueOf(this.numColors) + " colors and " + String.valueOf(this.numPegs) + " pegs:\n" +
				this.colors.toString();
	}
	
	public int getNumPegs(){ return this.numPegs; }
	
	public int getNumColors(){ return this.numColors; }
	
	public ArrayList<PegColor> getColors(){ return this.colors; }
	
	public GuessCode evaluate(GuessCode guess) {
	//Evaluates scores of a guess code by comparing it to itself.
		assert (this.numPegs == guess.getNumPegs());
		assert (this.numColors == guess.getNumColors());
		ArrayList<PegColor> gCols = guess.getColors();
		int cScore = 0;
		int pScore = 0;
		for (int i=0; i<numPegs; i++){
			if (gCols.get(i) == colors.get(i)){ pScore++; }
		}
		ArrayList<PegColor> checked = new ArrayList<PegColor>();
		for ( PegColor i : colors){
			for (PegColor j : gCols){
				if (i == j && !checked.contains(j)){
					checked.add(j);
					cScore++;
				}
			}
		}
		cScore -= pScore;
		if(cScore < 0){ cScore = 0; }
		guess.setColorScore(cScore);
		guess.setPositionScore(pScore);	
		return guess;
	}
	
	
}





import java.util.ArrayList;
import java.util.Random;

public class RandomCodeGen {
	
	/**
	 *Generates random code combinations.
	 */


	//Fields:
	int numPegs;
	int numColors;
	Random rng = new Random();
	
	public RandomCodeGen(int numColors, int numPegs) {
		this.numColors = numColors;
		this.numPegs = numPegs;
	}
	
	public Code randomize() {
		ArrayList<PegColor> colors = new ArrayList<PegColor>();
		for (int i=0; i< this.numPegs; i++){
			colors.add(PegColor.withRank(rng.nextInt(this.numColors) + 1)); //+1 to include last value.
		}
		return new Code(numColors, numPegs, colors);
	}
	
	
}

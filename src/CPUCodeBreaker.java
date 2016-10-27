import java.util.ArrayList;
import java.util.Random;

public class CPUCodeBreaker extends CodeBreaker {
	
	/**
	 * CPU version the code breaker player.
	 * Uses a preconfigured genetic algorithm to make guesses.
	 */

	//Fields:
	protected GeneticAlgorithm algorithm; 
	protected Random rng;
	protected RandomCodeGen rcg; //Random code generator.
	
	//Constructor:
	public CPUCodeBreaker(GeneticAlgorithm algorithm) {
		super();
		this.algorithm = algorithm;
		rng = new Random();
	}

	public GuessCode guess() {
	//CPU uses its GA to make a guess.
		//Ask the algorithm for a sample population of valid codes:
		ArrayList<GuessCode> pop = new ArrayList<GuessCode>();
		if ( algorithm.getNumGen() < 1){ 
			algorithm.initializeGeneration(); 
			algorithm.populate(); 
			pop = algorithm.getPopulation();}
		else{ 
			//We want to be sure to have at least one code in the pop.
			//Can slow down substantially or block the solving with too harsh parameters.
			while ( pop.size() < 1){ 
				algorithm.populate();
				pop = algorithm.getPopulation();
			}
		}
		pop = algorithm.getPopulation();
		//Select one at random to make a guess:
		guess =  pop.get(rng.nextInt(pop.size())); 
		return guess;
	}

	public void setGuess(GuessCode guess){ 
	//Used to get evaluated guess back and feed it to the GA.
		this.guess = guess;
		algorithm.addGene(guess);
	}
	
	
}


import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Random;

public class GeneticAlgorithm {

	/**
	 *Genetic algorithm class.
	 *Modeled from Berghman et al, KBI 0806.
	 */
	
	//Fields:
	ArrayList<GuessCode> genome; //Contains the previous guesses.
	ArrayList<GuessCode> population; //Stores a population of maxPop codes. One of these is randomly played each turn.
	ArrayList<GuessCode> generation; //Stores a generation of maxGen codes.
	Random rng; //Random number generator.
	RandomCodeGen rcg; //Random code generator.
	int numPegs, numColors; //Code rules.
	int popSize, maxGen; //Limits for the GA.
	float crossoverBalance, mutation, permutation, inversion; //Probability rates for code generation mechanics. 
	float posWeight, colWeight; //Weights of the scores in the fitness function.
	float eligibility; //Threshold under which a score is selected in population.
	int numGen; //Counts the generations.

	//Constructors:
	public GeneticAlgorithm(int numColors, int numPegs) {
	// Default constructor for testing.
		this.genome = new ArrayList<GuessCode>();
		this.population = new ArrayList<GuessCode>();
		this.generation = new ArrayList<GuessCode>();
		rng = new Random();
		rcg = new RandomCodeGen(numColors, numPegs);
		this.numPegs = numPegs;
		this.numColors = numColors;
		//Defaults:
		maxGen = 100;
		popSize = 60;
		crossoverBalance = (float) 0.5;
		mutation = (float) 0.03;
		permutation = (float) 0.03;
		inversion = (float) 0.02;
		posWeight = (float) 1.0;
		colWeight = (float) 1.0;
		eligibility = (float) 3.0;
		numGen = 0;
	}
	
	public GeneticAlgorithm(int numColors, int numPegs, int maxGen, int popSize,
			float crossoverBalance, float mutation, float permutation, 
			float inversion, float posWeight, float colWeight, float eligibility){
	//Main constructor handled by the GUI.
		genome = new ArrayList<GuessCode>();
		population = new ArrayList<GuessCode>();
		generation = new ArrayList<GuessCode>();
		this.numPegs = numPegs;
		this.numColors = numColors;
		rng = new Random();
		rcg = new RandomCodeGen(numColors, numPegs);
		this.popSize = popSize;
		this.maxGen = maxGen;
		//Population can't contain more codes than all possible codes:
		if ( popSize > Math.pow(numPegs,numColors) ) { popSize = (int) Math.pow(numPegs,numColors); }
		else { this.popSize = popSize; }
		this.crossoverBalance = crossoverBalance;
		this.mutation = mutation;
		this.permutation = permutation;
		this.inversion = inversion;
		this.posWeight = posWeight;
		this.colWeight = colWeight;
		this.eligibility = eligibility;
	}
	
	
	//Methods:	
	public void addGene(GuessCode gene) { this.genome.add(gene); }
	
	public ArrayList<GuessCode> getGenome() { return this.genome; }
	
	public ArrayList<GuessCode> OnePointCrossover(GuessCode g1, GuessCode g2) {
	// Cuts both sequences at the same random point and swaps the halves.
		assert (numPegs == g1.getNumPegs());
		assert (numColors == g1.getNumColors());
		assert (numPegs == g2.getNumPegs());
		assert (numColors == g2.getNumColors());
		//Random cut point. Ranges from after the first peg to before the last peg.
		int point = rng.nextInt(numPegs - 1) + 1;
		//Colors of given codes:
		ArrayList<PegColor> g1c = g1.getColors();
		ArrayList<PegColor> g2c = g2.getColors();
		//New color arrays:
		ArrayList<PegColor> g1x = new ArrayList<PegColor>();
		ArrayList<PegColor> g2x = new ArrayList<PegColor>();
		//Cut to point and mix: 
		g1x.addAll(g1c.subList(0, 
				point)); //1 to point-1.
		g1x.addAll(g2c.subList(point, 
				numPegs)); //point to end.
		g2x.addAll(g2c.subList(0, 
				point));
		g2x.addAll(g1c.subList(point, 
				numPegs));
		//Create codes from new arrays:
		GuessCode g1n = new GuessCode(numColors, numPegs, g1x);
		GuessCode g2n = new GuessCode(numColors, numPegs, g2x);
		ArrayList<GuessCode> newGenes = new ArrayList<GuessCode>();
		newGenes.add(g1n);
		newGenes.add(g2n);
		return newGenes;
	}

	
	public ArrayList<GuessCode> TwoPointCrossover(GuessCode g1, GuessCode g2) {
	//Cuts both sequences at the same two random points and swaps the middle parts.
		assert (numPegs == g1.getNumPegs());
		assert (numColors == g1.getNumColors());
		assert (numPegs == g2.getNumPegs());
		assert (numColors == g2.getNumColors());
		//Random cut point #1:
		int point1 = rng.nextInt(numPegs - 1) + 1;
		//Random cut point #2:
		int point2 = rng.nextInt(numPegs - 1) + 1;
		while (point1 == point2 && Math.abs(point1 - point2) < 2){ 
		//#1 and #2 must be different and at least 2 apart.
			point2 = rng.nextInt(numPegs - 1) + 1;
		}
		//Order points:
		ArrayList<Integer> points = new ArrayList<Integer>();
		points.add(point1);
		points.add(point2);
		Collections.sort(points); 
		//Colors of given codes:
		ArrayList<PegColor> g1c = g1.getColors();
		ArrayList<PegColor> g2c = g2.getColors();
		//New color arrays:
		ArrayList<PegColor> g1x = new ArrayList<PegColor>();
		ArrayList<PegColor> g2x = new ArrayList<PegColor>();
		//Cut to points and mix: 
		g1x.addAll(g1c.subList(0, 
				points.get(0))); //1 to first point - 1.
		g1x.addAll(g2c.subList(points.get(0), 
				points.get(1))); //first point to second point -1.
		g1x.addAll(g1c.subList(points.get(1), 
				numPegs)); //second point to end.
		g2x.addAll(g2c.subList(0, 
				points.get(0)));
		g2x.addAll(g1c.subList(points.get(0), 
				points.get(1)));
		g2x.addAll(g2c.subList(points.get(1), 
				numPegs));
		//Create codes from new arrays:
		GuessCode g1n = new GuessCode(numColors, numPegs, g1x);
		GuessCode g2n = new GuessCode(numColors, numPegs, g2x);
		ArrayList<GuessCode> newGenes = new ArrayList<GuessCode>();
		newGenes.add(g1n);
		newGenes.add(g2n);
		return newGenes;
	}
	
	
	public GuessCode mutate(GuessCode gene) {
	//Changes randomly the color of a random peg in the sequence.
		assert (numPegs == gene.getNumPegs());
		assert (numColors == gene.getNumColors());
		ArrayList<PegColor> colors = gene.getColors();
		int pos = rng.nextInt(numPegs);
		PegColor col = PegColor.withRank(rng.nextInt(numColors) + 1);
		colors.set(pos, col);
		GuessCode g2 = new GuessCode(numColors, numPegs, colors);
		return g2;
	}

	
	public GuessCode permute(GuessCode gene) {
	//Swaps the colors of two random positions.
		assert (numPegs == gene.getNumPegs());
		assert (numColors == gene.getNumColors());
		ArrayList<PegColor> colors = gene.getColors();
		//Random position #1:
		int pos1 = rng.nextInt(numPegs);
		//Random position #2:
		int pos2 = rng.nextInt(numPegs);
		while (pos1 == pos2){ //#1 and #2 must be different.
			pos2 = rng.nextInt(numPegs);
		}
		//Get colors:
		PegColor col1 = colors.get(pos1);
		PegColor col2 = colors.get(pos2);
		//Swap:
		colors.set(pos1, col2);
		colors.set(pos2, col1);
		GuessCode g2 = new GuessCode(numColors, numPegs, colors);
		return g2;
	}
	
	
	public GuessCode invert(GuessCode gene) {
	//Cuts the sequence at two random points and inverts the middle part.
		assert (numPegs == gene.getNumPegs());
		assert (numColors == gene.getNumColors());
		ArrayList<PegColor> colors = gene.getColors();
		//Random cut point #1:
		int point1 = rng.nextInt(numPegs - 1) + 1;
		//Random cut point #2:
		int point2 = rng.nextInt(numPegs - 1) + 1;
		while (point1 == point2 && Math.abs(point1 - point2) < 2){ //#1 and #2 must be different.
			point2 = rng.nextInt(numPegs - 1) + 1;
		}
		//Order points:
		ArrayList<Integer> points = new ArrayList<Integer>();
		points.add(point1);
		points.add(point2);
		Collections.sort(points); 
		//Reverse middle part.
		ArrayList<PegColor> mid = new ArrayList<PegColor>( colors.subList(points.get(0),points.get(1)) );
		Collections.reverse(mid);
		//Stitch back together:
		ArrayList<PegColor> colors2 = new ArrayList<PegColor>();
		colors2.addAll(colors.subList(0, points.get(0))); //1 to first point - 1.
		colors2.addAll(mid); //first point to second point -1.
		colors2.addAll(colors.subList(points.get(1), numPegs)); //second point to end.
		GuessCode g2 = new GuessCode(numColors, numPegs, colors2);
		return g2;
	}
	
	
	public void buildGeneration() {
	//Builds a generation from randomly crossing and mutating individuals from the previous generation.
		//Build generation by crossing individuals from the population:
		ArrayList<GuessCode> individuals = new ArrayList<GuessCode>(generation); //shallow copy
		generation = new ArrayList<GuessCode>(); //empty old generation to put new in.
		numGen += 1; //count generations.
		ArrayList<GuessCode> tmp = new ArrayList<GuessCode>();
		while (individuals.size() > 0){
			//Pair random codes and crossover:
			int pos = rng.nextInt(individuals.size());
			GuessCode g1 = individuals.get(pos);
			individuals.remove(pos);
			if (individuals.size() > 0) { //Make sure we can form a pair.
				pos = rng.nextInt(individuals.size());
				GuessCode g2 = individuals.get(pos);
				individuals.remove(pos);
				//Randomly select one point or two point crossover:
				if (rng.nextInt() < crossoverBalance){ tmp.addAll(this.OnePointCrossover(g1, g2)); }
				else { tmp.addAll(this.TwoPointCrossover(g1, g2)); }
			}
			else { tmp.add(g1); }//Just add the last lonely gene and let it mutate.
		}
		//Randomly mutate individuals of the generation:
		ListIterator<GuessCode> g = tmp.listIterator();
		while (g.hasNext()){
			GuessCode m = g.next();
			if (rng.nextInt() >= mutation){ m = this.mutate(m); }
			if (rng.nextInt() >= permutation){ m = this.permute(m); }
			if (rng.nextInt() >= inversion){ m = this.invert(m); }
			// Instead of adding doubles, add random code for diversity:
			while ( generation.contains(m) ){ m = new GuessCode( rcg.randomize() ); }
			generation.add(m); 
		}
		//New generation should have the same size as old population.
	}
	
	
	public void initializeGeneration() {
	//Resets population with unique random codes. Should only be used for generation 1.
		this.generation = new ArrayList<GuessCode>();
		for (int i = 0; i < popSize; i++ ){
			GuessCode newCode = new GuessCode( rcg.randomize() );
			while (generation.contains(newCode)){
				newCode = (GuessCode) rcg.randomize();
			}
			this.generation.add(newCode);
		}
		numGen = 1;
	}
	
	
	public int getNumGen() { return numGen; }
	
	
	public float fitnessFunction(GuessCode gene) {
	//Computes the fitness value of a guess from all previously played guesses (genome);
		int[] posScoreDiff = new int[genome.size()];
		int[] colScoreDiff = new int[genome.size()];;
		for (int i = 0; i < genome.size(); i++){
			GuessCode comp = genome.get(i);
			gene = comp.evaluate(gene);
			posScoreDiff[i] = Math.abs(gene.getPositionScore() - comp.getPositionScore());
			colScoreDiff[i] = Math.abs(gene.getColorScore() - comp.getColorScore());
		}
		int posDiffSum = 0, colDiffSum = 0;
		for (int p : posScoreDiff){ posDiffSum += p; }
		for (int c : colScoreDiff){ colDiffSum += c; }
		return ( posWeight * posDiffSum ) + (colWeight * colDiffSum);
	}
	
	public void populate() {
	//Builds a population by selecting unique individuals from consecutive generations.
		assert (numGen > 0); // generation 0 means that generation field is empty.
		//Seed generations with pop codes:
		generation = population;
		//Fill to limit with random codes to increase diversity.
		while (generation.size() < popSize) {
			generation.add(new GuessCode( rcg.randomize() ) );
		}
		population = new ArrayList<GuessCode>();
		int h = 1;
		while (h <= maxGen && population.size() < popSize / 2 ){
			this.buildGeneration();
			ListIterator<GuessCode> g = generation.listIterator();
			while( g.hasNext() ){
				GuessCode gene = g.next();
				if (this.fitnessFunction(gene) <= eligibility){
					population.add(gene); }
			}
			h ++; }
	}
	
	
	public ArrayList<GuessCode> getPopulation() { return this.population; }
	
	
	public String toString() {
		return "c:" + String.valueOf(numColors) + "p:" + String.valueOf(numPegs) + " codes:\n\n"
				+ "Crossovers: 1p:" + String.valueOf(crossoverBalance) + " | 2p:" + String.valueOf(1 - crossoverBalance) + "\n" 
				+ "maxgen: " + String.valueOf(maxGen) + "\n"
				+ "maxpop: " + String.valueOf(popSize) + "\n"
				+ "Mutations: " + "\t" + String.valueOf(mutation) + "\n"
				+ "Permutations: " + "\t"  + String.valueOf(permutation) + "\n"
				+ "Inversions: " + "\t"  + String.valueOf(inversion) + "\n"
				+ "Generations: " + "\t"  + String.valueOf(numGen);
	}
	
	
}

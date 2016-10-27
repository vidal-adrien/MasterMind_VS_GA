
public class CodeBreaker{
	
	/**
	 * Class to represent the code breaker player.
	 */
	
	//Fields:
	protected GuessCode guess;
	
	//Constructors:
	public CodeBreaker() {
		super();
		this.guess = null;
	}

	
	//Methods:
	public GuessCode guess() { return this.guess; }
	
	public GuessCode getGuess() { return this.guess; }

	public void setGuess(GuessCode guess){ this.guess = guess;	}
	
}


public class Game {
	
	/**
	 * Class to handle the win ad loose conditions or the mastermind game,
	 * as well as the exchange of information between the players.
	 */
	
	//Fields:
	protected CodeMaker maker; 
	protected CodeBreaker breaker;
	protected int numTurns;
	protected int turn;
	protected GameState state;
	protected Code secretCode;

	public Game(CodeMaker maker, CodeBreaker breaker, int numTurns) {
		this.maker = maker;
		this.breaker = breaker;
		this.numTurns = numTurns;
		turn = -1;
		state = GameState.CONTINUE;
	}
	
	public void start(){
		turn = 0;
		secretCode = maker.getSecretCode();
	}
	
	public void playTurn(){
		if ( turn == numTurns -1 ){ state = GameState.TIMEOUT; }
		GuessCode guess = breaker.guess();
		guess = secretCode.evaluate(guess);
		breaker.setGuess(guess);
		if ( guess.getPositionScore() == secretCode.getNumPegs()) { state = GameState.BROKEN; }
		turn ++;
	}

	public GameState getState() { return state; }
	
	public int getTurn() { return turn; }
	
}


public enum GameState {

	/**
	 * Enum to record the state of the game.
	 */
	
//Constants:
CONTINUE, //The secret code hasn't been guessed yet.
BROKEN, //The code has been correctly guessed.
TIMEOUT; //The code breaker is out of guesses.


	public String toString() {
		switch(this){
			case CONTINUE:
				return "Keep playing...";
			case BROKEN:
				return "Code Breaker wins!";
			case TIMEOUT:
				return "Code Maker wins!";
			default:
				return "Keep playing...";
		}
	}
}

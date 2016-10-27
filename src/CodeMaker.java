
public class CodeMaker{
	
	/**
	 * Class to represent the code maker player.
	 */
	
	//Fields:
	protected Code secretCode;
	
	//Constructor:
	public CodeMaker() {
		super();
		secretCode = null;
	}
	

	//Methods:
	public Code getSecretCode() { return this.secretCode; }
	
	public void generateSecretCode(int numColors, int numPegs) {
		RandomCodeGen rnc = new RandomCodeGen(numColors, numPegs);
		this.secretCode = rnc.randomize();
	}
	
	public void setSecretCode (Code secretCode) { this.secretCode = secretCode; }

}

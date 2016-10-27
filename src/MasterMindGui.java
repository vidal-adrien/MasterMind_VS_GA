
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class MasterMindGui extends JFrame{

	/**
	 *GUI for the Mastermind VS Genetic Algorithm Game. 
	 */
	
	
//#################################################################~~Fields~~###########################################################################
	
	final int genWidth = 450;
	final int genHeight = 730;
	
	//Resources:
	final ImageIcon mainIcon = createImageIcon("/images/icon_main.png", 512, 512);
	final ImageIcon geneticsIcon = createImageIcon("/images/icon_genetics.png", 512, 512);
	
	final ImageIcon slot_emptyIcon = createImageIcon("/images/slot_empty.png", 35, 35);
	final ImageIcon slot_redIcon = createImageIcon("/images/slot_red.png", 35, 35);
	final ImageIcon slot_blueIcon = createImageIcon("/images/slot_blue.png", 35, 35);
	final ImageIcon slot_yellowIcon = createImageIcon("/images/slot_yellow.png", 35, 35);
	final ImageIcon slot_orangeIcon = createImageIcon("/images/slot_orange.png", 35, 35);
	final ImageIcon slot_greenIcon = createImageIcon("/images/slot_green.png", 35, 35);
	final ImageIcon slot_purpleIcon = createImageIcon("/images/slot_purple.png", 35, 35);
	
	final ImageIcon clue_posIcon = createImageIcon("/images/clue_pos.png", 15, 15);
	final ImageIcon clue_colIcon = createImageIcon("/images/clue_col.png", 15, 15);
	
	final ImageIcon peg_redIcon = createImageIcon("/images/peg_red.png", 15, 15);
	final ImageIcon peg_blueIcon = createImageIcon("/images/peg_blue.png", 15, 15);
	final ImageIcon peg_yellowIcon = createImageIcon("/images/peg_yellow.png", 15, 15);
	final ImageIcon peg_orangeIcon = createImageIcon("/images/peg_orange.png", 15, 15);
	final ImageIcon peg_greenIcon = createImageIcon("/images/peg_green.png", 15, 15);
	final ImageIcon peg_purpleIcon = createImageIcon("/images/peg_purple.png", 15, 15);
	
	final ImageIcon cancelIcon = createImageIcon("/images/cancel.png", 15, 15);
	
	//Main frame components:
	JMenuBar menu = new JMenuBar();
	Box board = new Box(BoxLayout.Y_AXIS);
	Box leftPanel = new Box(BoxLayout.PAGE_AXIS);
	Box GAStats = new Box(BoxLayout.PAGE_AXIS);
	JLabel GAStatsLabel = new JLabel("<html><font color='white'>Genetic Algorithm:</font></html>");
	JTextPane GAStatsText = new JTextPane();
	Box rightPanel = new Box(BoxLayout.PAGE_AXIS);
	JPanel secretCodePanel = new JPanel();
	Box codeKeyboard =  new Box(BoxLayout.PAGE_AXIS);
	ArrayList<JPanel> guessTabs = new ArrayList<JPanel>();
	ArrayList<JLabel> posClues = new ArrayList<JLabel>();
	ArrayList<JLabel> colClues = new ArrayList<JLabel>();
	ArrayList<Box> clueBoxes = new ArrayList<Box>();
	JPanel pegButtonsPanel = new JPanel();
	ArrayList<JButton> pegButtons = new ArrayList<JButton>();
	JPanel keyboardCodePanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JButton returnButton = new JButton("<html>&#8678<html>");
	JButton okButton = new JButton("Ok");
	JLabel infoLabel = new JLabel("<html><font color='white'>Click new game to start.</font></html>");
	
	//Menu:
	JMenu gameMenu = new JMenu("New Game");
	JMenuItem HvH = new JMenuItem("Challenge a friend");
	JMenuItem HvC = new JMenuItem("Challenge the computer");
	JMenuItem CvH = new JMenuItem("Guess random code");
	JMenuItem CvC = new JMenuItem("CPU guesses random code");
	JMenuItem Quit = new JMenuItem("Quit game");
	
	JButton genetics = new JButton("Genetics");
	
	JLabel colorsText = new JLabel("Colors:");
	JSlider colorsSlider = new JSlider(3, 6, 3);
	JLabel pegsText = new JLabel("Pegs:");
	JSlider pegsSlider = new JSlider(4, 8, 4);
	JSlider turnsSlider = new JSlider(8, 16, 10);
	JLabel turnsText = new JLabel("Turns:");

	//Genetics sliders and components:
	int popMax = 300;
	JLabel popSizeText = new JLabel("Population size:");
	JSlider popSizeSlider = new JSlider( 50, (int) Math.pow(pegsSlider.getValue(), colorsSlider.getValue()), 60 );
	JLabel popSizeVal = new JLabel(String.valueOf(popSizeSlider.getValue()));
	
	JTextPane genInfo = new JTextPane();
	
	JLabel generationText = new JLabel("<html><span style='font-size:11px'>Generations:</span></html>");
	
	JLabel maxGenText = new JLabel("Number of generations per guess:");
	JSlider maxGenSlider = new JSlider(50, 1000, 150);
	JLabel maxGenVal = new JLabel(String.valueOf(maxGenSlider.getValue()));
	
	JLabel crossoverText = new JLabel("Crossover type preference:");
	JSlider crossoverSlider = new JSlider(0, 100, 50); //Values are x100.
	JLabel crossoverVal = new JLabel(String.valueOf((double)crossoverSlider.getValue() / 100));
	JLabel crossoverOPText = new JLabel("1 point");
	JLabel crossoverTPText = new JLabel("2 point");
	
	JLabel mutText = new JLabel("Mutation rate:");
	JSlider mutSlider = new JSlider(0, 100, 3); //Values are x100.
	JLabel mutVal = new JLabel(String.valueOf((double) mutSlider.getValue() / 100));
	
	JLabel perText = new JLabel("Permutation rate:");
	JSlider perSlider = new JSlider(0, 100, 3); //Values are x100.
	JLabel perVal = new JLabel(String.valueOf((double) perSlider.getValue() / 100));
	
	JLabel invText = new JLabel("Inversion rate:");
	JSlider invSlider = new JSlider(0, 100, 2); //Values are x100.
	JLabel invVal = new JLabel(String.valueOf((double) invSlider.getValue() / 100)); 
	
	JLabel fitnessText = new JLabel("<html><span style='font-size:11px'>Fitness:</span></html>");
	
	JLabel eligibilityText = new JLabel("Eligibility of codes:");
	JSlider eligibilitySlider = new JSlider(0, 100, 0); //Values are x10.
	JLabel eligibilityVal = new JLabel(String.valueOf((double) eligibilitySlider.getValue() / 10));
	
	JLabel posWeightText = new JLabel("Weight of orange clues:");
	JSlider posWeightSlider = new JSlider(0, 100, 10); //Values are x10.
	JLabel posWeightVal = new JLabel(String.valueOf((double) posWeightSlider.getValue() / 10));
	
	JLabel colWeightText = new JLabel("Weight of white clues:");
	JSlider colWeightSlider = new JSlider(0, 100, 10); //Values are x10.
	JLabel colWeightVal = new JLabel(String.valueOf((double) colWeightSlider.getValue() / 10));
	
	JPanel genTestPanel = new JPanel();
	JLabel genTestLabel1 = new JLabel("Measure efficiency on");
	JSpinner genTestNumber = new JSpinner(new SpinnerNumberModel(100, 100, 999, 1));
	JLabel genTestLabel2 = new JLabel("games:");
	JButton genTest = new JButton("Go");
	JTextField genAvTurns = new JTextField("N/A", 4);
	JTextField genAvTime = new JTextField("N/A", 4);
	JButton genTestStop = new JButton(cancelIcon);
	
	JPanel genButtons = new JPanel();
	JButton genCancel = new JButton("Cancel");
	JButton genOk = new JButton("Ok");
	
	//Values temporary storage:
	int popSizeTmp = popSizeSlider.getValue();
	int maxGenTmp = maxGenSlider.getValue();
	int crossoverTmp = crossoverSlider.getValue();
	int mutTmp = mutSlider.getValue();
	int perTmp = perSlider.getValue();
	int invTmp = invSlider.getValue();
	int eligibilityTmp = eligibilitySlider.getValue();
	int posWeightTmp = posWeightSlider.getValue();
	int colWeightTmp = colWeightSlider.getValue();
	
	final GeneticsMenu genFrame = new GeneticsMenu(this, false);
	
	//Game instances:
	Game game = null;
	GeneticAlgorithm algorithm = null;
	CodeBreaker breaker = null;
	CodeMaker maker = null;
	Code secretCode = null;
	ArrayList<PegColor> inputColors = new ArrayList<PegColor>();
	
	//CPU workers:
	CPUSolve worker = null;
	CPUSolveMany workerM = null;
	double solveTimer = 0;
	DecimalFormat df = new DecimalFormat("#.###");

	
//###########################################################~~Constructor~~########################################################################
	
	public MasterMindGui() {
		
		super();
		
		//Menu:		
		setJMenuBar(menu);
			menu.add(gameMenu);
				gameMenu.add(HvH);
				gameMenu.add(HvC);
				gameMenu.add(CvH);
				gameMenu.add(CvC);
				gameMenu.add(new JSeparator());
				//----------------
				gameMenu.add(Quit);
				Quit.setEnabled(false); 
			//----------------------------------
			menu.add(new JSeparator( SwingConstants.VERTICAL));
			menu.add(colorsText);
			colorsSlider.setMinorTickSpacing(1);
			colorsSlider.setPaintTicks(true);
			menu.add(colorsSlider);
			colorsSlider.setPreferredSize(new Dimension(100, colorsSlider.getHeight()));
			menu.add(new JSeparator( SwingConstants.VERTICAL));
			//----------------------------------
			menu.add(pegsText);
			pegsSlider.setMinorTickSpacing(1);
			pegsSlider.setPaintTicks(true);
			menu.add(pegsSlider);
			pegsSlider.setPreferredSize(new Dimension(100, pegsSlider.getHeight()));
			menu.add(new JSeparator( SwingConstants.VERTICAL));
			//----------------------------------
			menu.add(turnsText);
			turnsSlider.setMinorTickSpacing(1);
			turnsSlider.setPaintTicks(true);
			menu.add(turnsSlider);
			turnsSlider.setPreferredSize(new Dimension(100,turnsSlider.getHeight()));
			menu.add(new JSeparator( SwingConstants.VERTICAL));
			//----------------------------------
			menu.add(genetics);
				genetics.setIcon(new ImageIcon( geneticsIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT) ));
			
		//Container:
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.setBackground(Color.BLACK);
		
			//Genetic algorithm statistics panel:
			GAStats.setAlignmentX(RIGHT_ALIGNMENT);
				GAStatsLabel.setAlignmentX(LEFT_ALIGNMENT);
				GAStatsLabel.setEnabled(false);
				GAStats.add(GAStatsLabel);
				GAStatsText.setAlignmentX(LEFT_ALIGNMENT);
				leftPanel.setBackground(Color.BLACK);
				GAStatsText.setEditable(false);
				GAStatsText.setContentType("text/html");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
				GAStatsText.setPreferredSize( new Dimension(200, content.getHeight()) ); 
				GAStatsText.setBackground(Color.BLACK);
				GAStats.add(GAStatsText);
			leftPanel.add(GAStats);
			leftPanel.add(new JSeparator());
			
			//Code typing Keyboard:
			codeKeyboard.setAlignmentX(RIGHT_ALIGNMENT);
			codeKeyboard.setBackground(Color.BLACK);
				infoLabel.setAlignmentX(RIGHT_ALIGNMENT);
				codeKeyboard.add(infoLabel); //Displays all kinds of messages for the user.
				//Visual input for composing codes:
				keyboardCodePanel.setAlignmentX(RIGHT_ALIGNMENT);
				keyboardCodePanel.setLayout(new FlowLayout());
				keyboardCodePanel.setBackground(Color.BLACK);
				keyboardCodePanel.setPreferredSize(new Dimension(keyboardCodePanel.getWidth(), 25));
				codeKeyboard.add(new JSeparator());
				codeKeyboard.add(keyboardCodePanel);
				//The peg buttons of the keyboard:
				pegButtonsPanel.setAlignmentX(RIGHT_ALIGNMENT);
				pegButtonsPanel.setLayout(new GridLayout(2, 3));
					//Each button gets its icon:
					pegButtons.add(new JButton(peg_redIcon));
					pegButtons.add(new JButton(peg_blueIcon));
					pegButtons.add(new JButton(peg_yellowIcon));
					pegButtons.add(new JButton(peg_orangeIcon));
					pegButtons.add(new JButton(peg_greenIcon));
					pegButtons.add(new JButton(peg_purpleIcon));
					//Enable the allowed peg buttons for the default colors setting, disable the rest:
					for (int b=0; b < pegButtons.size(); b++){ 
						pegButtons.get(b).setEnabled(false); 
						pegButtonsPanel.add(pegButtons.get(b));
					}
					for (int b=0; b < colorsSlider.getValue(); b++){ 
						pegButtons.get(b).setEnabled(true); 
					}
				codeKeyboard.add(pegButtonsPanel);
				//Ok and return button for the keyboard:
				buttonsPanel.setLayout(new GridLayout(1, 2));
				buttonsPanel.setAlignmentX(RIGHT_ALIGNMENT);
					returnButton.setEnabled(false); 
					buttonsPanel.add(returnButton);
					okButton.setEnabled(false); 
					buttonsPanel.add(okButton);
				codeKeyboard.add(buttonsPanel);
			codeKeyboard.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			leftPanel.add(codeKeyboard);
		leftPanel.setBackground(Color.BLACK);
		content.add(leftPanel, BorderLayout.WEST);
		content.add(new JSeparator( SwingConstants.VERTICAL));
		
			//Game board:
			board.setAlignmentX(LEFT_ALIGNMENT);
				for (int i=0; i<turnsSlider.getMaximum(); i++){
					//Initialize all guess tabs.
					guessTabs.add(new JPanel());
					guessTabs.get(i).setAlignmentX(LEFT_ALIGNMENT);
					guessTabs.get(i).setLayout(new FlowLayout());
					guessTabs.get(i).setBackground(new Color(78, 61, 14));
					posClues.add(new JLabel("<html><font color='white'>0</font></html>", clue_posIcon, SwingConstants.TRAILING));
					colClues.add(new JLabel("<html><font color='white'>0</font></html>", clue_colIcon, SwingConstants.TRAILING));
					clueBoxes.add(new Box(BoxLayout.Y_AXIS));
						posClues.get(i).setAlignmentX(LEFT_ALIGNMENT);
						clueBoxes.get(i).add(posClues.get(i));
						clueBoxes.get(i).add(new JSeparator());
						colClues.get(i).setAlignmentX(LEFT_ALIGNMENT);
						clueBoxes.get(i).add(colClues.get(i));
				}
			rightPanel.add(board);
			//Secret code slots at the bottom of the board:
			secretCodePanel.setLayout(new FlowLayout());
			secretCodePanel.setAlignmentX(LEFT_ALIGNMENT);
			secretCodePanel.setBackground(new Color(123, 83, 54));
			rightPanel.add(secretCodePanel);
			rightPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			rightPanel.setMaximumSize(new Dimension(content.getWidth() - leftPanel.getWidth(), content.getHeight()));
		resetBoard();
		content.add(rightPanel, BorderLayout.EAST);
		
		
			
		//Listeners:
		//Code Sliders:
		colorsSlider.addChangeListener( new ChangeListener() {
		//Changes the number of colors to play with. Resets the board.
			public void stateChanged(ChangeEvent e) {
				//Adjust defaults:
				int max = Math.min(popMax, (int) Math.pow( pegsSlider.getValue(), ((JSlider)e.getSource()).getValue()));
				popSizeSlider.setMaximum( max );
				popSizeSlider.setValue(Math.max(max / 2, 60));
				maxGenSlider.setValue(Math.min(1000, pegsSlider.getValue() * ((JSlider)e.getSource()).getValue() * 20));
				//Enable/disable buttons:
				for (int b=0; b < pegButtons.size(); b++){ 
					pegButtons.get(b).setEnabled(false); 
				}
				for (int b=0; b < ((JSlider)e.getSource()).getValue(); b++){ 
					pegButtons.get(b).setEnabled(true); 
				}
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
				resetBoard(); //Reset the board.
			}
		});
		
		
		pegsSlider.addChangeListener( new ChangeListener() {
		//Changes the number of pegs to play with. Resets the board.
			public void stateChanged(ChangeEvent e) {
				//Adjust defaults:
				int max = Math.min(popMax, (int) Math.pow( ((JSlider)e.getSource()).getValue(), colorsSlider.getValue() ));
				popSizeSlider.setMaximum( max );
				popSizeSlider.setValue(Math.max(max / 2, 60));
				maxGenSlider.setValue(Math.min(1000, ((JSlider)e.getSource()).getValue() * colorsSlider.getValue() * 20));
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");// erase GA info.
				//Redraw appropriate board:
				resetBoard();
			}
		});
		
		turnsSlider.addChangeListener( new ChangeListener() {
		//Changes the number of turns to play with. Resets the board.
			public void stateChanged(ChangeEvent e) {
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");// erase GA info.
				//Redraw appropriate board:
				resetBoard();
			}
		});
		
		//Genetics Sliders:
		//Resets all info from previous settings.
		popSizeSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				popSizeVal.setText(String.valueOf(((JSlider)e.getSource()).getValue())); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
			}
		});
		
		maxGenSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maxGenVal.setText(String.valueOf(((JSlider)e.getSource()).getValue())); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		crossoverSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				crossoverVal.setText(String.valueOf((double) ((JSlider)e.getSource()).getValue() / 100)); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		mutSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mutVal.setText(String.valueOf((double) ((JSlider)e.getSource()).getValue() / 100)); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		perSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				perVal.setText(String.valueOf((double)((JSlider)e.getSource()).getValue() / 100)); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		invSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				invVal.setText(String.valueOf((double)((JSlider)e.getSource()).getValue() / 100)); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		eligibilitySlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				eligibilityVal.setText(String.valueOf((double)((JSlider)e.getSource()).getValue() / 10)); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		posWeightSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				posWeightVal.setText(String.valueOf((double)((JSlider)e.getSource()).getValue() / 10)); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		colWeightSlider.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colWeightVal.setText(String.valueOf((double)((JSlider)e.getSource()).getValue() / 10)); //Show value.
				genAvTurns.setText("N/A");
				genAvTime.setText("N/A");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		//Genetics button:
		genetics.addActionListener(new ActionListener() {
		//Displays the Genetic Algorithm settings menu.
			public void actionPerformed(ActionEvent a) {
				popSizeTmp = popSizeSlider.getValue();
				maxGenTmp = maxGenSlider.getValue();
				crossoverTmp = crossoverSlider.getValue();
				mutTmp = mutSlider.getValue();
				perTmp = perSlider.getValue();
				invTmp = invSlider.getValue();
				eligibilityTmp = eligibilitySlider.getValue();
				posWeightTmp = posWeightSlider.getValue();
				colWeightTmp = colWeightSlider.getValue();
				if ( !genFrame.isVisible() ){
					genFrame.setVisible(true);
				}
				else{ genFrame.setVisible(false); }
				//Position box under the button.
				genFrame.setBounds( getBounds().x + getWidth() - genFrame.getWidth(), 
									getBounds().y + 55, 
									genFrame.getWidth(), 
									genFrame.getHeight());
			}
		});
				
		
		//New Game menu buttons:
		HvH.addActionListener(new ActionListener() {
		//Two users play the game together. All inputs are handled by the code keyboard.
			public void actionPerformed(ActionEvent a) {
				inputColors = new ArrayList<PegColor>();
				resetBoard();
				Quit.setEnabled(true);
				genetics.setEnabled(false);
				pegsSlider.setEnabled(false);
				colorsSlider.setEnabled(false);
				turnsSlider.setEnabled(false);
				maker = new CodeMaker();
				breaker = new CodeBreaker();
				game = new Game (maker, breaker, turnsSlider.getValue());
				returnButton.setEnabled(true);
				infoLabel.setText("<html><font color='white'>Enter secret code.</font></html>");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		HvC.addActionListener(new ActionListener() {
		//User enters a code for the CPU to guess. Guesses are handled by CPUSolve.
			public void actionPerformed(ActionEvent a) {
				inputColors = new ArrayList<PegColor>();
				resetBoard();
				Quit.setEnabled(true);
				genetics.setEnabled(false);
				pegsSlider.setEnabled(false);
				colorsSlider.setEnabled(false);
				turnsSlider.setEnabled(false);
				maker = new CodeMaker();
				algorithm = setGA(algorithm);
				breaker = new CPUCodeBreaker(algorithm);
				game = new Game (maker, breaker, turnsSlider.getValue());
				returnButton.setEnabled(true);
				infoLabel.setText("<html><font color='white'>Enter secret code.</font></html>");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		CvH.addActionListener(new ActionListener() {
		//User guesses a randomly generated code. Secret code input handled automatically.
			public void actionPerformed(ActionEvent a) {
				inputColors = new ArrayList<PegColor>();
				resetBoard();
				Quit.setEnabled(true);
				genetics.setEnabled(false);
				pegsSlider.setEnabled(false);
				colorsSlider.setEnabled(false);
				turnsSlider.setEnabled(false);
				maker = new CodeMaker();
				breaker = new CodeBreaker();
				game = new Game (maker, breaker, turnsSlider.getValue());
				returnButton.setEnabled(true);
				maker.generateSecretCode(colorsSlider.getValue(), pegsSlider.getValue());
				inputColors = maker.getSecretCode().getColors();
				drawSecretBoard();
				inputColors = new ArrayList<PegColor>();
				game.start();
				infoLabel.setText("<html><font color='white'>Enter guess.</font></html>");
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
			}
		});
		
		CvC.addActionListener(new ActionListener() {
		//CPU solves a randomly generated code. Game plays itself.
			public void actionPerformed(ActionEvent a) {
				GAStatsText.setText("<html><font color='white'>N/A</font></html>");
				inputColors = new ArrayList<PegColor>();
				resetBoard();
				genetics.setEnabled(false);
				pegsSlider.setEnabled(false);
				turnsSlider.setEnabled(false);
				colorsSlider.setEnabled(false);
				Quit.setEnabled(true);
				maker = new CodeMaker();
				algorithm = setGA(algorithm);
				breaker = new CPUCodeBreaker(algorithm);
				game = new Game (maker, breaker, turnsSlider.getValue());
				returnButton.setEnabled(false);
				maker.generateSecretCode(colorsSlider.getValue(), pegsSlider.getValue());
				inputColors = maker.getSecretCode().getColors();
				drawSecretBoard();
				infoLabel.setText("<html><font color='white'>Enter guess.</font></html>");
				game.start();
				worker = new CPUSolve();
				worker.execute();//Calls the solving loop.
			}
		});
		
		
		Quit.addActionListener(new ActionListener() {
		//Interrupts the current game. 
			public void actionPerformed(ActionEvent a) {
			//Reset everything:
				inputColors = new ArrayList<PegColor>();
				resetBoard();
				Quit.setEnabled(false);
				genetics.setEnabled(true);
				pegsSlider.setEnabled(true);
				colorsSlider.setEnabled(true);
				turnsSlider.setEnabled(true);
				if (worker != null) {
					worker.cancel(true);
					worker = null;
				}
				returnButton.setEnabled(false);
				infoLabel.setText("<html><font color='white'>Click new game to start.</font></html>");
				maker = null;
				breaker = null;
				game = null;
			}
		});
		
		
		//Code keyboard buttons:
		for (int b=0; b < pegButtons.size(); b++){ 
		//Each button adds its color to the input sequence. 
			final int tmp = b;
			pegButtons.get(b).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					PegColor col = PegColor.withRank(tmp+1);
					if ( inputColors.size() < pegsSlider.getValue() && game != null){
					//Buttons are always enabled for visual purpose but do nothing if there is no game or code is complete and ready to be inputed.
						inputColors.add(col); //Real input.
						switch(col){
						//Visual inputs.
							case RED:
								keyboardCodePanel.add(new JLabel(peg_redIcon));
								break;
							case BLUE:
								keyboardCodePanel.add(new JLabel(peg_blueIcon));
								break;
							case YELLOW:
								keyboardCodePanel.add(new JLabel(peg_yellowIcon));
								break;
							case ORANGE:
								keyboardCodePanel.add(new JLabel(peg_orangeIcon));
								break;
							case GREEN:
								keyboardCodePanel.add(new JLabel(peg_greenIcon));
								break;
							case PURPLE:
								keyboardCodePanel.add(new JLabel(peg_purpleIcon));
								break;
						}
						keyboardCodePanel.repaint();
					}
					if (inputColors.size() == pegsSlider.getValue()){
					//Code is complete and can be inputed.
						okButton.setEnabled(true);
					}
					pack();
				}
			}); 
		}
		
		returnButton.addActionListener(new ActionListener() {
		//Allows user to come back on the pegs to play.
			public void actionPerformed(ActionEvent a) {
				if (inputColors.size() >= 1){
					keyboardCodePanel.remove(inputColors.size()-1); //Visual input.
					keyboardCodePanel.repaint();
					inputColors.remove(inputColors.size()-1); //Real input.
				}
				okButton.setEnabled(false);//code isn't complete anymore. Can't be inputed.
				pack();
			}
		});
		
		okButton.addActionListener(new ActionListener() {
		//Controls color inputs and calls game turns for human interactions.
			public void actionPerformed(ActionEvent a) {
				if (inputColors.size() == pegsSlider.getValue() && game != null){
					returnButton.setEnabled(true);
					okButton.setEnabled(false);
					keyboardCodePanel.removeAll();
					keyboardCodePanel.repaint();
					if (game.getTurn() < 0){
					//Secret code is entered. Game starts.
						drawSecretBoard();
						maker.setSecretCode(new Code(colorsSlider.getValue(), pegsSlider.getValue(), inputColors));
						game.start();
						infoLabel.setText("<html><font color='white'>Enter guess.</font></html>");
						if ( breaker instanceof CPUCodeBreaker){
						//Code breaker is a CPU, CPUSolve takes control of inputs.
							returnButton.setEnabled(false);
							for (int b=0; b < pegButtonsPanel.getComponentCount(); b++){
								//Hide secret code:
								pegButtonsPanel.getComponents()[b].setEnabled(false);	
							}
							CPUSolve worker = new CPUSolve();
							worker.execute();//Calls the solving loop.
						}
					}
					else {
					//Guess code is entered, game proceeds to next turn.
						drawGuessTab(game.getTurn());
						breaker.setGuess(new GuessCode(new Code(colorsSlider.getValue(), pegsSlider.getValue(), inputColors)));	
						game.playTurn();
						drawClues(game.getTurn());
						if (game.getState() != GameState.CONTINUE) {
						//Game ends. Winner is announced. Menu is re-enabled. Keyboard is disabled. Pegs stay on board until new game or board change.
							okButton.setEnabled(false);
							infoLabel.setText("<html><font color='white'>" + game.getState().toString() + "</font></html>");
							for (int p=0; p<pegsSlider.getValue(); p++){
								//Show secret code:
								secretCodePanel.getComponents()[p].setEnabled(true);
							}
							returnButton.setEnabled(false);
							genetics.setEnabled(true);
							pegsSlider.setEnabled(true);
							colorsSlider.setEnabled(true);
							turnsSlider.setEnabled(true);
							Quit.setEnabled(false);
							game = null;//delete game.
						}
					}
					inputColors = new ArrayList<PegColor>();
					pack();
				}
			}
		});
		
		
		//Window: 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mastermind VS Genetic Algorithm");
		setIconImage(mainIcon.getImage());
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		pack(); //Pack the components.
		//Sit the window on the middle-top position:
		setBounds((screenWidth - getWidth()) / 2, 
				  0, 
				  getWidth(), 
				  getHeight());
		setVisible(true);
		
	}
	

//################################################################~~Methods~~##########################################################################

	//Image loader:
	protected ImageIcon createImageIcon(String path, int width, int height) {
	// Returns an ImageIcon, or null if the path was invalid.
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        ImageIcon tmp =  new ImageIcon(imgURL);
	        return new ImageIcon(tmp.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	    } else {
	        System.err.println("IMAGE: Couldn't find file: " + path);
	        return null;
	    }
	}
	
	
	//Drawing methods:
	public void drawSecretBoard(){
	//Draws the secret code on the bottom of the board.
		assert (game != null);
		// Reset the panel:
		secretCodePanel.removeAll();
		for (int p=0; p<pegsSlider.getValue(); p++){
			PegColor col = inputColors.get(p);
			//Pick the right icon:
			switch(col){
				case RED:
					secretCodePanel.add(new JLabel(slot_redIcon));
					break;
				case BLUE:
					secretCodePanel.add(new JLabel(slot_blueIcon));
					break;
				case YELLOW:
					secretCodePanel.add(new JLabel(slot_yellowIcon));
					break;
				case ORANGE:
					secretCodePanel.add(new JLabel(slot_orangeIcon));
					break;
				case GREEN:
					secretCodePanel.add(new JLabel(slot_greenIcon));
					break;
				case PURPLE:
					secretCodePanel.add(new JLabel(slot_purpleIcon));
					break;
			}
			secretCodePanel.getComponents()[p].setEnabled(false);
		}
	}
	

	public void drawGuessTab(int turn){
	//Draws the guesses on the right panel:
		assert (game != null);
		guessTabs.get(turn).removeAll(); //Reset the tab.
		//Redraw the tab number:
		guessTabs.get(turn).add(new JLabel("<html><font color='white'>" + String.valueOf(turn+1) + "</font></html>"));;
		for (int p=0; p<pegsSlider.getValue(); p++){
			PegColor col = inputColors.get(p);
			//Pick the right icon:
			switch(col){
				case RED:
					guessTabs.get(turn).add(new JLabel(slot_redIcon));
					break;
				case BLUE:
					guessTabs.get(turn).add(new JLabel(slot_blueIcon));
					break;
				case YELLOW:
					guessTabs.get(turn).add(new JLabel(slot_yellowIcon));
					break;
				case ORANGE:
					guessTabs.get(turn).add(new JLabel(slot_orangeIcon));
					break;
				case GREEN:
					guessTabs.get(turn).add(new JLabel(slot_greenIcon));
					break;
				case PURPLE:
					guessTabs.get(turn).add(new JLabel(slot_purpleIcon));
					break;
			}
		}
	}
	
	
	public void drawClues(int turn){
	//Draws clues from the from score fields of the breaker instance provided by the game on the last guess tab:
		guessTabs.get(game.getTurn() - 1).add(clueBoxes.get(game.getTurn() - 1));
		posClues.get(game.getTurn() - 1).setText("<html><font color='white'>" + String.valueOf(breaker.getGuess().getPositionScore()) + "</font></html>");
		colClues.get(game.getTurn() - 1).setText("<html><font color='white'>" + String.valueOf(breaker.getGuess().getColorScore()) + "</font></html>");
		guessTabs.get(game.getTurn() - 1).repaint();
	}

	
	public void resetBoard(){
	//Resets the empty board with the given number of peg slots.
		board.removeAll();
		for (int i=0; i<turnsSlider.getValue(); i++){
			board.add(guessTabs.get(i));
			board.add(new JSeparator());
			guessTabs.get(i).removeAll();
			guessTabs.get(i).add(new JLabel("<html><font color='white'>" + String.valueOf(i+1) + "</font></html>"));
			for (int p=0; p < pegsSlider.getValue(); p++){
				guessTabs.get(i).add(new JLabel(slot_emptyIcon));
			}
		guessTabs.get(i).add(clueBoxes.get(i));
		}
		board.add(secretCodePanel);
		secretCodePanel.removeAll();
		for (int p=0; p < pegsSlider.getValue(); p++){
			secretCodePanel.add(new JLabel(slot_emptyIcon));
		}
		keyboardCodePanel.removeAll();
		keyboardCodePanel.repaint();
		for (int c = 0; c < turnsSlider.getValue(); c++){
			posClues.get(c).setText("<html><font color='white'>0</font></html>");
			colClues.get(c).setText("<html><font color='white'>0</font></html>");
		}
		inputColors = new ArrayList<PegColor>();	
		pack();
	}
	
	
	public void pringGAStats(GeneticAlgorithm algo){
		//Prints informative text about the genetic algorithm.
		String text = algo.toString();
		text = text.replace("\n", "<br>"); 
		text = text.replace("\t", "   ");
		if (game.getState() == GameState.BROKEN){ text += "<br><br><b>Success in " + game.getTurn() + " turns.<br>and "+  df.format( (double) solveTimer) +"s</b>"; }
		else if (game.getState() == GameState.TIMEOUT){ text += "<br><br><b>Failure.</b>"; }
		GAStatsText.setText("<html><font color='white'>" + text + "</font></html>");
	}
	
	//Genetic algorithm init:
	public GeneticAlgorithm  setGA(GeneticAlgorithm algo){
		algo = new GeneticAlgorithm(colorsSlider.getValue(),
				 pegsSlider.getValue(),
				 maxGenSlider.getValue(),
				 popSizeSlider.getValue(),
				 (float) crossoverSlider.getValue() / 100,
				 (float) mutSlider.getValue() / 100,
				 (float) perSlider.getValue() / 100,
				 (float) invSlider.getValue() / 100,
				 (float) posWeightSlider.getValue() / 10,
				 (float) colWeightSlider.getValue() / 10,
				 (float) eligibilitySlider.getValue() / 10);
		return algo;
	}
	

	
//################################################################~~GameSolver~~##########################################################################
	
	class CPUSolve extends SwingWorker<Void, Void> {
		
		/**
		 * Multithreaded worker class to solve a game without freezing the UI.
		 * Displays the results on the board continually.
		 */
		
		//Fields:
		private long start = 0;
		
		//Constructor:
		public CPUSolve(){ 
			infoLabel.setText("<html><font color='white'>Solving...</font></html>");
			GAStatsText.setEnabled(true);
			okButton.setEnabled(false);
			returnButton.setEnabled(false);
		}
		
		//Methods:
		@Override
		protected Void doInBackground() throws Exception {
			start = System.nanoTime(); //Start timer.
			while (game.getState() == GameState.CONTINUE){
			//Play game with loosing condition.
				pringGAStats(algorithm);
				game.playTurn(); //Play the turn.
				inputColors = breaker.getGuess().getColors(); //Get the colors from the guess.
	        	infoLabel.setText("<html><font color='white'>Solving...</font></html>");
				pringGAStats(algorithm); //Print info on GA.
				drawGuessTab(game.getTurn() - 1); //Draw the pegs from this turn.
				//Draw the clues from scores returned to the CPU:
				guessTabs.get(game.getTurn() - 1).add(clueBoxes.get(game.getTurn() - 1)); 
				posClues.get(game.getTurn() - 1).setText("<html><font color='white'>" + String.valueOf(breaker.getGuess().getPositionScore()) + "</font></html>");
				colClues.get(game.getTurn() - 1).setText("<html><font color='white'>" + String.valueOf(breaker.getGuess().getColorScore()) + "</font></html>");
				guessTabs.get(game.getTurn() - 1).repaint();
				//Interruption:
				if (isCancelled()){ return null; }
			}
			solveTimer = (double) (System.nanoTime() - start) / 1000000000; //Get time in seconds.
			pringGAStats(algorithm); //Print info on GA.
			return null;
		}
		
		
		@Override
		protected void done(){ 
			infoLabel.setText("<html><font color='white'>" + game.getState().toString() + "</font></html>");
			pringGAStats(algorithm);
			for (int b=0; b < colorsSlider.getValue(); b++){ 
				pegButtons.get(b).setEnabled(true); 
			}
			//Re-enable the UI:
			genetics.setEnabled(true);
			pegsSlider.setEnabled(true);
			colorsSlider.setEnabled(true);
			turnsSlider.setEnabled(true);
			Quit.setEnabled(false); //Disable game interruption.
			game = null;
			for (int p=0; p<pegsSlider.getValue(); p++){
				//Show secret code:
				secretCodePanel.getComponents()[p].setEnabled(true);	
			}
		}
	}
	
	
	
//##############################################################~~Genetics_dialog~~#######################################################################

	class GeneticsMenu extends JDialog {
		
		/**
		 *GUI dialog frame for the genetics settings menu. 
		 */
		
		
		public GeneticsMenu(Frame owner, boolean modal) {
			
			super(owner, modal);
			
			Container genContent = getContentPane();
			genContent.setLayout(new BoxLayout(genContent, BoxLayout.PAGE_AXIS));
			
	
			//Info bar:
				genInfo.setAlignmentX(LEFT_ALIGNMENT);
				genInfo.setBackground(Color.BLACK);
				genInfo.setEditable(false);
				genInfo.setContentType("text/html");
				genInfo.setText("<html><font color='white'><center>Genetic algorithm settings menu.</center></font></html>");
			genContent.add(genInfo);
	
			genContent.add(new JSeparator());
			
			//Population size:
			popSizeText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(popSizeText); 
			Box popSizeBox = new Box(BoxLayout.Y_AXIS);
				popSizeSlider.setAlignmentX(CENTER_ALIGNMENT); 
				popSizeBox.add(popSizeSlider);
					popSizeSlider.setMajorTickSpacing(10);
					popSizeSlider.setMinorTickSpacing(1);
					popSizeSlider.setPaintTicks(true);
				popSizeVal.setAlignmentX(CENTER_ALIGNMENT); 
				popSizeBox.add(popSizeVal);
				popSizeBox.addMouseMotionListener(new MouseMotionListener() {
					public void mouseMoved(MouseEvent e){
						genInfo.setText("<html><font color='white'><center>Number of eligible guesses by turn.</center></font></html>");
					}
					public void mouseDragged(MouseEvent e){ /** do nothing */ }
				});
			popSizeBox.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(popSizeBox);
			
			genContent.add(new JSeparator());
			
			generationText.setAlignmentX(LEFT_ALIGNMENT); 
			generationText.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center> Mechanics for the generation of new codes.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});
			genContent.add(generationText);
			
			genContent.add(new JSeparator());
			
			//Maximum generation number:
			maxGenText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(maxGenText); 
			Box maxGenBox = new Box(BoxLayout.Y_AXIS);
				maxGenSlider.setAlignmentX(CENTER_ALIGNMENT); 
				maxGenBox.add(maxGenSlider);
					maxGenSlider.setMajorTickSpacing(10);
					maxGenSlider.setMinorTickSpacing(1);
					maxGenSlider.setPaintTicks(true);
				maxGenVal.setAlignmentX(CENTER_ALIGNMENT); 
				maxGenBox.add(maxGenVal);
			maxGenBox.setAlignmentX(LEFT_ALIGNMENT); 
			maxGenBox.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center> Maximum number of generations per turn.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});
			genContent.add(maxGenBox);
			
			//Crossover preference:
			crossoverText.setAlignmentX(LEFT_ALIGNMENT);
			genContent.add(crossoverText);
			Box crossoverBox = new Box(BoxLayout.Y_AXIS);
				JPanel crossover12 = new JPanel(); 
					crossover12.add(crossoverOPText, BorderLayout.LINE_START);
					crossoverOPText.addMouseMotionListener(new MouseMotionListener() {
						//Provide info on mouseover:
						public void mouseMoved(MouseEvent e){
							genInfo.setText("<html><font color='white'><center>Cuts both sequences at the same random point and swaps the halves</center></font></html>");
						}
						public void mouseDragged(MouseEvent e){ /** do nothing */ }
					});
					crossover12.add(crossoverSlider, BorderLayout.LINE_END);
						crossoverSlider.setMajorTickSpacing(10);
						crossoverSlider.setMinorTickSpacing(1);
						crossoverSlider.setPaintTicks(true);
						crossoverSlider.setPreferredSize(new Dimension(330,30));
					crossover12.add(crossoverTPText, BorderLayout.LINE_END);
					crossoverTPText.addMouseMotionListener(new MouseMotionListener() {
						//Provide info on mouseover:
						public void mouseMoved(MouseEvent e){
							genInfo.setText("<html><font color='white'><center>Cuts both sequences at the same two random points and swaps the middle parts.</center></font></html>");
						}
						public void mouseDragged(MouseEvent e){ /** do nothing */ }
					});
				crossover12.setAlignmentX(CENTER_ALIGNMENT);
				crossoverBox.add(crossover12);
				crossoverVal.setAlignmentX(CENTER_ALIGNMENT); 
				crossoverBox.add(crossoverVal);
			crossoverBox.addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e){
					//Provide info on mouseover:
					genInfo.setText("<html><font color='white'><center> Balance between the two types of crossover.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});
			crossoverBox.setAlignmentX(LEFT_ALIGNMENT);
			genContent.add(crossoverBox);
			
			
			//Mutation rate:
			mutText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(mutText); 
			Box mutBox = new Box(BoxLayout.Y_AXIS);
				mutSlider.setAlignmentX(CENTER_ALIGNMENT); 
				mutBox.add(mutSlider);
					mutSlider.setMajorTickSpacing(10);
					mutSlider.setMinorTickSpacing(1);
					mutSlider.setPaintTicks(true);
				mutVal.setAlignmentX(CENTER_ALIGNMENT); 
				mutBox.add(mutVal);
			mutBox.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center>Changes randomly the color of a random peg in the code.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});	
			mutBox.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(mutBox);
			
			//Permutation rate:
			perText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(perText); 
			Box perBox = new Box(BoxLayout.Y_AXIS);
			perSlider.setAlignmentX(CENTER_ALIGNMENT); 
				perBox.add(perSlider);
					perSlider.setMajorTickSpacing(10);
					perSlider.setMinorTickSpacing(1);
					perSlider.setPaintTicks(true);
				perVal.setAlignmentX(CENTER_ALIGNMENT); 
				perBox.add(perVal);
			perBox.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center>Swaps the colors of two random pegs in the code.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});	
			perBox.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(perBox);
			
			//Inversion rate:
			invText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(invText); 
			Box invBox = new Box(BoxLayout.Y_AXIS);
			invSlider.setAlignmentX(CENTER_ALIGNMENT); 
				invBox.add(invSlider);
					invSlider.setMajorTickSpacing(10);
					invSlider.setMinorTickSpacing(1);
					invSlider.setPaintTicks(true);
				invVal.setAlignmentX(CENTER_ALIGNMENT); 
				invBox.add(invVal);
			invBox.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center>Cuts the code at two random points and inverts the middle part.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});	
			invBox.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(invBox);
	
			genContent.add(new JSeparator());
			
			fitnessText.setAlignmentX(LEFT_ALIGNMENT); 
			fitnessText.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center>" +
										"<i>fitness</i> = " +
										"<font color='orange'>" +
											"a * (&Sigma<sup>T</sup><sub>t=1</sub>|X'<sub>t</sub>(c) - X<sub>t</sub>|) " +
										"</font>" +
										"+ <font color=#D3D3D3>" +
											"b * (&Sigma<sup>T</sup><sub>t=1</sub>|Y'<sub>t</sub>(c) - Y<sub>t</sub>|)" +
										"</font>" +
									"</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});
			genContent.add(fitnessText);
			
			genContent.add(new JSeparator());			
			
			//Eligibility of Codes:
			eligibilityText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(eligibilityText); 
			Box eligibilityBox = new Box(BoxLayout.Y_AXIS);
			posWeightSlider.setAlignmentX(CENTER_ALIGNMENT); 
				eligibilityBox.add(eligibilitySlider);
					eligibilitySlider.setMajorTickSpacing(10);
					eligibilitySlider.setMinorTickSpacing(1);
					eligibilitySlider.setPaintTicks(true);
				eligibilityVal.setAlignmentX(CENTER_ALIGNMENT); 
				eligibilityBox.add(eligibilityVal);
			eligibilityBox.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center>Threshold under which a score is selected in population.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});	
			eligibilityBox.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(eligibilityBox);
			
			//Position score Weight:
			posWeightText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(posWeightText); 
			Box posWeightBox = new Box(BoxLayout.Y_AXIS);
			posWeightSlider.setAlignmentX(CENTER_ALIGNMENT); 
				posWeightBox.add(posWeightSlider);
					posWeightSlider.setMajorTickSpacing(10);
					posWeightSlider.setMinorTickSpacing(1);
					posWeightSlider.setPaintTicks(true);
				posWeightVal.setAlignmentX(CENTER_ALIGNMENT); 
				posWeightBox.add(posWeightVal);
			posWeightBox.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='orange'><center>Weight (a) of the position score (X) in the fitness function.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});	
			posWeightBox.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(posWeightBox);
			
			//Color Score Weight:
			colWeightText.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(colWeightText); 
			Box colWeightBox = new Box(BoxLayout.Y_AXIS);
			colWeightSlider.setAlignmentX(CENTER_ALIGNMENT); 
				colWeightBox.add(colWeightSlider);
					colWeightSlider.setMajorTickSpacing(10);
					colWeightSlider.setMinorTickSpacing(1);
					colWeightSlider.setPaintTicks(true);
				colWeightVal.setAlignmentX(CENTER_ALIGNMENT); 
				colWeightBox.add(colWeightVal);
			colWeightBox.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color=#D3D3D3><center>Weight (b) of the color score (Y) in the fitness function.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});	
			colWeightBox.setAlignmentX(LEFT_ALIGNMENT); 
			genContent.add(colWeightBox);
			
			genContent.add(new JSeparator());
			
			//Test:
			genTestPanel.setAlignmentX(LEFT_ALIGNMENT);
				genTestPanel.add(genTestLabel1);
				genTestPanel.add(genTestNumber);
				genTestPanel.add(genTestLabel2);
				genTestPanel.add(genTest);
				genTest.setPreferredSize(new Dimension(65, 27));
				genTestPanel.add(genTestStop);
				genTestStop.setPreferredSize(new Dimension(cancelIcon.getIconWidth()+6, cancelIcon.getIconHeight()+6));
				genTestStop.setOpaque(false);
				genTestStop.setContentAreaFilled(false);
				genTestStop.setBorderPainted(false);
				genTestStop.setEnabled(false);
				genTestPanel.add(genAvTurns);
				genAvTurns.setEditable(false);
				genAvTurns.setOpaque(false);
				genAvTurns.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				genTestPanel.add(genAvTime);
				genAvTime.setEditable(false);
				genAvTime.setOpaque(false);
				genAvTime.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			genTestPanel.addMouseMotionListener(new MouseMotionListener() {
				//Provide info on mouseover:
				public void mouseMoved(MouseEvent e){
					genInfo.setText("<html><font color='white'><center>Make the CPU play " + genTestNumber.getValue() + " games and calculate the average number of turns played and solve time.</center></font></html>");
				}
				public void mouseDragged(MouseEvent e){ /** do nothing */ }
			});	
			genTestNumber.addChangeListener( new ChangeListener() {
				//Provide info on mouseover:
				public void stateChanged(ChangeEvent e) {
					genInfo.setText("<html><font color='white'><center>Make the CPU play " + genTestNumber.getValue() + " games and calculate the average number of turns played and solve time.</center></font></html>");
					}
			});
			genContent.add(genTestPanel);

			genContent.add(new JSeparator());
			
			//Buttons:
			genButtons.setAlignmentX(LEFT_ALIGNMENT);
				genButtons.add(genCancel);
				genButtons.add(genOk);
			genContent.add(genButtons);
	
				
			//Listeners:
			genCancel.addActionListener(new ActionListener() {
				//Resets the sliders to the positions they had before opening the window:
				public void actionPerformed(ActionEvent a) {
					popSizeSlider.setValue(popSizeTmp);
					maxGenSlider.setValue(maxGenTmp);
					crossoverSlider.setValue(crossoverTmp);
					mutSlider.setValue(mutTmp);
					perSlider.setValue(perTmp);
					invSlider.setValue(invTmp);
					eligibilitySlider.setValue(eligibilityTmp);
					posWeightSlider.setValue(posWeightTmp);
					colWeightSlider.setValue(colWeightTmp);
					genInfo.setText("<html><font color='white'><center>Genetic algorithm settings menu.</center></font></html>");
					setVisible(false);
				}
			});
				
			
			genOk.addActionListener(new ActionListener() {
				//Close the window:
				public void actionPerformed(ActionEvent a) {
					genInfo.setText("<html><font color='white'><center>Genetic algorithm settings menu.</center></font></html>");
					genFrame.setVisible(false);
				}
			});
			
			
			genTest.addActionListener(new ActionListener() {
				//Calls an instance of a multithreaded class to solve games in a loop and compute the configured GA's performance.
				//Displays progress continually.
				public void actionPerformed(ActionEvent a) {
					genTest.setEnabled(false);
					genTestStop.setEnabled(true);
					workerM = new CPUSolveMany((Integer) genTestNumber.getValue());
					workerM.addPropertyChangeListener(
							//Records the progress of the solver:
							new PropertyChangeListener(){
								public  void propertyChange(PropertyChangeEvent e) {
									if ("progress".equals(e.getPropertyName())) {
										genInfo.setText("<html><font color='white'><center>Solving...</center></font></html>");
										//Display progress:
										genTest.setEnabled(false);
										genTest.setText(String.valueOf((Integer)e.getNewValue()) + "%");
									}
									//Interruption happened:
									if (workerM.isCancelled()){
										//Reset the panel:
										genTest.setText("Go");
										genTest.setEnabled(true);
										genInfo.setText("<html><font color='white'><center>Cancelled.</center></font></html>");
									}
								}
							});
					workerM.execute();//Calls the solving loop.
				}
			});
			
			
			genTestStop.addActionListener(new ActionListener() {
				//Cancel the test:
				public void actionPerformed(ActionEvent a) {
					if (workerM != null){ workerM.cancel(true);}
				}
			});
			
			
			//Window:
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setTitle("Genetics settings:");
			setIconImage(geneticsIcon.getImage());
			pack();//Pack everything.
			//Give room for the info panel:
			setBounds( getBounds().x, 
					   getBounds().y, 
					   getWidth(), 
					   getHeight() + 20);
			setResizable(false);			
		}
	}
	
	

//################################################################~~MultiSolver~~##########################################################################

	class CPUSolveMany extends SwingWorker<Void, Integer> {
		
		/**
		 * Multithreaded worker class to solve multiple games without freezing the UI.
		 * All other functionalities of the UI remain accessible while it is running.
		 * Keeps going even if the genetics window is closed.
		 */
		
		//Fields
		private int numSolves;
		private int totalTurns;
		private int gamesPlayed;
		private GeneticAlgorithm algorithmM;
		private CodeMaker makerM;
		private CodeBreaker breakerM;
		private Game gameM;
		private long start;
		private long totalTime;
		
		//Constructor:
		public CPUSolveMany(int numSolves){ 
			this.numSolves = numSolves;
		}
		
		//Methods:
		@Override
		protected Void doInBackground() throws Exception {
			genTest.setText("0%");
			for (int g=0; g<numSolves; g++){
				start = System.nanoTime(); //Start timer.
				// Build separate game instances to allow normal games to run ||.
				makerM = new CodeMaker();
				algorithmM = setGA(algorithmM);
				breakerM = new CPUCodeBreaker(algorithmM);
				gameM = new Game(makerM, breakerM, turnsSlider.getValue());
				//Use a random secret code:
				makerM.generateSecretCode(colorsSlider.getValue(), pegsSlider.getValue());
				gameM.start();
				//Play games without visual output:
				while (gameM.getState() != GameState.BROKEN){
					gameM.playTurn();
				}
				gamesPlayed ++;
				totalTurns += gameM.getTurn();
				totalTime += ((System.nanoTime() - start)); //Get completion time in seconds.
				//Get average time:
				genAvTime.setText(df.format( (double) ( (float) (totalTime / 1000000000f) / gamesPlayed)) + 's');
				//Get average numturns:
				genAvTurns.setText(df.format( (double) totalTurns / gamesPlayed));
				setProgress(100 * gamesPlayed / numSolves ); //Activates the PropertyChangeListener.
				//Interruption:
				if (isCancelled()){ return null; }
			}
			return null;
		}
		
		
		@Override
		protected void done(){ 
			//Reset the button and indicate completion:
			genTest.setText("Go");
			genTest.setEnabled(true);
			genTestStop.setEnabled(false);
			genInfo.setText("<html><font color='white'><center>Done.</center></font></html>");	
		}
	    

	}
	
//###################################################################~~Main~~##############################################################################
	
	

	public static void main(String[] args) {
		String title = 
	" ╔════════════════════════════════════════════════════════════════════════════════════╗\n" +
	" ║███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗ ███╗   ███╗██╗███╗   ██╗██████╗ ║\n" + 
	" ║████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗║\n" + 
	" ║██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝██╔████╔██║██║██╔██╗ ██║██║  ██║║\n" + 
	" ║██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗██║╚██╔╝██║██║██║╚██╗██║██║  ██║║\n" + 
	" ║██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██████╔╝║\n" + 
	" ║╚═╝ ╔══╗╚═╝╔═════╚═╝╚═════╝══╗╚═╝╔══╚══════╣╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═════╝ ║\n" +
	" ║××××║VS║×××║Genetic Algorithm║×××║TERMINAL ║××××××××××××××××××××××××××××××××××××××××║\n" +
	" ╚════╚══╝═══╚═════════════════╝═══╚═════════╝════════════════════════════════════════╝\n" ;
		System.out.println(title); 
		new MasterMindGui();
		
		
	}
		

		
}
	
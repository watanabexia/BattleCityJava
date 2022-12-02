package edu.uchicago.gerber._08final.mvc.view;

import edu.uchicago.gerber._08final.mvc.controller.Game;
import edu.uchicago.gerber._08final.mvc.model.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;


public class GamePanel extends Panel {
	
	// ==============================================================
	// FIELDS 
	// ============================================================== 
	 
	// The following "off" vars are used for the off-screen double-buffered image.
	private Image imgOff;
	private Graphics grpOff;
	
	private GameFrame gmf;
	private Font fnt = new Font("SansSerif", Font.BOLD, 12);
	private Font fntBig = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
	private FontMetrics fmt; 
	private int fontWidth;
	private int fontHeight;
	private String strDisplay = "";
	

	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	
	public GamePanel(Dimension dim){
	    gmf = new GameFrame();
		gmf.getContentPane().add(this);
		gmf.pack();
		initView();
		
		gmf.setSize(dim);
		gmf.setTitle("Game Base");
		gmf.setResizable(false);
		gmf.setVisible(true);
		setFocusable(true);
	}
	
	
	// ==============================================================
	// METHODS 
	// ==============================================================
	
	private void drawScore(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		if (CommandCenter.getInstance().getScore() != 0) {
			g.drawString("SCORE :  " + CommandCenter.getInstance().getScore(), fontWidth, fontHeight);
		} else {
			g.drawString("NO SCORE", fontWidth, fontHeight);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void update(Graphics g) {
		//create an image off-screen
		imgOff = createImage(Game.DIM.width, Game.DIM.height);
		//get its graphics context
		grpOff = imgOff.getGraphics();

		//Fill the off-screen image background with black.
		grpOff.setColor(Color.black);
		grpOff.fillRect(0, 0, Game.DIM.width, Game.DIM.height);

		drawScore(grpOff);
		
		if (CommandCenter.getInstance().isGameOver()) {
			displayTextOnScreen();
		} else if (CommandCenter.getInstance().isPaused()) {
			strDisplay = "Game Paused";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);
		}
		
		//playing and not paused!
		else {

			iterateMovables(grpOff,
				CommandCenter.getInstance().getMovHQs(),
				CommandCenter.getInstance().getMovEnemies(),
				CommandCenter.getInstance().getMovBullets_enemy(),
				CommandCenter.getInstance().getMovFriends(),
				CommandCenter.getInstance().getMovBullets_friend(),
				CommandCenter.getInstance().getMovWalls());


			drawNumberShipsLeft(grpOff);


		}

		//after drawing all the movables or text on the offscreen-image, copy it in one fell-swoop to graphics context
		// of the game panel, and show it for ~40ms. If you attempt to draw sprites directly on the gamePanel, e.g.
		// without the use of a double-buffered off-screen image, you will see flickering.
		g.drawImage(imgOff, 0, 0, this);
	} 

	@SafeVarargs
	private final void iterateMovables(final Graphics g, List<Movable>... arrayOfListMovables){

		BiConsumer<Graphics, Movable> moveDraw = (grp, mov) -> {
			mov.move();
			mov.draw(grp);
		};

		//we use flatMap to flatten the List<Movable>[] passed-in above into a single stream of Movables
		Arrays.stream(arrayOfListMovables) //Stream<List<Movable>>
				.flatMap(Collection::stream) //Stream<Movable>
				.forEach(m -> moveDraw.accept(g, m));

		
	}

	private void drawNumberShipsLeft(Graphics g){
//		int numFalcons = CommandCenter.getInstance().getNumFalcons();

		g.setColor(Color.white);
		g.setFont(fnt);
		g.drawString("LEVEL:  " + CommandCenter.getInstance().getLevel(), fontWidth, 3*fontHeight);
		g.drawString("ENEMY LEFT:  " + CommandCenter.getInstance().getCurrentMap().Playbook.size(), fontWidth, 5*fontHeight);
		g.drawString("PLAYER HP:  " + CommandCenter.getInstance().getNumFalcons(), fontWidth, 7*fontHeight);

//		while (numFalcons > 0){
//			drawOneShipLeft(g, numFalcons--);
//		}
	}

	// Draw the number of falcons left on the bottom-right of the screen. Upside-down, but ok.
//	private void drawOneShipLeft(Graphics g, int offSet) {
//		Tank tank = CommandCenter.getInstance().getTank_player();
//
//		g.setColor(tank.getColor());
//
//		g.drawPolygon(
//					Arrays.stream(tank.getCartesians())
//							.map(pnt -> pnt.x + Game.DIM.width - (20 * offSet))
//							.mapToInt(Integer::intValue)
//							.toArray(),
//
//					Arrays.stream(tank.getCartesians())
//							.map(pnt -> pnt.y + Game.DIM.height - 40)
//							.mapToInt(Integer::intValue)
//							.toArray(),
//
//					tank.getCartesians().length);
//
//	}
	
	private void initView() {
		Graphics g = getGraphics();			// get the graphics context for the panel
		g.setFont(fnt);						// take care of some simple font stuff
		fmt = g.getFontMetrics();
		fontWidth = fmt.getMaxAdvance() + Game.FIELD_LENGTH;
		fontHeight = fmt.getHeight();
		g.setFont(fntBig);					// set font info
	}
	
	// This method draws some text to the middle of the screen before/after a game
	private void displayTextOnScreen() {

		strDisplay = "GAME OVER";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

		strDisplay = "use the arrow keys to turn and thrust";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ fontHeight + 40);

		strDisplay = "use the space bar to fire";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ fontHeight + 80);

		strDisplay = "'S' to Start";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ fontHeight + 120);

		strDisplay = "'P' to Pause";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ fontHeight + 160);

		strDisplay = "'Q' to Quit";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ fontHeight + 200);
//		strDisplay = "left pinkie on 'A' for Shield";
//		grpOff.drawString(strDisplay,
//				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
//						+ fontHeight + 240);

//		strDisplay = "'Numeric-Enter' for Hyperspace";
//		grpOff.drawString(strDisplay,
//				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
//						+ fontHeight + 280);
	}
	

}

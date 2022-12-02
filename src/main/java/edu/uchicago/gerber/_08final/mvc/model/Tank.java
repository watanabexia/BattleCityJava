package edu.uchicago.gerber._08final.mvc.model;

import edu.uchicago.gerber._08final.mvc.controller.Game;
import edu.uchicago.gerber._08final.mvc.controller.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



public class Tank extends Sprite {

	// ==============================================================
	// FIELDS 
	// ==============================================================
	
	private final int THRUST = 10; // must be multiple of 5

	private int RELOAD = 0;
	private final int RELOAD_FRAME = 20;

	public static final int FADE_INCREMENT = 3;
	public static final int FADE_INITIAL_VALUE = FADE_INCREMENT * 20;  //must be multiple of 3, less than 255

	private final int LEFT = 37,
					  UP = 38,
					  RIGHT = 39,
					  DOWN = 40;
	
	//private boolean shield = false;

	private final LinkedList<Integer> thrustingCommand = new LinkedList<>();
	private boolean thrusting = false;
	private boolean block_left = false;
	private boolean block_right = false;
	private boolean block_up = false;
	private boolean block_down = false;

	private final int LENGTH = Game.BLOCK_LENGTH;

	private boolean CPU = false;
	public LinkedList<Point> Targets;
	public int target_orientation;

	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	
	public Tank(Team team) {
		super();

		setTeam(team);

		switch(team) {
			case FRIEND:
				setFade(FADE_INITIAL_VALUE);
				break;
			case ENEMY:
				setFade(FADE_INCREMENT * 70);
				break;
		}

		Targets = new LinkedList<>();

		setWidth(LENGTH);
		setHeight(LENGTH);

		List<Point> pntCs = new ArrayList<>();
		// Robert Alef's awesome falcon design

		// todo: change the falcon to player tank shape
		pntCs.add(new Point(-40, 30));
		pntCs.add(new Point(-20, 30));
		pntCs.add(new Point(-20, -20));
		pntCs.add(new Point(-15, -20));
		pntCs.add(new Point(-15, -10));
		pntCs.add(new Point(-5, -10));
		pntCs.add(new Point(-5, 40));
		pntCs.add(new Point(5, 40));
		pntCs.add(new Point(5, -10));
		pntCs.add(new Point(15, -10));
		pntCs.add(new Point(15, -20));
		pntCs.add(new Point(20, -20));
		pntCs.add(new Point(20, 30));
		pntCs.add(new Point(40, 30));
		pntCs.add(new Point(40, -40));
		pntCs.add(new Point(20, -40));
		pntCs.add(new Point(20, -30));
		pntCs.add(new Point(15, -30));
		pntCs.add(new Point(15, -40));
		pntCs.add(new Point(-15, -40));
		pntCs.add(new Point(-15, -30));
		pntCs.add(new Point(-20, -30));
		pntCs.add(new Point(-20, -40));
		pntCs.add(new Point(-40, -40));

		setCartesians(pntCs);
	}

	@Override
	public boolean isProtected() {
		return getFade() < 255;
	}

	// ==============================================================
	// METHODS 
	// ==============================================================
	@Override
	public void move() {

		if (isProtected()) {
			setFade(getFade() + FADE_INCREMENT);
		}

		//apply some thrust vectors using trig.
		int adjustX = 0;
		int adjustY = 0;

		if (CPU) { // CPU is controlling this tank

			// Move to target destination
			if (!Targets.isEmpty()) { // didn't reach the final destination
				Point Center = getCenter();
				Point next_target = new Point(Targets.getFirst().x * Game.BLOCK_LENGTH + Game.BLOCK_LENGTH/2, Targets.getFirst().y * Game.BLOCK_LENGTH + Game.BLOCK_LENGTH/2);

				if (Center.x == next_target.x) {
					if (Center.y == next_target.y) { // Reached the next destination
						Targets.removeFirst();
					} else if (Center.y < next_target.y) { // Need to go down
						faceDown();
						if (!block_down) {
							adjustX = 0;
							adjustY = THRUST;
						}
					} else { // Need to go up
						faceUp();
						if (!block_up) {
							adjustX = 0;
							adjustY = -THRUST;
						}
					}
				} else if (Center.y == next_target.y) {
					if (Center.x == next_target.x) { // Reached the next destination
						Targets.removeFirst();
					} else if (Center.x < next_target.x) { // Need to go right
						faceRight();
						if (!block_right) {
							adjustX = THRUST;
							adjustY = 0;
						}
					} else {
						faceLeft();
						if (!block_left) {
							adjustX = -THRUST;
							adjustY = 0;
						}
					}
				}
			} else {
				setOrientation(target_orientation);
			}

			// Randomly fire, 25% Chance
			if (Game.R.nextInt(6) == 0) {
				fire();
			}

		} else {
			if (!thrustingCommand.isEmpty()) {

				//Debug
//			System.out.println(thrustingCommand);

				switch (thrustingCommand.getFirst()) {
					case LEFT:
						faceLeft();
						if (!block_left) {
							adjustX = -THRUST;
							adjustY = 0;
						}
						break;
					case DOWN:
						faceDown();
						if (!block_down) {
							adjustX = 0;
							adjustY = THRUST;
						}
						break;
					case RIGHT:
						faceRight();
						if (!block_right) {
							adjustX = THRUST;
							adjustY = 0;
						}
						break;
					case UP:
						faceUp();
						if (!block_up) {
							adjustX = 0;
							adjustY = -THRUST;
						}

						break;
				}
			}
		}

		setDeltaX(adjustX);
		setDeltaY(adjustY);

		super.move();

		if (RELOAD > 0) {
			reload();
		}

	} //end move

	public void fire() {
		if (RELOAD == 0) {
			CommandCenter.getInstance().getOpsQueue().enqueue(new Bullet(this, Bullet.TYPE.NORMAL), GameOp.Action.ADD);
			Sound.playSound("laser.wav");
			RELOAD = RELOAD_FRAME;
		}
	}

	private void reload() {
		RELOAD -= 1;
	}

	public void thrustOn(int DIRECTION) {
		if (thrustingCommand.indexOf(DIRECTION) == -1) {
			thrustingCommand.addFirst(DIRECTION);
		}
	}

	public void thrustOff(int DIRECTION) {
		thrustingCommand.remove((Integer) DIRECTION);
	}

	public void clear_block() {
		block_left = false;
		block_right = false;
		block_up = false;
		block_down = false;
	}

	public void blockUp() { block_up = true; }

	public void blockDown() { block_down = true; }

	public void blockLeft() { block_left = true; }

	public void blockRight() { block_right = true; }

	public void faceUp() { setOrientation(270); }

	public void faceLeft() { setOrientation(180); }

	public void faceRight() { setOrientation(0); }

	public void faceDown() { setOrientation(90); }

	public void setCPU(boolean CPU) {
		this.CPU = CPU;
	}

	private int adjustColor(int colorNum, int adjust) {
		return Math.max(colorNum - adjust, 0);
	}

	@Override
	public void draw(Graphics g) {

		Color colShip;
		if (getFade() == 255) {

			if (getTeam() == Team.FRIEND) {
				colShip = Color.YELLOW;
			} else {
				colShip = getColor(); //get native color of the sprite
			}
		}
		//flash to warn player of impending non-protection
		else if (getFade() > 220 && getFade() % 9 == 0 ){
			colShip = new Color(0, 32, 128); //dark blue
		}
		//some increasingly lighter shade of blue
		else {
			colShip = new Color(

					adjustColor(getFade(), 200), //red
					adjustColor(getFade(), 175), //green
					getFade() //blue
			);
		}

		draw(g,colShip);

	} //end draw()

	@Override
	public String toString() {
		return "Tank{" +
				"CENTER:" + getCenter() + ", " +
				"SPEED:" + getDeltaX() + ", " + getDeltaY() + ", " +
				"Block: " + "U:" + block_up + " D:" + block_down + " L:" + block_left + " R:" + block_right + ", " +
				"WIDTH&HEIGHT: " + getWidth() + " " + getHeight() + ", " +
				'}';
	}
} //end class

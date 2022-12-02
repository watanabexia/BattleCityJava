package edu.uchicago.gerber._08final.mvc.controller;

import edu.uchicago.gerber._08final.mvc.model.*;
import edu.uchicago.gerber._08final.mvc.view.GamePanel;


import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

// ===============================================
// == This Game class is the CONTROLLER
// ===============================================

public class Game implements Runnable, KeyListener {

	// ===============================================
	// FIELDS
	// ===============================================

	public static final int BLOCK_LENGTH = 80; // MULTIPLE OF 4
	public static final int STAT_WIDTH = 5*BLOCK_LENGTH;
	public static final int FIELD_SIZE = 13;
	public static final int FIELD_LENGTH = FIELD_SIZE*BLOCK_LENGTH;
	public static final Dimension DIM = new Dimension(FIELD_LENGTH + STAT_WIDTH, FIELD_LENGTH + BLOCK_LENGTH); //the dimension of the game.
	private GamePanel gmpPanel;
	//this is used throughout many classes.
	public static Random R = new Random();

	public final static int ANI_DELAY = 40; // milliseconds between screen
											// updates (animation)

	public final static int FRAMES_PER_SECOND = 1000 / ANI_DELAY;

	private Thread animationThread;

	//todo this is state: move to CommandCenter
	private boolean muted = true;
	

	private final int PAUSE = 80, // p key
			QUIT = 81, // q key
			LEFT = 37, // rotate left; left arrow
			RIGHT = 39, // rotate right; right arrow
			UP = 38, // thrust; up arrow
			DOWN = 40, // ; down arrow
			START = 83, // s key
			FIRE = 32, // space key
			MUTE = 77; // m-key mute

	// for possible future use
	// HYPER = 68, 					// D key
	// SHIELD = 65, 				// A key
	// SPECIAL = 70; 					// fire special weapon;  F key

	private Clip clpThrust;
	private Clip clpMusicBackground;

	//spawn every 30 seconds
	private static final int SPAWN_NEW_SHIP_FLOATER = FRAMES_PER_SECOND * 30;



	// ===============================================
	// ==CONSTRUCTOR
	// ===============================================

	public Game() {

		gmpPanel = new GamePanel(DIM);
		gmpPanel.addKeyListener(this); //Game object implements KeyListener
		clpThrust = Sound.clipForLoopFactory("whitenoise.wav");
		clpMusicBackground = Sound.clipForLoopFactory("music-background.wav");

		//fire up the animation thread
		animationThread = new Thread(this); // pass the animation thread a runnable object, the Game object
		animationThread.start();
	

	}

	// ===============================================
	// ==METHODS
	// ===============================================

	public static void main(String args[]) {
		//typical Swing application start; we pass EventQueue a Runnable object.
		EventQueue.invokeLater(Game::new);
	}

	// Game implements runnable, and must have run method
	@Override
	public void run() {

		// lower animation thread's priority, thereby yielding to the "main" aka 'Event Dispatch'
		// thread which listens to keystrokes
		animationThread.setPriority(Thread.MIN_PRIORITY);

		// and get the current time
		long lStartTime = System.currentTimeMillis();

		// this thread animates the scene
		while (Thread.currentThread() == animationThread) {

			//Debug
//			System.out.println("[DRAW FRAME]");
//			System.out.println(CommandCenter.getInstance().isGameOver() + " " + CommandCenter.getInstance().getLevel());
//			System.out.println(CommandCenter.getInstance().getTank_player().getFade() + " " + CommandCenter.getInstance().getTank_player().isProtected());

			gmpPanel.update(gmpPanel.getGraphics()); // see GamePanel class

			checkCollisions();
			checkNewLevel();

			if(!CommandCenter.getInstance().isGameOver()) {
				CommandCenter.getInstance().addFrame_count();
				spawnTanks_enemy();
			}

			// surrqound the sleep() in a try/catch block
			// this simply controls delay time between
			// the frames of the animation
			try {
				// The total amount of time is guaranteed to be at least ANI_DELAY long.  If processing (update) 
				// between frames takes longer than ANI_DELAY, then the difference between lStartTime - 
				// System.currentTimeMillis() will be negative, then zero will be the sleep time
				lStartTime += ANI_DELAY;

				Thread.sleep(Math.max(0,
						lStartTime - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				// do nothing (bury the exception), and just continue, e.g. skip this frame -- no big deal
			}
		} // end while
	} // end run

	private void CollisionSide_border(Movable mov1) {
		Point pntCenter1;
		int height1, width1;

		pntCenter1 = mov1.getCenter();
		height1 = mov1.getHeight();
		width1 = mov1.getWidth();

		if (pntCenter1.x + width1/2 == FIELD_LENGTH) {
			((Tank) mov1).blockRight();
		}
		if (pntCenter1.x - width1/2 == 0) {
			((Tank) mov1).blockLeft();
		}
		if (pntCenter1.y + height1/2 == FIELD_LENGTH) {
			((Tank) mov1).blockDown();
		}
		if (pntCenter1.y - height1/2 == 0) {
			((Tank) mov1).blockUp();
		}
	}

	private void Collision_tank_border(java.util.List<Movable> Team1) {
		for (Movable mov1 : Team1) {
			CollisionSide_border(mov1);
		}
	}

	private int CollisionSide_location(Movable mov1, Movable mov2) {
		Point pntCenter1, pntCenter2;
		int x_diff, y_diff;
		int height1, height2, width1, width2;

		pntCenter1 = mov1.getCenter();
		pntCenter2 = mov2.getCenter();
		x_diff = Math.abs(pntCenter1.x - pntCenter2.x);
		y_diff = Math.abs(pntCenter1.y - pntCenter2.y);
		height1 = mov1.getHeight();
		width1 = mov1.getWidth();
		height2 = mov2.getHeight();
		width2 = mov2.getWidth();

		int overlap_x = x_diff - (width1 + width2)/2, overlap_y = y_diff - (height1 + height2)/2;

		if ((overlap_x <= 0) && (overlap_y <= 0)) {
			if (pntCenter1.x + width1/2 == pntCenter2.x - width2/2 && overlap_y < 0) {
				return RIGHT;
			}
			if (pntCenter1.x - width1/2 == pntCenter2.x + width2/2 && overlap_y < 0) {
				return LEFT;
			}
			if (pntCenter1.y + height1/2 == pntCenter2.y - height2/2 && overlap_x < 0) {
				return DOWN;
			}
			if (pntCenter1.y - height1/2 == pntCenter2.y + height2/2 && overlap_x < 0) {
				return UP;
			}
		}

		return -1;
	}

	private void Collision_tank_wall(java.util.List<Movable> Team1, java.util.List<Movable> Team2) {
		for (Movable mov1 : Team1) {
			for (Movable mov2 : Team2) {
				switch(CollisionSide_location(mov1, mov2)) {
					case RIGHT:
						((Tank) mov1).blockRight();
						break;
					case LEFT:
						((Tank) mov1).blockLeft();
						break;
					case DOWN:
						((Tank) mov1).blockDown();
						break;
					case UP:
						((Tank) mov1).blockUp();
						break;
				}
			}
		}
	}

	private int CollisionSide_orientation(Movable mov1, Movable mov2) {
		Point pntCenter1, pntCenter2;
		int x_diff, y_diff;
		int height1, height2, width1, width2;

		pntCenter1 = mov1.getCenter();
		pntCenter2 = mov2.getCenter();
		x_diff = Math.abs(pntCenter1.x - pntCenter2.x);
		y_diff = Math.abs(pntCenter1.y - pntCenter2.y);
		height1 = mov1.getHeight();
		width1 = mov1.getWidth();
		height2 = mov2.getHeight();
		width2 = mov2.getWidth();

		int overlap_x = x_diff - (width1 + width2)/2, overlap_y = y_diff - (height1 + height2)/2;

		if ((overlap_x <= 0) && (overlap_y <= 0)) {
			switch (mov1.getOrientation()) {
				case 0:
					return LEFT;
				case 90:
					return UP;
				case 180:
					return RIGHT;
				case 270:
					return DOWN;
				default:
					return -1;
			}
		} else {
			return -1;
		}
	}

	private void Collision_bullet_wall(java.util.List<Movable> Team1, java.util.List<Movable> Team2) {
		for (Movable mov1 : Team1) {
			for (Movable mov2 : Team2) {

				int collisionSide = CollisionSide_orientation(mov1, mov2);

				if (collisionSide != -1) {
					CommandCenter.getInstance().getOpsQueue().enqueue(mov1, GameOp.Action.REMOVE);

					//todo: possible bug when two bullets collide. multiple remove operations
					switch (collisionSide) {
						case LEFT:
							((Wall) mov2).hit_left();
							break;
						case RIGHT:
							((Wall) mov2).hit_right();
							break;
						case UP:
							((Wall) mov2).hit_up();
							break;
						case DOWN:
							((Wall) mov2).hit_down();
							break;
					}
				}
			}//end inner for
		}//end outer for
	}

	private void Collision_bullet_tank(java.util.List<Movable> Team1, java.util.List<Movable> Team2) {
		for (Movable mov1 : Team1) {
			for (Movable mov2 : Team2) {

				int collisionSide = CollisionSide_orientation(mov1, mov2);

				if (collisionSide != -1) {
					CommandCenter.getInstance().getOpsQueue().enqueue(mov1, GameOp.Action.REMOVE);

					if (!mov2.isProtected()) {
						CommandCenter.getInstance().getOpsQueue().enqueue(mov2, GameOp.Action.REMOVE);
					}
				}
			}//end inner for
		}//end outer for
	}

	private void FreeBlocks(java.util.List<Movable> tanks) {
		for (Movable tank : tanks) {
			((Tank) tank).clear_block();
		}
	}

	private void checkCollisions() {

		FreeBlocks(CommandCenter.getInstance().getMovFriends());
		FreeBlocks(CommandCenter.getInstance().getMovEnemies());

		Collision_tank_border(CommandCenter.getInstance().getMovFriends());
		Collision_tank_border(CommandCenter.getInstance().getMovEnemies());

		Collision_tank_wall(CommandCenter.getInstance().getMovFriends(), CommandCenter.getInstance().getMovWalls());
		Collision_tank_wall(CommandCenter.getInstance().getMovEnemies(), CommandCenter.getInstance().getMovWalls());
		Collision_tank_wall(CommandCenter.getInstance().getMovFriends(), CommandCenter.getInstance().getMovEnemies());
		Collision_tank_wall(CommandCenter.getInstance().getMovEnemies(), CommandCenter.getInstance().getMovFriends());
		Collision_tank_wall(CommandCenter.getInstance().getMovEnemies(), CommandCenter.getInstance().getMovEnemies());

		Collision_bullet_wall(CommandCenter.getInstance().getMovBullets_friend(), CommandCenter.getInstance().getMovWalls());
		Collision_bullet_wall(CommandCenter.getInstance().getMovBullets_enemy(), CommandCenter.getInstance().getMovWalls());

		Collision_bullet_tank(CommandCenter.getInstance().getMovBullets_friend(), CommandCenter.getInstance().getMovEnemies());
		Collision_bullet_tank(CommandCenter.getInstance().getMovBullets_enemy(), CommandCenter.getInstance().getMovFriends());

		Collision_bullet_tank(CommandCenter.getInstance().getMovBullets_friend(), CommandCenter.getInstance().getMovBullets_enemy());

		Collision_bullet_tank(CommandCenter.getInstance().getMovBullets_friend(), CommandCenter.getInstance().getMovHQs());
		Collision_bullet_tank(CommandCenter.getInstance().getMovBullets_enemy(), CommandCenter.getInstance().getMovHQs());

		processGameOpsQueue();

	}//end meth

	private void processGameOpsQueue() {

		//deferred mutation: these operations are done AFTER we have completed our collision detection to avoid
		// mutating the movable linkedlists while iterating them above
		while(!CommandCenter.getInstance().getOpsQueue().isEmpty()){
			GameOp gameOp =  CommandCenter.getInstance().getOpsQueue().dequeue();
			Movable mov = gameOp.getMovable();
			GameOp.Action action = gameOp.getAction();

			switch (mov.getTeam()){
				case HQ:
					if (action == GameOp.Action.ADD){
						CommandCenter.getInstance().getMovHQs().add(mov);
					} else { //GameOp.Operation.REMOVE
						CommandCenter.getInstance().getMovHQs().remove(mov);
						CommandCenter.getInstance().setOver(true);
					}
					break;
				case ENEMY:
					if (action == GameOp.Action.ADD){
						CommandCenter.getInstance().getMovEnemies().add(mov);
					} else { //GameOp.Operation.REMOVE

						CommandCenter.getInstance().setScore(CommandCenter.getInstance().getScore() + 100);

						CommandCenter.getInstance().getMovEnemies().remove(mov);
					}
					break;
				case FRIEND:
					if (action == GameOp.Action.ADD){
						CommandCenter.getInstance().getMovFriends().add(mov);
					} else { //GameOp.Operation.REMOVE
						if (mov instanceof Tank) {
							CommandCenter.getInstance().getMovFriends().remove(mov);
							CommandCenter.getInstance().initFalconAndDecrementFalconNum();
						}
						else {
							CommandCenter.getInstance().getMovFriends().remove(mov);
						}
					}
					break;
				case WALL:
					if (action == GameOp.Action.ADD){
						CommandCenter.getInstance().getMovWalls().add(mov);
					} else { //GameOp.Operation.REMOVE
						CommandCenter.getInstance().getMovWalls().remove(mov);
					}
					break;
				case BULLET_FRIEND:
					if (action == GameOp.Action.ADD){
						CommandCenter.getInstance().getMovBullets_friend().add(mov);
					} else { //GameOp.Operation.REMOVE
						CommandCenter.getInstance().getMovBullets_friend().remove(mov);
					}
					break;
				case BULLET_ENEMY:
					if (action == GameOp.Action.ADD){
						CommandCenter.getInstance().getMovBullets_enemy().add(mov);
					} else { //GameOp.Operation.REMOVE
						CommandCenter.getInstance().getMovBullets_enemy().remove(mov);
					}
					break;
			}

		}
	}

	private void spawnMap(Map map) {
		if (!CommandCenter.getInstance().isGameOver()) {
			spawnHQ(map);
			spawnWalls(map);
		}
	}

	private void spawnHQ(Map map) {
		CommandCenter.getInstance().getOpsQueue().enqueue(new HQ(map.getHQ_respawn()), GameOp.Action.ADD);
	}

	private void spawnTanks_enemy() {
		if (!CommandCenter.getInstance().isGameOver()) {
			Map currentMap = CommandCenter.getInstance().getCurrentMap();
			if (!currentMap.Playbook.isEmpty()) {

				//Debug
//				System.out.println(CommandCenter.getInstance().getFrame_count() + " " + currentMap.Playbook.getFirst());

				if (CommandCenter.getInstance().getFrame_count() == currentMap.Playbook.getFirst()) {
					// Spawn a new tank at this frame

					Tank newEnemy = new Tank(Movable.Team.ENEMY);

					Point Enemy_respawn = (currentMap.getEnemy_respawns().get(R.nextInt(currentMap.getEnemy_respawns().size())));

					newEnemy.setCenter(Enemy_respawn.x * BLOCK_LENGTH + BLOCK_LENGTH/2, Enemy_respawn.y * BLOCK_LENGTH + BLOCK_LENGTH/2);

					Point HQ = currentMap.getHQ_respawn();
					Point FinalTarget = currentMap.getTargets().get(R.nextInt(currentMap.getTargets().size()));

					if (FinalTarget.x == HQ.x) {
						if (FinalTarget.y < HQ.y) {
							newEnemy.target_orientation = 90;
						} else {
							newEnemy.target_orientation = 270;
						}
					} else if (FinalTarget.y == HQ.y) {
						if (FinalTarget.x < HQ.x) {
							newEnemy.target_orientation = 0;
						} else {
							newEnemy.target_orientation = 180;
						}
					}

					newEnemy.Targets = currentMap.getRounte(Enemy_respawn, FinalTarget);

					//Debug
//					System.out.println(newEnemy.Targets);

					newEnemy.setCPU(true);

					CommandCenter.getInstance().getOpsQueue().add(new GameOp(newEnemy, GameOp.Action.ADD));

					currentMap.Playbook.removeFirst();
				}
			}
		}
	}

	//this method spawns new Large (0) Asteroids
	private void spawnWalls(Map map) {
		for (Wall wall:
			 map.getWalls()) {
			CommandCenter.getInstance().getOpsQueue().enqueue(wall, GameOp.Action.ADD);
		}
	}

	//	private void spawnNewShipFloater() {
//
//		//appears more often as your level increases.
//		if ((System.currentTimeMillis() / ANI_DELAY) % (SPAWN_NEW_SHIP_FLOATER - CommandCenter.getInstance().getLevel() * 7L) == 0) {
//			CommandCenter.getInstance().getOpsQueue().enqueue(new NewShipFloater(), GameOp.Action.ADD);
//		}
//	}

	private boolean isLevelClear(){
		//if there are no more walls
		if (!CommandCenter.getInstance().isGameOver()) {
			if (CommandCenter.getInstance().getCurrentMap().Playbook.isEmpty() && CommandCenter.getInstance().getMovEnemies().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	private void checkNewLevel(){
		if (isLevelClear()) {
			//Debug
			System.out.println("Initialize new level");

			//more asteroids at each level to increase difficulty
			CommandCenter.getInstance().clearAll();

			CommandCenter.getInstance().setScore(CommandCenter.getInstance().getScore() + 1000 * CommandCenter.getInstance().getCurrentMap().getPlayer_hp());

			CommandCenter.getInstance().setLevel(CommandCenter.getInstance().getLevel() + 1);

			CommandCenter.getInstance().setFrame_count(0);

			Map currentMap = new Map(CommandCenter.getInstance().getLevel());

			CommandCenter.getInstance().setCurrentMap(currentMap);

			spawnMap(CommandCenter.getInstance().getCurrentMap());

			//set to one greater than number of falcons lives in your game as initFalconAndDecrementNum() also decrements
			CommandCenter.getInstance().setNumFalcons(currentMap.getPlayer_hp() + 1);
			CommandCenter.getInstance().initFalconAndDecrementFalconNum();

			//setFade e.g. protect the falcon so that player has time to avoid newly spawned asteroids.
			CommandCenter.getInstance().getTank_player().setFade(Tank.FADE_INITIAL_VALUE);

		}
	}

	// Varargs for stopping looping-music-clips
	private static void stopLoopingSounds(Clip... clpClips) {
		for (Clip clp : clpClips) {
			clp.stop();
		}
	}

	// ===============================================
	// KEYLISTENER METHODS
	// ===============================================

	@Override
	public void keyPressed(KeyEvent e) {
		Tank tank_player = CommandCenter.getInstance().getTank_player();
		int nKey = e.getKeyCode();

		//Debug
//		System.out.println(nKey);

		if (nKey == START && CommandCenter.getInstance().isGameOver())
			CommandCenter.getInstance().initGame();


		if (tank_player != null) {

			switch (nKey) {
			case PAUSE:
				CommandCenter.getInstance().setPaused(!CommandCenter.getInstance().isPaused());
				if (CommandCenter.getInstance().isPaused())
					stopLoopingSounds(clpMusicBackground, clpThrust);
				break;
			case QUIT:
				System.exit(0);
				break;
			case UP:
			case DOWN:
			case LEFT:
			case RIGHT:
				tank_player.thrustOn(nKey);
				if (!CommandCenter.getInstance().isPaused() && !CommandCenter.getInstance().isGameOver())
					clpThrust.loop(Clip.LOOP_CONTINUOUSLY);
				break;

			// possible future use
			// case KILL:
			// case SHIELD:
			// case NUM_ENTER:

			default:
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Tank tank_player = CommandCenter.getInstance().getTank_player();
		int nKey = e.getKeyCode();
		//Debug show the key-code in the console
//		 System.out.println(nKey);

		if (tank_player != null) {
			switch (nKey) {
			case FIRE:
				tank_player.fire();
				break;
			case LEFT:
			case RIGHT:
			case DOWN:
			case UP:
				tank_player.thrustOff(nKey);
				clpThrust.stop();
				break;
			case MUTE:
				if (!muted){
					stopLoopingSounds(clpMusicBackground);
				} 
				else {
					clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
				}
				muted = !muted;
				break;
			default:
				break;
			}
		}
	}

	@Override
	// does nothing, but we need it b/c of KeyListener contract
	public void keyTyped(KeyEvent e) {
	}

}



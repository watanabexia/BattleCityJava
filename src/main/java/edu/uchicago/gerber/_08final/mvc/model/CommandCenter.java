package edu.uchicago.gerber._08final.mvc.model;



import edu.uchicago.gerber._08final.mvc.controller.Game;
import edu.uchicago.gerber._08final.mvc.controller.Sound;
import lombok.Data;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

//the lombok @Data gives us automatic getters and setters on all members
@Data
public class CommandCenter {

	private  int numFalcons;
	private  int level;
	private  long score;
	private  boolean paused;
	private  boolean over = true;
	private long frame_count = 0;

	//the falcon is located in the movFriends list, but since we use this reference a lot, we keep track of it in a
	//separate reference. Use final to ensure that the falcon ref always points to the single falcon object on heap
	//Lombok will not provide setter methods on final members
	private final Tank tank_player = new Tank(Movable.Team.FRIEND);

	//lists containing our movables
	private Map currentMap;

	private final List<Movable> movHQs = new LinkedList<>();
	private final List<Movable> movFriends = new LinkedList<>();
	private final List<Movable> movWalls = new LinkedList<>();
	private final List<Movable> movEnemies = new LinkedList<>();
	private final List<Movable> movBullets_friend = new LinkedList<>();
	private final List<Movable> movBullets_enemy = new LinkedList<>();

	private final GameOpsQueue opsQueue = new GameOpsQueue();

	//singleton
	private static CommandCenter instance = null;

	// Constructor made private
	private CommandCenter() {}

    //this class maintains game state - make this a singleton.
	public static CommandCenter getInstance(){
		if (instance == null){
			instance = new CommandCenter();
		}
		return instance;
	}

	public void initGame(){
		clearAll();
		setLevel(0);
		setScore(0);
		setFrame_count(0);
		setPaused(false);
		setOver(false);
		Map currentMap = new Map(CommandCenter.getInstance().getLevel());
		CommandCenter.getInstance().setCurrentMap(currentMap);
	}

	public void initFalconAndDecrementFalconNum(){
		setNumFalcons(getNumFalcons() - 1);

		if (numFalcons == 0) {
			CommandCenter.getInstance().setOver(true);
		}

		if (isGameOver()) return;
		Sound.playSound("shipspawn.wav");
		tank_player.setFade(Tank.FADE_INITIAL_VALUE);
		//put falcon in the middle of the game-space
		Point Player_respawn = currentMap.getPlayer_respawn();

		//Debug
//		System.out.println(Player_respawn);

		tank_player.setCenter(new Point(Player_respawn.x*Game.BLOCK_LENGTH + Game.BLOCK_LENGTH/2, Player_respawn.y*Game.BLOCK_LENGTH + Game.BLOCK_LENGTH/2));
		tank_player.setOrientation(270);

		tank_player.setDeltaX(0);
		tank_player.setDeltaY(0);

		opsQueue.enqueue(tank_player, GameOp.Action.ADD);
	}

	public void clearAll(){
		movHQs.clear();
		movFriends.clear();
		movWalls.clear();
		movEnemies.clear();
		movBullets_friend.clear();
		movBullets_enemy.clear();
	}

	public boolean isGameOver() {		//if the number of falcons is zero, then game over
		return over;
	}

	public void addFrame_count() {
		frame_count++;
	}
}

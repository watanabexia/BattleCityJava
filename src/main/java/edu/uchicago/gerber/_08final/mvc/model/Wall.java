package edu.uchicago.gerber._08final.mvc.model;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.*;

import edu.uchicago.gerber._08final.mvc.controller.Game;


public class Wall extends Sprite {

	//radius of a large asteroid
	private final int LENGTH = Game.BLOCK_LENGTH;
	private final int PIECES_MAX = 4;
	private final int UNIT_LENGTH = LENGTH/(PIECES_MAX);

	private int map_x, map_y;

	private boolean[] horizon_pieces = {false, false, false, false}, vertical_pieces = {false, false, false, false};


	//size determines if the Asteroid is Large (0), Medium (1), or Small (2)
	//when you explode a Large asteroid, you should spawn 2 or 3 medium asteroids
	//same for medium asteroid, you should spawn small asteroids
	//small asteroids get blasted into debris, but do not spawn anything
	public Wall(int map_x, int map_y, int up, int down, int left, int right){

		//Asteroid is FOE
		setTeam(Team.WALL);

		this.map_x = map_x;
		this.map_y = map_y;

		for (int i = up; i < down; i++) {
			vertical_pieces[i] = true;
		}

		for (int i = left; i < right; i++) {
			horizon_pieces[i] = true;
		}

		reshape();
	}

	public void hit_left() {
		for (int i = 0; i < PIECES_MAX; i++) {
			if (horizon_pieces[i] == true) {
				horizon_pieces[i] = false;
				break;
			}
		}
		reshape();
	}

	public void hit_right() {
		for (int i = PIECES_MAX - 1; i >= 0; i--) {
			if (horizon_pieces[i] == true) {
				horizon_pieces[i] = false;
				break;
			}
		}
		reshape();
	}

	public void hit_up() {
		for (int i = 0; i < PIECES_MAX; i++) {
			if (vertical_pieces[i] == true) {
				vertical_pieces[i] = false;
				break;
			}
		}
		reshape();
	}

	public void hit_down() {
		for (int i = PIECES_MAX - 1; i >= 0; i--) {
			if (vertical_pieces[i] == true) {
				vertical_pieces[i] = false;
				break;
			}
		}
		reshape();
	}

	private void reshape() {
		int x = Game.BLOCK_LENGTH * map_x;
		int y = Game.BLOCK_LENGTH * map_y;

		int i = 0, j = 0;
		int width = 0, height = 0;
		while (i < PIECES_MAX && horizon_pieces[i] == false) {
			x += UNIT_LENGTH;
			i++;
		}
		while (i < PIECES_MAX && horizon_pieces[i] == true) {
				width += UNIT_LENGTH;
				i++;
			}
		x += width/2;
		while (j < PIECES_MAX && vertical_pieces[j] == false) {
			y += UNIT_LENGTH;
			j++;
		}
		while (j < PIECES_MAX && vertical_pieces[j] == true) {
			height += UNIT_LENGTH;
			j++;
		}
		y += height/2;

		if (width == 0 || height == 0) {
			CommandCenter.getInstance().getCurrentMap().map_info[map_x][map_y] = 0; // Map.EMPTY
			CommandCenter.getInstance().getOpsQueue().add(new GameOp(this, GameOp.Action.REMOVE));
		} else {
			setCenter(new Point(x, y));

			setWidth(width);
			setHeight(height);

			List<Point> pntCs = new ArrayList<>();

			pntCs.add(new Point( -height/2, width/2));
			pntCs.add(new Point(-height/2, -width/2));
			pntCs.add(new Point(height/2, -width/2));
			pntCs.add(new Point(height/2, width/2));

			setCartesians(pntCs);
		}
	}
}

package edu.uchicago.gerber._08final.mvc.model;

import java.awt.*;

public interface Movable {

	enum Team {
		HQ, FRIEND, ENEMY, WALL, BULLET_FRIEND, BULLET_ENEMY, FLOATER
	}

	//for the game to move and draw movable objects
	void move();
	void draw(Graphics g);

	//for collision detection
	void setCenter(int x, int y);
	Point getCenter();
	int getRadius();
	int getHeight();
	int getWidth();
	int getDeltaX();
	int getDeltaY();
	int getOrientation();
	Team getTeam();
	boolean isProtected();


} //end Movable

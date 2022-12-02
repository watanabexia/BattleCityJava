package edu.uchicago.gerber._08final.mvc.model;

import edu.uchicago.gerber._08final.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bullet extends Sprite {

    public final int FIRE_POWER = 20;

    public enum TYPE {
        NORMAL
    }

    private TYPE type;

    @Override
    public void move() {

        Point center = getCenter();
        int x = center.x;
        int y = center.y;

        if (x >= Game.FIELD_LENGTH || x <= 0 || y >= Game.FIELD_LENGTH || y <= 0) {
            CommandCenter.getInstance().getOpsQueue().add(new GameOp(this, GameOp.Action.REMOVE));
        }

        super.move();
    }

    private void init_bullet_proerty() {

        //a bullet expires after 20 frames. set to one more than frame expiration
//        setExpiry(21);

        //defined the points on a cartesian grid
        List<Point> pntCs = new ArrayList<>();

        switch (type) {
            case NORMAL:
                setWidth(10);
                setHeight(10);
                pntCs.add(new Point(0, 7)); //top point
                pntCs.add(new Point(5, -1));
                pntCs.add(new Point(0, -2));
                pntCs.add(new Point(-5, -1));
                break;
        }

        setCartesians(pntCs);
    }

    public Bullet(Tank tank, TYPE type) {

        switch (tank.getTeam()) {
            case FRIEND:
                setTeam(Team.BULLET_FRIEND);
                break;
            case ENEMY:
                setTeam(Team.BULLET_ENEMY);
                break;
        }

        this.type = type;

        init_bullet_proerty();

        //everything is relative to the falcon ship that fired the bullet
        Point tankCenter = tank.getCenter();

        switch (tank.getOrientation()) {
            case 0:
                setCenter(new Point(tankCenter.x + tank.getWidth()/2, tankCenter.y));
                break;
            case 90:
                setCenter(new Point(tankCenter.x, tankCenter.y + tank.getHeight()/2));
                break;
            case 180:
                setCenter(new Point(tankCenter.x - tank.getWidth()/2, tankCenter.y));
                break;
            case 270:
                setCenter(new Point(tankCenter.x, tankCenter.y - tank.getHeight()/2));
                break;
        }

        //set the bullet orientation to the falcon (ship) orientation
        setOrientation(tank.getOrientation());

        setDeltaX((int) (tank.getDeltaX() +
                Math.cos(Math.toRadians(tank.getOrientation())) * FIRE_POWER));
        setDeltaY((int) (tank.getDeltaY() +
                Math.sin(Math.toRadians(tank.getOrientation())) * FIRE_POWER));
    }
}

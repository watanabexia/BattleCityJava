package edu.uchicago.gerber._08final.mvc.model;

import edu.uchicago.gerber._08final.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HQ extends Sprite {

    public HQ(Point respawn) {
        setTeam(Team.HQ);

        setCenter(respawn.x * Game.BLOCK_LENGTH + Game.BLOCK_LENGTH/2, respawn.y * Game.BLOCK_LENGTH + Game.BLOCK_LENGTH/2);

        //defined the points on a cartesian grid
        List<Point> pntCs = new ArrayList<>();

        setWidth(Game.BLOCK_LENGTH);
        setHeight(Game.BLOCK_LENGTH);
        pntCs.add(new Point(0, 40)); //top point
        pntCs.add(new Point(40, -40));
        pntCs.add(new Point(-40, 10));
        pntCs.add(new Point(40, 10));
        pntCs.add(new Point(-40, -40));

        setCartesians(pntCs);

    }

}

package edu.uchicago.gerber._08final.mvc.model;

import edu.uchicago.gerber._08final.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map {
    private final ArrayList<Wall> walls = new ArrayList<>();
//    private ArrayList<Tank> tanks_enemy;

    private final ArrayList<Point> Targets = new ArrayList<>();
    private final ArrayList<Point> Enemy_respawns = new ArrayList<>();
//    private final LinkedList<Tank> Enemies = new LinkedList<>();
    public final LinkedList<Integer> Playbook = new LinkedList<>();
    private Point Player_respawn, HQ_respawn;
    private int Player_hp;

    public ArrayList<Point> getEnemy_respawns() {return Enemy_respawns; }

    public Point getPlayer_respawn() {
        return Player_respawn;
    }

    public Point getHQ_respawn() {
        return HQ_respawn;
    }

    public ArrayList<Point> getTargets() { return Targets; }

    public int[][] map_info = new int[Game.FIELD_SIZE][Game.FIELD_SIZE];

    private final int EMPTY = 0, HQ = 1, PLAYER = 2, ENEMY = 3, WALL = 4, TARGET = 5;

    public ArrayList<Wall> getWalls() {
        return walls;
    }

//    public ArrayList<Tank> getTanks_enemy() {
//        return tanks_enemy;
//    }

    private void initialize_map_info() {
        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            for (int j = 0; j < Game.FIELD_SIZE; j++) {
                map_info[i][j] = EMPTY;
            }
        }
    }

    private void generate_map_element() {
        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            for (int j = 0; j < Game.FIELD_SIZE; j++) {
                switch (map_info[i][j]) {
                    case WALL:
                        walls.add(new Wall(i,j,0,4,0,4));
                        break;
                    case HQ:
                        HQ_respawn = new Point(i,j);
                        break;
                    case PLAYER:
                        Player_respawn = new Point(i,j);
                        break;
                    case ENEMY:
                        Enemy_respawns.add(new Point(i,j));
                        break;
                    case TARGET:
                        Targets.add(new Point(i,j));
                        break;

                }
            }
        }
    }

    private void generate_map_element_MPCS() {
        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            for (int j = 0; j < Game.FIELD_SIZE; j++) {
                switch (map_info[i][j]) {
                    case HQ:
                        HQ_respawn = new Point(i,j);
                        break;
                    case PLAYER:
                        Player_respawn = new Point(i,j);
                        break;
                    case ENEMY:
                        Enemy_respawns.add(new Point(i,j));
                        break;
                    case TARGET:
                        Targets.add(new Point(i,j));
                        break;

                }
            }
        }
    }


    private final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;

    public LinkedList<Point> getRounte(Point s, Point d) {
        int[][] map_route = new int[Game.FIELD_SIZE][Game.FIELD_SIZE];
        boolean[][] map_route_visited = new boolean[Game.FIELD_SIZE][Game.FIELD_SIZE];

        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            for (int j = 0; j < Game.FIELD_SIZE; j++) {
                map_route[i][j] = -1;
                map_route_visited[i][j] = false;
            }
        }

        getRoute_Visits(s, d, map_route, map_route_visited);

        LinkedList<Point> route = new LinkedList<>();

        Point p = (Point) d.clone();
        while (p.x != s.x || p.y != s.y) {
            route.addFirst( new Point(p.x, p.y));
            switch (map_route[p.x][p.y]) {
                case UP:
                    p.y--;
                    break;
                case DOWN:
                    p.y++;
                    break;
                case LEFT:
                    p.x--;
                    break;
                case RIGHT:
                    p.x++;
                    break;
            }
        }

        //Debug
//        System.out.println(s + " " + d);
//        for (int i = 0; i < Game.FIELD_SIZE; i++) {
//            for (int j = 0; j < Game.FIELD_SIZE; j++) {
//                if (map_route[j][i] >= 0) {
//                    System.out.print(map_route[j][i] + " ");
//                } else {
//                    System.out.print("X" + " ");
//                }
//
//            }
//            System.out.println();
//        }
//        System.out.println(route);

        return route;
    }

    private void getRoute_Visits(Point p, Point d, int[][] map_route, boolean[][] map_route_visited) {
        map_route_visited[p.x][p.y] = true;

        if (p.x+1 < Game.FIELD_SIZE && !map_route_visited[p.x+1][p.y] && map_info[p.x+1][p.y] != WALL) {
            map_route[p.x+1][p.y] = LEFT;
            getRoute_Visits(new Point(p.x+1, p.y), d, map_route, map_route_visited);
        }
        if (p.x-1 >= 0 && !map_route_visited[p.x-1][p.y] && map_info[p.x-1][p.y] != WALL) {
            map_route[p.x-1][p.y] = RIGHT;
            getRoute_Visits(new Point(p.x-1, p.y), d, map_route, map_route_visited);
        }
        if (p.y+1 < Game.FIELD_SIZE && !map_route_visited[p.x][p.y+1] && map_info[p.x][p.y+1] != WALL) {
            map_route[p.x][p.y+1] = UP;
            getRoute_Visits(new Point(p.x, p.y+1), d, map_route, map_route_visited);
        }
        if (p.y-1 >= 0 && !map_route_visited[p.x][p.y-1] && map_info[p.x][p.y-1] != WALL) {
            map_route[p.x][p.y-1] = DOWN;
            getRoute_Visits(new Point(p.x, p.y-1), d, map_route, map_route_visited);
        }
    }

//    private void getRoute_Visit(Point p, Point d, int[][] map_route, boolean[][] map_route_visited) {
//
//    }

    public Map(int no) {
        initialize_map_info();

        switch (no) {
            case 0:
                break;
            case 1:
                Player_hp = 3;

                for (int i = 0; i < Game.FIELD_SIZE; i++) {
                    for (int j = 0; j < Game.FIELD_SIZE; j++) {
                        if ((i%2 != 0) && ((j >= 1 && j <= 4) || (j >= 8 && j <= 11))) {
                            map_info[i][j] = WALL;
                        }
                    }
                }

                map_info[0][6] = WALL;
                map_info[2][6] = WALL;
                map_info[3][6] = WALL;
                map_info[5][6] = WALL;
                map_info[7][6] = WALL;
                map_info[9][6] = WALL;
                map_info[10][6] = WALL;
                map_info[12][6] = WALL;

                map_info[5][7] = WALL;
                map_info[6][7] = WALL;
                map_info[7][7] = WALL;
                map_info[5][10] = EMPTY;
                map_info[7][10] = EMPTY;

                map_info[6][11] = WALL;
                map_info[5][12] = WALL;
                map_info[7][12] = WALL;

                map_info[6][12] = HQ;

                map_info[4][12] = PLAYER;

                map_info[0][0] = ENEMY;
                map_info[6][2] = ENEMY;
                map_info[12][0] = ENEMY;

                map_info[0][12] = TARGET;
                map_info[1][12] = TARGET;
                map_info[2][12] = TARGET;
                map_info[3][12] = TARGET;
                map_info[6][8] = TARGET;
                map_info[6][9] = TARGET;
                map_info[6][10] = TARGET;
                map_info[8][12] = TARGET;
                map_info[9][12] = TARGET;
                map_info[10][12] = TARGET;
                map_info[11][12] = TARGET;
                map_info[12][12] = TARGET;

                Playbook.addLast(5);
                Playbook.addLast(200);
                Playbook.addLast(400);
                Playbook.addLast(600);
                Playbook.addLast(800);
                Playbook.addLast(1000);

                generate_map_element();

                break;
            case 2:
                Player_hp = 3;

                map_info[1][2] = WALL;
                walls.add(new Wall(1,2, 0, 4, 2, 4));
                map_info[2][2] = WALL;
                walls.add(new Wall(2,2, 2, 4, 0, 2));
                map_info[3][2] = WALL;
                walls.add(new Wall(3,2, 2, 4, 2, 4));
                map_info[4][2] = WALL;
                walls.add(new Wall(4,2, 0, 4, 0, 2));
                map_info[6][2] = WALL;
                walls.add(new Wall(6,2, 0, 4, 0, 4));
                map_info[7][2] = WALL;
                walls.add(new Wall(7,2, 0, 2, 0, 4));
                map_info[8][2] = WALL;
                walls.add(new Wall(8,2, 0, 4, 0, 2));
                map_info[1][3] = WALL;
                walls.add(new Wall(1,3, 0, 4, 2, 4));
                map_info[2][3] = WALL;
                walls.add(new Wall(2,3, 0, 4, 2, 4));
                map_info[3][3] = WALL;
                walls.add(new Wall(3,3, 0, 4, 0, 2));
                map_info[4][3] = WALL;
                walls.add(new Wall(4,3, 0, 4, 0, 2));
                map_info[6][3] = WALL;
                walls.add(new Wall(6,3, 0, 4, 0, 4));
                map_info[7][3] = WALL;
                walls.add(new Wall(7,3, 2, 4, 0, 4));
                map_info[8][3] = WALL;
                walls.add(new Wall(8,3, 0, 4, 0, 2));
                map_info[1][4] = WALL;
                walls.add(new Wall(1,4, 0, 4, 2, 4));
//                map_info[3][4] = WALL;
//                walls.add(new Wall(3,4, 0, 2, 0, 2));
                map_info[4][4] = WALL;
                walls.add(new Wall(4,4, 0, 4, 0, 2));
                map_info[6][4] = WALL;
                walls.add(new Wall(6,4, 0, 4, 0, 2));
                map_info[1][6] = WALL;
                walls.add(new Wall(1,6, 0, 4, 2, 4));
                map_info[2][6] = WALL;
                walls.add(new Wall(2,6, 0, 2, 0, 4));
                map_info[3][6] = WALL;
                walls.add(new Wall(3,6, 0, 2, 0, 4));
                map_info[4][6] = WALL;
                walls.add(new Wall(4,6, 0, 2, 0, 2));
                map_info[6][6] = WALL;
                walls.add(new Wall(6,6, 0, 4, 0, 4));
                map_info[7][6] = WALL;
                walls.add(new Wall(7,6, 0, 2, 0, 4));
                map_info[8][6] = WALL;
                walls.add(new Wall(8,6, 0, 2, 0, 2));
                map_info[1][7] = WALL;
                walls.add(new Wall(1,7, 0, 4, 2, 4));
//                map_info[4][7] = WALL;
//                walls.add(new Wall(4,7, 0, 4, 0, 4));
                map_info[6][7] = WALL;
                walls.add(new Wall(6,7, 0, 2, 0, 4));
                map_info[7][7] = WALL;
                walls.add(new Wall(7,7, 0, 2, 0, 4));
                map_info[8][7] = WALL;
                walls.add(new Wall(8,7, 0, 4, 0, 2));
                map_info[1][8] = WALL;
                walls.add(new Wall(1,8, 0, 4, 2, 4));
                map_info[2][8] = WALL;
                walls.add(new Wall(2,8, 2, 4, 0, 4));
                map_info[3][8] = WALL;
                walls.add(new Wall(3,8, 2, 4, 0, 4));
                map_info[4][8] = WALL;
                walls.add(new Wall(4,8, 2, 4, 0, 2));
                map_info[6][8] = WALL;
                walls.add(new Wall(6,8, 2, 4, 0, 4));
                map_info[7][8] = WALL;
                walls.add(new Wall(7,8, 2, 4, 0, 4));
                map_info[8][8] = WALL;
                walls.add(new Wall(8,8, 0, 4, 0, 2));

                map_info[3][7] = HQ;

                map_info[1][11] = PLAYER;

                map_info[11][3] = ENEMY;
                map_info[11][8] = ENEMY;
                map_info[11][12] = ENEMY;

                map_info[0][7] = TARGET;
                map_info[5][7] = TARGET;
                map_info[9][7] = TARGET;
                map_info[10][7] = TARGET;
                map_info[11][7] = TARGET;
                map_info[12][7] = TARGET;
                map_info[3][0] = TARGET;
                map_info[3][1] = TARGET;
                map_info[3][5] = TARGET;
                map_info[3][9] = TARGET;
                map_info[3][10] = TARGET;
                map_info[3][11] = TARGET;
                map_info[3][12] = TARGET;

                generate_map_element_MPCS();

                Playbook.addLast(5);
                Playbook.addLast(200);
                Playbook.addLast(300);
                Playbook.addLast(400);
                Playbook.addLast(500);
                Playbook.addLast(600);
                Playbook.addLast(700);
                Playbook.addLast(800);
                Playbook.addLast(850);
                Playbook.addLast(900);

                break;
            case 3:

                //Debug
                System.out.println("LEVEL3 OVER!");

                CommandCenter.getInstance().setOver(true);
                break;
        }
    }

    public int getPlayer_hp() {
        return Player_hp;
    }
}

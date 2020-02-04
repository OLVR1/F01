/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.initial.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {


    public final int width;
    public final int height;
    private static int countMoves = 0;

    private Space[][] spaces;
    private Space active;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y, (x+y)%2 == 0);
                spaces[x][y] = space;
            }
        }
        if (width > 0 && height > 0) {
            Space space = getSpace(0,0);
            space.isActive();
            active = space;
        }
    }

    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public Space getActive() {
        return active;
    }

    public void setActive(Space space) {
        if (space.board == this) {
            if (active != null && active != space) {
                active.setActive(false);
                active = space;
                active.setActive(true);
                countMoves++;



                // not needed for current version; but for being clear,
                // we add a notifyChange() here anyway!
                notifyChange();


            }
        }

    }

    public int getCounter(){
        return countMoves;
    }

}

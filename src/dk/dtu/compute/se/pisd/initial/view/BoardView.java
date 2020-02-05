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
package dk.dtu.compute.se.pisd.initial.view;

import com.sun.istack.internal.NotNull;
import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.initial.model.Board;
import dk.dtu.compute.se.pisd.initial.model.Space;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.nio.file.AtomicMoveNotSupportedException;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class BoardView extends GridPane implements Observer {

    private Board board;
    private SpaceView[][] spaces;
    private Label label;
    private GridPane gridPane;


    public BoardView(Board board) {
        this.board = board;

        gridPane = new GridPane();
        label = new Label();

        this.add(gridPane,0,0);
        this.add(label, 0, 1);

        spaces = new SpaceView[board.width][board.height];

        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                Space space = board.getSpace(x, y);
                SpaceView spaceView = new SpaceView(space);
                spaces[x][y] = spaceView;
                gridPane.add(spaceView, x, y);
            }
        }

        label.setText("Moves:" + board.getCounter());

        // not needed for now since only spaces change
        board.attach(this);
    }

    public SpaceView getSpaceView(Space space) {
        if (space != null && space.board == this.board &&
                space.x < board.width && space.y < board.height ) {
            SpaceView spaceView = spaces[space.x][space.y];
            if (spaceView.space == space) {
                return spaceView;
            }
        }
        return null;
    }

    @Override
    public void update(Subject subject) {
        if (subject == board) {
            if (Platform.isFxApplicationThread()) {

                label.setText("Moves:" + board.getCounter());
                // here the view should be updated (e.g. the status line)
            } else {
                Platform.runLater(() -> {
                    label.setText("Moves:" + board.getCounter()) ;
                    // here the view should be updated (e.g. the status line)
                });
            }
        }
    }
}

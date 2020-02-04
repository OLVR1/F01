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
package dk.dtu.compute.se.pisd.initial;

import dk.dtu.compute.se.pisd.initial.controller.SpaceEventHandler;

import dk.dtu.compute.se.pisd.initial.model.Board;
import dk.dtu.compute.se.pisd.initial.model.Space;

import dk.dtu.compute.se.pisd.initial.view.BoardView;
import dk.dtu.compute.se.pisd.initial.view.SpaceView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class InitialApplication extends Application {

    private Board board;
    private BoardView boardView;

    private SpaceEventHandler spaceEventHandler = new SpaceEventHandler();

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // creates the model
        board = new Board(8, 8);
        board.getSpace(0,0).setActive(true);

        // create the primary scene
        primaryStage.setTitle("Initial Application");
        BorderPane root = new BorderPane();
        Scene primaryScene = new Scene(root);
        primaryStage.setScene(primaryScene);

        // creates the view (for the model)
        boardView = new BoardView(board);

        // add the view and show it
        root.setCenter(boardView);
        primaryStage.show();

        // add the controllers to handle user events (clicks)
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                Space space = board.getSpace(x,y);
                SpaceView spaceView = boardView.getSpaceView(space);
                if (spaceView != null) {
                    spaceView.setOnAction(spaceEventHandler);
                }
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
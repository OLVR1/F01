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

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.initial.model.Space;

import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends Button implements Observer {


    public final Space space;


    public SpaceView(Space space) {
        this.space = space;


        // TODO the following styling should better be done with styles
        this.setPrefWidth(50);
        this.setPrefHeight(50);
        setStyle();

        // This space view should listen to changes of the space
        space.attach(this);
    }

    private void setStyle() {
        if (space.isActive()) {
            this.setStyle("-fx-background-color: red;");
        } else if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }
    }

    @Override
    public void update(Subject subject) {
        if (subject == this.space) {
            // update the style of the space according to the changes;
            // note that the update needs to be done in the FX application thread.
            if (Platform.isFxApplicationThread()) {
                setStyle();
            } else {
                Platform.runLater(() -> {
                    setStyle();
                });
            }
        }
    }

}

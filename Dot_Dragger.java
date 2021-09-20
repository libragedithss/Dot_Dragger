 

/**
 * Based on demo http://download.oracle.com/otndocs/products/javafx/2/samples/Ensemble/index.html#SAMPLES/Scenegraph/Events/Mouse Events
 * with next license:
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.RectangleBuilder;

public class Dot_Dragger extends Application {

    //variables for storing initial position before drag of circle
    private double initX;
    private double initY;
    private Group root = new Group();

    private void init(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 500, 400));
        
        // do "rectangle"
        final Circle c1 = createCircle(Color.CORAL, 5, 250, 150);
        final Circle c2 = createCircle(Color.DODGERBLUE, 5, 250, 250);
        final Circle c3 = createCircle(Color.GREEN, 5, 150, 250);
        final Circle c4 = createCircle(Color.MAGENTA, 5, 150, 150);

        // show all the circle , rectangle and console
        root.getChildren().addAll(c1, c2, c3, c4);
        
        linkCircles(c1, c2);
        linkCircles(c2, c3);
        linkCircles(c3, c4);
        linkCircles(c4, c1);
    }

    private void linkCircles(Circle c1, Circle c2) {
        Line link = new Line();
        link.setFill(Color.RED);
        link.setStrokeWidth(3);
        link.startXProperty().bind(c1.centerXProperty());
        link.startYProperty().bind(c1.centerYProperty());
        link.endXProperty().bind(c2.centerXProperty());
        link.endYProperty().bind(c2.centerYProperty());
        root.getChildren().add(link);
        link.toBack();
    }

    private Circle createCircle(final Color color, int radius, int x, int y) {
        //create a circle with desired name,  color and radius
        final Circle circle = new Circle(radius, new RadialGradient(0, 0, 0.2, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
            new Stop(0, Color.rgb(250, 250, 255)),
            new Stop(1, color)
        }));
        circle.setCenterX(x);
        circle.setCenterY(y);
        //add a shadow effect
        circle.setEffect(new InnerShadow(7, color.darker().darker()));
        //change a cursor when it is over circle
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX();
                double dragY = me.getSceneY();
                //calculate new position of the circle
                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;
                //if new position do not exceeds borders of the rectangle, translate to this position
                if ((newXPosition >= circle.getRadius())){
                    circle.setCenterX(newXPosition);
                }
                if ((newYPosition >= circle.getRadius())){
                    circle.setCenterY(newYPosition);
                }
            }
        });
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //change the z-coordinate of the circle
                circle.toFront();
            }
        });
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //when mouse is pressed, store initial position
                initX = circle.getTranslateX();
                initY = circle.getTranslateY();
            }
        });

        return circle;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
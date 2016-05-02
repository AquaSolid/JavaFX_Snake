package Snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by filip on 23.04.2016.
 */
public class Snake {

    @FXML private Button buttonBackToMenu;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static final int BLOCK_SIZE = 30;
    public static final int APP_W = 20 * BLOCK_SIZE;
    public static final int APP_H = 15 * BLOCK_SIZE;

    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;

    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;

    // Additions
    private int points;
    private GameTimer gameTimer = new GameTimer();
    private Label labelPoints = new Label();
    private Label labelTime = new Label();

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        javafx.scene.shape.Rectangle food = new javafx.scene.shape.Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(javafx.scene.paint.Color.BLUE);
        food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labelTime.setText("Time: "+gameTimer.getTime());
            }
        });

        KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
            if (!running){
                return;
            }

            boolean toRemove = snake.size() > 1;

            Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (direction){
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if (toRemove){
                snake.add(0, tail);
            }

            // Collision Detection
            for (Node rect : snake){
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX() && tail.getTranslateY() == rect.getTranslateY()){
                    restartGame();
                    break;
                }
            }

            // Collision Detection for Walls
            if (tail.getTranslateX() < 0 || tail.getTranslateX() >= APP_W
                    || tail.getTranslateY() < 0 || tail.getTranslateY() >= APP_H){
                restartGame();
            }

            if (tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()){
                food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
                food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                points++;
                labelPoints.setText("Points: "+points);


                snake.add(rect);
            }
            labelTime.setText("Time: "+gameTimer.getTime());
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        HBox hboxTop = new HBox();
        HBox hboxBottom = new HBox();
        root.getChildren().addAll(food, snakeBody);

        // Bottom Buttons
        Button buttonBack  = new Button("← Back");
        Button buttonScore = new Button("Score Board");
        Button buttonTwoPlayers = new Button("Two Player");

        buttonBack.setOnAction(event -> {
            Stage stage = (Stage) buttonBack.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(Controller.class);
            Parent menu = null;
            try {
                menu = FXMLLoader.load(getClass().getResource("../Snake/Menu.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Snake");
            stage.setScene(new Scene(menu, 600, 400));
            System.out.print("Back To Menu From Instructions ");
        });

        buttonBack.setStyle("-fx-pref-width: 100;");
        buttonScore.setStyle("-fx-pref-width: 100;");
        buttonTwoPlayers.setStyle("-fx-pref-width: 100;");

        hboxBottom.setAlignment(Pos.CENTER);
        hboxBottom.setStyle(
                "-fx-spacing: 30;" +
                "-fx-border-width: 1 0 0 0;" +
                "-fx-padding: 10;" +
                "-fx-background-color: white;");
        hboxBottom.getChildren().addAll(buttonBack, buttonScore, buttonTwoPlayers);



        hboxTop.setAlignment(Pos.BOTTOM_LEFT);
        hboxTop.setStyle(
                "-fx-spacing: 30;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-padding: 10;" +
                "-fx-background-color: white;");
        hboxTop.getChildren().addAll(labelPoints, labelTime);

        root.setStyle("-fx-background-color: GainsBoro;");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hboxTop, root, hboxBottom);
        return  vBox;
    }

    private void restartGame(){
        stopGame();
        startGame();
    }

    private void stopGame(){
        gameTimer.stopTimer();

        running = false;
        timeline.stop();
        snake.clear();
    }

    private void startGame(){
        direction = Direction.RIGHT;
        javafx.scene.shape.Rectangle head = new javafx.scene.shape.Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        snake.add(head);
        timeline.play();

        points = 0;
        gameTimer.startTimer();

        labelPoints.setStyle("-fx-font-size: 18 ;");
        labelTime.setStyle("-fx-font-size: 18 ;");
        labelPoints.setText("Points: "+points);

        running = true;
    }

    public void startSnake(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(event -> {
            if (!moved) { return; }



            switch (event.getCode()){
                case W:
                    if (direction != Direction.DOWN){
                        direction = Direction.UP;
                    }
                    break;
                case S:
                    if (direction != Direction.UP){
                        direction = Direction.DOWN;
                    }
                    break;
                case A:
                    if (direction != Direction.RIGHT){
                        direction = Direction.LEFT;
                    }
                    break;
                case D:
                    if (direction != Direction.LEFT){
                        direction = Direction.RIGHT;
                    }
                    break;
            }

            moved = false;
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
        System.out.print("Started Game. ");
    }

    public void actionBackToMenu(javafx.event.ActionEvent event) throws Exception {
        stopGame();
        gameTimer.stopTimer();

        Stage stage = (Stage) buttonBackToMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(Controller.class);
        Parent root = fxmlLoader.load(getClass().getResource("../Snake/Menu.fxml"));
        stage.setTitle("Snake");
        stage.setScene(new Scene(root, 600, 400));
        System.out.print("Back To Menu From Game ");
    }

}

package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProcessGame extends Application {


    BlockingQueue<ProcessAction> queue=new ArrayBlockingQueue(1);
    public static boolean isServer;
    static String ipAdress;
    boolean pageDrag;

    //public static Button button;
    public static Ball ball;
    public static Ball ball2;
    public static Rectangle healthbar1;
    public static Rectangle healthbar2;
    public static Character player1;
    public static Character player2;
    public static Scene scene;
    public static Boolean disable;
    public static ImageView loading;
    public static ImageView win1;
    public static ImageView win2;
    public static Button exitbutton;
    public static Boolean gameover;
    public static ImageView endBackground;


    Double point1X,point1Y,point2X,point2Y;
    private static boolean isActive= true;


    public static synchronized void setIsActive(boolean isActive) {
        ProcessGame.isActive = isActive;
    }

    public static synchronized boolean isIsActive() {
        return isActive;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        if(isServer)ProcessGame.disable = true;
        else ProcessGame.disable = false;
        ProcessGame.gameover = false;

        //if(isServer)primaryStage.setOpacity(.5);
        if(isServer)
        {
            NetWorkWorker netWorkWorker=new NetWorkWorker(true,queue);
        }
        else
        {
            NetWorkWorker netWorkWorker=new NetWorkWorker(false,queue);
        }

        AnchorPane root = new AnchorPane();
        ;
        primaryStage.setTitle("Game");
        scene = new Scene(root, 1000, 500);
        //scene.
        primaryStage.setScene(scene);
        primaryStage.show();

        Label label = new Label("Player1");
        label.setLayoutX(50);
        label.setLayoutY(30);
        label.setTextFill(Color.WHITE);

        Label label1 = new Label("Player2");
        label1.setLayoutX(800);
        label1.setLayoutY(30);
        label1.setTextFill(Color.WHITE);
        //label.setFont("");

        ImageView background = new ImageView(new Image("background.gif"));
        background.setFitWidth(1000);
        background.setFitHeight(500);
        player1 = new Character(true);
        player2 = new Character(false);
        ball = new Ball(true);
        ball2 = new Ball(false);

        Rectangle rectangle[] = new Rectangle[2];

        rectangle[0] = new Rectangle(50,50,150,25);
        rectangle[1] = new Rectangle(800,50,150,25);
        healthbar1 = new Rectangle(50,50,150,25);
        healthbar2 = new Rectangle(800,50,150,25);

        for(int i = 0 ; i<2 ; i++)
        {
            rectangle[i].setFill(Color.valueOf("#f5f514"));
            rectangle[i].setArcHeight(5);
            rectangle[i].setArcWidth(5);
            rectangle[i].setStroke(Color.valueOf("#373434"));
            rectangle[i].setStrokeType(StrokeType.OUTSIDE);
            rectangle[i].setStrokeWidth(2);
        }

        healthbar1.setFill(Color.valueOf("ff1f4e"));
        healthbar1.setArcHeight(5);
        healthbar1.setArcWidth(5);

        healthbar2.setFill(Color.valueOf("ff1f4e"));
        healthbar2.setArcHeight(5);
        healthbar2.setArcWidth(5);
//
//        button = new Button("ok");
//        button.setLayoutX(100);
//        button.setLayoutY(400);
        root.getChildren().addAll(background,player1.getImageView(),player2.getImageView(),ball.getImageView(),ball2.getImageView());
        root.getChildren().addAll(rectangle);
        root.getChildren().addAll(healthbar1,healthbar2,label,label1);

        endBackground = new ImageView(new Image("background.gif"));
        endBackground.setVisible(false);
        endBackground.setFitWidth(1000);
        endBackground.setFitHeight(500);

        win1 = new ImageView(new Image("win1.png"));
        win1.setLayoutX(330);
        win1.setLayoutY(50);
        win1.setFitWidth(300);
        win1.setFitHeight(100);
        win1.setVisible(false);
        win2 = new ImageView(new Image("win2.png"));
        win2.setLayoutX(330);
        win2.setLayoutY(50);
        win2.setFitWidth(300);
        win2.setFitHeight(100);
        win2.setVisible(false);
        //root.getChildren().addAll(win1,win2);


        if(isServer)
        {
            loading = new ImageView(new Image("loading.gif"));
            loading.setFitWidth(1000);
            loading.setFitHeight(500);
            root.getChildren().add(loading);
        }

        exitbutton = new Button("Exit");
       // exitbutton.setFont(Font.font(32));
        exitbutton.setFont(Font.font("Arial Black",32));
        exitbutton.setLayoutX(440);
        exitbutton.setLayoutY(150);

        exitbutton.setOnAction(event -> System.exit(0));
        exitbutton.setVisible(false);
        root.getChildren().addAll(endBackground,win1,win2,exitbutton);


        scene.setOnMouseClicked(evnt -> {
           //if(isIsActive()) button.setText("active");
            //else button.setText("inactive");
            if(ProcessGame.disable == false ) {
                scene.setOnMousePressed(event -> {
                    System.out.println(event.getX() + " " + event.getY());
                    point1X = event.getX();
                    point1Y = event.getY();
                    //healthbar1.setWidth(healthbar1.getWidth()-10);
                });

                scene.setOnMouseDragged(event -> {
                    System.out.println("pos " + event.getX() + "" + event.getY());
                    pageDrag = true;
                    if(isServer) {
                        player1.readyToThrow();
                    }
                    else
                    {
                        player2.readyToThrow();
                    }
                });

                scene.setOnMouseReleased(event -> {
                    if (pageDrag) {
                        {

                            if(isServer)
                            {
                                player1.normal();
                            }
                            else {
                                player2.normal();
                            }
                            System.out.println(event.getX() + " " + event.getY());
                            point2X = event.getX();
                            point2Y = event.getY();
                            ProcessAction processAction = new ProcessAction(point1X, point1Y, point2X, point2Y);
                            try {
                                queue.put(processAction);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            AnimationPath animationPath = new AnimationPath(isServer?ball:ball2,processAction.getPower(),processAction.getAngle(),isServer,true);
                            animationPath.getPathTransition().playFrom(Duration.millis(0));

                        }
                    } else {
                        System.out.println("no drag");
                    }
                    pageDrag = false;
                });
            }
        });

//        if(isServer)
//        {
//           NetWorkWorker netWorkWorker=new NetWorkWorker(true,queue);
//        }
//        else
//        {
//            NetWorkWorker netWorkWorker=new NetWorkWorker(false,queue);
//        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}

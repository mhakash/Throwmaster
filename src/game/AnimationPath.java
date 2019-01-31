package game;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class AnimationPath
{

    private Node node;
    private Ball ball;
    private double velocity;
    private double angle;
    private double p;
    private double q;
    private double distance;
    private Boolean a,b;


    public AnimationPath(Ball ball,double velocity, double angle,Boolean a, Boolean b)
    {
        this.a = a;
        this.b = b;
        this.velocity = velocity;
        this.angle = angle;
        this.ball = ball;
        p = ball.getLayoutX();
        System.out.println(p);
        q = ball.getLayoutY();
        System.out.println(q);
    }

    public PathTransition getPathTransition()
    {
        Path path = new Path();
        MoveTo moveTo = new MoveTo();

        //determining points of the quadcurve
        double x = velocity*velocity*Math.sin(2*angle*Math.PI/180.0)/10.0;
        System.out.println("x = "+x);
        double y = 55;
        distance = x;
        double controlx = x/2;
        double controly = -controlx*Math.tan(angle*Math.PI/180.0);
        //System.out.printf("%f %f %f %f\n",controlx,controly,x,y);

        QuadCurveTo quadCurveTo = new QuadCurveTo(controlx,controly,x,y);
        //System.out.println(Math.sin(angle*Math.PI/180.0));

        path.getElements().add(moveTo);
        path.getElements().add(quadCurveTo);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(ball.getImageView());
        pathTransition.setPath(path);
        //path.


        if((a == true && b ==true)|| (a==false && b==false))
        {
            ProcessGame.player1.throwing();
        }
        else
        {
            ProcessGame.player2.throwing();
        }

        pathTransition.setOnFinished(event -> {
            System.out.println("animation done");
            if((a == true && b ==true)||(a == false && b == false)) {
                ProcessGame.healthbar2.setWidth(ProcessGame.healthbar2.getWidth()-getDamage());
                if(ProcessGame.healthbar2.getWidth()-getDamage() < 0)
                {
                    System.out.println("player1 wins");
                    ProcessGame.win1.setVisible(true);
                    ProcessGame.exitbutton.setVisible(true);
                    ProcessGame.player2.dead();
                    ProcessGame.gameover = true;
                    ProcessGame.endBackground.setVisible(true);
                }
                ProcessGame.player1.normal();
            }
//            else if(a == false && b == false){
//                ProcessGame.healthbar2.setWidth(ProcessGame.healthbar2.getWidth()-getDamage());
//                ProcessGame.player1.normal();
//            }
            else  {
                ProcessGame.healthbar1.setWidth(ProcessGame.healthbar1.getWidth()-getDamage());
                if(ProcessGame.healthbar1.getWidth()-getDamage() < 0)
                {
                    System.out.println("player2 wins");
                    ProcessGame.win2.setVisible(true);
                    ProcessGame.exitbutton.setVisible(true);
                    ProcessGame.player1.dead();
                    ProcessGame.gameover=true;
                    ProcessGame.endBackground.setVisible(true);
                }
                ProcessGame.player2.normal();
            }
//            else {
//                ProcessGame.healthbar1.setWidth(ProcessGame.healthbar1.getWidth()-getDamage());
//                ProcessGame.player2.normal();
//            }

        });

        //double time = 2*velocity*Math.sin(angle*Math.PI/180.0)/10.0 *1000*.25;
        double time = 1000;
        pathTransition.setDuration(Duration.millis(time));

        return pathTransition;
    }

    public double getDamage()
    {


        if((a == true && b ==true)|| (a==false && b==false))
        {
            if(distance>759 && distance<906) return 10;
            else return 0;
        }

        else {
            if(distance<-759 && distance>-906) return 10;
            else return 0;
        }
    }

}

package game;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character  {

    private ImageView imageView;
    private Image image;
    private Boolean b;
    private Image readyToThrow;
    private Image throwing;
    private Image dead;


    public Character(boolean b)
    {
        this.b = b;
        if (b)
        {   image = new Image("idle.gif");
            imageView = new ImageView(image);
            imageView.setLayoutX(50);
            readyToThrow = new Image("readyToThrow.png");
            throwing = new Image("throwing.gif");
            dead = new Image("dead.gif");
        }
        else
        {
            image = new Image("idle1.gif");
            imageView = new ImageView(image);
            imageView.setLayoutX(850);
            readyToThrow = new Image("readyToThrow1.png");
            throwing = new Image("throwing1.png");
            dead = new Image("dead1.gif");
        }

        imageView.setLayoutY(350);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void readyToThrow ()
    {
        imageView.setImage(readyToThrow);
    }

    public void normal()
    {
        imageView.setImage(image);
    }

    public void throwing()
    {
        imageView.setImage(throwing);
    }

    public void dead()
    {
        imageView.setImage(dead);
    }

}

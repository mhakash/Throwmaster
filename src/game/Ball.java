package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    private ImageView imageView;
    private Image image;
    Boolean b;

    public Ball(boolean b)
    {
        this.b = b;
        image = new Image("ball.png");
        imageView = new ImageView(image);

        if(b)imageView.setLayoutX(120);
        else  imageView.setLayoutX(880-20);
        imageView.setLayoutY(385);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getLayoutX()
    {
        return imageView.getLayoutX();
    }

    public double getLayoutY()
    {
        return imageView.getLayoutY();
    }
}

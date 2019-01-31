package game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerReal {

    ProcessGame gameloop;

    @FXML
    Button hostbtn;
    @FXML
    Button guestbtn;
    @FXML
    Button playbtn;
    @FXML
    TextField servertext;

    public void initialize()
    {
        servertext.setDisable(true);
        playbtn.setDisable(true);
        gameloop=new ProcessGame();

    }

    public void onGuestButtonClicked()
    {
        servertext.setDisable(false);
        ProcessGame.isServer = false;
        //Main.myturn = false;
        playbtn.setDisable(false);
    }

    public void onHostButtonClicked()
    {
        servertext.setDisable(true);
        servertext.clear();
        ProcessGame.isServer = true;
        //Main.myturn = true;
        playbtn.setDisable(false);
    }

    public void onPlayButtonClicked()
    {
        if(ProcessGame.isServer) openGame();

        else if(!servertext.getText().isEmpty() && !servertext.getText().trim().isEmpty()){
            ProcessGame.ipAdress = servertext.getText();
            System.out.println(ProcessGame.ipAdress);
            openGame();
        }
    }

    private void openGame()
    {

        try {
            gameloop.start(Real.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package networking;

import game.AnimationPath;
import game.ProcessAction;
import game.ProcessGame;
import util.NetworkUtil;


import java.util.concurrent.BlockingQueue;

public class ReadThread implements Runnable {
    private Thread thr;
    private NetworkUtil nc;
    private BlockingQueue<ProcessAction> queue;
    public ReadThread(NetworkUtil nc,BlockingQueue queue) {
        this.nc = nc;
        this.thr = new Thread(this);
        this.queue=queue;
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = nc.read();
                if (o != null) {
                    if (o instanceof ProcessAction) {
                        ProcessAction obj = (ProcessAction) o;
                       // ProcessGame.setIsActive(true);
                        System.out.println(obj);
                        AnimationPath animationPath = new AnimationPath(ProcessGame.isServer?ProcessGame.ball2:ProcessGame.ball,obj.getPower(),obj.getAngle(),ProcessGame.isServer,false);

                        animationPath.getPathTransition().play();


                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            nc.closeConnection();
        }
    }
}




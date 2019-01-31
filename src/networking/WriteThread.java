package networking;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import game.ProcessAction;
import game.ProcessGame;
import util.NetworkUtil;

public class WriteThread implements Runnable {

    private BlockingQueue<ProcessAction> queue;
    private Thread thr;
    private NetworkUtil nc;
    String name;

    public WriteThread(NetworkUtil nc, BlockingQueue queue, String name) {
        this.nc = nc;
        this.name = name;
        this.thr = new Thread(this);
        this.queue=queue;
        thr.start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(System.in);
            while (true) {

                //TODO:Accept Commands From  A Queue
               // while (ProcessGame.isIsActive()) {
                    ProcessAction temp = queue.take();
                    nc.write(temp);
                   // ProcessGame.setIsActive(false);
                //}

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            nc.closeConnection();
        }
    }
}




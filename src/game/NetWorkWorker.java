package game;

import networking.Client;
import networking.Server;

import java.util.concurrent.BlockingQueue;

public class NetWorkWorker implements  Runnable {


    boolean isServer;
    BlockingQueue<ProcessAction> queue;
    public NetWorkWorker() {
    }

    public NetWorkWorker(boolean isServer, BlockingQueue<ProcessAction> queue) {
        this.isServer = isServer;
        this.queue=queue;
       Thread t=new Thread(this);
       t.start();
    }

    @Override
    public void run() {

        if(isServer) {
            Server server = new Server(queue);
        }
        else
            {
                Client client =new Client(ProcessGame.ipAdress,22222,queue);
            }


    }
}

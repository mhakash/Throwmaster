package networking;

import game.ProcessAction;
import util.NetworkUtil;

import java.util.concurrent.BlockingQueue;

public class Client {

    BlockingQueue<ProcessAction> queue;
    public Client(String serverAddress, int serverPort, BlockingQueue<ProcessAction> queue) {
        this.queue=queue;
        try {
            NetworkUtil nc = new NetworkUtil(serverAddress, serverPort);
            new ReadThread(nc,queue);
            new WriteThread(nc, queue,"Client");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        String serverAddress = "127.0.0.1";
        int serverPort = 22222;
       // Client client = new Client(serverAddress, serverPort);
    }
}


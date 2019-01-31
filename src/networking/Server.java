package networking;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import game.ProcessAction;
import game.ProcessGame;
import util.NetworkUtil;

public class Server {

    private ServerSocket serverSocket;
    BlockingQueue<ProcessAction> queue;
    public Server(BlockingQueue queue) {
        this.queue =queue;
        try {
            serverSocket = new ServerSocket(22222);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                //ProcessGame.disable = false;
                System.out.println("connection complete");
                ProcessGame.disable = false;
                ProcessGame.loading.setVisible(false);
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) {
        NetworkUtil nc = new NetworkUtil(clientSocket);
        new ReadThread(nc,queue);
        new WriteThread(nc,queue, "Server");
    }

    public static void main(String args[]) {
        //Server server = new Server();
    }
}

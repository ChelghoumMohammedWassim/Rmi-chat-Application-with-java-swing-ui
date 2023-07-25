package Client;


import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, InterruptedException, AlreadyBoundException {
       new ConnectionFrame();
//        new ClientFrame1();
//        new ClientFrame2();
    }
}

package Repondeur;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Repondeur extends Remote {

    public void poseQuestion(String quetion) throws RemoteException;
    public String getReponse() throws RemoteException;
    public void exit() throws RemoteException, MalformedURLException, NotBoundException;

}

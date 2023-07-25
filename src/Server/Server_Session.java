package Server;

import Repondeur.Repondeur;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server_Session extends Remote {
    public Repondeur createSession() throws RemoteException, MalformedURLException, NotBoundException, InterruptedException, AlreadyBoundException;
}

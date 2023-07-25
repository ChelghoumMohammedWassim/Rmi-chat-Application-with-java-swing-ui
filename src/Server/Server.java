package Server;

import Repondeur.Repondeur;
import Repondeur.RepondeurServant;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Server_Session {

    Registry registry;
    protected Server() throws RemoteException, AlreadyBoundException {
        registry= LocateRegistry.createRegistry(5090);
        registry.bind("Repondeur",new RepondeurServant());
    }

    @Override
    public Repondeur createSession() throws RemoteException, MalformedURLException, NotBoundException, AlreadyBoundException {
        Repondeur repondeur=(Repondeur) Naming.lookup("rmi://localhost:5090/Repondeur");
        return repondeur;
    }
}

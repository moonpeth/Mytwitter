/**
 * @author Ye SUN(SI4 IMAFA), Ying JIANG(SI4 Groupe3)
 *
 */

import java.net.MalformedURLException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author Ye SUN(SI4 IMAFA), Ying JIANG(SI4 Groupe3)
 *
 */
public class twitterServer {
    public static void main(String args[]) throws java.rmi.AlreadyBoundException { 

        try { 
            twitterInterface twitterDistante = new twitterImpl(); 
            LocateRegistry.createRegistry(8888); 
            Naming.bind("rmi://localhost:8888/twitterDistante",twitterDistante); 
            System.out.println("Server Ready."); 
        } catch (RemoteException e) { 
            System.out.println("RemoteException"); 
            e.printStackTrace(); 
        } catch (AlreadyBoundException e) { 
            System.out.println("AlreadyBoundException"); 
            e.printStackTrace(); 
        } catch (MalformedURLException e) { 
            System.out.println("MalformedURLException"); 
            e.printStackTrace(); 
        } 
    } 
}


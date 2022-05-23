package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import compute.Compute;

public class ComputeTask {
    public static void main (String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            Compute comp = (Compute) registry.lookup(name);

            TaskArray task = new TaskArray(args);
            String str = comp.executeTask(task);

            System.out.println(str);
        } catch(Exception e) {
            System.err.println("ComputeTask exception:");
            e.printStackTrace();
        }
    }
}

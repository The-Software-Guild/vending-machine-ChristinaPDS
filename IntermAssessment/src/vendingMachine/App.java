package vendingMachine;

import controller.Controller;
import dao.Dao;
import dao.DaoImpl;
import dao.PersistenceException;
import service.InsufficientFundsException;
import service.ServiceLayer;
import service.ServiceLayerImpl;
import ui.View;
import ui.UserIO;
import ui.UserIOConsoleImpl;
import service.ServiceLayer;

public class App {
    public static void main (String[] args) throws PersistenceException, InsufficientFundsException {
        UserIO myIo = new UserIOConsoleImpl();
        Dao myDao = (Dao) new DaoImpl();
        View myView = new View(myIo, myDao);
        ServiceLayer myServiceLayer = new ServiceLayerImpl(myDao, myView);
        Controller controller =
                new Controller(myDao, myView, myServiceLayer);
        controller.run();
    }
}

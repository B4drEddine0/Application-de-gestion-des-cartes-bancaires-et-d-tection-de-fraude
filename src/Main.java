import Dao.CardDao;
import Dao.ClientDao;
import Dao.OperationDao;
import Service.AuthService;
import Service.CardService;
import Service.OperationService;
import View.Console;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ClientDao clientDao = new ClientDao();
        CardDao cardDao = new CardDao();
        OperationDao operationDao = new OperationDao();
        AuthService authService = new AuthService(clientDao);
        CardService cardService = new CardService(cardDao);
        OperationService operationService = new OperationService(operationDao, cardDao);
        Console console = new Console(authService , cardService , operationService);

        console.Start();
    }
}
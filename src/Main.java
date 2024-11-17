import Logic.Controller.Serialization;
import Logic.Controller.Service;
import Logic.Controller.Validator;
import Logic.GUI.MainWindow;


public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Too many arguments");
            System.exit(-1);
        }

        if (args.length == 1 && args[0].equals("-h")) {
            System.out.println("Usage: java -jar main.jar <Project and staff .txt file>");
            System.exit(0);
        }

        try {
            Service service = new Service();
            Serialization serialization = new Serialization(service);
            Validator validator = new Validator();

            javax.swing.SwingUtilities.invokeLater(() -> {
                MainWindow mainWindow = new MainWindow(service, serialization, validator);
                mainWindow.setVisible(true);
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Wrong arguments, use -h if you need help");
        }

    }
}
import Logic.Controller.Service;
import Logic.GUI.MainWindow;
import Logic.Model.Product;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new MainWindow().setVisible(true);
            }
        });
    }
}
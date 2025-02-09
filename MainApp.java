public class MainApp {
    public static void main(String[] args) {
        SuitView view = new SuitView();
        new SuitController(view);
        view.setVisible(true);
    }
}

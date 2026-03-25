package my_app;

public class ProdutoCadastradoEvent {
    private static final ProdutoCadastradoEvent INSTANCE = new ProdutoCadastradoEvent();

    private ProdutoCadastradoEvent() {}

    public static ProdutoCadastradoEvent getInstance() {
        return INSTANCE;
    }
}

package my_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import my_app.models.DbModel;
import my_app.models.FornecedorModel;
import my_app.models.ProdutoModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonDB {
    private final Path filePath;
    private ObjectMapper om;

    public JsonDB() {
        Path home = Path.of(System.getProperty("user.home"));
        filePath = home.resolve("Documents/licita-facil-app/db.json");
    }

    private ObjectMapper getOM() {
        if (om == null) om = new ObjectMapper();
        return om;
    }

    private DbModel carregarDb() throws IOException {
        Files.createDirectories(filePath.getParent());

        if (!Files.exists(filePath)) {
            return DbModel.vazio();
        }

        return getOM().readValue(filePath.toFile(), DbModel.class);
    }

    private void salvarDb(DbModel db) throws IOException {
        getOM().writeValue(filePath.toFile(), db);
    }

    public void salvarFornecedor(FornecedorModel model) throws IOException {
        DbModel db = carregarDb();
        db.fornecedores().add(model);
        salvarDb(db);
    }

    public void salvarProduto(ProdutoModel model) throws IOException {
        DbModel db = carregarDb();
        db.produtos().add(model);
        salvarDb(db);
    }

    public List<FornecedorModel> listarFornecedores() throws IOException {
        return carregarDb().fornecedores();
    }

    public List<ProdutoModel> listarProdutos() throws IOException {
        return carregarDb().produtos();
    }
}
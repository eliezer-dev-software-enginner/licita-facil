package my_app;

import megalodonte.ComputedState;
import megalodonte.ListState;
import megalodonte.Show;
import megalodonte.State;
import megalodonte.base.Redirect;
import megalodonte.components.*;
import megalodonte.components.inputs.Input;
import megalodonte.components.layout_components.Column;
import megalodonte.components.layout_components.Row;
import megalodonte.props.ButtonProps;
import megalodonte.props.ColumnProps;
import megalodonte.props.InputProps;
import megalodonte.props.TextProps;
import megalodonte.router.v2.Router;
import megalodonte.utils.related.TextVariant;
import my_app.models.ProdutoModel;

import java.math.BigDecimal;
import java.util.List;

public class ProdutosTableScreen {

    private final Router router;

    ListState<ProdutoModel> produtosListState = ListState.of(List.of());

    public ProdutosTableScreen(Router router) {
        this.router = router;

        EventBus.getInstance().subscribe(event -> {
                    if (event instanceof ModelCadastradoEvent) {
                        fetchData();
                    }
                });
    }

    public Component render() {
        return new Column(new ColumnProps().paddingAll(20))
                .children(
                        table(),
                        new Text("Criado por Eliezer - 2026", new TextProps().fontSize(12))
                );
    }


    void fetchData(){
        try{
            var list = Main.jsonDB.listarProdutos();
            produtosListState.set(list);
        } catch (Exception e) {e.printStackTrace();}
    }


    public Component table() {
        return new SimpleTable<ProdutoModel>()
                .fromData(produtosListState)
                .header()
                .columns()
                .column("Código", it -> it.codigo, (double) 90)
                .column("Titulo-Busca", it -> it.tituloBusca)
                .column("URL", it -> it.urlEncontrada)
                .column("Data de criação", it -> DateUtils.millisToBrazilianDateTime(it.dataCriacao))
                .end()
                .build()
                .onItemDoubleClick(it-> {
                    Components.ShowModal( ItemDetails(it), 550);
                });
    }


    String cnpjFromUrl(String url){
       // if(url.contains(""))
        return null;

        //TODO: implementar
    }

    Component ItemDetails(ProdutoModel model){
        return new Column(new ColumnProps().paddingAll(20))
                .c_child(new Text("Detalhes do produto", new TextProps().variant(TextVariant.SUBTITLE)))
                .c_child(new SpacerVertical(20))
                .c_child(Components.TextWithDetails("Código: ", model.codigo))
                .c_child(Components.TextWithDetails("Titulo: ", model.tituloBusca))
                .c_child(new Text("--------Fornecedor 1--------------"))
                .c_child(Components.TextWithDetails("CNPJ: ", model.urlEncontrada))
                .c_child(Components.TextWithDetails("Url: ", cnpjFromUrl(model.urlEncontrada)))
                .c_child(Components.TextWithDetails("Preço (R$): ", Utils.toBRLCurrency(model.precoEncontrado)))
                .c_child(new Text("--------Fornecedor 2--------------"))
                .c_child(Components.TextWithDetails("CNPJ: ", model.urlEncontrada))
                .c_child(Components.TextWithDetails("Url: ", cnpjFromUrl(model.urlEncontrada)))
                .c_child(Components.TextWithDetails("Preço (R$): ", Utils.toBRLCurrency(model.precoEncontrado)))
                .c_child(new Text("--------Fornecedor 3--------------"))
                .c_child(Components.TextWithDetails("CNPJ: ", model.urlEncontrada))
                .c_child(Components.TextWithDetails("Url: ", cnpjFromUrl(model.urlEncontrada)))
                .c_child(Components.TextWithDetails("Preço (R$): ", Utils.toBRLCurrency(model.precoEncontrado)));
    }
}

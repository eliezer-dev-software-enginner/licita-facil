package my_app;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import megalodonte.*;
import megalodonte.base.Redirect;
import megalodonte.components.*;
import megalodonte.components.inputs.Input;
import megalodonte.components.layout_components.Column;
import megalodonte.components.layout_components.Row;
import megalodonte.props.*;
import megalodonte.router.v2.Router;
import megalodonte.utils.related.TextVariant;
import my_app.models.ProdutoModel;

import java.math.BigDecimal;
import java.util.List;

public class HomeScreen {

    private final Router router;

    State<String> tituloBusca = State.of("");
    State<String> codigo = State.of("");
    State<Boolean> buscarWasClicked = State.of(false);

    State<String> urlState1=State.of(""),  precoState1=State.of("0"), imprimiuState1=State.of("Não"), cadastrouNoSiplanState1=State.of("Não"),  cnpjState1=State.of("");
    State<String> urlState2=State.of(""),  precoState2=State.of("0"), imprimiuState2=State.of("Não"), cadastrouNoSiplanState2=State.of("Não"),  cnpjState2=State.of("");
    State<String> urlState3=State.of(""),  precoState3=State.of("0"), imprimiuState3=State.of("Não"), cadastrouNoSiplanState3=State.of("Não"),  cnpjState3=State.of("");

    ComputedState<String> precoMedioComputedState = ComputedState.of(()->{
        double preco1 = Double.parseDouble(precoState1.get()) / 100.0;
        double preco2 = Double.parseDouble(precoState2.get()) / 100.0;
        double preco3 = Double.parseDouble(precoState3.get()) / 100.0;

        double result = (preco1 + preco2 + preco3)/3;

        return Utils.toBRLCurrency(BigDecimal.valueOf(result));
    }, precoState1, precoState2, precoState3 );

    public HomeScreen(Router router) {this.router = router;}

    public Component render() {
        return new Column(new ColumnProps().paddingAll(20))
                .children(
                        menuBar(),
                        new Button("Siga-me no Github").onClick(()-> Redirect.to("https://github.com/eliezer-dev-software-enginner")),
                        new SpacerVertical(10),
                        new Text("Algumas facilidades para agilizar o cadastro de fornecedores no Siplan"),
                        new SpacerVertical(20),
                        topForm(),
                        new SpacerVertical(20),
                        Show.when(buscarWasClicked, ()->
                             new Column(new ColumnProps().spacingOf(10))
                                    .children(
                                      Components.produtoForm(urlState1,  precoState1, imprimiuState1, cadastrouNoSiplanState1,  cnpjState1),
                                            Components.produtoForm(urlState2,  precoState2, imprimiuState2, cadastrouNoSiplanState2,  cnpjState2),
                                            Components.produtoForm(urlState3,  precoState3, imprimiuState3, cadastrouNoSiplanState3,  cnpjState3)
                                    )
                        ),
                        new SpacerVertical(20),
                        Components.TextWithValue("Média: ", precoMedioComputedState),
                        new SpacerVertical(20),
                        new Text("Criado por Eliezer - 2026", new TextProps().fontSize(12))
                );
    }

    private Component menuBar(){
        return new MenuBar()
                .menu(new Menu("Cadastros")
                        .item("Fornecedores", ()-> {
                           var stage =  new Stage();

                           var scene = new Scene((Parent) new FornecedoresScreen(router).render().getJavaFxNode());
                           stage.setScene(scene);
                           stage.setWidth(800);
                           stage.setHeight(600);

                           stage.show();
                        })
                );
    }

    private Row topForm() {
        return new Row().children(
                new Column().children(
                        new Text("Código"),
                        new Input(codigo, new InputProps().placeHolder("").borderColor("black"))
                ),
                new SpacerHorizontal(20),
                new Column().children(
                        new Text("Titulo da busca"),
                        new Input(tituloBusca, new InputProps().placeHolder("Ex: Produto teste").borderColor("black")),
                        new SpacerVertical(10)
                ),
                new SpacerHorizontal(20),
                new Column().children(
                        new SpacerVertical(13),
                        new Button("Buscar", new ButtonProps().height(30)).onClick(this::buscar)
                ),
                new SpacerHorizontal(20),
                new Column().children(
                        new SpacerVertical(13),
                        new Button("Limpar", new ButtonProps().height(30)).onClick(this::limpar)
                )
        );
    }


//EventBus.getInstance().publish(DadosFinanceirosAtualizadosEvent.getInstance());

    //EventBus.getInstance().subscribe(event -> {
    //            if (event instanceof DadosFinanceirosAtualizadosEvent) {
    //                calcularFinanceiroMesAtual();
    //            }
    //        });
    void buscar(){
        buscarWasClicked.set(true);
    }

    void limpar(){
        urlState1.set("");
        precoState1.set("0");
        imprimiuState1.set("Não");
        cadastrouNoSiplanState1.set("Não");
        cnpjState1.set("");
        //
        urlState2.set("");
        precoState2.set("0");
        imprimiuState2.set("Não");
        cadastrouNoSiplanState2.set("Não");
        cnpjState2.set("");
        //
        urlState3.set("");
        precoState3.set("0");
        imprimiuState3.set("Não");
        cadastrouNoSiplanState3.set("Não");
        cnpjState3.set("");

        buscarWasClicked.set(false);
    }

}

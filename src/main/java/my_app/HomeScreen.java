package my_app;

import megalodonte.*;
import megalodonte.base.Redirect;
import megalodonte.components.*;
import megalodonte.components.inputs.Input;
import megalodonte.components.layout_components.Column;
import megalodonte.components.layout_components.Row;
import megalodonte.props.*;
import my_app.models.FornecedorModel;
import my_app.models.ItemLicitacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeScreen {
    State<String> tituloBusca = State.of("");
    State<String> codigo = State.of("");
    State<Boolean> descricaoResultIsVisible = State.of(false);
    State<String> descricaoResult = State.of("");

    boolean s = false;

    ListState<ItemLicitacao> itemLicitacaoListState = ListState.of(List.of());

    public Component render() {

        ForEachState<ItemLicitacao, Component> forEachState = ForEachState.of(itemLicitacaoListState,
                (it)-> produtoItem());


        if(s)return new FornecedoresScreen().render();
        return new Column(new ColumnProps().paddingAll(20))
                .children(
                        new Button("Siga-me no Github").onClick(()-> Redirect.to("https://github.com/eliezer-dev-software-enginner")),
                        new SpacerVertical(10),
                        new Text("Algumas facilidades para agilizar o cadastro de fornecedores no PNCP"),
                        new SpacerVertical(20),
                        new Row().children(
                                new Column().children(
                                        new Text("Código PNCP"),
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
                                        new Button("Buscar", new ButtonProps().height(30)).onClick(()->{})
                                )
                        ),

                        new SpacerVertical(20),
                        new Button("Gerar entradas").onClick(this::handleClickListar),
                        new Text(codigo),
                        new Button("Encerrar processos", new ButtonProps().bgColor("red")).onClick(this::handleClickEncerrarProcessos),
                        new SpacerVertical(20),
                        Show.when(descricaoResultIsVisible ,()->  new Text(descricaoResult)),
                        new SpacerVertical(20),
                        new Text("Criado por Eliezer - 2026", new TextProps().fontSize(12))
                );
    }

    Component produtoItem(){
        State<String> urlEncontrada = State.of("");
        State<String> precoEncontrado = State.of("");
        State<Boolean> imprimiu = State.of(false);
        State<Boolean> cadastrouNoPNCP = State.of(false);

        ComputedState<String> cnpjFornecedor = ComputedState.of(()->{
            //...

            return "";
        },urlEncontrada);

        return new Row(

        ).children(
                itemColumn("Url encontrada", urlEncontrada),
                itemColumn("Preço encontrado", precoEncontrado),
                //itemColumn("Url encontrada", imprimiu.),
                //itemColumn("Url encontrada", cadastrouNoPNCP),
                itemColumn("CNPJ fornecedor", cnpjFornecedor)
        );
    }

    Component itemColumn(String label, State<String> inputState){
        return new Column().children(
                new Text(label),
                new Text(inputState)
        );
    }

    Component itemColumn(String label, ComputedState<String> state){
        return new Column().children(
                new Text(label),
                new Text(state)
        );
    }

    void handleClickListar(){

    }

    void handleClickEncerrarProcessos(){

    }
}

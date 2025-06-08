package com.example.calculadora;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.geometry.Insets;

public class Calculadora extends Application {
    private final Label tela = new Label("");
    private String input = "";
    private String operador = "";
    private float teclaInicial = 0;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculadora");

        // alinhamento e posição dos botões - layout principal
        VBox alinhamento = new VBox();
        alinhamento.setPadding(new Insets(25));
        alinhamento.setSpacing(20);

        // telinha que mostra a entrada dos dados
        tela.setId("tela");
        tela.setMinSize(200, 50);
        tela.setMaxSize(200, 50);
        tela.setMaxWidth(Float.MAX_VALUE);
        VBox.setVgrow(tela, Priority.NEVER);
        alinhamento.getChildren().add(tela);


        // espaçamento e layout dos botões
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        String[] botoes = new String[]{
                "7", "8", "9", "÷",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        int linha = 0;
        int coluna = 0;
        int totalBotoes = botoes.length;
        // percorre os botões e os add a grade
        for (int x=0;x<totalBotoes;x++) {
            String teclas = botoes[x]; // pra pegar todo botão do array
            Button botao = new Button(teclas);
            botao.setMinSize(50, 50);
            // ve quando e em qual tecla o usuário clicou
            botao.addEventHandler(MouseEvent.MOUSE_CLICKED, acao -> logicaCalculadora(teclas));
            // add botão a grade na posição certa[4][4]
            grid.add(botao, coluna, linha);
            coluna++;
            if (coluna > 3) { // qnd chega na coluna 4 pula pra próxima linha
                coluna = 0;
                linha++;
            }
        }

        alinhamento.getChildren().add(grid);

        // configurando a cena
        Scene cena = new Scene(alinhamento, 300, 400);
        cena.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(cena);
        stage.show();
    }

    // regras de negócio da calculadora
    private void logicaCalculadora(String teclaOp) {
        switch(teclaOp) {
            case "C":
                input = "";
                operador = "";
                teclaInicial = 0;
                tela.setText("");
                break;
            case "=":
                if(!input.isEmpty() && !operador.isEmpty()) {
                    float valorAtual = Float.parseFloat(input);
                    float resultado = operacoes(teclaInicial, valorAtual, operador);
                    tela.setText(String.valueOf(resultado));
                    input = String.valueOf(resultado);
                    operador = "";
                }
                break;
            case "+": case "-": case "*": case "/": // todas essas opções executam o mesmo bloco
                if(!input.isEmpty()) {
                    operador = teclaOp;
                    teclaInicial = Float.parseFloat(input);
                    input = "";
                }
                break;
            default:
                input+=teclaOp;
                tela.setText(input);
                break;
        }
    }

    // realiza o calculo
    private float operacoes(float a, float b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "÷":
                return a / b;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
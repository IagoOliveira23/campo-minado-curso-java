package br.com.iago.cm.visao;

import br.com.iago.cm.excecao.ExplosaoException;
import br.com.iago.cm.excecao.SairExcrption;
import br.com.iago.cm.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;

            while(continuar){
                clicloDoJogo();
                System.out.println("Outra partida? (S/n)");
                String resposta = entrada.nextLine();
                if("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }
        }catch (SairExcrption e){
            System.out.println("Tchau!");
        } finally {
            entrada.close();
        }
    }

    private void clicloDoJogo() {
        try {
            while (!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro);
                String digitado = capturarValorDigitado("Digite (x , y): ");
                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim()))
                        .iterator();
                digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");
                if("1".equals(digitado)){
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if ("2".equals(digitado)) {
                    tabuleiro.alterarMarcacao(xy.next(), xy.next());
                }
            }
            System.out.println(tabuleiro);
            System.out.println("Você ganhou!!!");
        } catch (ExplosaoException e){
            System.out.println(tabuleiro);
            System.out.println("Você perdeu!");
        }
    }

    private String capturarValorDigitado(String texto){
        System.out.print(texto);
        String digitado = entrada.nextLine();
        if("sair".equalsIgnoreCase(digitado)){
            throw new SairExcrption();
        }
        return digitado;
    }
}

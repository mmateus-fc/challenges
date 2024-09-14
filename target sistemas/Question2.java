/*
2) Dado a sequência de Fibonacci, 
onde se inicia por 0 e 1 e o próximo valor sempre será a soma dos 2 valores anteriores (exemplo: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34...), 
escreva um programa na linguagem que desejar onde, informado um número, 
ele calcule a sequência de Fibonacci e retorne uma mensagem avisando se o número informado pertence ou não a sequência.
 */

import java.util.ArrayList;
import java.util.List;

class Question2 
{
    public static void main(String[] args) 
    {
        // definindo um valor 
        int n = 4181;

        // criando sequência fibonacci
        List<Integer> listFibonacci = createSequence(n);

        // verifica se o número de entrada existe na sequência
        boolean existe = checkExiste(listFibonacci, n);

        // mensagem
        mensagem(listFibonacci, n, existe);

        return;
    }

    public static List<Integer> createSequence(int nMax)
    {
        List<Integer> sequence = new ArrayList<>();

        sequence.add(0);
        sequence.add(1);

        // verifica se n informado é menor que o valor máximo na sequencia
            // se valor no último index sequência é menor que N entrada, então adiciona mais números à sequência
        boolean nEntradaMenor = checkMaior(sequence, nMax);
        //System.out.println(nEntradaMenor);

        while (nEntradaMenor == false) // while false, adiciona novo número na sequência
        {
            int length = sequence.size();

            // indexes dos números no último index e no penúltimo index
            int indexU = length - 1;
            int indexP = indexU - 1;

            // valores dos números no último index e no penúltimo index
            int nU = sequence.get(indexU);
            int nP = sequence.get(indexP);

            // novo número da sequência
            int nNovo = nU + nP;

            // adiciona à lista
            sequence.add(nNovo);

            //System.out.println(sequence);

            nEntradaMenor = checkMaior(sequence, nMax);
            //System.out.println(nEntradaMenor);
        }

        return sequence;

    }

    public static boolean checkMaior(List<Integer> list, int nMax)
    {
        for (int i = 0; i < list.size(); i++)
        {
            int nList = list.get(i);
            //System.out.println(nList + " > " + nMax + " : " + (nList > nMax));

            if (nList > nMax)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExiste(List<Integer> list, int n)
    {
        for (int i = 0; i < list.size(); i++)
        {
            int nFib = list.get(i);
            
            if (nFib == n)
            { 
                return true;
            }
        }
        return false;
    }

    public static void  mensagem(List<Integer> list, int n, boolean existe)
    {
        System.out.println("O número de entrada é: " + n);
        System.out.println("A sequência de Fibonacci é: " + list + " ...");

        if (existe)
        {
            System.out.println("Portanto " + n + " existe na sequência de Fibonacci!");
        }
        else
        {
            System.out.println("Portanto " + n + " não existe na sequência de Fibonacci!");
        }
    }
}
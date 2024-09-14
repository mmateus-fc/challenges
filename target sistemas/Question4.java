/*
4) Dado o valor de faturamento mensal de uma distribuidora, detalhado por estado:
• SP – R$67.836,43
• RJ – R$36.678,66
• MG – R$29.229,88
• ES – R$27.165,48
• Outros – R$19.849,53

Escreva um programa na linguagem que desejar onde calcule o percentual de representação que cada estado teve dentro do valor total mensal da distribuidora.  
 */

import java.util.ArrayList;
import java.util.List;

class Question4 
{
    public static void main(String[] args) 
    {
        List<FaturamentoUF> listFaturamento = new ArrayList<>();

        // Adiciona estados e faturamento
        listFaturamento.add(new FaturamentoUF("SP", (double)67836.43));
        listFaturamento.add(new FaturamentoUF("RJ", (double)36678.66));
        listFaturamento.add(new FaturamentoUF("MG", (double)29229.88));
        listFaturamento.add(new FaturamentoUF("ES", (double)27165.48));
        listFaturamento.add(new FaturamentoUF("Outros", (double)19849.53));

        // percentual
        setPercentual(listFaturamento);

        // print métricas
        printMetricas(listFaturamento);

        return;
    }

    public static void setPercentual(List<FaturamentoUF> listFaturamento)
    {
        for (FaturamentoUF objeto : listFaturamento)
        {
            objeto.setPercentual();
        }
        return;
    }

    public static void printMetricas(List<FaturamentoUF> listFaturamento) 
    {
        // Cabeçalho da tabela
        System.out.printf("%-10s | %-15s | %-18s | %-30s%n", 
                          "Estado (UF)", "Faturamento (UF)", "Faturamento Total", "Percentual Faturamento (UF) %");
        System.out.println("-----------------------------------------------------------");

        // Exibe os dados da tabela
        for (FaturamentoUF faturamento : listFaturamento) {
            System.out.printf("%-11s | %-16.2f | %-18.2f | %-30.2f%%%n",
                              faturamento.getUF(), faturamento.getFat(), FaturamentoUF.faturamentoTotal, faturamento.getPercentual());
        }

        return;
    }
}

class FaturamentoUF
{
    static double faturamentoTotal = 0;

    String uf;
    double faturamento;
    double percentual;

    public FaturamentoUF(String uf, double faturamento)
    {
        this.uf = uf;
        this.faturamento = faturamento;
        FaturamentoUF.faturamentoTotal = FaturamentoUF.faturamentoTotal + faturamento; // Acumula o faturamento total
    }

    public String getUF()
    {
        return this.uf;
    }

    public double getFat()
    {
        return this.faturamento;
    }

    public double getPercentual() {
        return percentual;
    }

    public double getFatTotal()
    {
        return FaturamentoUF.faturamentoTotal;
    }

    public void setPercentual()
    {
        this.percentual = (this.faturamento / FaturamentoUF.faturamentoTotal) * 100; // %
    }
}
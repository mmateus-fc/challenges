/*
3) Dado um vetor que guarda o valor de faturamento diário de uma distribuidora, faça um programa, na linguagem que desejar, que calcule e retorne:
• O menor valor de faturamento ocorrido em um dia do mês;
• O maior valor de faturamento ocorrido em um dia do mês;
• Número de dias no mês em que o valor de faturamento diário foi superior à média mensal.

IMPORTANTE:
a) Usar o json ou xml disponível como fonte dos dados do faturamento mensal;
b) Podem existir dias sem faturamento, como nos finais de semana e feriados. Estes dias devem ser ignorados no cálculo da média;
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class Question3 
{
    public static void main(String[] args) 
    {
        // lê arquivo json
        String jsonString = readJson();
        //System.out.println("------------------- Json Arquivo -------------------");
        //System.out.println(jsonString);

        // processando jsonString para extrair pares do objeto
        List<List<String>> listObjetosString = processJsonString(jsonString);
        //System.out.println("\n------------------- Lista Objetos String -------------------");
        //System.out.println(listObjetosString);

        // lista de objetos
        List<Faturamento> listFaturamento = createListObjectFaturamento(listObjetosString);
        printListObjetos(listFaturamento);

        // métricas
        double maiorFat = maiorFat(listFaturamento);
        double menorFat = menorFat(listFaturamento); // desconsiderando faturamento 0
        double mediaFat = mediaFat(listFaturamento);
        int nDiasAcimaMedia = nDiasAcimaMedia(listFaturamento, mediaFat);

        printMetricas(maiorFat, menorFat, mediaFat, nDiasAcimaMedia);
    }

    public static String readJson()
    {
        String fileName = "dados.json";

        String jsonString = new String();

        // lendo arquivo
        try 
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = new String();

            // lendo cada linha do arquivo
            while ((line = bufferedReader.readLine()) != null) 
            {
                jsonString = jsonString + line;
            }

        } catch (Exception e) 
        {
            System.out.println("IOException: " + e.getMessage());
        }

        return jsonString;
    }

    public static List<List<String>> processJsonString(String jsonString)
    {
        //System.out.println("\n ------------------- Processing String ------------------- \n");

        // Remove colchetes e chaves
        jsonString = removeChaveColchete(jsonString);
        
        //System.out.println(jsonString);
        //System.out.println("\n -------------------------------------- \n");

        int indexInicio = 0;
        int indexFim = 0;

        // Lista de objetos
        List<List<String>> listObjetos = new ArrayList<>();

        // cada objeto (dia, valor) está dentro de {}
        for (int i = 0; i < jsonString.length(); i++)
        {   
            // caracter da string
            char ch = jsonString.charAt(i); // char
            String sh = Character.toString(ch); // string

            if (sh.equals("{")) // início de um objeto
            {
                indexInicio = i;
            }
            else if (sh.equals("}")) // fim do objeto
            {
                indexFim = i;

                // adiciona objeto
                String stringObjeto = jsonString.substring(indexInicio, indexFim + 1); // index fim não incluso em substring metodo, portanto +1
                //System.out.println("1 Adiciona objeto:" + stringObjeto); // {      "dia": 34,      "valor": 234324.00    }
                
                // Remove colchetes e chaves
                stringObjeto = removeChaveColchete(stringObjeto);
                //System.out.println("2 Remove colchetes:" + stringObjeto); // "dia": 34,      "valor": 234324.00

                // separa os atributes pela ","
                List<String> objetoString = listAtributos(stringObjeto); 
                //System.out.println("3 Separa os atributes:" + objetoString); // ["dia": 34, "valor": 234324.00] 

                // processa objeto string
                objetoString = processaObjetoString(objetoString);
                //System.out.println("4 Processa objeto string:" + objetoString); // [dia, 34, valor, 234324.00] 

                // adiciona à lista de objetos
                listObjetos.add(objetoString); 
            }
        }

        return listObjetos;
        
    }

    public static String removeChaveColchete(String string)
    {
        String stringProcessada = new String();

        // trim
        stringProcessada = string.trim();

        // remove chaves ou colchetes index 0 e tamanho - 1
        stringProcessada = stringProcessada.substring(1, stringProcessada.length() - 1);

        // trim
        stringProcessada = stringProcessada.trim();

        return stringProcessada;
    }

    public static List<String> listAtributos(String stringObjeto)
    {
        // dividindo a string pelos atributos separados por vírgula
        String[] array = stringObjeto.split(",");

        // list para armazenar os atributos
        List<String> list = new ArrayList<>();

        for (String atributo : array)
        {
            String s = atributo.trim(); // trim espaços
            list.add(s); // adiciona na lista
        }

        // retornando a lista
        return list;
    }

    public static List<String> processaObjetoString(List<String> objetoString) // entrada: ["dia": 34, "valor": 234324.00] 
    {
        List<String> listKeyValue = new ArrayList<>();

        for (int i = 0; i < objetoString.size(); i++) // string 1: "dia", 34 // string 2: "valor": 234324.00
        {
            String sh = objetoString.get(i); 

            String[] atributoValor = sh.split(":"); // saída: ["dia", 34]

            String atributo = atributoValor[0]; // "dia"
            atributo = atributo.replace("\"", "").trim(); // dia

            String valor = atributoValor[1]; //  34
            valor = valor.trim(); // 34

            listKeyValue.add(atributo);
            listKeyValue.add(valor);
        }
        return listKeyValue;
    }

    public static List<Faturamento> createListObjectFaturamento(List<List<String>> listString)
    {
        List<Faturamento> list = new ArrayList<>();

        for (int i = 0; i < listString.size(); i++)
        {
            List<String> row = listString.get(i); // [dia, 34, valor, 234324.00] 
            //System.out.println(row);

            // Criando objetos
            String dia = row.get(1); // dia index 1
            String valor = row.get(3); // valor index 3

            Faturamento objeto = new Faturamento(dia, valor);

            // adiciona objeto à lista
            list.add(objeto);
        }
        return list;
    }

    public static void printListObjetos(List<Faturamento> listFaturamento)
    {
        System.out.println("\n---------- Lista Objetos Faturamento ----------\n");

        // Cabeçalho da tabela
        System.out.printf("%-5s | %-10s%n", "dia", "valor");
        System.out.println("---------------");

        // Exibe os objetos formatados
        for (Faturamento f : listFaturamento) {
            System.out.printf("%-5d | %-10.2f%n", f.getDia(), f.getValor());
        }
    }

    public static double maiorFat(List<Faturamento> listFaturamento)
    {
        double fat = 0;
        
        for (Faturamento objeto : listFaturamento)
        {
            double objFat = objeto.getValor();

            if (objFat > fat)
            {
                fat = objFat;
            }
        }

        return fat;
    }

    public static double menorFat(List<Faturamento> listFaturamento)
    {
        double menorFat = 0;

        // primeio fat não zero
        for (Faturamento objeto : listFaturamento)
        {
            double objFat = objeto.getValor();

            if (objFat > (double)0) // desconsiderar fat 0
            {
                menorFat = objFat;
            }
        } 

        // iterando para encontrar o menor fat
        for (int i = 0; i < listFaturamento.size(); i++)
        {
            Faturamento objeto = listFaturamento.get(i);
            double objFat = objeto.getValor();

            if ((objFat < menorFat) && (objFat > (double)0))
            {
                menorFat = objFat;
            }
        }
        return menorFat;
    }

    public static double mediaFat(List<Faturamento> listFaturamento)
    {
        double somaFat = 0;
        double diasFat = 0;

        for (Faturamento objeto : listFaturamento)
        {
            double fat = objeto.getValor();

            if (fat > (double) 0) // desconsidera fat 0
            {
                somaFat = somaFat + fat;
                diasFat++;
            }
        }

        double media = somaFat / diasFat;

        return media;
    }

    public static int nDiasAcimaMedia(List<Faturamento> listFaturamento, double mediaFat)
    {
        int dias = 0;

        for (Faturamento objeto : listFaturamento)
        {
            double fat = objeto.getValor();

            if (fat > mediaFat) 
            {
                dias++;
            }
        }

        return dias;
    }

    public static void printMetricas(double maiorFat, double menorFat, double mediaFat, int nDiasAcimaMedia)
    {
        System.out.println("\n ----------------- RESULTADOS - MÉTRICAS -----------------\n");
        System.out.printf("A) O menor valor de faturamento ocorrido em um dia do mês (Desconsiderando faturamento 0): %.2f%n", menorFat);
        System.out.printf("B) O maior valor de faturamento ocorrido em um dia do mês: %.2f%n", maiorFat);
        System.out.printf("C1) Média mensal (Desconsiderando faturamento 0): %.2f%n", mediaFat);
        System.out.printf("C2) Número de dias no mês em que o valor de faturamento diário foi superior à média mensal: %d", nDiasAcimaMedia);
        return;
    }
}

class Faturamento {
    int dia;
    double valor;

    // construtor
    public Faturamento(String dia, String valor)
    {
        this.dia = Integer.parseInt(dia);
        this.valor = Double.parseDouble(valor);
    }

    // get
    public int getDia()
    {
        return this.dia;
    }

    public double getValor()
    {
        return this.valor;
    }
}
/*
5) Escreva um programa que inverta os caracteres de um string.

IMPORTANTE:
a) Essa string pode ser informada através de qualquer entrada de sua preferência ou pode ser previamente definida no código;
b) Evite usar funções prontas, como, por exemplo, reverse;
*/

class Question5 
{
    public static void main(String[] args) 
    {
        String string = "Target Sistemas";

        // invertendo caracteres string
        String invertedString = invert(string);
        System.out.println("String original: " + string);
        System.out.println("String invertida: " + invertedString);
    }

    public static String invert(String string)
    {
        String newString = new String();

        for (int i = string.length() - 1; i >= 0; i--)
        {
            char ch = string.charAt(i); // char
            String sh = Character.toString(ch); // string

            newString = newString + sh;
            //System.out.println(newString);
        }

        return newString;
    }

}
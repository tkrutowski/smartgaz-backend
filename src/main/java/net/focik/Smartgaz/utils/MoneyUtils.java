package net.focik.Smartgaz.utils;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.AmountFormatParams;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class MoneyUtils {

    public static String mapMoneyToString(Money money) {
        MonetaryAmountFormat amountFormat = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("pl", "PL"))
//                .set(AmountFormatParams.PATTERN, "###,###.## ¤")
                        .set(AmountFormatParams.PATTERN, "###,##0.00 zł")
                        .build());

        return amountFormat.format(money);
    }


    public static String amountByWords(Money amount)
    {
        String kwota2 = String.format("%.2f", amount.getNumberStripped());

        //usuwanie spacji
        String kwota = "";
        for (char ch : kwota2.toCharArray())
        {
            if (ch != (char)160)
            {
                kwota += ch;
            }
        }

        String slownie = "";
        String post = "tysięcy";
        String kwotaPo = "";//dodaniu zer
        //dodanie zer przed liczbe jezeli jest za krótka
        if (kwota.length() < 9)
        {
            for (int i = 0; i < 9 - kwota.length(); i++)
                kwotaPo += "0";
        }
        kwotaPo += kwota;

        //tysiące
        if (kwotaPo.charAt(0) == '1')
            slownie += " sto";
        else if (kwotaPo.charAt(0) == '2')
            slownie += " dwieście";
        else if (kwotaPo.charAt(0) == '3')
            slownie += " trzysta";
        else if (kwotaPo.charAt(0) == '4')
            slownie += " czterysta";
        else if (kwotaPo.charAt(0) == '5')
            slownie += " pięćset";
        else if (kwotaPo.charAt(0) == '6')
            slownie += " sześćset";
        else if (kwotaPo.charAt(0) == '7')
            slownie += " siedemset";
        else if (kwotaPo.charAt(0) == '8')
            slownie += " osiemset";
        else if (kwotaPo.charAt(0) == '9')
            slownie += " dziewięćset";


        switch (kwotaPo.substring(1, 1)) {
            case "2":
                slownie += " dwadzieścia";
                break;
            case "3":
                slownie += " trzydzieści";
                break;
            case "4":
                slownie += " czterdześci";
                break;
            case " 5":
                slownie += " pięćdziesiąt";
                break;
            case "6":
                slownie += " sześćdziesiąt";
                break;
            case "7":
                slownie += " siedemdziesiąt";
                break;
            case "8":
                slownie += " osiemdziesiąt";
                break;
            case "9":
                slownie += " dziewięćdziesiąt";
                break;
        }


        if (kwotaPo.substring(1, 3).equals("10"))
            slownie += " dziesięć";
        else if (kwotaPo.substring(1, 3).equals("11"))
            slownie += " jedenaście";
        else if (kwotaPo.substring(1, 3).equals("12"))
            slownie += " dwanaście";
        else if (kwotaPo.substring(1, 3).equals("13"))
            slownie += " trzynaście";
        else if (kwotaPo.substring(1, 3).equals("14"))
            slownie += " czternaście";
        else if (kwotaPo.substring(1, 3).equals("15"))
            slownie += " piętnaście";
        else if (kwotaPo.substring(1, 3).equals("16"))
            slownie += " szesnaście";
        else if (kwotaPo.substring(1, 3).equals("17"))
            slownie += " siedemnaście";
        else if (kwotaPo.substring(1, 3).equals("18"))
            slownie += " osiemnaście";
        else if (kwotaPo.substring(1, 3).equals("19"))
            slownie += " dziewietnaście";

        else if (kwotaPo.substring(2, 3).equals("1"))
        {
            slownie += " jeden";
            //jezeli kwota zaczyna się od tysiąca np. 1.234,89
            if (slownie.equals(" jeden"))
                post = "tysiąc";
        }
        else if (kwotaPo.substring(2, 3).equals("2"))
        {
            slownie += " dwa";
            post = "tysięce";
        }
        else if (kwotaPo.substring(2, 3).equals("3"))
        {
            slownie += " trzy";
            post = "tysięce";
        }
        else if (kwotaPo.substring(2, 3).equals("4"))
        {
            slownie += " cztery";
            post = "tysięce";
        }
        else if (kwotaPo.substring(2, 3).equals("5"))
            slownie += " pięć";
        else if (kwotaPo.substring(2, 3).equals("6"))
            slownie += " sześć";
        else if (kwotaPo.substring(2, 3).equals("7"))
            slownie += " siedem";
        else if (kwotaPo.substring(2, 3).equals("8"))
            slownie += " osiem";
        else if (kwotaPo.substring(2, 3).equals("9"))
            slownie += " dziewięć";
        //jeżęli są tysiące
        if (kwota.length() > 6)
            slownie += " " + post;

        //
        //setki
        //
        if (kwotaPo.substring(3, 4).equals("1"))
            slownie += " sto";
        else if (kwotaPo.substring(3, 4).equals("2"))
            slownie += " dwieście";
        else if (kwotaPo.substring(3, 4).equals("3"))
            slownie += " trzysta";
        else if (kwotaPo.substring(3, 4).equals("4"))
            slownie += " czterysta";
        else if (kwotaPo.substring(3, 4).equals("5"))
            slownie += " pięćset";
        else if (kwotaPo.substring(3, 4).equals("6"))
            slownie += " sześćset";
        else if (kwotaPo.substring(3, 4).equals("7"))
            slownie += " siedemset";
        else if (kwotaPo.substring(3, 4).equals("8"))
            slownie += " osiemset";
        else if (kwotaPo.substring(3, 4).equals("9"))
            slownie += " dziewięćset";


        if (kwotaPo.substring(4, 5).equals("2"))
            slownie += " dwadzieścia";
        else if (kwotaPo.substring(4, 5).equals("3"))
            slownie += " trzydzieści";
        else if (kwotaPo.substring(4, 5).equals("4"))
            slownie += " czterdześci";
        else if (kwotaPo.substring(4, 5).equals("5"))
            slownie += " pięćdziesiąt";
        else if (kwotaPo.substring(4, 5).equals("6"))
            slownie += " sześćdziesiąt";
        else if (kwotaPo.substring(4, 5).equals("7"))
            slownie += " siedemdziesiąt";
        else if (kwotaPo.substring(4, 5).equals("8"))
            slownie += " osiemdziesiąt";
        else if (kwotaPo.substring(4, 5).equals("9"))
            slownie += " dziewięćdziesiąt";

        if (kwotaPo.substring(4, 6).equals("10"))
            slownie += " dziesięć";
        else if (kwotaPo.substring(4, 6).equals("11"))
            slownie += " jedenaśie";
        else if (kwotaPo.substring(4, 6).equals("12"))
            slownie += " dwanaście";
        else if (kwotaPo.substring(4, 6).equals("13"))
            slownie += " trzynaście";
        else if (kwotaPo.substring(4, 6).equals("14"))
            slownie += " czternaście";
        else if (kwotaPo.substring(4, 6).equals("15"))
            slownie += " piętnaście";
        else if (kwotaPo.substring(4, 6).equals("16"))
            slownie += " szesnaście";
        else if (kwotaPo.substring(4, 6).equals("17"))
            slownie += " siedemnaście";
        else if (kwotaPo.substring(4, 6).equals("18"))
            slownie += " osiemnaście";
        else if (kwotaPo.substring(4, 6).equals("19"))
            slownie += " dziewiętnaście";

        else if (kwotaPo.substring(5, 6).equals("1"))
            slownie += " jeden";
        else if (kwotaPo.substring(5, 6).equals("2"))
            slownie += " dwa";
        else if (kwotaPo.substring(5, 6).equals("3"))
            slownie += " trzy";
        else if (kwotaPo.substring(5, 6).equals("4"))
            slownie += " cztery";
        else if (kwotaPo.substring(5, 6).equals("5"))
            slownie += " pięć";
        else if (kwotaPo.substring(5, 6).equals("6"))
            slownie += " sześć";
        else if (kwotaPo.substring(5, 6).equals("7"))
            slownie += " siedem";
        else if (kwotaPo.substring(5, 6).equals("8"))
            slownie += " osiem";
        else if (kwotaPo.substring(5, 6).equals("9"))
            slownie += " dziewięć";

        if (slownie == null)
            slownie += " zero";
        slownie += " PLN " + kwota.substring(kwota.length() - 2) + "/100";

        return slownie;
    }

    public static Money mapToMoney(Number amount) {
        return Money.of(amount, "PLN");
    }
}

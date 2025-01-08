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


        if (kwotaPo.startsWith("10", 1))
            slownie += " dziesięć";
        else if (kwotaPo.startsWith("11", 1))
            slownie += " jedenaście";
        else if (kwotaPo.startsWith("12", 1))
            slownie += " dwanaście";
        else if (kwotaPo.startsWith("13", 1))
            slownie += " trzynaście";
        else if (kwotaPo.startsWith("14", 1))
            slownie += " czternaście";
        else if (kwotaPo.startsWith("15", 1))
            slownie += " piętnaście";
        else if (kwotaPo.startsWith("16", 1))
            slownie += " szesnaście";
        else if (kwotaPo.startsWith("17", 1))
            slownie += " siedemnaście";
        else if (kwotaPo.startsWith("18", 1))
            slownie += " osiemnaście";
        else if (kwotaPo.startsWith("19", 1))
            slownie += " dziewietnaście";

        else if (kwotaPo.charAt(2) == '1')
        {
            slownie += " jeden";
            //jezeli kwota zaczyna się od tysiąca np. 1.234,89
            if (slownie.equals(" jeden"))
                post = "tysiąc";
        }
        else if (kwotaPo.charAt(2) == '2')
        {
            slownie += " dwa";
            post = "tysięce";
        }
        else if (kwotaPo.charAt(2) == '3')
        {
            slownie += " trzy";
            post = "tysięce";
        }
        else if (kwotaPo.charAt(2) == '4')
        {
            slownie += " cztery";
            post = "tysięce";
        }
        else if (kwotaPo.charAt(2) == '5')
            slownie += " pięć";
        else if (kwotaPo.charAt(2) == '6')
            slownie += " sześć";
        else if (kwotaPo.charAt(2) == '7')
            slownie += " siedem";
        else if (kwotaPo.charAt(2) == '8')
            slownie += " osiem";
        else if (kwotaPo.charAt(2) == '9')
            slownie += " dziewięć";
        //jeżęli są tysiące
        if (kwota.length() > 6)
            slownie += " " + post;

        //
        //setki
        //
        if (kwotaPo.charAt(3) == '1')
            slownie += " sto";
        else if (kwotaPo.charAt(3) == '2')
            slownie += " dwieście";
        else if (kwotaPo.charAt(3) == '3')
            slownie += " trzysta";
        else if (kwotaPo.charAt(3) == '4')
            slownie += " czterysta";
        else if (kwotaPo.charAt(3) == '5')
            slownie += " pięćset";
        else if (kwotaPo.charAt(3) == '6')
            slownie += " sześćset";
        else if (kwotaPo.charAt(3) == '7')
            slownie += " siedemset";
        else if (kwotaPo.charAt(3) == '8')
            slownie += " osiemset";
        else if (kwotaPo.charAt(3) == '9')
            slownie += " dziewięćset";


        if (kwotaPo.charAt(4) == '2')
            slownie += " dwadzieścia";
        else if (kwotaPo.charAt(4) == '3')
            slownie += " trzydzieści";
        else if (kwotaPo.charAt(4) == '4')
            slownie += " czterdześci";
        else if (kwotaPo.charAt(4) == '5')
            slownie += " pięćdziesiąt";
        else if (kwotaPo.charAt(4) == '6')
            slownie += " sześćdziesiąt";
        else if (kwotaPo.charAt(4) == '7')
            slownie += " siedemdziesiąt";
        else if (kwotaPo.charAt(4) == '8')
            slownie += " osiemdziesiąt";
        else if (kwotaPo.charAt(4) == '9')
            slownie += " dziewięćdziesiąt";

        if (kwotaPo.startsWith("10", 4))
            slownie += " dziesięć";
        else if (kwotaPo.startsWith("11", 4))
            slownie += " jedenaśie";
        else if (kwotaPo.startsWith("12", 4))
            slownie += " dwanaście";
        else if (kwotaPo.startsWith("13", 4))
            slownie += " trzynaście";
        else if (kwotaPo.startsWith("14", 4))
            slownie += " czternaście";
        else if (kwotaPo.startsWith("15", 4))
            slownie += " piętnaście";
        else if (kwotaPo.startsWith("16", 4))
            slownie += " szesnaście";
        else if (kwotaPo.startsWith("17", 4))
            slownie += " siedemnaście";
        else if (kwotaPo.startsWith("18", 4))
            slownie += " osiemnaście";
        else if (kwotaPo.startsWith("19", 4))
            slownie += " dziewiętnaście";

        else if (kwotaPo.charAt(5) == '1')
            slownie += " jeden";
        else if (kwotaPo.charAt(5) == '2')
            slownie += " dwa";
        else if (kwotaPo.charAt(5) == '3')
            slownie += " trzy";
        else if (kwotaPo.charAt(5) == '4')
            slownie += " cztery";
        else if (kwotaPo.charAt(5) == '5')
            slownie += " pięć";
        else if (kwotaPo.charAt(5) == '6')
            slownie += " sześć";
        else if (kwotaPo.charAt(5) == '7')
            slownie += " siedem";
        else if (kwotaPo.charAt(5) == '8')
            slownie += " osiem";
        else if (kwotaPo.charAt(5) == '9')
            slownie += " dziewięć";

        slownie += " PLN " + kwota.substring(kwota.length() - 2) + "/100";

        return slownie;
    }

    public static Money mapToMoney(Number amount) {
        return Money.of(amount, "PLN");
    }
}

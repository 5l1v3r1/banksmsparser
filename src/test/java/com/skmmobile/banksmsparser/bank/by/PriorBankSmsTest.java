package com.skmmobile.banksmsparser.bank.by;

import com.skmmobile.banksmsparser.bank.AbsXmlBankSmsTest;

import org.w3c.dom.Document;

public class PriorBankSmsTest extends AbsXmlBankSmsTest {

    public PriorBankSmsTest(Document xmlDocument) {
        super("priorbank", xmlDocument);
    }

    @Override
    protected void smsTest() {
        checkBankSms(
                parser,
                "Priorbank. Karta 4***7241. 13-01-18 18:26:08. Oplata 61.71 BYN. BLR GIPPO TRADE CENTRE. Dostupno:123.91BYN Spravka: 80172899292",
                "expense",
                "4***7241",
                "61.71",
                ""
        );
        checkBankSms(
                parser,
                "Priorbank. Karta 5***5224. 15-01-18 18:12:39. Zachislenie perevoda 800.00 BYN. BLR P2P SDBO NO FEE. Dostupno:1412.76BYN Spravka: 80172899292",
                "popolnenie",
                "5***5224",
                "800",
                ""
        );
        checkBankSms(
                parser,
                "Priorbank. Karta 4***4862. 15-01-18 18:12:39. Perevod 800.00 BYN. BLR P2P SDBO NO FEE. Dostupno:350.83BYN Spravka: 80172899292",
                "perevod",
                "4***4862",
                "800",
                ""
        );
        checkBankSms(
                parser,
                "Priorbank 25/01 11:46. Na vashu kartu zachisleno 50.00 BYN. Dostupnaja summa: 50.41 BYN. Spravka: 80172899292",
                "zachislenie",
                "priorbank",
                "50",
                ""
        );
    }
}

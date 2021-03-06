package com.skmmobile.banksmsparser.bank;

import org.w3c.dom.Document;

public class AvangardSmsTest extends AbsXmlBankSmsTest {

    public AvangardSmsTest(Document xmlDocument) {
        super("avangard", xmlDocument);
    }

    @Override
    protected void smsTest() {
        checkBankSms(
                parser,
                "POKUPKA ROSTELEKOM, RU 465.00 RUB, CARD*2197 ELENA BONDAR BAL 7037.90 RUB 24.01.18 09:50 END",
                "expense",
                "2197",
                "465.00",
                "ROSTELEKOM"
        );
        checkBankSms(
                parser,
                "POKUPKA AO \"PETROELEKTROSBYT\", RU 1560.12 RUB, CARD*2197 ELENA BONDAR BAL 5477.78 RUB 24.01.18 09:53 END",
                "expense",
                "2197",
                "1560.12",
                "AO \"PETROELEKTROSBYT\""
        );
        checkBankSms(
                parser,
                "POKUPKA LENINGRADSK BULOCHNYE, RU 77.00 RUB, CARD*2197 ELENA BONDAR BAL 4139.64 RUB 24.01.18 18:24 END",
                "expense",
                "2197",
                "77",
                "LENINGRADSK BULOCHNYE"
        );
        checkBankSms(
                parser,
                "POPOLN BEZNAL AVANGARD 9979.59 RUB #*05981 BAL 10002.90 RUB 24.01.18 08:25 END",
                "popolnenie",
                "05981",
                "9979.59",
                ""
        );
    }
}

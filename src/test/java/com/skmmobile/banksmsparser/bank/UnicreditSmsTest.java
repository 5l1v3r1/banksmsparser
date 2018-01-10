package com.skmmobile.banksmsparser.bank;

import com.skmmobile.banksmsparser.BankSmsParser;
import org.w3c.dom.Document;

public class UnicreditSmsTest extends AbsXmlBankSmsTest {

    public UnicreditSmsTest(Document xmlDocument) {
        super("unicreditbank", xmlDocument);
    }

    @Override
    protected void smsTest() {
        checkBankSms(
                parser,
                "Карта 1139 07.01.2018 11:34 Покупка 1850.00 RUB. Доступно: 37203.74 RUR LUKOIL.AZS 87 59P CHUSOVOY",
                BankSmsParser.CATEGORY_EXPENSE,
                "1139",
                "1850.00",
                ""
        );
        checkBankSms(
                parser,
                "Карта 1139 07.01.2018 11:58 Покупка 170.00 RUB. Доступно: 37033.74 RUR MBU SOK CHUSOVOY",
                BankSmsParser.CATEGORY_EXPENSE,
                "1139",
                "170",
                ""
        );
    }
}
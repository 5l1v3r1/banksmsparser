package com.skmmobile.banksmsparser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankSmsParser {
    private static String TAG = "BankSmsParser";

    public static final String CATEGORY_EXPENSE = "expense";

    private final Set<Operation> operationSet = new HashSet<>();

    protected void addOperationTemplate(Operation operation) {
        operationSet.add(operation);
    }

    public Result parseSms(String text) {
        Result result = null;
        for (Operation operation : operationSet) {
            if (operation.typeRex.matcher(text).matches()) {
                result = new Result(operation.type);
                Matcher m;
                if (operation.cardIdRex != null) {
                    m = operation.cardIdRex.matcher(text);
                    if (m.find()) {
                        //App.SystemLog(TAG, "account: " + m.group());
                        result.cardIdStr = m.group(operation.cardIdRexGroup);
                    }
                    else
                        result.cardIdStr = "";
                }
                if (operation.amountRex != null) {
                    m = operation.amountRex.matcher(text);
                    if (m.find()) {
                        //App.SystemLog(TAG, "amount: " + m.group());
                        try {
                            String src = m.group(operation.amountRexGroup);
                            src = operation.decDelim.equals("")? src : src.replace(operation.decDelim, "");
                            src = src.replace(operation.decChar, ".");
                            result.amount = new BigDecimal(src);
                        }
                        catch (Exception e){
                            result.amount = BigDecimal.ZERO;
                        }
                    }
                    else
                        result.amount = BigDecimal.ZERO;
                }
                if (operation.dateRex != null) {
                    m = operation.dateRex.matcher(text);
                    if (m.find()) {
                        //App.SystemLog(TAG, "date: " + m.group());
                    }
                }
                if (operation.detailRex != null) {
                    m = operation.detailRex.matcher(text);
                    if (m.find()) {
                        //App.SystemLog(TAG, "location: " + m.group());
                        result.details = m.group(operation.detailRexGroup);
                    }
                    else
                        result.details = "indefinite";
                }
            }
        }

        if (result != null && (result.cardIdStr.equals("") | result.amount.compareTo(BigDecimal.ZERO) == 0)) {
            return null;
        }
        else
            return result;
    }

    public static class Result {
        String type;
        String cardIdStr;
        BigDecimal amount;
        Date date;
        String details;

        public Result(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getCardIdStr() {
            return cardIdStr;
        }

        public BigDecimal getAmount() {
            return amount == null ? BigDecimal.ZERO : amount;
        }

        public Date getDate() {
            return date;
        }

        public String getDetails() {
            return details != null ? details : "";
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + ": "
                    + "type: "     + type
                    + " cardId: "  + (cardIdStr != null ? cardIdStr : "indefinite")
                    + " amount: "  + (amount == null ? "indefinite" : amount.toPlainString())
                    + " date: "    + (date != null ? date.toString() : "indefinite")
                    + " details: " + (details != null ? details : "indefinite")
                    ;
        }
    }

    public static class Operation {
        private String type;
        private Pattern typeRex;
        private Pattern cardIdRex;
        private Pattern amountRex;
        private Pattern dateRex;
        private Pattern detailRex;
        private int cardIdRexGroup = 0;
        private int amountRexGroup = 0;
        private int dateRexGroup = 0;
        private int detailRexGroup = 0;
        private CharSequence decChar = ".";
        private CharSequence decDelim = "";

        public Operation(String type) {
            this.type = type;
        }

        public void setTypePattern(String s) {
            typeRex = Pattern.compile(s);
        }

        public void setCardIdRex(String s) {
            cardIdRex = Pattern.compile(s);
        }

        public void setCardIdRex(String s, int group) {
            cardIdRex = Pattern.compile(s);
            cardIdRexGroup = group;
        }

        public void setAmountRex(String s) {
            amountRex = Pattern.compile(s);
        }

        public void setAmountRex(String s, int group) {
            amountRex = Pattern.compile(s);
            amountRexGroup = group;
        }

        public void setDateRex(String s) {
            dateRex = Pattern.compile(s);
        }

        public void setDetailRex(String s) {
            detailRex = Pattern.compile(s);
        }

        public void setDetailRex(String s, int group) {
            detailRex = Pattern.compile(s);
            detailRexGroup = group;
        }

        public void setDecChar(CharSequence decChar) {
            this.decChar = decChar;
        }

        public void setDecDelim(CharSequence decDelim) {
            this.decDelim = decDelim;
        }
    }

    // Шаблоны системных sms для исключения
    private static String[] systemSmsTemplate = new String[]{
            "Задолженность по налогу.*",
            "Vhod v Tinkoff.ru.*",
            "Вход в Сбербанк Онлайн для Android.*",
            ".*пароль:\\s\\d+.*"
    };

    public static boolean isSystemBankSms(String text) {
        Pattern p;
        for (String s : systemSmsTemplate) {
            p = Pattern.compile(s);
            if (p.matcher(text).matches())
                return true;
        }
        return false;
    }
}

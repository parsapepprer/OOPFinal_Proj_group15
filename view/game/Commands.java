package view.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    ADD_PERSON("^\\s*(?i)add\\s+person\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ADD_COMPANY("^\\s*(?i)add\\s+company\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ADD_BANK("^\\s*(?i)add\\s+bank\\s+(\\S+)(?:\\s+(\\S+))?\\s*$"),
    SET_BANK_PARAMETER("^\\s*(?i)(?:set|increase)\\s+bank\\s+(income\\s+percent|short\\s+interest\\s+percent|long\\s+interest\\s+percent|balance)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    OPEN_CURRENT_ACCOUNT("^\\s*(?i)open\\s+current\\s+account\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    OPEN_DEPOSIT_ACCOUNT("^\\s*(?i)open\\s+deposit\\s+account\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    CLOSE_ACCOUNT("^\\s*(?i)close\\s+account\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    SET_CARD_PARAMETER("^\\s*(?i)(change|set)\\s+card\\s+(password|second\\s+password)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    EXTEND_EXPIRATION("^\\s*(?i)extend\\s+the\\s+expiration\\s+date\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    DEPOSIT_MONEY("^\\s*(?i)deposit\\s+money\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    WITHDRAW_MONEY("^\\s*(?i)withdraw\\s+money\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)(?:\\s+(\\S+))?\\s*$"),
    GET_ACCOUNT_BALANCE("^\\s*(?i)get\\s+account\\s+balance\\s+(\\S+)\\s+(\\S+)\\s*$"),
    TRANSFER_MONET_TO_ANOTHER_ACCOUNT("^\\s*(?i)transfer\\s+money\\s+to\\s+another\\s+account\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)(?:\\s+(\\S+))?\\s*$"),
    RECEIVE_LOAN("^\\s*(?i)receive\\s+loan\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    PAY_OFF_THE_LOAN("^\\s*(?i)pay\\s+off\\s+the\\s+loan\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$"),
    GO("^\\s*(?i)go\\s+(next|for|to\\s+date)\\s+(\\S+)(?:\\s+(\\S+))?\\s*$"),
    SHOW_ALL("^\\s*(?i)show\\s+all\\s+(\\S+)\\s*$"),
    SHOW_DETAIL("^\\s*(?i)show\\s+(accounts\\s+for|details\\s+of\\s+the\\s+loan\\s+for)\\s+(\\S+)\\s*$"),
    SHOW_BANK("^\\s*(?i)show\\s+bank\\s+(long\\s+interest|short\\s+interest|balance)\\s+(\\S+)\\s*$"),
    SHOW_CENTRAL_BANK("^\\s*(?i)show\\s+central\\s+bank\\s+balance\\s*$"),
    SHOW_DATE("^\\s*(?i)show\\s+date\\s*$"),
    EXIT("^(?i)\\s*Exit\\s*$");

    private final Pattern commandPattern;

    Commands(String s) {
        this.commandPattern = Pattern.compile(s);
    }

    public Matcher getMatcher(String command) {
        return this.commandPattern.matcher(command);
    }

}

package org.apache.fineract.portfolio.inward.api;
import java.util.*;
public class InwardApiConstant {

	//request parameter
	public static final String nostroAccountParamName = "nostroAccount";
	public static final String amountParamName = "amount";
	public static final String nostroBalanceParamName = "nostroBalnce";
	public static final String currencyParamName = "currency";
	public static final String fromAcoountNoParamName = "fromAccountNo";
	public static final String chequeNumberParamName  = "chequeNumber";
	public  static final String availableBalanceParamName = "availableBalance";
	public static final String nameParamName = "name";
	
	protected static final Set<String> INWARD_RESPOND_DATA_PARAMATER = new HashSet<>(
			Arrays.asList(nostroAccountParamName, amountParamName, nostroBalanceParamName,
					currencyParamName, fromAcoountNoParamName, chequeNumberParamName, availableBalanceParamName,
					nameParamName));
}

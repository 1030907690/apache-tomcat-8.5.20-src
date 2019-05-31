package com.zzq.digester;

import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.digester.RuleSetBase;

@SuppressWarnings("deprecation")
public class EmployeeRuleSet extends RuleSetBase {

	@Override
	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("employee", "com.zzq.digester.Employee");
		digester.addSetProperties("employee");
		digester.addObjectCreate("employee/office", "com.zzq.digester.Office");
		digester.addSetProperties("employee/office");
		digester.addSetNext("employee/office", "addOffice","com.zzq.digester.Office");
		digester.addObjectCreate("employee/office/address",
		"com.zzq.digester.Address");
		digester.addSetProperties("employee/office/address");
		digester.addSetNext("employee/office/address", "setAddress","com.zzq.digester.Address");
	}

}

package com.zzq.digester;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tomcat.util.digester.Digester;

public class Test02 {
	public static void main(String[] args) {
		String path = "D:/eclipse_workspace/CeShi2/apache-tomcat-8.5.20-windows-x64/tomcatsrc/src/test/java/com/zzq/digester";
		File file = new File(path, "employee2.xml");
		Digester digester = new Digester();
		// add rules
		digester.addObjectCreate("employee", "com.zzq.digester.Employee");
		digester.addSetProperties("employee");
		digester.addObjectCreate("employee/office", "com.zzq.digester.Office");
		digester.addSetProperties("employee/office");
		//建立对象的联系  
		digester.addSetNext("employee/office", "addOffice","com.zzq.digester.Office");
		digester.addObjectCreate("employee/office/address", "com.zzq.digester.Address");
		digester.addSetProperties("employee/office/address");
		digester.addSetNext("employee/office/address", "setAddress","com.zzq.digester.Address");
		try {
			Employee employee = (Employee) digester.parse(file);
			ArrayList offices = employee.getOffices();
			Iterator iterator = offices.iterator();
			System.out.println("-------------------------------------------------");
			while (iterator.hasNext()) {
				Office office = (Office) iterator.next();
				Address address = office.getAddress();
				System.out.println(office.getDescription());
				System.out.println("Address : " + address.getStreetNumber() + " " + address.getStreetName());
				System.out.println(" -------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

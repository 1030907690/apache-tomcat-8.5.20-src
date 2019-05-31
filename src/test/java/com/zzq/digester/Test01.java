package com.zzq.digester;

import java.io.File;

import org.apache.tomcat.util.digester.Digester;

public class Test01 {
	public static void main(String[] args) {
		String path = "D:/eclipse_workspace/CeShi2/apache-tomcat-8.5.20-windows-x64/tomcatsrc/src/test/java/com/zzq/digester";
		System.out.println(path);
		File file = new File(path, "employee1.xml");
		Digester digester = new Digester();
		// add rules
		//Digester实例首先创建一个com.zzq.digester.Employee类的实例
		digester.addObjectCreate("employee", "com.zzq.digester.Employee");
		//由第一个规则得到。然后 com.zzq.digester.Employee使用第二条规
		//则来根据 XML文档调用 setFirstName和 setLasetName属性来设置 Employee对象
		digester.addSetProperties("employee");
		//调用printName方法
		digester.addCallMethod("employee", "printName");
		
		try {
			Employee employee = (Employee) digester.parse(file);
			System.out.println("First name : " + employee.getFirstName());
			System.out.println("Last name : " + employee.getLastName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

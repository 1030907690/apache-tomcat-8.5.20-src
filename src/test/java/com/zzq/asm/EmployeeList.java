package com.zzq.asm;

import java.util.ArrayList;
import java.util.List;

/**
 访问者模式是一种将算法与对象结构分离的软件设计模式。
这个模式的基本想法如下：首先我们拥有一个由许多对象构成的对象结构，这些对象的类都拥有一个accept方法用来接受访问者对象；
访问者是一个接口，它拥有一个visit方法，这个方法对访问到的对象结构中不同类型的元素作出不同的反应；在对象结构的一次访问过程中，
我们遍历整个对象结构，对每一个元素都实施accept方法，在每一个元素的accept方法中回调访问者的visit方法，
从而使访问者得以处理对象结构的每一个元素。我们可以针对对象结构设计不同的实在的访问者类来完成不同的操作。
 * **/
public class EmployeeList {

    private List<Employee> list = new ArrayList<>();

    public void addEmployee(Employee employee) {
        list.add(employee);
    }

    public void accept(Department department) {
        for (Employee employee : list) {
            employee.accept(department);
        }
    }

    public static void main(String[] args) {
        EmployeeList list = new EmployeeList();
        Employee employee1 = new FulltimeEmployee("张三", "1000");
        Employee employee2 = new ParttimeEmployee("李四", "500");
        Employee employee3 = new ParttimeEmployee("王五", "400");
        list.addEmployee(employee1);
        list.addEmployee(employee2);
        list.addEmployee(employee3);

        Department department1 = new FADepartment();
        list.accept(department1);
    }
}
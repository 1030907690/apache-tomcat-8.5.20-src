package com.zzq.asm;

public class FADepartment extends Department {

    @Override
    public void visit(FulltimeEmployee employee) {
        System.out.println("正式员工 : " + employee.getName());
    }

    @Override
    public void visit(ParttimeEmployee employee) {
        System.out.println("临时工 : " + employee.getName());
    }
}

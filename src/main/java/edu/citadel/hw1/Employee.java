package edu.citadel.hw1;

import java.time.LocalDate;

public abstract class Employee implements Comparable<Employee> {
    private String name;
    private LocalDate hireDate;

    public Employee(String name, LocalDate hireDate) {
        this.name = name;
        this.hireDate = hireDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public abstract double getMonthlyPay();

    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.getMonthlyPay(), other.getMonthlyPay());
    }
}

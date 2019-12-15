package com.atypon.crud.client.main;

import com.atypon.crud.client.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** * Main View act as View that prints to console */
public class MainView {

  private MainController controller;

  private InputStreamReader in;
  private BufferedReader bufferedReader;

  /**
   * * Parameterized constructor to initialize the MainView.
   *
   * @param controller instance of MainController
   */
  public MainView(MainController controller) {
    this.controller = controller;
  }

  public void start() {
    controller.onAttach(this);
  }

  public void printWelcomeMsg() {
    System.out.println("================================");
    System.out.println("Welcome to ballan CRUD System!!!");
    System.out.println("================================");
    System.out.println("(1) find all employees");
    System.out.println("(2) find by id employees");
    System.out.println("(3) insert employee");
    System.out.println("(4) update by id employee");
    System.out.println("(5) delete by id employee");
    System.out.println("(-1) exit");
  }

  public void listenToInput() {
    try {
      in = new InputStreamReader(System.in);
      bufferedReader = new BufferedReader(in);

      String code;
      boolean isRunning = true;
      while (isRunning) {
        System.out.println("Enter code: ");
        code = bufferedReader.readLine();
        isRunning = controller.onReceiveCode(code);
      }

      bufferedReader.close();
      in.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int readId() {
    try {
      System.out.println("Enter id: ");
      String input = bufferedReader.readLine();
      return Integer.parseInt(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String readName() {
    try {
      System.out.println("Enter name: ");
      return bufferedReader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public double readSalary() {
    try {
      System.out.println("Enter Salary: ");
      String input = bufferedReader.readLine();
      return Double.parseDouble(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String readDepartment() {
    try {
      System.out.println("Enter Department: ");
      return bufferedReader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void printResponse(boolean isSuccess) {
    if (isSuccess) {
      System.out.println(
          "-----------------------------------------------------------------------------");
      System.out.println("done successfully!!!");
      System.out.println(
          "-----------------------------------------------------------------------------");
    } else {
      System.out.println(
          "-----------------------------------------------------------------------------");
      System.out.println("Failed!!!");
      System.out.println(
          "-----------------------------------------------------------------------------");
    }
  }

  public void printResponse(Optional<Employee> findByIdEmployee) {
    if (findByIdEmployee.isPresent()) {
      final Employee employee = findByIdEmployee.get();
      printResponse(Collections.singletonList(employee));
    } else {
      System.out.println(
          "-----------------------------------------------------------------------------");
      System.out.println("NOT FOUND");
      System.out.println(
          "-----------------------------------------------------------------------------");
    }
  }

  public void printResponse(List<Employee> allEmployees) {
    if (allEmployees.isEmpty()) {
      System.out.println(
          "-----------------------------------------------------------------------------");
      System.out.println("0 EMPLOYEES");
      System.out.println(
          "-----------------------------------------------------------------------------");
    } else {
      System.out.println(
          "-----------------------------------------------------------------------------");
      System.out.printf("%10s %30s %20s %20s", "ID", "NAME", "SALARY", "DEPARTMENT");
      System.out.println();
      System.out.println(
          "-----------------------------------------------------------------------------");
      for (Employee employee : allEmployees) {
        System.out.format(
            "%10s %30s %20s %20s",
            employee.getId(), employee.getName(), employee.getSalary(), employee.getDepartment());
        System.out.println();
      }
      System.out.println(
          "-----------------------------------------------------------------------------");
    }
  }

  public void printIncorrectCode() {
    System.out.println("Incorrect Code!!!");
  }
}

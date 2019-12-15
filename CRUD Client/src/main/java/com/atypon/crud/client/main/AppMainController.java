package com.atypon.crud.client.main;

import com.atypon.crud.client.data.DataManager;
import com.atypon.crud.client.model.Employee;
import com.atypon.crud.client.requests.ByIdRequest;
import com.atypon.crud.client.requests.EntityByIdRequest;
import com.atypon.crud.client.requests.EntityRequest;
import com.atypon.crud.client.requests.Request;
import com.atypon.crud.client.utils.AppConstants;

import java.util.List;
import java.util.Optional;

/** An implementation for MainController, for more detailed documentation: {@see MainController}. */
public class AppMainController implements MainController {

  private final DataManager dataManager;
  private MainView mainView;

  /**
   * * constructor to initialize the AppMainController.
   *
   * @param dataManager the instance of DataManager that represent Model on MVC
   */
  public AppMainController(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public void onAttach(MainView mainView) {
    this.mainView = mainView;
    start();
  }

  private void start() {
    mainView.printWelcomeMsg();
    mainView.listenToInput();
  }

  @Override
  public boolean onReceiveCode(String code) {
    if (code.equals("-1")) return false;

    Optional<Request> request = getRequest(code);
    request.ifPresent(this::getAndPrintResponse);
    if (!request.isPresent()) mainView.printIncorrectCode();

    return true;
  }

  private Optional<Request> getRequest(String code) {
    if (code.equals("1")) {
      return Optional.of(getFindAllEmployeesRequest());
    } else if (code.equals("2")) {
      return Optional.of(getFindByIdEmployeeRequest());
    } else if (code.equals("3")) {
      return Optional.of(getInsertEmployeeRequest());
    } else if (code.equals("4")) {
      return Optional.of(getUpdateByIdEmployeeRequest());
    } else if (code.equals("5")) {
      return Optional.of(getDeleteByIdEmployeeRequest());
    } else {
      return Optional.empty();
    }
  }

  private ByIdRequest getDeleteByIdEmployeeRequest() {
    int id = mainView.readId();
    return new ByIdRequest(AppConstants.RequestType.DELETE_BY_ID_EMPLOYEE, id);
  }

  private EntityByIdRequest<Employee> getUpdateByIdEmployeeRequest() {
    int id = mainView.readId();
    String name = mainView.readName();
    double salary = mainView.readSalary();
    String department = mainView.readDepartment();
    final Employee employee = new Employee(name, salary, department);
    return new EntityByIdRequest<Employee>(
        AppConstants.RequestType.UPDATE_BY_ID_EMPLOYEE, id, employee);
  }

  private EntityRequest<Employee> getInsertEmployeeRequest() {
    String name = mainView.readName();
    double salary = mainView.readSalary();
    String department = mainView.readDepartment();
    Employee employee = new Employee(name, salary, department);
    return new EntityRequest<Employee>(AppConstants.RequestType.INSERT_EMPLOYEE, employee);
  }

  private ByIdRequest getFindByIdEmployeeRequest() {
    int id = mainView.readId();
    return new ByIdRequest(AppConstants.RequestType.FIND_BY_ID_EMPLOYEE, id);
  }

  private Request getFindAllEmployeesRequest() {
    return new Request(AppConstants.RequestType.FIND_ALL_EMPLOYEES);
  }

  private void getAndPrintResponse(Request request) {
    final AppConstants.RequestType requestType = request.getRequestType();
    switch (requestType) {
      case FIND_ALL_EMPLOYEES:
        final List<Employee> allEmployees = dataManager.findAll(request);
        mainView.printResponse(allEmployees);
        break;
      case FIND_BY_ID_EMPLOYEE:
        final Optional<Employee> findByIdEmployee = dataManager.findById((ByIdRequest) request);
        mainView.printResponse(findByIdEmployee);
        break;
      case INSERT_EMPLOYEE:
        final boolean isInserted = dataManager.insert((EntityRequest<Employee>) request);
        mainView.printResponse(isInserted);
        break;
      case UPDATE_BY_ID_EMPLOYEE:
        final boolean isUpdated = dataManager.updateById((ByIdRequest) request);
        mainView.printResponse(isUpdated);
        break;
      case DELETE_BY_ID_EMPLOYEE:
        final boolean isDeleted = dataManager.deleteById((ByIdRequest) request);
        mainView.printResponse(isDeleted);
        break;
    }
  }
}

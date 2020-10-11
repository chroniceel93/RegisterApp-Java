package edu.uark.registerapp.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeCreateCommand;
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController extends BaseRestController {


	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createEmployee(
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		boolean isInitialEmployee = false;
		ApiResponse canCreateEmployeeResponse;

		try {
			this.activeEmployeeExistsQuery.execute();
			canCreateEmployeeResponse =
				this.redirectUserNotElevated(request, response);
		} catch (final NotFoundException e) {
			isInitialEmployee = true;
			canCreateEmployeeResponse = new ApiResponse();
		}

		if (!isInitialEmployee && (employee.getClassification() == 1)) {
			return this.redirectUserNotElevated(request, response, "mainMenu");
		}

		if (!employee.getIsActive()) {
			return this.redirectSessionNotActive(response);
		}

		if (!canCreateEmployeeResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return canCreateEmployeeResponse;
		}

		// TODO: Create an employee;
		final Employee createdEmployee = this.employeeCreateCommand.execute();

		if (isInitialEmployee) {
			createdEmployee
				.setRedirectUrl(
					ViewNames.SIGN_IN.getRoute().concat(
						this.buildInitialQueryParameter(
							QueryParameterNames.EMPLOYEE_ID.getValue(),
							createdEmployee.getEmployeeId())));
		}

		return createdEmployee.setIsInitialEmployee(isInitialEmployee);
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH)
	public @ResponseBody ApiResponse updateEmployee(
		@PathVariable final UUID employeeId,
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {
		try {
			this.activeEmployeeExistsQuery.execute();
		} catch (NotFoundException e) {
			response.setStatus(302);
			System.out.println(e.getMessage());
			return this.redirectSessionNotActive(response);
		}

		if (employee.getClassification() != 1) {
			response.setStatus(302);
			return this.redirectUserNotElevated(request, response, "mainMenu");
		}

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(request, response);
		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		// TODO: Update the employee
		return this.employeeUpdateCommand.execute();
	}

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

	@Autowired
	private EmployeeCreateCommand employeeCreateCommand;

	@Autowired
	private EmployeeUpdateCommand employeeUpdateCommand;
}

package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);

		ModelAndView modelAndView = 
			new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());	
		try {
			this.activeEmployeeExistsQuery.execute();
		} catch (final Exception e) {
			return modelAndView;
		}

		if (this.isElevatedUser(activeUserEntity.get())) {
			return modelAndView;
		} else if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		}
			
		return this.buildNoPermissionsResponse();
		
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {

		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);

		if (!this.isElevatedUser(activeUserEntity.get())) {
			return this.buildNoPermissionsResponse();
		}

		final ModelAndView modelAndView = 
			new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());


		try {
			modelAndView.addObject(
				ViewModelNames.EMPLOYEE.getValue(),
				this.employeeQuery.setId(employeeId).execute());
		} catch (final Exception e) {
			return this.buildInvalidSessionResponse();
		}
		
		return modelAndView;
	}

	// Helper methods

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

	@Autowired
	private EmployeeQuery employeeQuery;
}

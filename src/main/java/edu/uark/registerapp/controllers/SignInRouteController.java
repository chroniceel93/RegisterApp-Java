package edu.uark.registerapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {
	// <DONE> TODO: Route for initial page load</DONE>
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(@RequestParam Map<String,String> queryParameters) {
		ModelAndView modelAndView = 
			new ModelAndView(ViewNames.SIGN_IN.getViewName());
		
		modelAndView = this.setErrorMessageFromQueryString(modelAndView, queryParameters);
		

		try {
			this.activeEmployeeExistsQuery.execute();

		} catch (final Exception e) {
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.EMPLOYEE_DETAIL.getRoute())
			);
		}

		if (!queryParameters.containsKey(QueryParameterNames.EMPLOYEE_ID.getValue())) {
			return modelAndView;
		} else {
			try{
				modelAndView.addObject(
					QueryParameterNames.EMPLOYEE_ID.getValue(), 
					queryParameters.get(QueryParameterNames.EMPLOYEE_ID.getValue()));
			} catch (final Exception e) {
				// I have not yet figured out what to do here. Maybe log at least.
			}
		}

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		// <DONE>TODO: Define an object that will represent the sign in request and add it as a parameter here</DONE>
		EmployeeSignIn employeeSignIn, HttpServletRequest request
	) {

		// <DONE>TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user</DONE>
		try{
			this.employeeSignInCommand.setEmployeeSignIn(employeeSignIn).setSessionKey(request.getSession().getId()).execute();
		} catch (final Exception e) {
			ModelAndView modelAndView = new ModelAndView(ViewNames.SIGN_IN.getViewName());
			modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), 
									e.getMessage());
			return modelAndView;
		}

		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
	}

	// Properties
	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

	@Autowired
	private EmployeeSignInCommand employeeSignInCommand;
}

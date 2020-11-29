package edu.uark.registerapp.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

 /** TO BE ADDED **/
import edu.uark.registerapp.commands.transactions.TransactionCalculationCommand;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
/** TO BE ADDED */
import edu.uark.registerapp.models.api.TransactionEntry; 
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showTransaction(final HttpServletRequest request) {
		Optional<ActiveUserEntity> activeUser = this.getCurrentUser(request);
		if(!activeUser.isPresent()) {
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.SIGN_IN.getRoute()));
		} // Else, activeUser exists, doNothing();


		ModelAndView modelAndView =
			new ModelAndView(ViewNames.TRANSACTION.getViewName());

		try {
			modelAndView.addObject(
				ViewModelNames.TRANSACTION_ENTRIES.getValue(),
                /**TO BE ADDED**/
                this.transactionQuery.setCashierId(activeUser.get().getEmployeeId()).execute()); 
			modelAndView.addObject(
				ViewModelNames.IS_ELEVATED_USER.getValue(),
                this.isElevatedUser(activeUser.get()));
            modelAndView.addObject(
                ViewModelNames.COUNT.getValue(),
                /** TO BE ADDED. TransactionCalculationCommand calculates the items' count based on the cashier id */
                this.transactionCalculationCommand.setCashierId(activeUser.get().getEmployeeId()).getCount());
            modelAndView.addObject(
                ViewModelNames.COST.getValue(),
                /** TO BE ADDED. TransactionCalculationCommand calculates the items' count based on the cashier id */
                this.transactionCalculationCommand.setCashierId(activeUser.get().getEmployeeId()).getCost());
		} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
                ViewModelNames.TRANSACTION_ENTRIES.getValue(),
                /** TO BE ADDED */
				(new TransactionEntry[0]));
		}
		
		return modelAndView;
	}

	// Properties
    
    @Autowired
    /** TO BE ADDED */
    private TransactionQuery transactionQuery;

    @Autowired
    /** TO BE ADDED. TransactionCalculationCommand calculates the items' count based on the cashier id */
    private TransactionCalculationCommand transactionCalculationCommand;
}
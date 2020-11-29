package edu.uark.registerapp.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

 /** TO BE ADDED (retrieve all transactionentries where cashierid 
  * = currentUsersID)**/
import edu.uark.registerapp.commands.transactions.TransactionEntriesQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

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
				ViewModelNames.PRODUCTS.getValue(),
                /**transactionsEntriesQuery TO BE ADDED(retrieve all transactionentries 
                 * where cashierid = currentUsersID)**/
                this.transactionEntriessQuery.execute()); 
			modelAndView.addObject(
				ViewModelNames.IS_ELEVATED_USER.getValue(),
				this.isElevatedUser(activeUser.get()));
		} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.PRODUCTS.getValue(),
				(new Product[0]));
		}
		
		return modelAndView;
	}

	// Properties
    @Autowired
    /**  TO BE ADDED (retrieve all transactionentries where 
     * cashierid = currentUsersID)**/
	private TransactionsQuery transactionEntriesQuery; 
}
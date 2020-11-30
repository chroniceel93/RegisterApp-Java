package edu.uark.registerapp.controllers;

import java.util.Optional;
import java.util.UUID;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductsQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/search")
public class SearchRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(final HttpServletRequest request) {
		Optional<ActiveUserEntity> activeUser = this.getCurrentUser(request);
		if(!activeUser.isPresent()) {
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.SIGN_IN.getRoute()));
		} // Else, activeUser exists, doNothing();


		ModelAndView modelAndView =
			new ModelAndView(ViewNames.PRODUCT_LISTING.getViewName());

		try {
			modelAndView.addObject(
				ViewModelNames.PRODUCTS.getValue(),
				this.productsQuery.execute());
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

	@RequestMapping(value = "/{partialyLookupcode}", method = RequestMethod.GET)
	public ModelAndView startWithSearchedKeywords(@PathVariable final String partialLookupcode,final HttpServletRequest request) {
		Optional<ActiveUserEntity> activeUser = this.getCurrentUser(request);
		if(!activeUser.isPresent()) {
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.SIGN_IN.getRoute()));
		} // Else, activeUser exists, doNothing();


		final ModelAndView modelAndView =
			new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName());

		try {
			modelAndView.addObject(
                ViewModelNames.PRODUCT.getValue(),
                // Needs ProductRepository and ProductsQuery TO BE CHANGED.
				this.productQuery.setPartialLookupcode(partialLookupcode).execute());
			modelAndView.addObject(
				ViewModelNames.IS_ELEVATED_USER.getValue(),
				this.isElevatedUser(activeUser.get()));
		} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.PRODUCT.getValue(),
				(new Product())
					.setCount(0)
					.setLookupCode(StringUtils.EMPTY));
		}

		return modelAndView;

	}


	// Properties
    @Autowired
    // IMPLEMENT METHOD TO RETRIEVE PRODUCTS BASED ON PARTIAL LOOKUPCODE TO
    // ProductsQuery AND ProductRepository
	private ProductsQuery productsQuery; 
}
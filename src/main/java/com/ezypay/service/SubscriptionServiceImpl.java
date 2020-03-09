package com.ezypay.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ezypay.model.SubscriptionModel;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	@Override
	public SubscriptionModel addSubscription(SubscriptionModel model) throws Exception{
		Date startDate = parseDate(model.getStartDate());
		Date endDate = parseDate(model.getEndDate());
		List<String> list = new ArrayList<>();
		switch(model.getType()) {
			case DAILY:
				list = getDateListByDay(list, startDate, endDate, 1);
				break;
			case WEEKLY:
				list = getDateListByDay(list, startDate, endDate, 7);
				break;
			default: // Monthly
				list = getDateListByDay(list, startDate, endDate, -1); // -1 to indicate its a monthly subscription
		}
		model.setInvoiceDates(list.toArray(new String[list.size()]));
		model.setTotalAmount(calculateTotalAmount(model.getAmount(), list.size()));
		return model;
	}
	
	// Calculate how many invoice date will be created for daily/weekly/monthly subscription
	private List<String> getDateListByDay(List<String> list, Date startDate, Date endDate, int step) {
		if(startDate.compareTo(endDate) <= 0) {
			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);  
			list.add(dateFormat.format(startDate.getTime()));
			Date newStartDate = addDays(startDate, step);
			return getDateListByDay(list, newStartDate, endDate, step);
		} else {
			return list;
		}
	}
	
	private Date addDays(Date input, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		if(days > 0) { // Daily or Weekly
			cal.add(Calendar.DAY_OF_MONTH, days);
		} else { // Monthly
			cal.add(Calendar.MONTH, 1);
		}
		return cal.getTime();
	}
	
	private Date parseDate(String input) throws Exception {
		Date result = new SimpleDateFormat(DATE_FORMAT).parse(input); 
		return result;
	}
	
	private BigDecimal calculateTotalAmount(BigDecimal amount, int numberOfInvoice) {
		if(numberOfInvoice > 0) {
			BigDecimal multiply = new BigDecimal(numberOfInvoice);
			amount = amount.multiply(multiply);
		}
		return amount;
	}

}

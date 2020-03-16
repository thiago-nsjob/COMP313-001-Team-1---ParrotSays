package com.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.models.Customer;
import com.spring.models.CustomerService;
import com.spring.models.IReportService;
import com.spring.models.Report;

@Controller
public class HomeController {
	
	protected Logger logger = Logger.getLogger(HomeController.class.getName());
	
	@Autowired
	IReportService reportService;	
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		clearSession(request);
		
		logger.info("index invoked");
		return "index";
    }
	
	@RequestMapping(value = "/createuser", method = RequestMethod.GET )
	public ModelAndView createuser(HttpServletRequest request) {
		clearSession(request);
		
		return new ModelAndView("createuser", "customer", new Customer());
    }
	
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public String saveuser(@ModelAttribute Customer customer, HttpServletRequest request)
    {
		clearSession(request);

		try {
			if(customer.getPassword().equals(customer.getToken()))
			{
			System.out.println("Actual token: "+request.getSession().getAttribute("token").toString());
			if (customerService.CreateUser(customer, request.getSession().getAttribute("token").toString()))
				request.getSession().setAttribute("message", "New User Created successfully.");
			}
			else
			{
				request.getSession().setAttribute("error", "Passwords do not match. Please, try again.");
				return "createuser";
			}
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
			logger.error(e.toString());
		}

        return "index";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET )
	public ModelAndView login(HttpServletRequest request) {
		clearSession(request);
		
		return new ModelAndView("login", "customer", new Customer());
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET )
	public String logout(HttpServletRequest request) {
		clearSession(request);
		request.getSession().removeAttribute("token");
		request.getSession().removeAttribute("user");
		return "index";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signin(@ModelAttribute Customer customer, HttpServletRequest request)
    {
		clearSession(request);
		try
		{
			String token = customerService.Login(customer);
			if(token != null)
			{
				request.getSession().setAttribute("user", customer.getUsername());
				request.getSession().setAttribute("token", token);
				request.getSession().setAttribute("message", "Sign In successfully.");
				return "index";
			}
			else
			{
				request.getSession().setAttribute("error", "Sign In Error. Please, try again.");
				return "login";
			}
			
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
			logger.error(e.getMessage());
			return "login";
		}
    }
	
	
	private void clearSession(HttpServletRequest request)
	{
		request.getSession().setAttribute("message", "");
		request.getSession().setAttribute("error", "");
	}
	
	@RequestMapping("/about")
	public String about(HttpServletRequest request) {
		clearSession(request);
		
		return "about";
    }
	
	@RequestMapping("/addreport")
	public ModelAndView addReport(HttpServletRequest request) {
		
		clearSession(request);
		
        return new ModelAndView("addreport", "report", new Report());
	}
	
	@RequestMapping("/reportlist")
	public ModelAndView getAllReports(HttpServletRequest request)
	{
		clearSession(request);
		List<Report> reportList = new ArrayList<Report>();

		try {
			String token = request.getSession().getAttribute("token").toString();
			System.out.println("Token: "+token);
			reportList = reportService.getAllReports(token);
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
			logger.error(e.getMessage());
		}
		
        return new ModelAndView("reportlist", "reportList", reportList);

	}
	
	@RequestMapping(value = "/saveSolution", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Report report, HttpServletRequest request)
    {
		clearSession(request);
		List<Report> reportList = new ArrayList<Report>();

		try {
			Long now = new java.util.Date().getTime();
			report.setDateTimeSolution(now);
			if(report.getReportId() != 0)
			{
				if (reportService.updateReport(report, request.getSession().getAttribute("token").toString()))
					request.getSession().setAttribute("message", "Report Solution Saved successfully.");
			}
			else {
				if (reportService.insertReport(report, request.getSession().getAttribute("token").toString()))
					request.getSession().setAttribute("message", "Report added successfully.");
			}
			
			reportList = reportService.getAllReports(request.getSession().getAttribute("token").toString());
			
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
			logger.error(e.getMessage());
		}

        return new ModelAndView("reportlist", "reportList", reportList);
    }
	
	@RequestMapping(value = "/edit", params = "reportId", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam("reportId") int reportId, HttpServletRequest request)
    {
		clearSession(request);
		Report report = new Report();
		
		try {
			report = reportService.getReportById(reportId, request.getSession().getAttribute("token").toString());
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
			logger.error(e.getMessage());
		}

        return new ModelAndView("reportdetails", "report", report);
    }
	
	@RequestMapping("/delete/{reportId}")
    public ModelAndView delete(@PathVariable("reportId") int reportId, HttpServletRequest request)
    {
		clearSession(request);
		List<Report> reportList = new ArrayList<Report>();
		
		try {
			reportService.deleteReport(reportId, request.getSession().getAttribute("token").toString()); // Proceeds Deletion.
			reportList = reportService.getAllReports(request.getSession().getAttribute("token").toString()); // Gets the new list to send to reportList page. 
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
			logger.error(e.getMessage());
		}		
		
		request.getSession().setAttribute("message", "Report removed successfully.");
		
		return new ModelAndView("reportlist", "reportList", reportList);
    }
	
}

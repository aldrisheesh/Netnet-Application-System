package com.group_9.project.database;

import com.group_9.project.session.UserApplicationData;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("customer")
public class Customer extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form data from web form (using database column names)
        String username = request.getParameter("username");
        String customerName = request.getParameter("customer_name");
        String birthdate = request.getParameter("birthdate");
        String gender = request.getParameter("gender");
        String civilStatus = request.getParameter("civil_status");
        String motherMn = request.getParameter("mother_mn");
        String spouseName = request.getParameter("spouse_name");
        String nationality = request.getParameter("nationality");
        String contactNo = request.getParameter("contact_no");
        String emailAdd = request.getParameter("email_add");

        // Store in session using snake_case keys to match UserApplicationData class
        UserApplicationData.set("username", username);
        UserApplicationData.set("customer_name", customerName);
        UserApplicationData.set("birthdate", birthdate);
        UserApplicationData.set("gender", gender);
        UserApplicationData.set("civil_status", civilStatus);
        UserApplicationData.set("mother_mn", motherMn);
        UserApplicationData.set("spouse_name", spouseName);
        UserApplicationData.set("nationality", nationality);
        UserApplicationData.set("contact_no", contactNo);
        UserApplicationData.set("email_add", emailAdd);

        // Redirect to page 2
        response.sendRedirect("residence.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("residence.jsp").forward(request, response);
    }
}
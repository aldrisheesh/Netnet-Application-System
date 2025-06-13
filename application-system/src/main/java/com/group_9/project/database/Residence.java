package com.group_9.project.database;

import com.group_9.project.session.UserApplicationData;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/residence")
public class Residence extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form data
        String ownerName = request.getParameter("owner_name");
        String ownerContact = request.getParameter("owner_contact");
        String residenceAdd = request.getParameter("residence_add");
        String residenceType = request.getParameter("residence_type");
        String residenceYrs = request.getParameter("residence_yrs");
        String compPaid = request.getParameter("comp_paid");

        // Store in session
        UserApplicationData.set("owner_name", ownerName);
        UserApplicationData.set("owner_contact", ownerContact);
        UserApplicationData.set("residence_add", residenceAdd);
        UserApplicationData.set("residence_type", residenceType);
        UserApplicationData.set("residence_yrs", residenceYrs);
        UserApplicationData.set("comp_paid", compPaid);

        // Redirect to page 3
        response.sendRedirect("plans.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("residence.jsp").forward(request, response);
    }
}

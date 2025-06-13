// package com.group_9.project.database;

// import com.group_9.project.session.UserApplicationData;
// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.sql.ResultSet;
// import java.sql.SQLException;

// @WebServlet("/plans")
// public class SelectedPlan extends HttpServlet {
    
//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
        
//         String planId = request.getParameter("plan_ID");
//         UserApplicationData.set("plan_ID", planId);
        
//         // Redirect to payment page
//         response.sendRedirect("payment.jsp");
//     }

//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
        
//         try {
//             ApplicationService service = new ApplicationService();
//             ResultSet plans = service.getServicePlans();
//             request.setAttribute("servicePlans", plans);
//             request.getRequestDispatcher("plans.jsp").forward(request, response);
//         } catch (SQLException e) {
//             e.printStackTrace();
//             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
//         }
//     }
// }

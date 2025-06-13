
// package com.group_9.project.database;

// import com.group_9.project.session.UserApplicationData;
// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;

// @WebServlet("/payment")
// public class ConfirmPayment extends HttpServlet {
    
//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
        
//         String action = request.getParameter("action");
        
//         if ("confirm_payment".equals(action)) {
//             String paymentOption = request.getParameter("payment_option");
//             UserApplicationData.set("payment_option", paymentOption);
            
//             ApplicationService service = new ApplicationService();
//             boolean success = service.processApplication();
            
//             if (success) {
//                 response.sendRedirect("success.jsp");
//             } else {
//                 request.setAttribute("error", "Failed to process application. Please try again.");
//                 request.getRequestDispatcher("page4.jsp").forward(request, response);
//             }
//         }
//     }

//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
        
//         // Check if we have all required data
//         if (!UserApplicationData.hasRequiredCustomerData() || 
//             !UserApplicationData.hasRequiredResidenceData() || 
//             !UserApplicationData.hasSelectedPlan()) {
//             response.sendRedirect("customer.jsp");
//             return;
//         }
        
//         request.getRequestDispatcher("payment.jsp").forward(request, response);
//     }
// }

package com.hotelbookingsystem.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.hotelbookingsystem.DAO.UserDAO;
import com.hotelbookingsystem.database.DatabaseConnection;
import com.hotelbookingsystem.model.Users;
import com.hotelbookingsystem.utility.EncryptDecrypt;

@WebServlet(urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // POST: Handles login logic
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600); // 1 hour

        request.setAttribute("email", email); // Retain input if error occurs

        // Validate inputs
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email is required.");
            forwardToLogin(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Password is required.");
            forwardToLogin(request, response);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            UserDAO userDAO = new UserDAO();

            String encryptedPassword = EncryptDecrypt.encrypt(password);
            Users user = userDAO.login(email, encryptedPassword);

            if (user != null) {
                // Store session attributes
                session.setAttribute("user_id", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("currentUser", user);

                // Redirect by role
                String role = user.getRole().toLowerCase();
                String contextPath = request.getContextPath();
                if ("admin".equals(role)) {
                    response.sendRedirect(contextPath + "/dashboard");
                } else {
                    response.sendRedirect(contextPath + "/customer/home.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid email or password.");
                forwardToLogin(request, response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            forwardToLogin(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            forwardToLogin(request, response);
        }
    }

    // GET: Redirect or forward to login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            String role = (String) session.getAttribute("role");
            if ("admin".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/customer/home.jsp");
            }
        } else {
            forwardToLogin(request, response);
        }
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("access/login.jsp").forward(request, response);
    }
}

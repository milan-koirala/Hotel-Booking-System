package com.hotelbookingsystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.hotelbookingsystem.DAO.UserDAO;
import com.hotelbookingsystem.model.Users;
import com.hotelbookingsystem.utility.EncryptDecrypt;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Failed to initialize UserDAO", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String retypePassword = request.getParameter("retypePassword");
        String phoneNo = request.getParameter("phoneNo");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String terms = request.getParameter("terms");

        // Get session
        HttpSession session = request.getSession();
        
        // Store form data in session for repopulation
        session.setAttribute("firstname", firstname);
        session.setAttribute("lastname", lastname);
        session.setAttribute("username", username);
        session.setAttribute("email", email);
        session.setAttribute("phoneNo", phoneNo);
        session.setAttribute("address", address);
        session.setAttribute("gender", gender);
        session.setAttribute("dob", dob);
        session.setAttribute("terms", terms);

        try {
            // Validation
            if (firstname == null || firstname.trim().isEmpty()) {
                session.setAttribute("errorMessage", "First name is required!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            if (lastname == null || lastname.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Last name is required!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            if (username == null || username.trim().isEmpty() || !username.matches("[a-zA-Z0-9]{3,50}")) {
                session.setAttribute("errorMessage", "Username must be 3-50 characters, letters and numbers only!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            if (email == null || email.trim().isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                session.setAttribute("errorMessage", "Please enter a valid email!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            if (password == null || password.length() < 6) {
                session.setAttribute("errorMessage", "Password must be at least 6 characters!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            if (!password.equals(retypePassword)) {
                session.setAttribute("errorMessage", "Passwords don't match!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            if (terms == null || !terms.equals("on")) {
                session.setAttribute("errorMessage", "You must agree to the terms and conditions!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            // Check for duplicate username or email
            if (userDAO.existsByUsernameOrEmail(username, email)) {
                session.setAttribute("errorMessage", "Username or email already taken!");
                response.sendRedirect("access/register.jsp");
                return;
            }
            
            // Encrypt password
            String encryptedPassword = EncryptDecrypt.encrypt(password);
            
            // Create Users object
            Users user = new Users();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(encryptedPassword);
            user.setRole("customer");
            user.setPhoneNo(phoneNo);
            user.setAddress(address);
            user.setGender(gender);
            if (dob != null && !dob.isEmpty()) {
                try {
                    user.setDob(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dob).getTime()));
                } catch (Exception e) {
                    session.setAttribute("errorMessage", "Invalid date format!");
                    response.sendRedirect("access/register.jsp");
                    return;
                }
            }

            // Register user via UserDAO
            boolean registered = userDAO.register(user);
            if (registered) {
                // Clear session attributes on success
                session.invalidate();
                response.sendRedirect("access/login.jsp");
            } else {
                session.setAttribute("errorMessage", "Registration failed. Try again!");
                response.sendRedirect("access/register.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error. Try again later!");
            response.sendRedirect("access/register.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Something went wrong. Try again later!");
            response.sendRedirect("access/register.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Clear session attributes on GET to start fresh
        HttpSession session = request.getSession();
        session.removeAttribute("firstname");
        session.removeAttribute("lastname");
        session.removeAttribute("username");
        session.removeAttribute("email");
        session.removeAttribute("phoneNo");
        session.removeAttribute("address");
        session.removeAttribute("gender");
        session.removeAttribute("dob");
        response.sendRedirect("access/register.jsp");
    }
}
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register | Hotel RockStar</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
  <style>
    .error-alert-container {
      position: fixed;
      top: 20px;
      left: 50%;
      transform: translateX(-50%);
      max-width: 500px;
      z-index: 1050;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    .error-alert {
      position: relative;
      background-color: #f8d7da;
      color: #721c24;
      padding: 15px 40px 15px 20px;
      border: 1px solid #f5c6cb;
      border-radius: 5px;
      width: 100%;
      animation: slideIn 0.5s ease-out;
      text-align: center;
      margin-bottom: 10px;
    }
    .error-alert .close-btn {
      position: absolute;
      top: 50%;
      right: 10px;
      transform: translateY(-50%);
      background: none;
      border: none;
      color: #721c24;
      font-size: 32px;
      cursor: pointer;
    }
    @keyframes slideIn {
      from {
        transform: translateY(-20px);
        opacity: 0;
      }
      to {
        transform: translateY(0);
        opacity: 1;
      }
    }
  </style>
</head>
<body>
  <div class="main-container">
    <div class="error-alert-container" id="errorContainer">
      <c:if test="${not empty errorMessage}">
        <div class="error-alert">
          ${errorMessage}
          <button class="close-btn" onclick="this.parentElement.remove()">×</button>
        </div>
        <script>
          const alertElement = document.querySelectorAll('.error-alert');
          alertElement.forEach(alert => {
            setTimeout(() => {
              alert.remove();
            }, 4000);
          });
        </script>
      </c:if>
    </div>

    <div class="register-container">
      <h2>Create Your Account</h2>
      <p>Please enter your details</p>
      <form method="post" action="${pageContext.request.contextPath}/RegisterController">
        <div class="form-container">
          <div class="form-row">
            <div class="form-fields">
              <label for="firstname" class="label-for-form">First Name:</label>
              <input type="text" class="form-control" 
                     id="firstname" name="firstname" value="${sessionScope.firstname}" 
                     placeholder="Enter your first name">
            </div>
            <div class="form-fields">
              <label for="lastname" class="label-for-form">Last Name:</label>
              <input type="text" class="form-control" 
                     id="lastname" name="lastname" value="${sessionScope.lastname}" 
                     placeholder="Enter your last name">
            </div>
          </div>

          <div class="form-row">
            <div class="form-fields">
              <label for="username" class="label-for-form">Username:</label>
              <input type="text" class="form-control" 
                     id="username" name="username" value="${sessionScope.username}" 
                     placeholder="Enter your username">
            </div>
            <div class="form-fields">
              <label for="email" class="label-for-form">Email:</label>
              <input type="email" class="form-control" 
                     id="email" name="email" value="${sessionScope.email}" 
                     placeholder="Enter your email">
            </div>
          </div>

          <div class="form-row">
            <div class="form-fields">
              <div class="password-wrapper">
                <label for="password" class="label-for-form">Password:</label>
                <input type="password" class="form-control" 
                       id="password" name="password" 
                       placeholder="Enter your password">
                <span class="material-symbols-rounded" onclick="togglePassword('password')">visibility</span>
              </div>
            </div>
            <div class="form-fields">
              <div class="password-wrapper">
                <label for="retypePassword" class="label-for-form">Retype Password:</label>
                <input type="password" class="form-control" 
                       id="retypePassword" name="retypePassword" 
                       placeholder="Retype your password">
                <span class="material-symbols-rounded" onclick="togglePassword('retypePassword')">visibility</span>
              </div>
            </div>
          </div>

          <div class="form-check">
            <input class="form-check-input" type="checkbox" id="terms" name="terms" ${sessionScope.terms == 'on' ? 'checked' : ''}>
            <label class="form-check-label" for="terms">I agree to the terms and conditions</label>
          </div>
        </div>
        <button type="submit" class="btn-register">Register</button>
      </form>
    </div>

    <div class="image-section">
      <img src="${pageContext.request.contextPath}/images/Room-view.jpg" alt="Hotel Image">
      <div class="overlay">
        <h2>Welcome to Hotel RockStar</h2>
        <p>Book your stay with us and enjoy a luxurious experience.</p>
        <div class="login">
          <span>Already have an account?</span><br/>
          <a href="${pageContext.request.contextPath}/access/login.jsp">Login</a>
        </div>
      </div>
    </div>
  </div>

  <script>
    function togglePassword(inputId) {
      const input = document.getElementById(inputId);
      const icon = input.nextElementSibling;
      if (input.type === "password") {
        input.type = "text";
        icon.textContent = "visibility_off";
      } else {
        input.type = "password";
        icon.textContent = "visibility";
      }
    }
  </script>  
</body>
</html>

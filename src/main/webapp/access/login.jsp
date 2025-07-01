<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Login | Hotel RockStar</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />

  <style>
    .error-alert-container {
      position: fixed;
      top: 40px;
      right:10%;
      transform: translateX(-50%);
      max-width: 500px;
      z-index: 1050;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    .error-alert {
      background-color: #f8d7da;
      color: #721c24;
      padding: 15px 40px 15px 20px;
      border: 1px solid #f5c6cb;
      border-radius: 5px;
      animation: slideIn 0.5s ease-out;
      position: relative;
      width: 100%;
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
      from { transform: translateY(-20px); opacity: 0; }
      to { transform: translateY(0); opacity: 1; }
    }
  </style>
</head>

<body>
  <div class="main-container">
  
    <!-- Popup error message -->
    <c:if test="${not empty errorMessage}">
      <div class="error-alert-container" id="errorContainer">
        <div class="error-alert">
          ${errorMessage}
          <button class="close-btn" onclick="this.parentElement.remove()">×</button>
        </div>
      </div>
      <script>
        setTimeout(() => {
          const container = document.getElementById("errorContainer");
          if (container) container.remove();
        }, 4000);
      </script>
    </c:if>

    <div class="image-section">
      <img src="${pageContext.request.contextPath}/images/Room-view.jpg" alt="Hotel Image" />
      <div class="overlay">
        <h2>Welcome to Hotel RockStar</h2>
        <p>Book your stay with us and enjoy a luxurious experience.</p>
        <div class="register">
          <span>Don't have an account?</span><br />
          <a href="${pageContext.request.contextPath}/access/register.jsp">Register</a>
        </div>
      </div>
    </div>

    <div class="login-container">
      <h2>Login Your Account</h2>
      <p>Please, enter your email and password</p>

      <form action="${pageContext.request.contextPath}/LoginController" method="post">
        <div class="form-container">
          <div class="form-row">
            <div class="form-fields">
              <label for="email" class="label-for-form">Email:</label>
              <input type="email" class="form-control" id="email" name="email" value="${email}" placeholder="Enter your email" />
            </div>
          </div>

          <div class="form-row">
            <div class="form-fields">
              <div class="password-wrapper">
                <label for="password" class="label-for-form">Password:</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" />
                <span class="material-symbols-rounded" role="button" onclick="togglePassword('password')">visibility</span>
              </div>
            </div>
          </div>
        </div>
        <button type="submit" class="btn-login">Login</button>
      </form>
    </div>
  </div>

  <script>
    function togglePassword(id) {
      const input = document.getElementById(id);
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

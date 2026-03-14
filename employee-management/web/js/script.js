// Global form validation and helpers

// Validate Login Form
function validateLogin() {
    let isValid = true;
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    
    if (username === '') {
        document.getElementById('usernameError').style.display = 'block';
        isValid = false;
    } else {
        document.getElementById('usernameError').style.display = 'none';
    }
    
    if (password === '') {
        document.getElementById('passwordError').style.display = 'block';
        isValid = false;
    } else {
        document.getElementById('passwordError').style.display = 'none';
    }
    
    return isValid;
}

// Validate Employee Form (Add/Edit)
function validateEmployeeForm() {
    let isValid = true;
    
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const department = document.getElementById('department').value.trim();
    const salary = document.getElementById('salary').value.trim();
    
    // Validate Name
    if (name === '') {
        showError('nameError', 'Name is required');
        isValid = false;
    } else {
        hideError('nameError');
    }
    
    // Validate Email
    if (email === '' || !email.includes('@')) {
        showError('emailError', 'Valid email containing "@" is required');
        isValid = false;
    } else {
        hideError('emailError');
    }
    
    // Validate Department
    if (department === '') {
        showError('departmentError', 'Department is required');
        isValid = false;
    } else {
        hideError('departmentError');
    }
    
    // Validate Salary
    if (salary === '' || isNaN(salary) || Number(salary) <= 0) {
        showError('salaryError', 'Salary must be a valid number greater than 0');
        isValid = false;
    } else {
        hideError('salaryError');
    }
    
    return isValid;
}

function showError(id, message) {
    const el = document.getElementById(id);
    if(el) {
        el.innerText = message;
        el.style.display = 'block';
    }
}

function hideError(id) {
    const el = document.getElementById(id);
    if(el) {
        el.style.display = 'none';
    }
}

// Format Currency Utility
function formatCurrency(amount) {
    return '$' + parseFloat(amount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}

export function initializeResumeAppScript() {
    // Helper function to remove commas from salary input
    function removeWrongFormatSalary(salary) {
      return salary.replace(',', '');
    }
  
    // Function to validate salary input fields
    function validateSalary(type) {
      let salaryInput = type === 'previous' ? document.getElementById("previousSalary") : document.getElementById("expectedSalary");
      let salary = salaryInput.value;
      
      if (salary.includes(',')) {
        salaryInput.value = removeWrongFormatSalary(salary);
      }
    }


    function calculateAge() {
          
        let str = document.getElementById("dateOfBirth").value;
  
          // Extracting the year, month, and day from the input
          let year = parseInt(str.split("/")[0], 10) - 543;
          let month = parseInt(str.split("/")[1], 10) - 1;
          let day = parseInt(str.split("/")[2], 10);
  
          // Constructing the birth date
          var birthDate = new Date(year, month, day);
  
          // Calculating age based on the difference of years
          var today = new Date();
          var age = today.getFullYear() - birthDate.getFullYear();
          var m = today.getMonth() - birthDate.getMonth();
  
          // Adjusting age if needed
          if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
          }
          return age;
    }
    
  
    // Function to calculate and display the length of the telephone number as the user types
    document.getElementById("tel").addEventListener("input", function () {
      let tel = document.getElementById("tel").value;
      document.getElementById("telLabel").innerHTML = tel.length;
    });
  
    // Function to calculate and display the total years of experience based on input
    document.getElementById("previousCompany").addEventListener("input", function () {
      let previousCompany = document.getElementById("previousCompany").value;
      let year = 0;
      let month = 0;
  
      let matches = previousCompany.match(/\((\d+) (ปี|เดือน)\)/g);
      if (matches) {
        matches.forEach(match => {
          let [_, num, unit] = match.match(/\((\d+) (ปี|เดือน)\)/);
          if (unit === 'ปี') {
            year += parseInt(num, 10);
          } else if (unit === 'เดือน') {
            month += parseInt(num, 10) / 12;
          }
        });
      }
  
      let resultTotalYear = Math.round((year + month) * 100) / 100;
      document.getElementById("experience").value = resultTotalYear;
    });
  
    // Event listeners for salary inputs
    document.getElementById("previousSalary").addEventListener("input", function () {
      validateSalary('previous');
    });
  
    document.getElementById("expectedSalary").addEventListener("input", function () {
      validateSalary('expected');
    });

    document.getElementById("dateOfBirth").addEventListener("input", function () {
        let age = calculateAge();
        document.getElementById("age").value = age;
    });
}
  
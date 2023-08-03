
  app.controller('HeaderController', function($scope, $http) {
    $scope.username = '';

    $scope.getUsername = function() {
      $http.get('http://localhost:8080/sportify/user/get-username', {
        withCredentials: true // Bao gồm thông tin xác thực (token xác thực) trong yêu cầu
      }).then(function(response) {
        if (response.data.username) {
          $scope.username = response.data.username;
        } else {
          console.log('Error fetching username:', response.data.error);
        }
      }).catch(function(error) {
        console.log('Error fetching username:', error);
      });
    };

    $scope.getUsername(); // Gọi hàm này khi controller được tải
  });
app.controller('TeamDetailController', function ($scope, $routeParams, $http) {
        // Lấy thông tin teamId từ $routeParams để gọi API lấy thông tin đội
        var teamid = $routeParams.teamid;

        // Hàm lấy thông tin chi tiết đội và thành viên từ backend
        $scope.getTeamDetails = function () {
            $http.get('/rest/teams/get/' + teamid).then(function (response) {
                $scope.selectedTeam = response.data;
            });

            $http.get('/rest/teams/' + teamid).then(function (response) {
                $scope.teamMembers = response.data;
            });
        };

        // Gọi hàm để lấy thông tin chi tiết đội và thành viên khi trang được tải
        $scope.getTeamDetails();
    });
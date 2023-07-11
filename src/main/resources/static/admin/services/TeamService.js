app.factory('TeamService', function($http) {
    var baseUrl = host + '/rest/teams';
  
    return {
      getAllTeam: function() {
        return $http.get(baseUrl + '/getAll');
      },
      getNewTeam: function() {
        return $http.get(baseUrl + '/newTeam');
      },   
      getTeamById: function(id) {
        return $http.get(baseUrl + '/edit/' + id);
      },
      createTeam: function(teamData) {
        return $http.post(baseUrl + '/createTeam', teamData);
      },
      updateTeam: function(teamData) {
        return $http.put(baseUrl + '/updateTeam', teamData);
      }
      ,
      deleteTeam: function(id) {
        return $http.delete(baseUrl + '/deleteTeam/' + id);
      }
    };
  });
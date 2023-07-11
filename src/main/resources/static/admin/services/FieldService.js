app.factory('ProductService', function($http) {
    var baseUrl = host + '/rest/products';
  
    return {
      getAllField: function() {
        return $http.get(baseUrl + '/getAll');
      },
      getNewField: function() {
        return $http.get(baseUrl + '/newField');
      },   
      getFieldById: function(id) {
        return $http.get(baseUrl + '/edit/' + id);
      },
      createField: function(fieldData) {
        return $http.post(baseUrl + '/createField', fieldData);
      },
      updateField: function(fieldData) {
        return $http.put(baseUrl + '/updateField', fieldData);
      }
      ,
      deleteField: function(id) {
        return $http.delete(baseUrl + '/deleteField/' + id);
      }
    };
  });
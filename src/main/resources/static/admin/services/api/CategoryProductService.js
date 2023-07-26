app.factory('CategoryProductService', function($http) {
    var baseUrl = host + '/rest/category-product';
  
    return {
      getAll: function() {
        return $http.get(baseUrl + '/getAll');
      },
      getNew: function() {
        return $http.get(baseUrl + '/newForm');
      },   
      getOne: function(id) {
        return $http.get(baseUrl + '/edit/' + id);
      },
      create: function(formData) {
        return $http.post(baseUrl + '/create', formData);
      },
      update: function(formData) {
        return $http.put(baseUrl + '/update', formData);
      }
      ,
      delete: function(id) {
        return $http.delete(baseUrl + '/delete/' + id);
      }
    };
});
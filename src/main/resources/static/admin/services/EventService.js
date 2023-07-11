app.factory('ProductService', function($http) {
    var baseUrl = host + '/rest/products';
  
    return {
      getAllProduct: function() {
        return $http.get(baseUrl + '/getAll');
      },
      getNewProduct: function() {
        return $http.get(baseUrl + '/newProduct');
      },   
      getProductById: function(id) {
        return $http.get(baseUrl + '/edit/' + id);
      },
      createProduct: function(productData) {
        return $http.post(baseUrl + '/createHoliday', productData);
      },
      updateProduct: function(productData) {
        return $http.put(baseUrl + '/updateProduct', productData);
      }
      ,
      deleteProduct: function(id) {
        return $http.delete(baseUrl + '/deleteProduct/' + id);
      }
    };
  });
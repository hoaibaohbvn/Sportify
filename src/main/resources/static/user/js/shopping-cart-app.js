const app = angular.module("shopping-cart-app", []);

/*app.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}])*/

app.controller("shopping-cart-ctrl", function($scope, $http) {

	$scope.cart = {
		items: [],
		//thêm SP
		addProduct(productid) {
			//alert(productid);
			const testItem = {name: "Product1", img: "product1_img.png", price: "$100"};
			//const testCart = JSON.stringify(angular.copy(testItem));
			//localStorage.setItem("testCart", testCart);
			var item = this.items.find(item => item.productid == productid);
			if (item) {
				item.quantity++;
				this.saveToSessionStorage();
			} else {
				$http.get(`/sportify/rest/products/${productid}`).then(resp => {
					resp.data.quantity = 1;
					this.items.push(resp.data);
					this.saveToSessionStorage();
				});
			}
		},
		//xóa SP
		removeProduct(productid) { },
		//clear giỏ hàng
		clearCart() { },
		//tính tiền 1 SP
		priceOf(item) { },
		//tính số lượng SP trong giỏ hàng
		get cartCount() { },
		//tính tổng tiền của giỏ hàng
		get totalPrice() { },
		//lưu giỏ hàng vào session storage
		saveToSessionStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			sessionStorage.setItem("cart", json);
		},
		saveToLocalStorage(){
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},
		//đọc giỏ hàng từ session storage
		loadFromSessionStorage() { }
	}
});
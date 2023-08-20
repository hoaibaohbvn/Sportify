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
			//const testItem = { name: "Product1", img: "product1_img.png", price: "$100" };
			//const testCart = JSON.stringify(angular.copy(testItem));
			//localStorage.setItem("testCart", testCart);
			var item = this.items.find(item => item.productid == productid);
			if (item) {
				item.quantity++;
				this.saveToSessionStorage();
			} else {
				$http.get(`/sportify/rest/products_user/${productid}`).then(resp => {
					resp.data.quantity = 1;
					this.items.push(resp.data);
					this.saveToSessionStorage();
				});
			}
		},
		//xóa SP
		removeProduct(productid) {
			var index = this.items.findIndex(item => item.productid == productid);
			this.items.splice(index, 1);
			this.saveToSessionStorage();
		},
		//clear giỏ hàng
		clearCart() {
			this.items = [];
			this.saveToSessionStorage();
		},
		//tính tiền 1 SP
		priceOf(item) { },
		//tính số lượng SP trong giỏ hàng
		get cartCount() {
			return this.items.map(item => item.quantity).reduce((total, quantity) => total += quantity, 0);
		},
		//tính tổng tiền của giỏ hàng
		get totalPrice() {
			return this.items.map(item => item.quantity * item.price).reduce((total, quantity) => total += quantity, 0);
		},
		//lưu giỏ hàng vào session storage
		saveToSessionStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			sessionStorage.setItem("cart", json);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},
		//đọc giỏ hàng từ session storage
		loadFromSessionStorage() {
			var json = sessionStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
	}

	//tính phí vận chuyển
	$scope.shippingFee = function() {
		return ($scope.cart.totalPrice >= 300000 ? 0 : 30000);
	}

	//tính mã giảm giá
	$scope.voucherDiscountPercent = function() {
		var discountPercent = document.getElementById('voucherDiscountPercent').value;
		sessionStorage.setItem("discountPercentStorage", discountPercent);
	}
	$scope.voucherDiscountPrice = function() {
		var discountPercent2 = sessionStorage.getItem("discountPercentStorage");
		if (discountPercent2) {
			var voucherPrice;
			voucherPrice = ($scope.cart.totalPrice * (discountPercent2 / 100));
			return voucherPrice;
		} else {
			return 0;
		}
	}
	$scope.clearVoucher = function() {
		sessionStorage.setItem("discountPercentStorage", 0);
	}

	//update tổng tiền
	$scope.updateTotalPrice = function() {
		var updatePrice;
		updatePrice = ($scope.cart.totalPrice + $scope.shippingFee() - $scope.voucherDiscountPrice());
		return updatePrice;
	}


	$scope.cart.loadFromSessionStorage();

	$scope.order = {
		username: $("#username").text() ,
		createdate: new Date(),
		address: "",
		note: "",
		totalprice: $scope.updateTotalPrice(),
		orderstatus: 1,
		paymentstatus: 0,
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					products: { productid: item.productid },
					price: item.price,
					quantity: item.quantity,
					//producTotalPrice: item.price * item.quantity 
				}
			});
		},
		purchase() {
			//alert("Đặt hàng thành công");
			var order = angular.copy(this);
			// thực hiện đặt hàng
			$http.post("/sportify/rest/orders", order).then(resp => {
				alert("Đặt hàng thành công!");
				$scope.cart.clearCart();
				$scope.clearVoucher();
				location.href = "/sportify/order/detail/" + resp.data.orderid;
			}).catch(error => {
				alert("Đặt hàng lỗi!")
				console.log(error)
			});
		}

	}

	//tính tổng tiền order
	$scope.orderTotalPrice = [];
	
	
	// Custom validation function
    $scope.validateNoSpecialCharacters = function(input) {
      return /^[a-zA-Z0-9,./ À-Ỹà-ỹ]*$/.test(input);
    };
})
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
	$scope.shippingFee = {
		shipFee: 0,
		cartShipFee() {
			return ($scope.cart.totalPrice >= 300000 ? 0 : 30000);
		}
	}

	//tính mã giảm giá
	//$scope.voucherid = voucher.id;
	$scope.voucherCode = {
		vouchers: [],
		addVoucher(voucherid) {
			var voucher = this.vouchers.find(voucher => voucher.voucherid == voucherid);
			if (voucher) {
				
			} else {
				$http.get(`/sportify/rest/order/cart/${voucherid}`).then(resp => {
					this.vouchers.push(resp.data);
					this.saveToSessionStorage();
				});
			};
			/*$http.get(`/sportify/rest/order/cart/${voucherid}`).then(resp => {
				this.vouchers = resp.data;
				this.vouchers.forEach(voucher => {
					voucher.startdate = new Date(voucher.startdate)
					voucher.enddate = new Date(voucher.enddate)
				})
				//alert(voucherid);
			});*/
		}
	};
	
	//update tổng tiền
	$scope.updateTotalPrice = {
		updateTotalPrice() {
			var updatePrice;
			updatePrice = ($scope.cart.totalPrice + $scope.shippingFee.cartShipFee())
			return updatePrice;
		}
	}
	$scope.cart.loadFromSessionStorage();

	$scope.order = {
		username: "",
		createdate: new Date(),
		address: "",
		note: "",
		totalprice: 0,
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
				location.href = "/sportify/order/detail/" + resp.data.orderid;
			}).catch(error => {
				alert("Đặt hàng lỗi!")
				console.log(error)
			});
		}
		
	}
	
	//tính tổng tiền order
	$scope.orderTotalPrice = [];
	
})
app.controller('OrderController', function($scope, $http) {
	
	$scope.username = '';

	$scope.getUsername = function() {
		$http.get('http://localhost:8080/sportify/user/get-username', {
			withCredentials: true // Bao gồm thông tin xác thực (token xác thực) trong yêu cầu
		}).then(function(response) {
			if (response.data.username) {
				$scope.username = response.data.username;
				$http.get("/rest/authorized/getRole/" + $scope.username).then(resp => {
					$scope.listRoles = resp.data;
					if ($scope.listRoles[0][1] === 'R01') {
						$scope.kiemDuyet = 'ok'
					}
				});
			} else {
				console.log('Error fetching username:', response.data.error);
			}
		}).catch(function(error) {
			console.log('Error fetching username:', error);
		});
	};


	$scope.getUsername(); // Gọi hàm này khi controller được tải
	// hàm đổ tất cả
	$scope.getAll = function() {

		// lấy danh sách product
		$http.get("/rest/orders/getAll").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createdate = new Date(item.createdate)
			})
		});
	}


	// hàm edit
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$scope.errors = [];
		// lấy danh sách bookingdetail theo idbooking
		$http.get("/rest/orderdetails/" + item.orderid).then(resp => {
			$scope.orderdetail = resp.data;
		});
	}

	// hàm cập nhập
	$scope.update = function() {
		var item = angular.copy($scope.form);
		if(item.orderstatus === 'Hoàn Thành'){
			item.paymentstatus = 1
		}
		$http.put(`/rest/orders/update/${item.orderid}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.orderid == item.orderid);
			$scope.items[index] = item;
			showSuccessToast("Đã cập nhập thành công trạng thái của đơn hàng")
			$('#edit').modal('hide')
			$scope.getAll();
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			// Xử lý lỗi phản hồi từ máy chủ
			if (error.data && error.data.errors) {
				$scope.errors = error.data.errors;
			}
			if (error.data) {
				showErrorToast("Vui lòng kiểm tra lại form");
			}
			console.log($scope.errors);
			console.log(error);
		});
	}
	// hàm xác nhận đơn hàng
	$scope.confirmOrder = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/orders/confirm/${item.orderid}`, item).then(resp => {
			showSuccessToast("Xác nhận đơn hàng thành công!")
			$('#edit').modal('hide')
			$scope.getAll();
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			// Xử lý lỗi phản hồi từ máy chủ
			if (error.data && error.data.errors) {
				$scope.errors = error.data.errors;
			}
			if (error.data) {
				showErrorToast("Vui lòng kiểm tra lại form");
			}
			console.log($scope.errors);
			console.log(error);
		});
	}
	// hàm hủy đơn hàng
	$scope.cancelOrder = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/orders/cancel/${item.orderid}`, item).then(resp => {
			showSuccessToast("Hủy đơn hàng thành công!")
			$('#edit').modal('hide')
			$scope.getAll();
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			// Xử lý lỗi phản hồi từ máy chủ
			if (error.data && error.data.errors) {
				$scope.errors = error.data.errors;
			}
			if (error.data) {
				showErrorToast("Vui lòng kiểm tra lại form");
			}
			console.log($scope.errors);
			console.log(error);
		});
	}

	$scope.getAll();

	// hàm refresh
	$scope.refresh = function refreshNow() {
		location.reload();
	}

	// Toast function
	function toast({ title = "", message = "", type = "info", duration = 3000 }) {
		const main = document.getElementById("toast");
		if (main) {
			const toast = document.createElement("div");

			// Auto remove toast
			const autoRemoveId = setTimeout(function() {
				main.removeChild(toast);
			}, duration + 1000);

			// Remove toast when clicked
			toast.onclick = function(e) {
				if (e.target.closest(".toast__close")) {
					main.removeChild(toast);
					clearTimeout(autoRemoveId);
				}
			};

			const icons = {
				success: "fas fa-check-circle",
				info: "fas fa-info-circle",
				warning: "fas fa-exclamation-circle",
				error: "fas fa-exclamation-circle"
			};
			const icon = icons[type];
			const delay = (duration / 1000).toFixed(2);

			toast.classList.add("toastDesign", `toast--${type}`);
			toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;

			toast.innerHTML = `
                <div class="toast__icon">
                    <i class="${icon}"></i>
                </div>
                <div class="toast__body">
                    <h3 class="toast__title">${title}</h3>
                    <p class="toast__msg">${message}</p>
                </div>
                <div class="toast__close">
                    <i class="fas fa-times"></i>
                </div>
            `;
			main.appendChild(toast);
		}
	};


	// Thông báo Toast Success
	function showSuccessToast(message) {
		var toastMessage = message || "Đã thêm nhân viên thành công.";
		toast({
			title: "Thành công!",
			message: toastMessage,
			type: "success",
			duration: 5000
		});
	}

	function showErrorToast(error) {
		toast({
			title: "Thất bại!",
			message: error,
			type: "error",
			duration: 5000
		});
	}

	$scope.clearErrors = function() {
		// Iterate through the errors array and remove errors with matching field names
		$scope.errors = [];
	};
	function refreshPageAfterThreeSeconds() {
		setTimeout(function() {
			location.reload();
		}, 100); // 3000 milliseconds tương đương 3 giây
	}
	// search
	$scope.searchName = '';
	$scope.searchDate = new Date();
	$scope.searchStatus = '';
	$scope.searchPayment = 0;
	$scope.search = function() {

		var momentDate = moment($scope.searchDate); // xài import thư viện Moment.js
		var dateString = momentDate.format("YYYY-MM-DD");

		$http.get('/rest/orders/search', {
			params:
			{
				keyword: $scope.searchName,
				datebook: dateString,
				status: $scope.searchStatus,
				payment: $scope.searchPayment
			}
		}).then(function(response) {
			$scope.items = response.data;

			console.log(dateString)
		})
			.catch(function(error) {
				console.log('Lỗi khi gửi yêu cầu:', error);
			});
	};
	// định dạng tiền tệ VND
	$scope.formatCurrency = function(value) {
		// Sử dụng filter number để định dạng thành 100,000
		var formattedValue = new Intl.NumberFormat('vi-VN').format(value);
		return formattedValue + ' VND';
	};
})
app.controller('VoucherController', function($scope, $http) {
	// hàm đổ tất cả
	$scope.fillDate = null
	$scope.getAll = function() {
		if($scope.fillDate == null){
			// lấy danh sách 
		$http.get("/rest/vouchers/getAll").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.startdate = new Date(item.startdate)
				item.enddate = new Date(item.enddate)
			})
		});
		}
		if($scope.fillDate == 1){
			// lấy danh sách 
		$http.get("/rest/vouchers/fillActive").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.startdate = new Date(item.startdate)
				item.enddate = new Date(item.enddate)
			})
		});
		}
		if($scope.fillDate == 0){
			// lấy danh sách 
		$http.get("/rest/vouchers/fillInActive").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.startdate = new Date(item.startdate)
				item.enddate = new Date(item.enddate)
			})
		});
		}
		if($scope.fillDate == 2){
			// lấy danh sách 
		$http.get("/rest/vouchers/fillWillActive").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.startdate = new Date(item.startdate)
				item.enddate = new Date(item.enddate)
			})
		});
		}
		$scope.reset();
	}
	// hàm rest form
	$scope.reset = function() {
		$scope.form = {
			startdate: new Date(),
			enddate: new Date(),
		}
		$scope.errors = [];
	}
	// hàm edit
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$scope.errors = [];
	}
	// hàm tạo
	$scope.create = function() {
		var item = angular.copy($scope.form);
		if (item.startdate >= item.enddate) {
			showErrorToast("Ngày bắt đầu phải trước ngày kết thúc sự kiện")
			return; // Dừng việc thêm mới nếu ngày không hợp lệ
		}
		$http.post(`/rest/vouchers/create`, item).then(resp => {
			resp.data.startdate = new Date(resp.data.startdate)
			resp.data.enddate = new Date(resp.data.enddate)
			$scope.items.push(resp.data);
			showSuccessToast("Đã thêm thành công voucher giảm giá " + item.discountpercent + " %")
			$('#add').modal('hide')
			$scope.reset();
			$scope.getAll();
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			// Xử lý lỗi phản hồi từ máy chủ
			if(error.status === 1000){
				showErrorToast("Voucher ID đã tồn tại. Vui lòng chọn một Voucher ID khác.");
			}
			 else if (error.data && error.data.errors) {
				$scope.errors = error.data.errors;
			}
			 else if(error.data) {
				showErrorToast("Vui lòng kiểm tra lại form");
			}
			
		});
	}
	// hàm cập nhập
	$scope.update = function() {
		var item = angular.copy($scope.form);
		if (item.startdate >= item.enddate) {
			showErrorToast("Ngày bắt đầu phải trước ngày kết thúc sự kiện")
			return; // Dừng việc thêm mới nếu ngày không hợp lệ
		}
		$http.put(`/rest/vouchers/update/${item.voucherid}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.voucherid == item.voucherid);
			$scope.items[index] = item;
			showSuccessToast("Đã cập nhập thành công voucher")
			$('#edit').modal('hide')
			$scope.getAll();
			refreshPageAfterThreeSeconds();
		})
			.catch(error => {
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
	// ham delete
	$scope.delete = function(item) {
		$http.delete(`/rest/vouchers/delete/${item.voucherid}`).then(resp => {
			var index = $scope.items.findIndex(p => p.voucherid == item.voucherid);
			$scope.items.splice(index, 1);
			// Đặt lại trạng thái của form (nếu có)
			$scope.reset();
			$('#delete').modal('hide')
			// Hiển thị thông báo thành công
			showSuccessToast("Đã xóa thành công voucher tên " + item.voucherid)
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			showErrorToast("Xóa voucher thất bại")
			console.log("Error", error);
		})
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
				error: "fas fa-exclamation-circle",
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
	}

	// Thông báo Toast Success
	function showSuccessToast(message) {
		var toastMessage = message || "Đã thêm phòng ban thành công.";
		toast({
			title: "Thành công!",
			message: toastMessage,
			type: "success",
			duration: 5000,
		});
	}

	function showErrorToast(error) {
		toast({
			title: "Thất bại!",
			message: error,
			type: "error",
			duration: 5000,
		});
	}

	$scope.clearErrors = function() {
		// Iterate through the errors array and remove errors with matching field names
		$scope.errors = [];
	};
	function refreshPageAfterThreeSeconds() {
		setTimeout(function() {
			location.reload();
		}, 2000); // 3000 milliseconds tương đương 3 giây
	}
	
})
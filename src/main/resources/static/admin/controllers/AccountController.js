app.controller('AccountController', function($scope, $http) {
	// hàm đổ tất cả
	$scope.getAll = function() {
		// lấy danh sách product
		$http.get("/rest/accounts/getAll").then(resp => {
			$scope.items = resp.data;
		});
		$scope.reset();
	}
	// hàm rest form
	$scope.reset = function() {
		$scope.form = {
			datecreate: new Date(),
			status: true,
			gender: true,
			image: "loading.jpg"
		}
		$scope.form.roleid = 'R03'
	}
	// hàm edit
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$scope.errors = [];
	}
	// hàm tạo

	$scope.create = function() {
		var item = angular.copy($scope.form);

		$http.post(`/rest/accounts/create`, item).then(resp => {
			$scope.items.push(resp.data);
			$http.post(`/rest/authorized/create`, item).then(rp => {
				$scope.items.push(rp.data);
			})
			showSuccessToast("Tài khoản mới tên " + item.username + " đã thành công")
			$scope.reset();
			$('#add').modal('hide')
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
	// hàm cập nhập
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/accounts/update/${item.username}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.username == item.username);
			$scope.items[index] = item;
			showSuccessToast("Cập nhập tài khoản thành công")
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
		$http.delete(`/rest/accounts/delete/${item.username}`).then(resp => {
			var index = $scope.items.findIndex(p => p.username == item.username);
			$scope.items.splice(index, 1);
			// Đặt lại trạng thái của form (nếu có)
			$scope.reset();
			$('#delete').modal('hide')
			// Hiển thị thông báo thành công
			showSuccessToast("Account tên " + item.username + " đã được xóa")
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			showErrorToast("Xóa sản phảm tên " + item.username + " thất bại")
			console.log("Error", error);
		})
	}

	$scope.imageChanged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			showErrorToast("Lỗi tải hình ảnh")
			console.log("Error", error);
		})
	}

	$scope.getAll();



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
		}, 2000); // 3000 milliseconds tương đương 3 giây
	}
	$scope.refresh = function refreshNow() {
		location.reload();
	}

	// search
	$scope.searchName = '';
	$scope.searchUser = '';
	$scope.searchRole = null;
	$scope.searchStatus = 1;
	$scope.search = function() {

		$http.get('/rest/products/search', {
			params:
			{
				productname: $scope.searchName,
				categoryid: $scope.searchCate,
				productstatus: $scope.searchStatus
			}
		}).then(function(response) {
			$scope.items = response.data;
			$scope.items.forEach(item => {
				item.datecreate = new Date(item.datecreate)
			})
			console.log($scope.items);
		})
			.catch(function(error) {
				console.log('Lỗi khi gửi yêu cầu:', error);
			});
	};
	// ẩn hiện password
	$scope.passwordFieldType = 'password'; // Mặc định là password (ẩn mật khẩu)

	$scope.togglePasswordVisibility = function() {
		if ($scope.passwordFieldType === 'password') {
			$scope.passwordFieldType = 'text'; // Hiện mật khẩu
		} else {
			$scope.passwordFieldType = 'password'; // Ẩn mật khẩu
		}
	};

})
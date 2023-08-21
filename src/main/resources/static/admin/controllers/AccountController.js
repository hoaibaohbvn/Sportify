app.controller('AccountController', function($scope, $http, $location) {
	$scope.username = '';

	$scope.getUsername = function() {
		$http.get('http://localhost:8080/sportify/user/get-username', {
			withCredentials: true // Bao gồm thông tin xác thực (token xác thực) trong yêu cầu
		}).then(function(response) {
			if (response.data.username) {
				$scope.username = response.data.username;
				$http.get("/rest/authorized/getRole/" + $scope.username).then(resp => {
					$scope.listRoles = resp.data;
					
					if ($scope.listRoles[0][1] === 'dont') {
						$location.path("/admin/unauthorized");
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
		$scope.errors = [];
	}

	// hàm edit

	$scope.edit = function(item) {

		$scope.form = angular.copy(item);

		$scope.errors = [];
		const admin = document.getElementById('admin');
		const staff = document.getElementById('staff');
		const user = document.getElementById('user');
		const usernameQuyen = item.username
		$scope.form.username = usernameQuyen



		// lấy quyền username được chọn /rest/authorized/getRole
		$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
			$scope.listRole = resp.data;
			// lấy admin
			if ($scope.listRole[0][1] === 'R01') {
				admin.checked = true;
			} else {
				admin.checked = false;
			}
			// lấy staff
			if ($scope.listRole[1][1] === 'R02') {
				staff.checked = true;
			} else {
				staff.checked = false;
			}
			// lay user
			if ($scope.listRole[2][1] === 'R03') {
				user.checked = true;
			} else {
				user.checked = false;
			}
		});
		// thay đổi quyền
		$scope.doiQuyenAdmin = function() {
			$scope.form.roleid = 'R01'
			if (admin.checked == true) {
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
				$http.post(`/rest/authorized/create`, $scope.form).then(rp => {
					//$scope.items.push(rp.data);

				})
				showSuccessToast("Cấp quyền Admin thành công")
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
			}
			else {
				// Lấy authorizedid từ dữ liệu resp.data của yêu cầu GET
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
				const authorizedid = parseInt($scope.listRole[0][2]);
				$http.delete(`/rest/authorized/` + authorizedid).then(resp => {
					var index = $scope.items.findIndex(p => p.authorizedid === authorizedid)
					//$scope.items.splice(index, 1);
				});
				showSuccessToast("Thu quyền Admin thành công");
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
			}
		}
		$scope.doiQuyenStaff = function() {
			$scope.form.roleid = 'R02'
			if (staff.checked == true) {
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
				$http.post(`/rest/authorized/create`, $scope.form).then(rp => {
					//$scope.items.push(rp.data);

				})
				showSuccessToast("Cấp quyền Staff thành công")
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
			}
			else {
				// Lấy authorizedid từ dữ liệu resp.data của yêu cầu GET
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
				const authorizedid = parseInt($scope.listRole[1][2]);
				$http.delete(`/rest/authorized/` + authorizedid).then(resp => {
					var index = $scope.items.findIndex(p => p.authorizedid === authorizedid)
					//$scope.items.splice(index, 1);

				});
				showSuccessToast("Thu quyền Staff thành công");
			}
		}
		$scope.doiQuyenUser = function() {
			$scope.form.roleid = 'R03'
			if (user.checked == true) {
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
				$http.post(`/rest/authorized/create`, $scope.form).then(rp => {
					//$scope.items.push(rp.data);

				})
				showSuccessToast("Cấp quyền User thành công")
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
			}
			else {
				// Lấy authorizedid từ dữ liệu resp.data của yêu cầu GET
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
				const authorizedid = parseInt($scope.listRole[2][2]);
				$http.delete(`/rest/authorized/` + authorizedid).then(resp => {
					var index = $scope.items.findIndex(p => p.authorizedid === authorizedid)
					//$scope.items.splice(index, 1);

				});
				showSuccessToast("Thu quyền User thành công");
				$http.get("/rest/authorized/getRole/" + item.username).then(resp => {
					$scope.listRole = resp.data;
				})
			}
		}

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
	$scope.keyword = ''
	$scope.searchUser = '';
	$scope.searchRole = '';
	$scope.searchStatus = null;
	$scope.search = function() {

		$http.get('/rest/accounts/search', {
			params:
			{
				keyword: $scope.keyword,
				user: $scope.searchUser,
				role: $scope.searchRole,
				status: $scope.searchStatus
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
	// hàm refresh
	$scope.refresh = function refreshNow() {
		location.reload();
	}
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
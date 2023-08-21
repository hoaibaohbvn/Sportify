app.controller('EventController', function($scope, $http) {
	// Khởi tạo trình soạn thảo Summernote khi trang đã sẵn sàng
    
	// Dữ liệu cứng mẫu cho eventtype
	$scope.tableData = [
		{ id: 1, name: "Bóng Đá" },
		{ id: 2, name: "Bóng Rổ" },
		{ id: 3, name: "Cầu Lông" },
		{ id: 4, name: "Bảo Trì" },
		{ id: 5, name: "Tennis" },
		{ id: 6, name: "Khuyến Mãi" },
		{ id: 7, name: "Khác" },
	];
	// hàm đổ tất cả
	$scope.getAll = function() {
		// lấy danh sách category
		$http.get("/rest/events/getAll").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.datestart = new Date(item.datestart)
				item.dateend = new Date(item.dateend)
			})
		});
		$scope.reset();
	}

	// hàm rest form
	$scope.reset = function() {
		$scope.form = {
			datestart: new Date(),
			dateend: new Date(),
			productstatus: true,
			image: "loading.jpg",
			eventtype: $scope.tableData.name = "Bóng Đá"
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
		if (item.datestart >= item.dateend) {
			showErrorToast("Ngày bắt đầu phải trước ngày kết thúc sự kiện")
			return; // Dừng việc thêm mới nếu ngày không hợp lệ
		}
		$http.post(`/rest/events/create`, item).then(resp => {

			resp.data.dateStart = new Date(resp.data.dateStart)
			resp.data.dateEnd = new Date(resp.data.dateEnd)
			$scope.items.push(resp.data);
			showSuccessToast("Event mới tên " + item.nameevent + " đã được thêm vào cửa hàng")
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
		if (item.datestart >= item.dateend) {
			showErrorToast("Ngày bắt đầu phải trước ngày kết thúc sự kiện")
			return; // Dừng việc thêm mới nếu ngày không hợp lệ
		}
		$http.put(`/rest/events/update/${item.eventid}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.eventid == item.eventid);
			$scope.items[index] = item;
			showSuccessToast("Cập nhập sự kiện thành công")
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
		$http.delete(`/rest/events/delete/${item.eventid}`).then(resp => {
			var index = $scope.items.findIndex(p => p.eventid == item.eventid);
			$scope.items.splice(index, 1);
			// Đặt lại trạng thái của form (nếu có)
			$scope.reset();

			$('#delete').modal('hide')
			// Hiển thị thông báo thành công
			showSuccessToast("Sự kiện tên " + item.nameevent + " đã được xóa")
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			showErrorToast("Xóa sự kiện " + item.nameevent + " thất bại")
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
	// hàm refresh
	$scope.refresh = function refreshNow() {
		location.reload();
	}
	// search
	$scope.searchName = '';
	$scope.searchStyte = null;
	$scope.search = function() {

		$http.get('/rest/events/search', {
			params:
			{
				nameevent: $scope.searchName,
				eventtype: $scope.searchStyte
			}
		}).then(function(response) {
			$scope.items = response.data;
			$scope.items.forEach(item => {
				item.datestart = new Date(item.datestart)
				item.dateend = new Date(item.dateend)
			})
			console.log($scope.items);
		})
			.catch(function(error) {
				console.log('Lỗi khi gửi yêu cầu:', error);
			});
	};
})
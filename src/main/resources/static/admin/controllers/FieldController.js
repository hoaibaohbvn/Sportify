app.controller('FieldController', function($scope, $http) {
	// hàm đổ tất cả
	$scope.getAll = function() {
		// lấy danh sách category
		$http.get("/rest/sportTypes/getAll").then(resp => {
			$scope.sporttype = resp.data;
		})
		// lấy danh sách product
		$http.get("/rest/fields/getAllActive").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate)
			})
		});
		$scope.reset();
	}
	// hàm rest form
	$scope.reset = function() {
		$scope.form = {
			sporttypeid: "B01",
			status: true,
			image: "loading.jpg",
			price: 0
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
		$http.post(`/rest/fields/create`, item).then(resp => {

			$scope.items.push(resp.data);
			showSuccessToast("Sân mới tên " + item.namefield + " đã được thêm vào cửa hàng")
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
		$http.put(`/rest/fields/update/${item.fieldid}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.fieldid == item.fieldid);
			$scope.items[index] = item;
			showSuccessToast("Cập nhập sân thành công")
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
		$http.delete(`/rest/fields/delete/${item.fieldid}`).then(resp => {
			var index = $scope.items.findIndex(p => p.fieldid == item.fieldid);
			$scope.items.splice(index, 1);
			// Đặt lại trạng thái của form (nếu có)
			$scope.reset();
			$('#delete').modal('hide')
			// Hiển thị thông báo thành công
			showSuccessToast("Sân tên " + item.namefield + " đã được xóa")
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			showErrorToast("Xóa sân tên " + item.namefield + " thất bại")
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
			showErrorToast("Lổi tải hình ảnh")
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
   	 $scope.searchSport = null;
   	 $scope.searchStatus = 1;
   	 $scope.search = function () {
			
      $http.get('/rest/fields/search', { params: 
      		{ 	
				namefield: $scope.searchName, 
      			sporttypeid: $scope.searchSport,
      			status: $scope.searchStatus
      		} 
      		}).then(function (response) {
          $scope.items = response.data;
          
			 console.log($scope.items);
        })
        .catch(function (error) {
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
app.controller('BookingController', function($scope, $http) {
	// ràng quyen truy cập
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
		$http.get("/rest/accounts/getAll").then(resp => {
			$scope.accounts = resp.data;
		})
		$http.get("/rest/fields/getAll").then(resp => {
			$scope.fields = resp.data;
		})
		$http.get("/rest/shifts/getAll").then(resp => {
			$scope.shifts = resp.data;
		})
		// lấy danh sách product
		$http.get("/rest/bookings/getAll").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.bookingdate = new Date(item.bookingdate)
			})
		});
		
		
	}
	// hàm refresh
	$scope.refresh = function refreshNow() {
		location.reload();
	}
	// hàm rest form
	$scope.reset = function() {
		$scope.form = {
			sporttypeid: "B01",
			status: true,
			image: "loading.jpg"
		}
		$scope.errors = [];
	}
	// hàm edit
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$scope.errors = [];
		// lấy danh sách bookingdetail theo idbooking
		$http.get("/rest/bookingdetails/" + item.bookingid).then(resp => {
			$scope.bookingdetail = resp.data;
			$scope.bookingdetail.forEach(item => {
				item.bookingdate = new Date(item.bookingdate)
			})
			
		});
	}

	// hàm cập nhập
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/bookings/update/${item.bookingid}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.bookingid == item.bookingid);
			$scope.items[index] = item;
			showSuccessToast("Đã cập nhập thành công trạng thái của phiếu đặt sân")
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
	// search
	 $scope.keyword = '';
   	 $scope.datebook = new Date();
   	 $scope.status = 'Đã Cọc';
   	 $scope.search = function () {
			   
				   var momentDate = moment($scope.datebook); // xài import thư viện Moment.js
			   var dateString = momentDate.format("YYYY-MM-DD");
			   
      $http.get('/rest/bookings/search', { params: 
      		{ 	
				keyword: $scope.keyword, 
      			datebook: dateString,
      			status: $scope.status
      		} 
      		}).then(function (response) {
          $scope.items = response.data;
          
			console.log(dateString)
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
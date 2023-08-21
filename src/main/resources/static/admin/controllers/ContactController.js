app.controller('ContactController', function($scope, $http) {
	// hàm đổ tất cả
	$scope.getAll = function() {
		// lấy danh sách category
		$http.get("/rest/contacts/getAll").then(resp => {
			$scope.items = resp.data;
		});
		$scope.reset();
	}
	// hàm rest form
	$scope.reset = function() {
		$scope.form = {
			
		}
		$scope.errors = [];
	}
	// hàm edit
	$scope.edit = function(item) {
		$scope.errors = [];
		$scope.form = angular.copy(item);
		
	}
	// hàm refresh
	$scope.refresh = function refreshNow() {
		location.reload();
	}
	// ham delete
	$scope.delete = function(item) {
		$http.delete(`/rest/contacts/delete/${item.contactid}`).then(resp => {
			var index = $scope.items.findIndex(p => p.contactid == item.catecontactidgoryid);
			$scope.items.splice(index, 1);
			// Đặt lại trạng thái của form (nếu có)
			$scope.reset();
			$('#delete').modal('hide')
			// Hiển thị thông báo thành công
			showSuccessToast("Đã xóa thành công loại hàng tên " + item.contactid)
			refreshPageAfterThreeSeconds();
		}).catch(error => {
			showErrorToast("Xóa loại hàng thất bại");
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
	// search
	
	$scope.searchDate = new Date();
	$scope.searchCate = '';
	$scope.search = function() {
		var momentDate = moment($scope.searchDate); // Giả sử bạn đã import thư viện Moment.js
		var dateString = momentDate.format("YYYY-MM-DD");
		$http.get('/rest/contacts/search', {
			params:
			{
				
				datecontact: dateString,
				category: $scope.searchCate
			}
		}).then(function(response) {
			$scope.items = response.data;
			$scope.items.forEach(item => {
				item.datecontact = new Date(item.datecontact)
				
			})
			
		})
			.catch(function(error) {
				console.log('Lỗi khi gửi yêu cầu:', error);
			});
	};
})
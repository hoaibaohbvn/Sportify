app.controller('ReportOrderController', function($scope, $http, $location) {
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

	
	// xử lý nút xem báo cáo
	// lấy năm của phiếu dặt san
	$http.get("/rest/reportOrder/getYearOrder").then(resp => {
		$scope.getYearOrder = resp.data;
	})

	// xử lý nút xem doanh thu

	$scope.monthNam = 0;
	$scope.monthThang = 0;
	$scope.year_nam = 0;
	$scope.loaiThongKe = 1
	$scope.hinhThuc = 'ko'
	$scope.titleBD = '';
	$scope.bangThongKe = '';
	$scope.xemBC = function() {
		if ($scope.hinhThuc === 'ko') {
			showErrorToast("Vui lòng chọn hình thức thống kê")
			return;
		}
		if ($scope.loaiThongKe === 1) {
			if ($scope.hinhThuc === 'year') {
				if ($scope.year_nam === 0) {
					showErrorToast("Vui lòng chọn năm")
					return;
				}
				$scope.titleBD = 'Doanh thu đặt hàng trong năm ' + $scope.year_nam;
				$http.get("/rest/reportOrder/rpDoanhThuOrderTrongNam", {
					params:
					{
						year: $scope.year_nam
					}
				}).then(resp => {

					// Lưu trữ dữ liệu đã lấy từ API vào biến $scope.charr
					$scope.rpDoanhThuOrderTrongNam = resp.data;

					// Tạo dữ liệu cho biểu đồ
					var barChartData = [];

					for (var i = 0; i < $scope.rpDoanhThuOrderTrongNam.length; i++) {
						var month = $scope.rpDoanhThuOrderTrongNam[i][0];
						var a = $scope.rpDoanhThuOrderTrongNam[i][1]
						barChartData.push({ y: month, a: a });
					}
					document.getElementById('bieuDo').innerHTML = '';
					$scope.bangThongKe = 'doanhThuNam';
					// Bar Chart
					Morris.Bar({
						element: 'bieuDo',
						data: barChartData,
						xkey: 'y',
						ykeys: ['a'],
						labels: ['Tổng doanh thu phiếu đặt hàng'],
						lineColors: ['#f43b48'],
						lineWidth: '3px',
						barColors: ['#f43b48'],
						resize: true,
						redraw: true,
						xLabels: 'y', // giữ nguyên thư tự cột y
						parseTime: false, // ngăn chặn xử lý ngày tháng
					});
				});
			} else {
				if ($scope.monthNam === 0) {
					showErrorToast("Vui lòng chọn năm")
					return;
				}
				if ($scope.monthThang === 0) {
					showErrorToast("Vui lòng chọn tháng")
					return;
				}
				$scope.titleBD = 'Doanh thu đặt hàng trong tháng ' + $scope.monthThang;
				$http.get("/rest/reportOrder/rpDoanhThuOrderTrongThang", {
					params:
					{
						year: $scope.monthNam,
						month: $scope.monthThang
					}
				}).then(resp => {
					// Lưu trữ dữ liệu đã lấy từ API vào biến $scope.charr
					$scope.rpDoanhThuOrderTrongThang = resp.data;
					// Tạo dữ liệu cho biểu đồ
					var lineChartData = [];
					for (var i = 0; i < $scope.rpDoanhThuOrderTrongThang.length; i++) {  // Sửa ở đây
						var ngay = $scope.rpDoanhThuOrderTrongThang[i][0];
						var a = $scope.rpDoanhThuOrderTrongThang[i][1];
						lineChartData.push({ y: ngay, a: a });
					}
					document.getElementById('bieuDo').innerHTML = '';
					$scope.bangThongKe = 'doanhThuThang';
					Morris.Line({
						element: 'bieuDo',
						data: lineChartData,
						xkey: 'y',
						ykeys: ['a'],
						labels: ['Tổng doanh thu phiếu đặt hàng'],
						lineColors: ['#f43b48'],
						lineWidth: '3px',
						xLabels: 'y', // giữ nguyên thư tự cột y
						parseTime: false, // ngăn chặn xử lý ngày tháng
						resize: true,
						redraw: true
					});
				});
			}
		} else {
			
			if ($scope.hinhThuc === 'year') {
				if ($scope.year_nam === 0) {
					showErrorToast("Vui lòng chọn năm")
					return;
				}
				$scope.titleBD = 'Tổng số lượng phiếu đặt hàng trong năm ' + $scope.year_nam;
				$http.get("/rest/reportOrder/rpSoLuongOrderTrongNam", {
					params:
					{
						year: $scope.year_nam
					}
				}).then(resp => {

					// Lưu trữ dữ liệu đã lấy từ API vào biến $scope.charr
					$scope.rpSoLuongOrderTrongNam = resp.data;

					// Tạo dữ liệu cho biểu đồ
					var barChartData = [];

					for (var i = 0; i < $scope.rpSoLuongOrderTrongNam.length; i++) {
						var month = $scope.rpSoLuongOrderTrongNam[i][0];
						var tong = $scope.rpSoLuongOrderTrongNam[i][1]
						var cancel = $scope.rpSoLuongOrderTrongNam[i][2]
						var done = $scope.rpSoLuongOrderTrongNam[i][3]
						barChartData.push({ y: month, a: tong, b: cancel, c: done });
					}
					document.getElementById('bieuDo').innerHTML = '';
					$scope.bangThongKe = 'soLuongNam';
					// Bar Chart
					Morris.Bar({
						element: 'bieuDo',
						data: barChartData,
						xkey: 'y',
						ykeys: ['a', 'b', 'c'],
						labels: ['Tổng số phiếu đặt sân', 'Tổng số phiếu đặt hàng chưa thanh toán', 'Tổng số phiếu đặt hàng hoàn thành'],
						lineColors: ['green', 'red', 'blue'],
						lineWidth: '1px',
						barColors: ['green', 'red', 'blue'],
						resize: true,
						redraw: true,
						xLabels: 'y', // giữ nguyên thư tự cột y
						parseTime: false, // ngăn chặn xử lý ngày tháng
					});
				})
			} else {
				if ($scope.monthNam === 0) {
					showErrorToast("Vui lòng chọn năm")
					return;
				}
				if ($scope.monthThang === 0) {
					showErrorToast("Vui lòng chọn tháng")
					return;
				}
				$scope.titleBD = 'Tổng số lượng phiếu đặt hàng trong tháng ' + $scope.monthThang;
				$http.get("/rest/reportOrder/rpSoLuongOrderTrongThang", {
					params:
					{
						year: $scope.monthNam,
						month: $scope.monthThang
					}
				}).then(resp => {
					// Lưu trữ dữ liệu đã lấy từ API vào biến $scope.charr
					$scope.rpSoLuongOrderTrongThang = resp.data;
					// Tạo dữ liệu cho biểu đồ
					var lineChartData = [];
					for (var i = 0; i < $scope.rpSoLuongOrderTrongThang.length; i++) {
						var ngay = $scope.rpSoLuongOrderTrongThang[i][0];
						var tong = $scope.rpSoLuongOrderTrongThang[i][1]
						var cancel = $scope.rpSoLuongOrderTrongThang[i][2]
						var done = $scope.rpSoLuongOrderTrongThang[i][3]
						lineChartData.push({ y: ngay, a: tong, b: cancel, c: done });
					}
					document.getElementById('bieuDo').innerHTML = '';
					$scope.bangThongKe = 'soLuongThang';
					Morris.Line({
						element: 'bieuDo',
						data: lineChartData,
						xkey: 'y',
						ykeys: ['a', 'b', 'c'],
						labels: ['Tổng số phiếu đặt sân', 'Tổng số phiếu đặt hàng chưa thanh toán', 'Tổng số phiếu đặt hàng hoàn thành'],
						lineColors: ['green', 'red', 'blue'],
						lineWidth: '3px',
						xLabels: 'y', // giữ nguyên thư tự cột y
						parseTime: false, // ngăn chặn xử lý ngày tháng
						resize: true,
						redraw: true
					});
				});
			}
		}
	};

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
	// định dạng tiền tệ VND
	$scope.formatCurrency = function(value) {
		// Sử dụng filter number để định dạng thành 100,000
		var formattedValue = new Intl.NumberFormat('vi-VN').format(value);
		return formattedValue + ' VND';
	};
	//Excel doanh thu booking trong nam
	$scope.downloadExcelDTOrderNam = function() {
		$http({
			url: "http://localhost:8080/rest/reportOrder/downloadExcelDTOrderNam",  // Địa chỉ API endpoint của Spring Boot backend
			method: "GET",
			responseType: "arraybuffer",
			params:
			{
				year: $scope.year_nam
			}

		}).then(
			function(response) {
				var blob = new Blob([response.data], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				});
				var url = window.URL.createObjectURL(blob);
				var a = document.createElement("a");
				a.href = url;
				// Lấy ngày hiện tại và định dạng
				var currentDate = new Date();
				var day = currentDate.getDate(); // Lấy ngày hiện tại
				var month = currentDate.getMonth() + 1; // Lấy tháng hiện tại (lưu ý: tháng trong JavaScript bắt đầu từ 0, nên cần cộng thêm 1)
				var year = currentDate.getFullYear(); // Lấy năm hiện tại
				// Định dạng ngày, tháng, năm thành chuỗi "dd-mm-yyyy"
				var formattedDate = day + '-' + month + '-' + year;
				a.download = "ReportDoanhThuOrderTheoNam_" + $scope.year_nam + "_" + formattedDate + ".xlsx";
				a.click();
				window.URL.revokeObjectURL(url);
			},
			function(error) {
				console.error("Lỗi khi tải xuống tệp Excel:", error);
			}
		);
	};
	//Excel doanh thu booking trong thang
	$scope.downloadExcelDTOrderThang = function() {
		$http({
			url: "http://localhost:8080/rest/reportOrder/downloadExcelDTOrderThang",  // Địa chỉ API endpoint của Spring Boot backend
			method: "GET",
			responseType: "arraybuffer",
			params:
			{
				year: $scope.monthNam,
				month: $scope.monthThang
			}

		}).then(
			function(response) {
				var blob = new Blob([response.data], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				});
				var url = window.URL.createObjectURL(blob);
				var a = document.createElement("a");
				a.href = url;
				// Lấy ngày hiện tại và định dạng
				var currentDate = new Date();
				var day = currentDate.getDate(); // Lấy ngày hiện tại
				var month = currentDate.getMonth() + 1; // Lấy tháng hiện tại (lưu ý: tháng trong JavaScript bắt đầu từ 0, nên cần cộng thêm 1)
				var year = currentDate.getFullYear(); // Lấy năm hiện tại
				// Định dạng ngày, tháng, năm thành chuỗi "dd-mm-yyyy"
				var formattedDate = day + '-' + month + '-' + year;
				a.download = "ReportDoanhThuOrderTheoThang_" + $scope.monthThang + "_" + $scope.monthNam + "_" + formattedDate + ".xlsx";
				a.click();
				window.URL.revokeObjectURL(url);
			},
			function(error) {
				console.error("Lỗi khi tải xuống tệp Excel:", error);
			}
		);
	};
	// Excel so luong booking trong nam
	$scope.downloadExcelSLOrderNam = function() {
		$http({
			url: "http://localhost:8080/rest/reportOrder/downloadExcelSLOrderNam",  // Địa chỉ API endpoint của Spring Boot backend
			method: "GET",
			responseType: "arraybuffer",
			params:
			{
				year: $scope.year_nam
			}

		}).then(
			function(response) {
				var blob = new Blob([response.data], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				});
				var url = window.URL.createObjectURL(blob);
				var a = document.createElement("a");
				a.href = url;
				// Lấy ngày hiện tại và định dạng
				var currentDate = new Date();
				var day = currentDate.getDate(); // Lấy ngày hiện tại
				var month = currentDate.getMonth() + 1; // Lấy tháng hiện tại (lưu ý: tháng trong JavaScript bắt đầu từ 0, nên cần cộng thêm 1)
				var year = currentDate.getFullYear(); // Lấy năm hiện tại
				// Định dạng ngày, tháng, năm thành chuỗi "dd-mm-yyyy"
				var formattedDate = day + '-' + month + '-' + year;
				a.download = "ReportSoLuongOrderTheoNam_" + $scope.year_nam + "_" + formattedDate + ".xlsx";
				a.click();
				window.URL.revokeObjectURL(url);
			},
			function(error) {
				console.error("Lỗi khi tải xuống tệp Excel:", error);
			}
		);
	};
	// Excel so luong booking trong thang
	$scope.downloadExcelSLOrderThang = function() {
		$http({
			url: "http://localhost:8080/rest/reportOrder/downloadExcelSLOrderThang",  // Địa chỉ API endpoint của Spring Boot backend
			method: "GET",
			responseType: "arraybuffer",
			params:
			{
				year: $scope.monthNam,
				month: $scope.monthThang
			}

		}).then(
			function(response) {
				var blob = new Blob([response.data], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				});
				var url = window.URL.createObjectURL(blob);
				var a = document.createElement("a");
				a.href = url;
				// Lấy ngày hiện tại và định dạng
				var currentDate = new Date();
				var day = currentDate.getDate(); // Lấy ngày hiện tại
				var month = currentDate.getMonth() + 1; // Lấy tháng hiện tại (lưu ý: tháng trong JavaScript bắt đầu từ 0, nên cần cộng thêm 1)
				var year = currentDate.getFullYear(); // Lấy năm hiện tại
				// Định dạng ngày, tháng, năm thành chuỗi "dd-mm-yyyy"
				var formattedDate = day + '-' + month + '-' + year;
				a.download = "ReportSoLuongOrderTheoThang_" + $scope.monthThang + "_" + $scope.monthNam + "_" + formattedDate + ".xlsx";
				a.click();
				window.URL.revokeObjectURL(url);
			},
			function(error) {
				console.error("Lỗi khi tải xuống tệp Excel:", error);
			}
		);
	};
});
app.controller('DashboardController', function($scope, $http) {
	// dash_widget
	$scope.dash_widget = function() {
		// tổng product
		$http.get("/rest/dashboard/totalProduct").then(resp => {
			$scope.totalProduct = resp.data;
		});
		// tổng field
		$http.get("/rest/dashboard/totalField").then(resp => {
			$scope.totalField = resp.data;
		});
		// tổng user
		$http.get("/rest/dashboard/totalUser").then(resp => {
			$scope.totalUser = resp.data;
		});
		// tổng phiểu trong ngày
		$http.get("/rest/dashboard/totalOrderBooking").then(resp => {
			$scope.totalOrderBooking = resp.data;
		});
	}

	// đồ thị
	$scope.barcharts = function() {
		// Lấy dữ liệu từ API
		$http.get("/rest/dashboard/barcharts_b").then(rp => {
			$scope.barcharts_b = rp.data;
		})
		$http.get("/rest/dashboard/barcharts_a").then(resp => {

			// Lưu trữ dữ liệu đã lấy từ API vào biến $scope.charr
			$scope.barcharts_a = resp.data;

			// Định nghĩa biến currentYear (nếu cần)
			var currentYear = new Date().getFullYear() - 5;

			// Tạo dữ liệu cho biểu đồ
			var barChartData = [];

			for (var i = 0; i <= 5; i++) {
				var year = currentYear + i;
				var a = $scope.barcharts_a[0][i] + "VND"
				var b = $scope.barcharts_b[0][i];
				barChartData.push({ y: year.toString(), a: a, b: b });
			}

			// Bar Chart
			Morris.Bar({
				element: 'bar-charts',
				data: barChartData,
				xkey: 'y',
				ykeys: ['a', 'b'],
				labels: ['Tổng doanh thu đặt sân', 'Tổng danh thu bán hàng'],
				lineColors: ['#f43b48', '#453a94'],
				lineWidth: '3px',
				barColors: ['#f43b48', '#453a94'],
				resize: true,
				redraw: true
			});
		});
	}
	$scope.line_charts = function() {
		// Lấy dữ liệu từ API
		$http.get("/rest/dashboard/linecharts_b").then(rp => {
			$scope.linecharts_b = rp.data;
		})
		$http.get("/rest/dashboard/linecharts_a").then(resp => {
			// Lưu trữ dữ liệu đã lấy từ API vào biến $scope.charr
			$scope.linecharts_a = resp.data;

			// Định nghĩa biến currentYear (nếu cần)
			var currentYear = new Date().getFullYear() - 5;

			// Tạo dữ liệu cho biểu đồ
			var lineChartData = [];

			for (var i = 0; i <= 5; i++) {
				var year = currentYear + i;
				var a = $scope.linecharts_a[0][i]
				var b = $scope.linecharts_b[0][i]
				lineChartData.push({ y: year.toString(), a: a, b: b });
			}
			Morris.Line({
				element: 'line-charts',
				data: lineChartData,
				xkey: 'y',
				ykeys: ['a', 'b'],
				labels: ['Tổng số phiếu đặt sân', 'Tổng số phiếu bán hàng'],
				lineColors: ['#f43b48', '#453a94'],
				lineWidth: '3px',
				resize: true,
				redraw: true
			});
		});
	}
	// Tính tổng số phiếu trong tháng hiện tại và tháng trước cho bảng bookings và orders
	$http.get("/rest/dashboard/thisthatMonth").then(rp => {
		$scope.thisthatMonth = rp.data;
		var thisMonth = $scope.thisthatMonth[0][0];
		var prevMonth = $scope.thisthatMonth[0][1];
		var percentGrowth = ((thisMonth - prevMonth) / prevMonth * 100).toFixed(2);
		if (percentGrowth > 0) {
			$scope.setColor1 = 'green'
			$scope.percentGrowthBooking_Order = '+' + percentGrowth + '%'
		} else {
			$scope.setColor1 = 'red'
			$scope.percentGrowthBooking_Order = percentGrowth + '%'
		}
	})
	// Tính tổng số doanh thu dặt sân tháng hiện tại và tháng trước cho bảng bookings
	$http.get("/rest/dashboard/sumThisThatMonth").then(rp => {
		$scope.sumThisThatMonth = rp.data;
		var revenue_this_month = $scope.sumThisThatMonth[0][0];
		var revenue_last_month = $scope.sumThisThatMonth[0][1];
		var percentGrowthBooking = ((revenue_this_month - revenue_last_month) / revenue_last_month * 100).toFixed(2);
		if (percentGrowthBooking > 0) {
			$scope.setColor2 = 'green'
			$scope.percentGrowthBooking = '+' + percentGrowthBooking + '%';
		} else {
			$scope.setColor2 = 'red'
			$scope.percentGrowthBooking = percentGrowthBooking + '%';
		}

	})
	// Tính tổng số doanh thu bán hàng tháng hiện tại và tháng trước cho bảng order
	$http.get("/rest/dashboard/sumRevenueOrder2Month").then(rp => {
		$scope.sumRevenueOrder2Month = rp.data;
		var revenue_this_month_order = $scope.sumRevenueOrder2Month[0][0];
		var revenue_last_month_order = $scope.sumRevenueOrder2Month[0][1];
		var percentGrowthOrder = ((revenue_this_month_order - revenue_last_month_order) / revenue_last_month_order * 100).toFixed(2);
		if (percentGrowthOrder > 0) {
			$scope.setColor3 = 'green'
			$scope.percentGrowthOrder = '+' + percentGrowthOrder +'%'
		} else {
			$scope.setColor3 = 'red'
			$scope.percentGrowthOrder = percentGrowthOrder +'%'
		}
	})
	// // tính tổng hoàn tiền tháng này và tháng trước
	$http.get("/rest/dashboard/total_cancelled_amount_this_that_month").then(rp => {
		$scope.total_cancelled_amount_this_that_month = rp.data;
		var total_cancelled_amount_this_month = $scope.total_cancelled_amount_this_that_month[0][0];
		var total_cancelled_amount_last_month = $scope.total_cancelled_amount_this_that_month[0][1];
		var percentGrowthCancel = total_cancelled_amount_this_month - total_cancelled_amount_last_month
		if (percentGrowthCancel > 0) {
			$scope.setColor4 = 'red'
			$scope.percentGrowthCancel = 'Lớn Hơn ' + percentGrowthCancel 
		} else {
			$scope.setColor4 = 'green'
			$scope.percentGrowthCancel = 'Nhỏ hơn ' + percentGrowthCancel 
		}
	})
	// Số liệu thống kế booking
	$http.get("/rest/dashboard/statisticsBooking").then(rp => {
		$scope.statisticsBooking = rp.data;
		var totalAllBooking = $scope.statisticsBooking[0][1]
		var revenueAllBooking = $scope.statisticsBooking[0][2];
		var revenueAllBookingCancel = $scope.statisticsBooking[3][2];
		$scope.finalRevenueBooking = revenueAllBooking - revenueAllBookingCancel * 2
		var countBookingCoc = $scope.statisticsBooking[1][1];
		var countBookingDone = $scope.statisticsBooking[2][1];
		var countBookingCancel = $scope.statisticsBooking[3][1];
		$scope.percentCoc = ((countBookingCoc / totalAllBooking) * 100).toFixed(1) + '%';
		$scope.percentDone = ((countBookingDone / totalAllBooking) * 100).toFixed(1) + '%';
		$scope.percentCancel = ((countBookingCancel / totalAllBooking) * 100).toFixed(1) + '%';
	})
	// định dạng tiền tệ VND
	$scope.formatCurrency = function(value) {
		// Sử dụng filter number để định dạng thành 100,000
		var formattedValue = new Intl.NumberFormat('vi-VN').format(value);
		return formattedValue + ' VND';
	};
	// gọi hàm
	$scope.dash_widget();
	$scope.barcharts()
	$scope.line_charts()
});
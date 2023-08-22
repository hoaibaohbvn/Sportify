app.controller('DashboardController', function($scope, $http) {
	// ràng quyền
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
		var revenue_this_month = $scope.sumThisThatMonth[0][1] + ($scope.sumThisThatMonth[2][1] * 0.3) - ($scope.sumThisThatMonth[1][1] * 2);
		var revenue_last_month = $scope.sumThisThatMonth[0][2] + ($scope.sumThisThatMonth[2][2] * 0.3) - ($scope.sumThisThatMonth[1][2] * 2)
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
	$http.get("/rest/dashboard/doanhThuOrder2Month").then(rp => {
		$scope.doanhThuOrder2Month = rp.data;
		$scope.tangtruong = ((($scope.doanhThuOrder2Month[0][0] - $scope.doanhThuOrder2Month[0][1]) / $scope.doanhThuOrder2Month[0][1]) * 100).toFixed(1);
		if ($scope.doanhThuOrder2Month[0][1] <= 0) {
			$scope.tangtruong = 'Vượt trội'
			$scope.colorDTOrder = 'blue';
		} else if ($scope.tangtruong > 0) {
			$scope.tangtruong = '+' + ((($scope.doanhThuOrder2Month[0][0] - $scope.doanhThuOrder2Month[0][1]) / $scope.doanhThuOrder2Month[0][1]) * 100).toFixed(1) + '%'
			$scope.colorDTOrder = 'green';
		} else {
			$scope.tangtruong = ((($scope.doanhThuOrder2Month[0][0] - $scope.doanhThuOrder2Month[0][1]) / $scope.doanhThuOrder2Month[0][1]) * 100).toFixed(1) + '%'
			$scope.colorDTOrder = 'red';
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
	// Đếm booking trong ngày
	$http.get("/rest/dashboard/countBookingInDate").then(rp => {
		$scope.countBookingInDate = rp.data;
	})
	// đếm sân đang hoạt động
	$http.get("/rest/dashboard/countFieldActiving").then(rp => {
		$scope.countFieldActiving = rp.data;
	})
	// đếm pheiu61 dặt hàng trong ngày
	$http.get("/rest/dashboard/countOrderInDate").then(rp => {
		$scope.countOrderInDate = rp.data;
	})
	// đếm pheiu61 dặt hàng trong ngày
	$http.get("/rest/dashboard/countProductActive").then(rp => {
		$scope.countProductActive = rp.data;
	})
	// thong ke booking trong ngày
	$http.get("/rest/dashboard/thongkebookingtrongngay").then(rp => {
		$scope.thongkebookingtrongngay = rp.data;
		var totalAllBooking = $scope.thongkebookingtrongngay[0][1]
		var countBookingCoc = $scope.thongkebookingtrongngay[2][1];
		var countBookingDone = $scope.thongkebookingtrongngay[1][1];
		var countBookingCancel = $scope.thongkebookingtrongngay[3][1];
		$scope.percentCoc_ngay = ((countBookingCoc / totalAllBooking) * 100).toFixed(1) + '%';
		if (countBookingCoc === 0) {
			$scope.percentCoc_ngay = '0%';
		}
		$scope.percentDone_ngay = ((countBookingDone / totalAllBooking) * 100).toFixed(1) + '%';
		if (countBookingDone === 0) {
			$scope.percentDone_ngay = '0%';
		}
		$scope.percentCancel_ngay = ((countBookingCancel / totalAllBooking) * 100).toFixed(1) + '%';
		if (countBookingCancel === 0) {
			$scope.percentCancel_ngay = '0%';
		}
	})
	// danh sach contact ngay
	$http.get("/rest/dashboard/danhsach3contact").then(rp => {
		$scope.list3Contact = rp.data;
	})
	// dem lien he trong ngày
	$http.get("/rest/dashboard/demLienHeTrongNgay").then(rp => {
		$scope.countLienHe = rp.data;
	})
	// tong số phiếu dat san trong thang này va thang trước

	$http.get("/rest/dashboard/tongSoPhieuDatSan2Thang").then(rp => {
		$scope.tongSoPhieuDatSan2Thang = rp.data;

		$scope.percentPhieuDat = ((($scope.tongSoPhieuDatSan2Thang[0][1] - $scope.tongSoPhieuDatSan2Thang[1][1]) / $scope.tongSoPhieuDatSan2Thang[1][1]) * 100)
		if ($scope.tongSoPhieuDatSan2Thang[1][1] <= 0) {
			$scope.percentPhieuDat = 'Vượt trội'
			$scope.colorDatSan = 'blue';
		} else if ($scope.percentPhieuDat > 0) {
			$scope.percentPhieuDat = '+' + ($scope.percentPhieuDat).toFixed(1) + '%'
			$scope.colorDatSan = 'green';
		} else {
			$scope.percentPhieuDat = ($scope.percentPhieuDat).toFixed(1) + '%'
			$scope.colorDatSan = 'red';
		}
	})
	// tong phieu ban hang trong thang nay va thang truoc
	$http.get("/rest/dashboard/tongSoPhieuOrder2Thang").then(rp => {
		$scope.tongSoPhieuOrder2Thang = rp.data;

		$scope.percentOrder = ((($scope.tongSoPhieuOrder2Thang[0][1] - $scope.tongSoPhieuOrder2Thang[1][1]) / $scope.tongSoPhieuOrder2Thang[1][1]) * 100)
		if ($scope.tongSoPhieuOrder2Thang[1][1] <= 0) {
			$scope.percentOrder = 'Vượt trội'
			$scope.colorOrder = 'blue';
		} else if ($scope.percentOrder > 0) {
			$scope.percentOrder = '+' + ($scope.percentOrder).toFixed(1) + '%'
			$scope.colorOrder = 'green';
		} else {
			$scope.percentOrder = ($scope.percentOrder).toFixed(1) + '%'
			$scope.colorOrder = 'red';
		}
	})
	// tổng da=oanh thu booking tháng này so với tháng trước
	$http.get("/rest/dashboard/tongDoanhThuBooking2Month").then(rp => {
		$scope.tongDoanhThuBooking2Month = rp.data;

		$scope.percentDTBooking = ((($scope.tongDoanhThuBooking2Month[0][0] - $scope.tongDoanhThuBooking2Month[0][1]) / $scope.tongDoanhThuBooking2Month[0][1]) * 100)
		if ($scope.tongDoanhThuBooking2Month[0][1] <= 0) {
			$scope.percentDTBooking = 'Vượt trội'
			$scope.colorDTBooking = 'blue';
		} else if ($scope.percentDTBooking > 0) {
			$scope.percentDTBooking = '+'+($scope.percentDTBooking).toFixed(1) + '%'
			$scope.colorDTBooking = 'green';
		} else {
			$scope.percentDTBooking = ($scope.percentDTBooking).toFixed(1) + '%'
			$scope.colorDTBooking = 'red';
		}
	})

	// tổng daoanh thu order tháng này so với tháng trước
	$http.get("/rest/dashboard/sumRevenueOrder2Month").then(rp => {
		$scope.sumRevenueOrder2Month = rp.data;

		$scope.percentDTOrder = ((($scope.sumRevenueOrder2Month[1][1] - $scope.sumRevenueOrder2Month[1][2]) / $scope.sumRevenueOrder2Month[1][2]) * 100)
		if ($scope.sumRevenueOrder2Month[1][2] <= 0) {
			$scope.percentDTOrder = 'Vượt trội'
			$scope.colorDTOrder = 'blue';
		} else if ($scope.percentDTOrder > 0) {
			$scope.percentDTOrder = '+'+($scope.percentDTOrder).toFixed(1) + '%'
			$scope.colorDTOrder = 'green';
		} else {
			$scope.percentDTOrder = ($scope.percentDTOrder).toFixed(1) + '%'
			$scope.colorDTOrder = 'red';
		}
	})
	// top 3 san được dặt nhiều nhat\
	$http.get("/rest/dashboard/top3SanDatNhieu").then(rp => {
		$scope.top3SanDatNhieu = rp.data;
	})
	// top 3 san pham ban nhiều nhất
	$http.get("/rest/dashboard/top3SanPhamBanNhieu").then(rp => {
		$scope.top3SanPhamBanNhieu = rp.data;
	})
	// top 5 user dat san nhieu nhat top5UserDatSan
	$http.get("/rest/dashboard/top5UserDatSan").then(rp => {
		$scope.top5UserDatSan = rp.data;
	})
	// top 5 user order nhieu nhat
	$http.get("/rest/dashboard/top5UserOrder").then(rp => {
		$scope.top5UserOrder = rp.data;
	})
	// thong ke order trong ngay thongKeOrderInDay
	$http.get("/rest/dashboard/thongKeOrderInDay").then(rp => {
		$scope.thongKeOrderInDay = rp.data;

		var totalAllOrder = $scope.thongKeOrderInDay[0][1]
		var countOrderDone = $scope.thongKeOrderInDay[2][1];
		var countOrderFailure = $scope.thongKeOrderInDay[1][1];
		$scope.percentOrderDone_ngay = ((countOrderDone / totalAllOrder) * 100).toFixed(1) + '%';
		if (countOrderDone === 0) {
			$scope.percentOrderDone_ngay = '0%';
		}
		$scope.percentOrderFailure_ngay = ((countOrderFailure / totalAllOrder) * 100).toFixed(1) + '%';
		if (countOrderFailure === 0) {
			$scope.percentOrderFailure_ngay = '0%';
		}

	})
	// định dạng tiền tệ VND
	$scope.formatCurrency = function(value) {
		// Sử dụng filter number để định dạng thành 100,000
		var formattedValue = new Intl.NumberFormat('vi-VN').format(value);
		return formattedValue + ' VND';
	};
	



	
	// gọi hàm
	$scope.dash_widget();
	
});
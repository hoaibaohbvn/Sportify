// lấy quyền
app.run(function($http, $rootScope) {
	$http.get(`/rest/security/authentication`).then(resp => {
		if (resp.data) {
			$auth = $rootScope.$auth = resp.data;
			$http.defaults.headers.common["Authorization"] = $auth.token;
		}
	});
})

app.config(function($routeProvider, $locationProvider) {
	$locationProvider.html5Mode(true);
	$locationProvider.hashPrefix('');

	$routeProvider
		.when('/admin/index.html', {
			templateUrl: '/admin/views/index.html',
			pageTitle: 'Dashboard'
		})
		.when('/', {
			redirectTo: '/admin/index.html'
		})
		.when('/admin/accounts', {
			templateUrl: '/admin/views/account.html',
			pageTitle: 'Quản lý tài khoản'
		})
		.when('/admin/bookings', {
			templateUrl: '/admin/views/booking.html',
			pageTitle: 'Lịch đặt sân'
		})
		.when('/admin/events', {
			templateUrl: '/admin/views/event-list.html',
			pageTitle: 'Sự kiện'
		})
		.when('/admin/fields', {
			templateUrl: '/admin/views/field-list.html',
			pageTitle: 'Sân'
		})
		.when('/admin/order-products', {
			templateUrl: '/admin/views/orderproduct.html',
			pageTitle: 'Đặt hàng'
		})
		.when('/admin/products', {
			templateUrl: '/admin/views/products-list.html',
			pageTitle: 'Sản phẩm'
		})
		.when('/admin/teams', {
			templateUrl: '/admin/views/team-list.html',
			pageTitle: 'Đội'
		})
		.when('/admin/vouchers', {
			templateUrl: '/admin/views/voucher-list.html',
			pageTitle: 'Mã khuyến mãi'
		})
		.when('/admin/category-product', {
			templateUrl: '/admin/views/category-product.html',
			pageTitle: 'Loại sản phẩm'

		})
		.when('/admin/category-sport', {
			templateUrl: '/admin/views/category-sport.html',
			pageTitle: 'Loại thể thao'
		})
		.when('/admin/contacts', {
			templateUrl: '/admin/views/contact.html',
			pageTitle: 'Liên hệ'
		})
		.when('/admin/reportBooking', {
			templateUrl: '/admin/views/reportBooking.html',
			pageTitle: 'Báo cáo thống kê đặt sân'
		})
		.when('/admin/reportOrder', {
			templateUrl: '/admin/views/reportOrder.html',
			pageTitle: 'Report Order'
		})
		.when('/admin/reportAll', {
			templateUrl: '/admin/views/reportAll.html',
			pageTitle: 'Report All'
		})
		.when("/admin/unauthorized", {
			templateUrl: "/admin/views/error-500.html",
			pageTitle: 'Unauthorized'
		})


}).run(function($rootScope, $route) {
	$rootScope.$on('$routeChangeSuccess', function() {
		$rootScope.pageTitle = $route.current.pageTitle;
		$rootScope.version = Math.random().toString().substr(2, 8);
	});
});


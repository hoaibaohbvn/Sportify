var app = angular.module("app", ["ngRoute"]);
app.config(function ($routeProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
    

    $routeProvider
        .when('/admin/index.html', {
            templateUrl: '/admin/views/index.html',
            pageTitle: 'Dashboard'
        })
        .when('/', {
            redirectTo : '/admin/index.html'
        })
        .when('/admin/accounts', {
            templateUrl: '/admin/views/account.html',
            pageTitle: 'Account'
        })
        .when('/admin/bookings', {
            templateUrl: '/admin/views/booking.html',
            pageTitle: 'Booking'
        })
        .when('/admin/events', {
            templateUrl: '/admin/views/event-list.html',
            pageTitle: 'Event'
        })
        .when('/admin/fields', {
            templateUrl: '/admin/views/field-list.html',
            pageTitle: 'Field'
        })
        .when('/admin/login', {
            templateUrl: '/admin/views/login.html',
            pageTitle: 'Login',
            
        })
        .when('/admin/order-products', {
            templateUrl: '/admin/views/orderproduct.html',
            pageTitle: 'Order Product'
        })
        .when('/admin/products', {
            templateUrl: '/admin/views/products-list.html',
            pageTitle: 'Products'
        })
        .when('/admin/profiles', {
            templateUrl: '/admin/views/profile.html',
            pageTitle: 'Profile'
        })
        .when('/admin/register', {
            templateUrl: '/admin/views/register.html',
            pageTitle: 'Register'
        })
        .when('/admin/reports', {
            templateUrl: '/admin/views/report.html',
            pageTitle: 'Report'
        })
        .when('/admin/resignation', {
            templateUrl: '/admin/views/resignation.html',
            pageTitle: 'Resignation'
        })
        .when('/admin/teams', {
            templateUrl: '/admin/views/team-list.html',
            pageTitle: 'Team'
        })
        .when('/admin/vouchers', {
            templateUrl: '/admin/views/voucher-list.html',
            pageTitle: 'Voucher'
        })

}).run(function ($rootScope, $route) {
    $rootScope.$on('$routeChangeSuccess', function () {
        $rootScope.pageTitle = $route.current.pageTitle;
        $rootScope.version = Math.random().toString().substr(2, 8);
    });
});


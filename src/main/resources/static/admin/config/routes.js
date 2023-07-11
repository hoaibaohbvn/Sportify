app.config(function ($routeProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
    

    $routeProvider
        .when('/', {
            templateUrl: '/views/index.html',
            pageTitle: 'Dashboard'
        })
        .when('/index.html', {
            redirectTo : '/'
        })
        .when('/account', {
            templateUrl: '/views/account.html',
            pageTitle: 'Account'
        })
        .when('/booking', {
            templateUrl: '/views/booking.html',
            pageTitle: 'Booking'
        })
        .when('/event', {
            templateUrl: '/views/event-list.html',
            pageTitle: 'Event'
        })
        .when('/field', {
            templateUrl: '/views/field-list.html',
            pageTitle: 'Field'
        })
        .when('/login', {
            templateUrl: '/views/login.html',
            pageTitle: 'Login',
            
        })
        .when('/order-product', {
            templateUrl: '/views/orderproduct.html',
            pageTitle: 'Order Product'
        })
        .when('/products', {
            templateUrl: '/views/products-list.html',
            pageTitle: 'Products'
        })
        .when('/profile', {
            templateUrl: '/views/profile.html',
            pageTitle: 'Profile'
        })
        .when('/register', {
            templateUrl: '/views/register.html',
            pageTitle: 'Register'
        })
        .when('/report', {
            templateUrl: '/views/report.html',
            pageTitle: 'Report'
        })
        .when('/resignation', {
            templateUrl: '/views/resignation.html',
            pageTitle: 'Resignation'
        })
        .when('/team', {
            templateUrl: '/views/team-list.html',
            pageTitle: 'Team'
        })
        .when('/voucher', {
            templateUrl: '/views/voucher-list.html',
            pageTitle: 'Voucher'
        })

}).run(function ($rootScope, $route) {
    $rootScope.$on('$routeChangeSuccess', function () {
        $rootScope.pageTitle = $route.current.pageTitle;
        $rootScope.version = Math.random().toString().substr(2, 8);
    });
});


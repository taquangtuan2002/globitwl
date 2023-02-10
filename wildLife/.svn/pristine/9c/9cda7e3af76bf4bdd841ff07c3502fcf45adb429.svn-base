(function () {

    'use strict';


    Education.config(['$translateProvider', function ($translateProvider) {
        $translateProvider.useStaticFilesLoader({
                prefix: 'i18n/lang_',
                suffix: '.json?v='+Education.version
            })
            .preferredLanguage('vi-VN')
            .useLocalStorage()
            .useSanitizeValueStrategy('escape')
            .useMissingTranslationHandlerLog()
    }]);

    /* OAuth2 configuration */
    Education.config(['OAuthProvider', 'OAuthTokenProvider', 'constants', function(OAuthProvider, OAuthTokenProvider, constants) {

        OAuthProvider.configure({
            baseUrl: Education.API_SERVER_URL,
            clientId: Education.API_CLIENT_ID,
            clientSecret: Education.API_CLIENT_KEY,
            grantPath: '/oauth/token',
            revokePath: '/oauth/logout'
        });

        OAuthTokenProvider.configure({
            name: constants.oauth2_token,
            options: {
                secure: false
            }
        });
    }]);

    Education.config(['$compileProvider', function ($compileProvider) {
        $compileProvider.debugInfoEnabled(false);
    }]);

    /* Block UI configuration */
    Education.config(['blockUIConfig', function(blockUIConfig) {

        // Change the default overlay message
        blockUIConfig.message = 'Vui lòng chờ...';

        // Change the default delay to 100ms before the blocking is visible
        blockUIConfig.delay = 10;

    }]);

    /* Toastr configuration */
    Education.config(['toastrConfig', function (toastrConfig) {
        angular.extend(toastrConfig, {
            autoDismiss: true,
            closeButton: true,
            containerId: 'toast-container',
            maxOpened: 0,
            newestOnTop: true,
            positionClass: 'toast-bottom-right',
            preventDuplicates: false,
            preventOpenDuplicates: false,
            target: 'body'
        });
    }]);

    /* HTTP Provider configuration */
    Education.config(['$httpProvider', function ($httpProvider) {
        // $httpProvider.defaults.withCredentials = true;
        // $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
        // $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
        // $httpProvider.defaults.useXDomain = true;
        //
        // $httpProvider.interceptors.push('XSRFInterceptor');
        $httpProvider.interceptors.push('ServerExceptionHandlerInterceptor');
    }]);

    /* Location provider */
    Education.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode(false);
    }]);

    /* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
    Education.config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
        $ocLazyLoadProvider.config({
            // global configs go here
        });
    }]);

    /* AngularJS v1.3.x workaround for old style controller declarition in HTML */
    Education.config(['$controllerProvider', function ($controllerProvider) {
    }]);

    Education.config(['$urlRouterProvider', '$stateProvider', 'constants',
        function ($urlRouterProvider, $stateProvider, constants) {
            // Redirect any unmatched url

            // Member area
            $stateProvider.state('application', {
                templateUrl: 'common/views/application.html?v='+Education.version,
                abstract: true
            });

            // Guest area
            $urlRouterProvider.otherwise(function(a, b) {
                var $injector = a, $location = b; // To support for minification

                var $cookies = $injector.get('$cookies');
                var user = $cookies.getObject(constants.cookies_user);

                if (user) {
                    $location.path('/dashboard');
                } else {
                    $location.path('/login');
                }
            });
        }
    ]);
})();
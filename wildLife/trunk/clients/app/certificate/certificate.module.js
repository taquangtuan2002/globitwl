(function () {
    'use strict';

    Education.Certificate = angular.module('Education.Certificate', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Certificate.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.certificate', {
                url: '/certificate',
                templateUrl: 'certificate/views/certificate.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Type of certificate',
                    pageSubTitle: ''
                },
                controller: 'CertificateController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Certificate',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'certificate/controllers/CertificateController.js',
                                'certificate/business/CertificateService.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();
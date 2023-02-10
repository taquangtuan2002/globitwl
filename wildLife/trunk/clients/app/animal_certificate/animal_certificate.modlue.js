(function () {
    'use strict';

    Education.AnimalCertificate = angular.module('Education.AnimalCertificate', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.AnimalCertificate.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.animalcertificate', {
                url: '/_animalcertificate',
                templateUrl: 'animal_certificate/views/animal_certificate.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Animal certificate',
                    pageSubTitle: ''
                },
                controller: 'AnimalCertificateController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AnimalCertificate',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal_certificate/controllers/AnimalCertificateController.js?v='+Education.version,
                                'animal_certificate/business/AnimalCertificateService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'users/business/UserService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();
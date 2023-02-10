(function () {
    'use strict';

    Education.LookUpTheSignature = angular.module('Education.LookUpTheSignature', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.LookUpTheSignature.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

	        .state('application.look_up_the_signature', {
	            url: '/look_up_the_signature',
	            templateUrl: 'look_up_the_signature/views/look_up_the_signature.html?v='+Education.version,
	            data: {pageTitle: 'Tra cứu mẫu chữ ký'},
	            controller: 'LookUpTheSignatureController as vm',
	            resolve: {
	                deps: ['$ocLazyLoad', function ($ocLazyLoad) {
	                    return $ocLazyLoad.load({
	                        name: 'Education.LookUpTheSignature',
	                        insertBefore: '#ng_load_plugins_before',
	                        files: [
	                            'look_up_the_signature/controllers/LookUpTheSignatureController.js?v='+Education.version,
	                            'look_up_the_signature/business/LookUpTheSignatureService.js?v='+Education.version,
	                        ]
	                    });
	                }]
	            }
            });
            
    }]);

})();
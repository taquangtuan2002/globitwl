(function () {
    'use strict';
    Education.UserFileUpload = angular.module('Education.UserFileUpload', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);
    Education.UserFileUpload.config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('application.UserFileUpload', {
                url: '/user_file_upload',
                templateUrl: 'user_file_upload/views/user_file_upload.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'File Upload'
                },
                controller: 'UserFileUploadController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.UserFileUpload',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'user_file_upload/controllers/UserFileUploadController.js',
                                'user_file_upload/business/UserFileUploadService.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();
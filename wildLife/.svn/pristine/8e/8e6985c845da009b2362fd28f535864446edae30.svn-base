(function () {
    'use strict';

    angular.module('Education.LookUpTheSignature').service('LookUpTheSignatureService', LookUpTheSignatureService);

    LookUpTheSignatureService.$inject = [
    	'$http',
        '$q',
        'settings',
        'Utilities',
        '$uibModal'
    ];

    function LookUpTheSignatureService($http, $q, settings, utils, modal) {
    	var self = this;

        var baseUrl = settings.api.baseUrl;
        var baseUrlUserAttachments = baseUrl + 'api/user_attachments';

        self.searchByPage  = searchByPage;

        function searchByPage(searchObject, successCallback, errorCallback) {
            var url = baseUrlUserAttachments + '/searchByPage';

            return utils.resolveAlt(url, 'POST', null, searchObject, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        
    }

})();
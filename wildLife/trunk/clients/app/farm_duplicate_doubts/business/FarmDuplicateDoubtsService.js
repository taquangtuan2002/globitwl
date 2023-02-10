(function () {
    'use strict';

    angular.module('Education.FarmDuplicateDoubts').service('FarmDuplicateDoubtsService', FarmDuplicateDoubtsService);

    FarmDuplicateDoubtsService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function FarmDuplicateDoubtsService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'doubtsoverlap/';
        
        self.getDuplicateFarmDoubts = getDuplicateFarmDoubts;
        self.searchByPage = searchByPage;

        function getDuplicateFarmDoubts(){
            var url = baseUrl + 'getall';
            return utils.resolve(url,'GET',angular.noop, angular.noop)
        }

        function searchByPage(dto) {
            var url = baseUrl + '/searchByPage';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        
    }
})();
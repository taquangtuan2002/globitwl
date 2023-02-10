(function () {
    'use strict';

    angular.module('Education.Report').service('AnimalRaisingReportService', AnimalRaisingReportService);

    AnimalRaisingReportService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function AnimalRaisingReportService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'report/';
        var Url = settings.api.baseUrl + settings.api.apiPrefix ;
        
        
		self.getReportRaising=getReportRaising;
        function getReportRaising(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/getReportAnimalRaising';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
    }
})();
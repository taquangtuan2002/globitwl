(function () {
    'use strict';

    angular.module('Education.InformationAnimal').service('FluctuationHerdByMonthService', FluctuationHerdByMonthService);

    FluctuationHerdByMonthService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function FluctuationHerdByMonthService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + '/';

        self.getTotalQuantityAnimal = getTotalQuantityAnimal
        function getTotalQuantityAnimal(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/getFluctuationHerd';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
       
    }
})();
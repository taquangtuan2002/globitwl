(function () {
    'use strict';

    angular.module('Education.InformationAnimal').service('InformationAnimalTotalService', InformationAnimalTotalService);

    InformationAnimalTotalService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function InformationAnimalTotalService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'drug/';

        self.getTotalQuantityAnimal = getTotalQuantityAnimal
        function getTotalQuantityAnimal(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/getAnimalWithTotalQuantity';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
       
    }
})();
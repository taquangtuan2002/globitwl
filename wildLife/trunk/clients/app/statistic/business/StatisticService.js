(function () {
    'use strict';

    angular.module('Education.Statistic').service('StatisticService', StatisticService);

    StatisticService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function StatisticService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + '/';

        
    }
})();
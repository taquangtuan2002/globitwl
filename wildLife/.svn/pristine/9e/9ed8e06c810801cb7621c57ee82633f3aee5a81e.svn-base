(function () {
    'use strict';

    angular.module('Education.Animal').service('SeedLevelQuantityReportService', SeedLevelQuantityReportService);

    SeedLevelQuantityReportService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function SeedLevelQuantityReportService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'report/';
		var Url = settings.api.baseUrl + settings.api.apiPrefix ;

        self.getTableDefinition = getTableDefinition;
        self.getData = getData;
		self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
		self.getCurrentUser=getCurrentUser;


		function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getData(dto, successCallback, errorCallback) {
            var url = baseUrl + 'seedLevelQuantityReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animal.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteAnimalRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animal.delete" | translate}}</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };


            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _typeFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.name;
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };

            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };      

            return [
                
                {
                    field: 'regionName',
                    title: 'Vùng' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'provinceName',
                    title: 'Tỉnh',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'amountChickenGrandparents',
                    title:'Gà ông bà',
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,
					formatter: _numberFormatter,

                },{
                    field: 'amountChickenParents',
                    title: 'Gà bố mẹ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _numberFormatter,
                },{
                    field: 'amountDuckGrandparents',
                    title: 'Vịt ông bà' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
					formatter: _numberFormatter,
                }
				,{
                    field: 'amountDuckParents',
                    title: 'Vịt bố mẹ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                   formatter: _numberFormatter,
                },{
                    field: 'amountOtherGrandparents',
                    title: 'Khác ông bà' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _numberFormatter,
                },{
                    field: 'amountOtherParents',
                    title: 'Khác bố mẹ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                   formatter: _numberFormatter,
                },{
                    field: 'totalAmountGrandparents',
                    title: 'Tổng số ông bà' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _numberFormatter,
                },{
                    field: 'totalAmountParents',
                    title: 'Tổng số bố mẹ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                   formatter: _numberFormatter,
                }
				
            ];
        }
    }
})();
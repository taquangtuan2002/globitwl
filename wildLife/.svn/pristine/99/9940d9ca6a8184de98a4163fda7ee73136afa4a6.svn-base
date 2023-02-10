/**
 * Created by bizic on 30/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.Ownership').service('OwnershipService', OwnershipService);

    OwnershipService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
        '$translate'
    ];

    function OwnershipService($http, $q, settings, utils, $translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        var ownerShipUrl = baseUrl + "ownership/";
        self.getTableDefinition = getTableDefinition;
        self.getOwnerShips = getOwnerShips;
        self.getAll = getAll;
		self.checkDuplicateCode=checkDuplicateCode;
		self.getOwnershipDtoById=getOwnershipDtoById;
		self.removeById=removeById;
		self.save=save;
		self.update=update;

        function getOwnerShips(dto, pageIndex, pageSize) {
            var url = ownerShipUrl + 'search/'+pageIndex +"/"+ pageSize;

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function save(dto) {
            var url = ownerShipUrl;

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function update(dto, id) {
            var url = ownerShipUrl+"update/"+id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        
		function checkDuplicateCode(code) {
            var url = ownerShipUrl + 'checkCode';
            return utils.resolveAlt(url, 'POST', null, code, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getAll() {
            var url = ownerShipUrl + 'getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getOwnershipDtoById(id) {
            var url = ownerShipUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function removeById(id) {
            var url = ownerShipUrl + 'delete/' + id;
            return utils.resolve(url, 'DELETE', angular.noop, angular.noop);
        }


        function getTableDefinition() {

            var _tableOperation = function(value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editOwnership(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"region.edit" | translate}}</a>' +
                    '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteOwnership(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i> {{"region.delete" | translate}}</a>';
            };

            var _cellNowrap = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'max-width': '120px' }
                };
            };

            var _dateFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };

            var _cellState = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '1%' }
                };
            };
            var _textCenter = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };

            var _getIndex = function(value, row, index, field) {
                if(index > -1){
                    return index +1;
                }
                return '';
            };


            return [{
                    field: 'state',
                    title: 'STT',
                     formatter: _getIndex,
                    cellStyle: _textCenter
                },{
                    field: '',
                    title: '{{"ownership.action" | translate}}',
                    sortable: true,
                    visible: true,
                    switchable: true,
                    formatter: _tableOperation,
                    cellStyle: _cellNowrap2
                }, {
                    field: 'code',
                    title: '{{"ownership.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap2
                }, {
                    field: 'name',
                    title: '{{"ownership.name" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap2
                }, {
                    field: 'description',
                    title: '{{"ownership.description" | translate}}',
                    sortable: true,
                    switchable: true,
                    cellStyle: _cellNowrap2
                } // {
                // field: 'acreage',
                // title: 'Diện tích',
                // sortable: true,  
                // switchable: true,
                // visible: true,
                // cellStyle: _cellNowrap2
                //}, 
                
            ]
        }
    }
})();
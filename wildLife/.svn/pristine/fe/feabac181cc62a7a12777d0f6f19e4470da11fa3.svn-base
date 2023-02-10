(function () {
    'use strict';

    angular.module('Education.Animal').service('AnimalService', AnimalService);

    AnimalService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function AnimalService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        //var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'animal/';

        self.getTableDefinition = getTableDefinition;
        self.getTableDefinition1 = getTableDefinition1;
        self.getPageAnimal = getPageAnimal;
        self.getAnimal = getAnimal;
        self.createAnimal = createAnimal;
        self.updateAnimal = updateAnimal;
        self.deleteAnimal = deleteAnimal;
        self.deleteListId = deleteListId;
        self.getAll = getAll;
		self.getListParent=getListParent;
		self.getListByParentId=getListByParentId;
		self.getPageSearchAnimal=getPageSearchAnimal;
        self.checkDuplicateCode=checkDuplicateCode;
        
        self.getListAnimalClass = getListAnimalClass;
        self.getListAnimalFamily = getListAnimalFamily;
        self.getListAnimalOrdo = getListAnimalOrdo;
        self.getListAnimalByFamily = getListAnimalByFamily;
        self.getListAnimalOrdoParam = getListAnimalOrdoParam;
        self.getListAnimalFamilyParam = getListAnimalFamilyParam;
        
        self.getListCites = getListCites;
        self.getVnLists = getVnLists;
        self.getVnList06s = getVnList06s;
        self.getAnimalGroup = getAnimalGroup;
        self.getFileTemplateCodeAnime2019= getFileTemplateCodeAnime2019;
        self.getListAnimalGroup = getListAnimalGroup;
        self.downloadListAnimal=downloadListAnimal;
        self.merge=merge;

        self.getCurrentUser = getCurrentUser;

        function merge(dto, successCallback, errorCallback) {
            var url = baseUrl + 'merge';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getListAnimalGroup(){
            var url = baseUrl + 'listAnimalGroup';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getCurrentUser() {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function downloadListAnimal (searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportAnimal' ,
                method:"POST",
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        }

        function getFileTemplateCodeAnime2019(){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/getFileTemplateCodeAnime2019' ,
                method:"GET",//you can use also GET or POST
                data:null,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
            // var url = settings.api.baseUrl + settings.api.apiPrefix + 'file/getFileTemplateCodeAnime2019';
            // return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAnimalGroup(){
            var url = baseUrl + 'listAnimalGroup';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListCites(){
            var url = baseUrl + 'listCites';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getVnLists(){
            var url = baseUrl + 'vnList';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getVnList06s(){
            var url = baseUrl + 'vnList06' ;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
       
        function getListAnimalByFamily(family){
            var url = baseUrl + 'getListAnimalByFamily' + (family ? ('?family=' + family) :'');
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        
        function getListAnimalClass(){
            var url = baseUrl + 'getListAnimalClass';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getListAnimalFamily(animalClass, ordo){
            var url = baseUrl + 'getListAnimalFamily'+ (animalClass ? ('?animalClass=' + animalClass)  : '?animalClass=') + (ordo ? ('&ordo=' + ordo) : '');
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        // Get list Family SearchDto
        function getListAnimalFamilyParam(searchAnimalDto, successCallback, errorCallback) {
            if(searchAnimalDto != null) {
                var url = baseUrl + 'getListAnimalFamilyParam';
                return utils.resolveAlt(url, 'POST', null, searchAnimalDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);
            }
        }

        function getListAnimalOrdo(animalClass){
            var url = baseUrl + 'getListAnimalOrdo' + (animalClass ? ('?animalClass=' + animalClass)  : '');
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        // Get list Ordo SearchDto
        function getListAnimalOrdoParam(searchAnimalDto, successCallback, errorCallback) {
            if(searchAnimalDto != null) {
                var url = baseUrl + 'getListAnimalOrdoParam';
                return utils.resolveAlt(url, 'POST', null, searchAnimalDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);
            }
        }

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAll(){
            var url = baseUrl + 'getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getListParent(){
            var url = baseUrl + 'getparent';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getListByParentId(id){
            var url = baseUrl + 'getbyparent/'+id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        
        function deleteListId(ids,successCallback, errorCallback){
            var url = baseUrl + 'delete/listid';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteAnimal(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        } 
        function updateAnimal(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function createAnimal(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getPageAnimal(pageIndex,pageSize){
            var url = baseUrl + 'getall/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAnimal(id){
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getPageSearchAnimal(searchAnimalDto,pageIndex, pageSize, successCallback, errorCallback) {
            if(searchAnimalDto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, searchAnimalDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a ng-show="$parent.vm.isRoleAdmin" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animal.edit" | translate}}</a>'+
                '<a ng-show="!$parent.vm.isRoleAdmin" class="green-dark margin-right-5" href="#" data-ng-click="$parent.selectAnimal(' + "'" + row.id + "'" + ')"><i class="icon-eye margin-right-5"></i> Xem</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a ng-show="$parent.vm.isRoleAdmin" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteAnimalRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animal.delete" | translate}}</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _cellNowrapBreak = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'break-spaces', 'min-width':'120px','max-width':'150px' }
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
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes:'text-center'
                };
            };

            var _originalFormatter = function(value,row,index){
                if (!value) {
                    return '';
                }
                return value.name;
            }
            var _reproductionFormFormatter = function(value,row,index){
                if (!value) {
                    return '';
                }else if(value==1){
                    return 'Đẻ con';
                }else if(value==2){
                    return 'Đẻ trứng';
                }
                return value;
            }
            var _productTargetFormatter = function(value,row,index){
                if (!value) {
                    return '';
                }
                return value.name;
            }
            var _numberFormatter = function(value,row,index){
                if(!value){
                    return '';
                }
                return $filter('number')(value,0);
            }
            // var _roomFormatter = function (value, row, index) {
            //     if (!value) {
            //         return '';
            //     }
            //     if(value!= null || value.length > 0){
            //     }
            //     return value.room.name;
            // };

            return [
            
            {
                field: '',
                title: 'TT',
                switchable: true,
                visible: true,
                formatter: _tableIndex,
                cellStyle: _tableTextRight
            },
               {
                   field: 'state',
                   checkbox: true
               },
                {
                    field: '',
                    title: '{{"product.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title: '{{"animal.code" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                // ,{
                //     field: 'oldCode',
                //     title: 'Mã 2015' ,
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                // }
                , {
                    field: 'name',
                    title: 'Tên loài',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },
                {
                    field: 'otherName',
                    title: 'Tên khác',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },
                {
                    field: 'scienceName',
                    title: 'Tên khoa học',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },{
                    field: 'enName',
                    title: 'Tên tiếng Anh',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                }, {
                    field: 'animalClass',
                    title:'Lớp',
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,

                },{
                    field: 'ordo',
                    title: 'Bộ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                },{
                    field: 'family',
                    title: 'Họ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                },{
                    field: 'cites',
                    title: 'phân nhóm theo Phụ lục CITES' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                    //formatter: _originalFormatter,
                }
                ,{
                    field: 'vnlist',
                    title: 'Phân nhóm theo Nghị định 64' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                   // formatter: _originalFormatter,
                }
                ,{
                    field: 'vnlist06',
                    title: 'Phân nhóm theo Nghị định 06' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                   // formatter: _originalFormatter,
                }
                // ,{
                //     field: 'vnlist06',
                //     title: 'VNlist-06' ,
                //     sortable: true,
                //     cellStyle: _cellNowrap,
                //     switchable: false
                // }
                ,{
                    field: 'reproductionForm',
                    title: 'Hình thức sinh sản' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                    formatter: _reproductionFormFormatter,
                }
            ];
        }
        function getTableDefinition1() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.selectAnimal(' + "'" + row.id + "'" + ')"><i class="icon-eye margin-right-5"></i> Xem</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _cellNowrapBreak = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'break-spaces', 'min-width':'120px','max-width':'150px' }
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
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes:'text-center'
                };
            };

            var _originalFormatter = function(value,row,index){
                if (!value) {
                    return '';
                }
                return value.name;
            }
            var _productTargetFormatter = function(value,row,index){
                if (!value) {
                    return '';
                }
                return value.name;
            }
            var _numberFormatter = function(value,row,index){
                if(!value){
                    return '';
                }
                return $filter('number')(value,0);
            }
            // var _roomFormatter = function (value, row, index) {
            //     if (!value) {
            //         return '';
            //     }
            //     if(value!= null || value.length > 0){
            //     }
            //     return value.room.name;
            // };

            return [
            
            {
                field: '',
                title: 'TT',
                switchable: true,
                visible: true,
                formatter: _tableIndex,
                cellStyle: _tableTextRight
            },
            //    {
            //        field: 'state',
            //        checkbox: true
            //    },
                {
                    field: '',
                    title: '{{"product.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title: '{{"animal.code" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                // ,{
                //     field: 'oldCode',
                //     title: 'Mã 2015' ,
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                // }
                , {
                    field: 'name',
                    title: 'Tên loài',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },
                {
                    field: 'otherName',
                    title: 'Tên khác',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },
                {
                    field: 'scienceName',
                    title: 'Tên khoa học',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },{
                    field: 'enName',
                    title: 'Tên tiếng Anh',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                }, {
                    field: 'animalClass',
                    title:'Lớp',
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,

                },{
                    field: 'ordo',
                    title: 'Bộ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                },{
                    field: 'family',
                    title: 'Họ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                },{
                    field: 'cites',
                    title: 'phân nhóm theo Phụ lục CITES' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                    //formatter: _originalFormatter,
                }
                ,{
                    field: 'vnlist',
                    title: 'Phân nhóm theo Nghị định 64' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                   // formatter: _originalFormatter,
                }
                ,{
                    field: 'vnlist06',
                    title: 'Phân nhóm theo Nghị định 06' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                   // formatter: _originalFormatter,
                }
                // ,{
                //     field: 'vnlist06',
                //     title: 'VNlist-06' ,
                //     sortable: true,
                //     cellStyle: _cellNowrap,
                //     switchable: false
                // }
            ];
        }
    }
})();
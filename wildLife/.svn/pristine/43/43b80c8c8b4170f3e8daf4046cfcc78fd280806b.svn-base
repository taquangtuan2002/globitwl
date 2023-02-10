(function () {
    'use strict';

    angular.module('Education.AnimalRequired').service('AnimalRequiredService', AnimalRequiredService);

    AnimalRequiredService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate',
        '$rootScope'
    ];

    function AnimalRequiredService($http, $q, $filter, settings, utils,$translate, $rootScope) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'animal/';

        var urlBiologicalClass = settings.api.baseUrl + settings.api.apiPrefix + 'biologicalClass/';

        var Url = settings.api.baseUrl + settings.api.apiPrefix;
        self.getTableDefinition = getTableDefinition;
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
		self.getPageSearchAnimalRequired=getPageSearchAnimalRequired;
		self.saveOrUpdateAnimalRequired=saveOrUpdateAnimalRequired;
		self.getAnimalRequiredById=getAnimalRequiredById;
		self.animalRequiredApplyToAnimal=animalRequiredApplyToAnimal;
		self.changeAnimalRequiredStatus=changeAnimalRequiredStatus;
        self.checkDuplicateCode=checkDuplicateCode;
        
        self.getListAnimalClass = getListAnimalClass;
        self.searchByPageBiologicalClass = searchByPageBiologicalClass;
        self.getListAnimalOrdoSci = getListAnimalOrdoSci;
        self.getListAnimalFamilySci = getListAnimalFamilySci;
        self.getListAnimalFamily = getListAnimalFamily;
        self.getListAnimalOrdo = getListAnimalOrdo;
        self.getListCites = getListCites;
        self.getVnLists = getVnLists;
        self.getVnList06s = getVnList06s;
		self.getCurrentUser= getCurrentUser;
        self.getListAnimalGroup = getListAnimalGroup;

        function getListAnimalGroup(){
            var url = baseUrl + 'listAnimalGroup';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        
		function getCurrentUser() {
            var url = Url + 'fms_users/getCurrentUser';
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
            var url = baseUrl + 'vnList06';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getListAnimalClass(){
            var url = baseUrl + 'getListAnimalClass';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function searchByPageBiologicalClass(dto) {
            if(dto != null){
                var url = urlBiologicalClass + 'searchByPage';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);
            }
        }

        function getListAnimalFamily(animalClass, ordo){
            var url = baseUrl + 'getListAnimalFamily'+ (animalClass ? ('?animalClass=' + animalClass)  : '?') + (ordo ? ('&ordo=' + ordo) : '');
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getListAnimalFamilySci(animalClassSci, ordoSci){
            var url = baseUrl + 'getListAnimalFamilySci'+ (animalClassSci ? ('?animalClassSci=' + animalClassSci)  : '?') + (ordoSci ? ('&ordoSci=' + ordoSci) : '');
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getListAnimalOrdo(animalClass){
            var url = baseUrl + 'getListAnimalOrdo' + (animalClass ? ('?animalClass=' + animalClass)  : '');
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListAnimalOrdoSci(animalClassSci){
            var url = baseUrl + 'getListAnimalOrdoSci' + (animalClassSci ? ('?animalClassSci=' + animalClassSci)  : '');
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
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
        function getAnimalRequiredById(id){
            var url = Url +'animalrequired/'+ id;
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
		function getPageSearchAnimalRequired(searchAnimalDto,pageIndex, pageSize) {
            if(searchAnimalDto != null){

                var url = Url + 'animalrequired/getPage/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, searchAnimalDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }
		function saveOrUpdateAnimalRequired(dto,pageIndex, pageSize) {
            if(dto != null){

                var url = Url + 'animalrequired';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }
		function animalRequiredApplyToAnimal(dto) {
            if(dto != null){
                var url = Url + 'animalrequired/animalRequiredApplyToAnimal';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }
		function changeAnimalRequiredStatus(dto) {
            if(dto != null){
                var url = Url + 'animalrequired/changeAnimalRequiredStatus';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function getTableDefinition() {
            var _convertToString = function(value){
                let result = "";
                for(let key in settings.AnimalRequiredStatus){
                    const obj = settings.AnimalRequiredStatus[key];
                    if(obj.value == value){
                        result = obj.name;
                    }
                }
                return result;
            }


            var _tableOperation = function (value, row, index) {
                return ''
                +(((!$rootScope.isApprovedSpecies) &&  row.animalRequiredStatus != settings.AnimalRequiredStatus.PENDING.value) ?'<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animalRequired.edit" | translate}}</a>': '')
                +(($rootScope.isApprovedSpecies)?'<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animalRequired.edit" | translate}}</a>':'')
                // + ((($rootScope.isApprovedSpecies) && row.animalRequiredStatus == settings.AnimalRequiredStatus.PENDING.value) ?'<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.changeStatus(' + "'" + row.id + "'" + ')"><i class="fa fa-check-circle"></i> Xác nhận </a>': '')
                // + ((($rootScope.isApprovedSpecies) && row.animalRequiredStatus == settings.AnimalRequiredStatus.PENDING.value) ?'<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.changeStatusReject(' + "'" + row.id + "'" + ')"><i class="fa fa-check-circle"></i> Từ chối </a>': '')
                // + (((!$rootScope.isApprovedSpecies) && row.animalRequiredStatus != settings.AnimalRequiredStatus.PENDING.value) ?'<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.sendEmail(' + "'" + row.id + "'" + ')"><i class="fa fa-check-circle"></i> Gửi email yêu cầu </a>': '')
                ;
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
                    css: { 'white-space': 'break-spaces', 'min-width':'300px','max-width':'300px' }
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
                {
                    field: '',
                    title: '{{"product.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'animalRequiredStatus',
                    title: 'Trạng thái' ,
                    sortable: true,
                    switchable: false,
                    formatter: _convertToString,
                    cellStyle: _cellNowrap,
                },
                // {
                //     field: 'code',
                //     title: '{{"animal.code" | translate}}' ,
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                // },
                // {
                //     field: 'oldCode',
                //     title: 'Mã 2015' ,
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                // }
                // ,
                 {
                    field: 'name',
                    title: 'Tên loài',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },{
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
                    title: 'Cites' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                    //formatter: _originalFormatter,
                }
                ,{
                    field: 'vnlist',
                    title: 'ND64' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: false,
                   // formatter: _originalFormatter,
                }
                ,{
                    field: 'vnlist06',
                    title: 'ND06' ,
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
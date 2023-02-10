/**
 * Created by bizic on 28/10/2017.
 */

angular.module('Education').filter('propsFilter', function () {
    return function (items, props) {
        var out = [];

        if (angular.isArray(items)) {
            items.forEach(function (item) {
                var itemMatches = false;

                var keys = Object.keys(props);
                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }

        return out;
    }
});

angular.module('Education').filter('contentType', ['Utilities', function(utils) {
    return function(cc) {
        return utils.translateContentType(cc);
    };
}]);

angular.module('Education').filter( 'fileSize', function () {
    var units = [
        'bytes',
        'KB',
        'MB',
        'GB',
        'TB',
        'PB'
    ];

    return function( bytes, precision ) {
        if ( isNaN( parseFloat( bytes )) || ! isFinite( bytes ) ) {
            return '?';
        }

        var unit = 0;

        while ( bytes >= 1024 ) {
            bytes /= 1024;
            unit ++;
        }

        return bytes.toFixed( + precision ) + ' ' + units[ unit ];
    };
});

angular.module('Education').filter('numberNoDecimalsIfZeroFilter', function($filter) {
    return function(value, fractionSize) {
        //If has  no decimals, then don't show
        if(value%1 === 0){
            fractionSize = 0;
        }
        return $filter('number')(value,fractionSize);
    }
});

angular.module('Education').filter('numberStand', function($filter) {
    return function(value, fractionSize) {
        if(!value){
            value = 0;
        }
        let numberTemp = $filter('number')(value,fractionSize);
        numberTemp.replace('.', '#').replace(',','.').replace('#',',');
        return numberTemp;
    }
});

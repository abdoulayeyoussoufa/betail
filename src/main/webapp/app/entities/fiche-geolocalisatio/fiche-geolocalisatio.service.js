(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('FicheGeolocalisatio', FicheGeolocalisatio);

    FicheGeolocalisatio.$inject = ['$resource'];

    function FicheGeolocalisatio ($resource) {
        var resourceUrl =  'api/fiche-geolocalisatios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Facture', Facture);

    Facture.$inject = ['$resource', 'DateUtils'];

    function Facture ($resource, DateUtils) {
        var resourceUrl =  'api/factures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datFacture = DateUtils.convertLocalDateFromServer(data.datFacture);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datFacture = DateUtils.convertLocalDateToServer(copy.datFacture);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datFacture = DateUtils.convertLocalDateToServer(copy.datFacture);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

/**
 * Created by azola.ndamase on 30-Aug-16.
 */

var app = angular.module('Forerex', []);

app.directive('homeIndicators', function() {

    return {
        restrict: 'A',
        controller: ['$scope', '$http', function($scope, $http){
            this.dollar = '';
            this.pound = '';
            this.euro = '';

            var _this = this;

            $http.get('rest/exchangeRate/getBaseExchangeRates').success(function(data){
                _this.currencies = data;
                for(var i = 0; i < _this.currencies.length; i++){
                    if(_this.currencies[i].symbol == "USD") {
                        _this.dollar = _this.currencies[i];
                    }
                    else if(_this.currencies[i].symbol == "GBP") {
                        _this.pound = _this.currencies[i];
                    }
                    else {
                        _this.euro = _this.currencies[i];
                    }
                }

            }).error(function(data) {
                console.log('Error occurred while getting currencies');
                _this.currencies = [];
            });
        }],

        controllerAs: 'homeIndicators'
    }

});
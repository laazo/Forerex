/**
 * Created by azola.ndamase on 30-Aug-16.
 */

var app = angular.module('Forerex', ['datatables', 'ngRoute']);

app.directive('homeController', function() {

    return {
        restrict: 'A',
        controller: ['$scope', '$http', function($scope, $http){
            // default values
            this.dollar = 14.07;
            this.pound = 20.21;
            this.euro = 18.30;

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

            $http.get('rest/sentiments/getNewsArticles').success(function(data){
                _this.newsArticles = data;
            }).error(function(data) {
                console.log('Error occurred while getting news articles');
            });

            $http.get('rest/sentiments/getTweets').success(function(data){
                _this.tweets = data;

            }).error(function(data) {
                console.log('Error occurred while getting tweets');
            });

            // get number of tweets processed today
            $http.get('rest/sentiments/getNumberOfTweets').success(function(data){
                _this.numberOfTweets = data;

            }).error(function(data) {
                console.log('Error occurred while getting tweets');
            });

            $http.get('rest/sentiments/getTopics').success(function(data){
                _this.topics = data;

            }).error(function(data) {
                console.log('Error occurred while getting topics');
            });

        }],

        controllerAs: 'homeController'
    }

});
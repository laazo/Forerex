/**
 * Created by azola.ndamase on 30-Aug-16.
 */

app.directive('wordController', function() {

    return {
        restrict: 'A',
        controller: ['$scope', '$http', function($scope, $http){

            this.initCloud = function() {


                $http.get('rest/sentiments/getTopics').success(function(data){

                    var topics = data;
                    var counts = {};

                    for(var i = 0; i< topics.length; i++) {
                        var topic = topics[i];
                        counts[topic] = counts[topic] ? counts[topic]+1 : 1;
                    }

                    var uniqueTopics = Array.from(new Set(topics));

                    var cloudWords  = [];
                    for(var i = 0; i < uniqueTopics.length; i++) {
                        var temp = {text: uniqueTopics[i], weight: counts[uniqueTopics[i]]};
                        cloudWords.push(temp);
                    }

                    $('#wordCloud').jQCloud(cloudWords, {
                        shape: 'rectangular',
                        autoResize: true
                    });

                }).error(function(data) {
                    console.log('Error occurred while getting topics');
                });

            }
        }],

        controllerAs: 'wordController'
    }

});
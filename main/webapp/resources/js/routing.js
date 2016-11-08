/**
 * Created by azola.ndamase on 2016-11-02.
 */

app.config(function($routeProvider) {
    $routeProvider.when("/", {
            templateUrl: "dashboard.html"
        })
        .when("/LivePredictions", {
            templateUrl: "chart.html"
        })
        .when("/Dashboard", {
            templateUrl: "dashboard.html"
        })
        .when("/Topics", {
            templateUrl: "word-cloud.html"
        })
        .when("/History", {
            templateUrl: "history.html"
        })
        .when("/Tweets", {
            templateUrl: "tweets.html"
        })
        .when("/News", {
            templateUrl: "news.html"
        })

});

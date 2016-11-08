/**
 * Created by azola.ndamase on 2016-11-02.
 */

app.directive('predictionController', function() {

    return {
        restrict: 'A',
        controller: ['$scope', '$http', function($scope, $http){
            this.selectedCurrency = '';

            var _this = this;

            this.setSelectedCurrency = function(currency) {
                _this.selectedCurrency = currency;
                _this.getLatestPredictions();
            };

            this.view =  function(currency) {
                $http.get('rest/sentiments/getCurrencyPredictions?id='+currency).success(function(data){
                    _this.predictions = data;

                    var hours = [];
                    var values = [];
                    for(var i = 0; i < _this.predictions.length; i++) {
                        hours.push(_this.predictions[i].hour);
                        values.push(_this.predictions[i].randValue);
                    }
                    _this.setTheme();
                    $(function () {
                        Highcharts.chart('chartContainer', {
                            chart: {
                                type: 'line'
                            },
                            title: {
                                text: _this.selectedCurrency + ' to ZAR Predictions'
                            },
                            subtitle: {
                                text: 'Date: ' + new Date().toDateString()
                            },
                            xAxis: {
                                categories: hours,
                                title: {
                                    text: 'Time of Day'
                                }
                            },
                            yAxis: {
                                title: {
                                    text: 'Rands (R)'
                                }
                            },
                            plotOptions: {
                                line: {
                                    dataLabels: {
                                        enabled: true
                                    },
                                    enableMouseTracking: false
                                }
                            },
                            series: [{
                                name: _this.selectedCurrency,
                                data: values
                            }]
                        });
                    });

                 }).error(function(data) {
                     console.log('Error occurred while getting topics');
                 });
            };

            this.getLatestPredictions = function() {
                $http.get('rest/sentiments/getDollarPrediction').success(function(data){

                }).error(function(data) {
                    console.log('Error occurred while getting dollar prediction');
                });

                $http.get('rest/sentiments/getPoundPrediction').success(function(data){

                }).error(function(data) {
                    console.log('Error occurred while getting pound prediction');
                });

                $http.get('rest/sentiments/getEuroPrediction').success(function(data){

                }).error(function(data) {
                    console.log('Error occurred while getting euro prediction');
                });
            };

            this.setTheme = function() {
                Highcharts.createElement('link', {
                    href: 'https://fonts.googleapis.com/css?family=Unica+One',
                    rel: 'stylesheet',
                    type: 'text/css'
                }, null, document.getElementsByTagName('head')[0]);

                Highcharts.theme = {
                    colors: ['#2b908f', '#90ee7e', '#f45b5b', '#7798BF', '#aaeeee', '#ff0066', '#eeaaee',
                        '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
                    chart: {
                        backgroundColor: {
                            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
                            stops: [
                                [0, '#2a2a2b'],
                                [1, '#3e3e40']
                            ]
                        },
                        style: {
                            fontFamily: '\'Unica One\', sans-serif'
                        },
                        plotBorderColor: '#606063'
                    },
                    title: {
                        style: {
                            color: '#E0E0E3',
                            textTransform: 'uppercase',
                            fontSize: '20px'
                        }
                    },
                    subtitle: {
                        style: {
                            color: '#E0E0E3',
                            textTransform: 'uppercase'
                        }
                    },
                    xAxis: {
                        gridLineColor: '#707073',
                        labels: {
                            style: {
                                color: '#E0E0E3'
                            }
                        },
                        lineColor: '#707073',
                        minorGridLineColor: '#505053',
                        tickColor: '#707073',
                        title: {
                            style: {
                                color: '#A0A0A3'

                            }
                        }
                    },
                    yAxis: {
                        gridLineColor: '#707073',
                        labels: {
                            style: {
                                color: '#E0E0E3'
                            }
                        },
                        lineColor: '#707073',
                        minorGridLineColor: '#505053',
                        tickColor: '#707073',
                        tickWidth: 1,
                        title: {
                            style: {
                                color: '#A0A0A3'
                            }
                        }
                    },
                    tooltip: {
                        backgroundColor: 'rgba(0, 0, 0, 0.85)',
                        style: {
                            color: '#F0F0F0'
                        }
                    },
                    plotOptions: {
                        series: {
                            dataLabels: {
                                color: '#B0B0B3'
                            },
                            marker: {
                                lineColor: '#333'
                            }
                        },
                        boxplot: {
                            fillColor: '#505053'
                        },
                        candlestick: {
                            lineColor: 'white'
                        },
                        errorbar: {
                            color: 'white'
                        }
                    },
                    legend: {
                        itemStyle: {
                            color: '#E0E0E3'
                        },
                        itemHoverStyle: {
                            color: '#FFF'
                        },
                        itemHiddenStyle: {
                            color: '#606063'
                        }
                    },
                    credits: {
                        style: {
                            color: '#666'
                        }
                    },
                    labels: {
                        style: {
                            color: '#707073'
                        }
                    },

                    drilldown: {
                        activeAxisLabelStyle: {
                            color: '#F0F0F3'
                        },
                        activeDataLabelStyle: {
                            color: '#F0F0F3'
                        }
                    },

                    navigation: {
                        buttonOptions: {
                            symbolStroke: '#DDDDDD',
                            theme: {
                                fill: '#505053'
                            }
                        }
                    },

                    // scroll charts
                    rangeSelector: {
                        buttonTheme: {
                            fill: '#505053',
                            stroke: '#000000',
                            style: {
                                color: '#CCC'
                            },
                            states: {
                                hover: {
                                    fill: '#707073',
                                    stroke: '#000000',
                                    style: {
                                        color: 'white'
                                    }
                                },
                                select: {
                                    fill: '#000003',
                                    stroke: '#000000',
                                    style: {
                                        color: 'white'
                                    }
                                }
                            }
                        },
                        inputBoxBorderColor: '#505053',
                        inputStyle: {
                            backgroundColor: '#333',
                            color: 'silver'
                        },
                        labelStyle: {
                            color: 'silver'
                        }
                    },

                    navigator: {
                        handles: {
                            backgroundColor: '#666',
                            borderColor: '#AAA'
                        },
                        outlineColor: '#CCC',
                        maskFill: 'rgba(255,255,255,0.1)',
                        series: {
                            color: '#7798BF',
                            lineColor: '#A6C7ED'
                        },
                        xAxis: {
                            gridLineColor: '#505053'
                        }
                    },

                    scrollbar: {
                        barBackgroundColor: '#808083',
                        barBorderColor: '#808083',
                        buttonArrowColor: '#CCC',
                        buttonBackgroundColor: '#606063',
                        buttonBorderColor: '#606063',
                        rifleColor: '#FFF',
                        trackBackgroundColor: '#404043',
                        trackBorderColor: '#404043'
                    },

                    // special colors for some of the
                    legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
                    background2: '#505053',
                    dataLabelsColor: '#B0B0B3',
                    textColor: '#C0C0C0',
                    contrastTextColor: '#F0F0F3',
                    maskColor: 'rgba(255,255,255,0.3)'
                };

                // Apply the theme
                Highcharts.setOptions(Highcharts.theme);
            }

        }],

        controllerAs: 'predictionController'
    }

});

app.directive('historyController', function() {

    return {
        restrict: 'A',
        controller: ['$scope', '$http', function($scope, $http){
            this.selectedCurrency = '';
            var _this = this;

            this.setSelectedCurrency = function(currency) {
                _this.selectedCurrency = currency;
            }

            this.getHistory = function(currency) {
                $http.get('rest/sentiments/getActualHistory?id='+currency).success(function(data){
                    _this.actualHistory = data;

                }).error(function(data) {
                    console.log('Error occurred while getting actual history');
                });

                $http.get('rest/sentiments/getPredictionHistory?id='+currency).success(function(data){
                    _this.predictionHistory = data;

                }).error(function(data) {
                    console.log('Error occurred while getting prediction history');
                });
            }

        }],

        controllerAs: 'historyController'
    }

});
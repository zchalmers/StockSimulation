import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import MakeMoneyClient from "../api/makeMoneyClient.js";



class MakeMoneyPage extends BaseClass {

        constructor() {
            super();
            this.bindClassMethods(['getPortfolioData', 'renderValueChart', 'sortTable', 'getStockData', 'renderRelPerfChart'], this);
            this.dataStore = new DataStore();
        }

        async mount() {
            this.client = new MakeMoneyClient();
            this.getPortfolioData();
//            document.getElementById("switchButton").addEventListener("click", renderChart);
            }


        async sortTable(n, dataType) {
          var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
          table = document.getElementById("portfolio-table");
          switching = true;
          dir = "asc";
            console.log(n);
          while (switching) {
            switching = false;
            rows = table.rows;

            for (i = 1; i < (rows.length - 1); i++) {
              shouldSwitch = false;
//              console.log(rows);
                console.log(rows[i].getElementsByTagName("TD")[n].innerHTML);
              x = rows[i].getElementsByTagName("TD")[n];
              y = rows[i + 1].getElementsByTagName("TD")[n];
//              console.log(x);
//              console.log(y);
//              if (x == null || y == null){
//                continue;
//              }

              if (dataType === "number") {
                var valueX = parseFloat(x.innerHTML.replace(/[$,]/g, ''));
                var valueY = parseFloat(y.innerHTML.replace(/[$,]/g, ''));

                if (dir === "asc" ? valueX > valueY : valueX < valueY) {
                  shouldSwitch = true;
                  break;
                }
              } else if (dataType === "date") {
                var dateX = new Date(x.innerHTML);
                var dateY = new Date(y.innerHTML);

                if (dir === "asc" ? dateX > dateY : dateX < dateY) {
                  shouldSwitch = true;
                  break;
                }
              } else {
                if (dir === "asc" ? x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase() : x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                  shouldSwitch = true;
                  break;
                }
              }
            }

            if (shouldSwitch) {
              rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
              switching = true;
              switchcount++;
            } else {
              if (switchcount === 0 && dir === "asc") {
                dir = "desc";
                switching = true;
              }
            }
          }
        }
        async getPortfolioData(){
            const chartData = await this.client.getPortfolioData(this.errorHandler);

            this.renderTable(chartData.recordList);
            this.renderValueChart(chartData);
            var table = document.getElementById("portfolio-table");
            var headers = table.getElementsByTagName("th");
            var self = this;
            console.log(headers);
            for (var i = 0; i < headers.length; i++) {
              (function(index) {
                var header = headers[index];
                if (index === 0) {
                  header.addEventListener('click', function() {
                    self.sortTable(index, 'asc');
                  });
                } else if (index === 7) {
                  header.addEventListener('click', function() {
                    self.sortTable(index, 'date');
                  });
                } else {
                  header.addEventListener('click', function() {
                    self.sortTable(index, 'number');
                  });
                }
                console.log(header.innerText);
              })(i);
            }
            var dropdownMenu = document.getElementById("switchChart")
            console.log(dropdownMenu);
            // Add event listener to the dropdown menu
            var self = this;
            dropdownMenu.addEventListener("click", function(event) {
              event.preventDefault(); // Prevent default link behavior
              // Get the selected option's ID
              var selectedOptionId = event.target.id;
              console.log(event + " " + selectedOptionId);
              // Call the appropriate method based on the selected option
              switch (selectedOptionId) {
                case "valueChart":
                  self.renderValueChart(chartData);
                  break;
                case "relPerf":
                  self.renderRelPerfChart(chartData);
                  break;
                default:
                  break;
              }
            });


            console.log(table.getElementsByTagName("th"));
        }


        async renderRelPerfChart(chartData){
            let resultArea = document.getElementById("stockresults");
            console.log("chartData" + chartData);
            var prettyData = [];
            var max = 0;
            var min = 999999;
            var totalValue = chartData.totalValue;
            var relativePerformanceOverTime = Object.entries(chartData.relativePerformanceOverTime).map(([date, value]) => ({ date, value }));

              // Sort the array by date in ascending order
              relativePerformanceOverTime.sort((a, b) => new Date(a.date) - new Date(b.date));
              let index = 0;
              let lastPercent = 0;

              for (let entry of relativePerformanceOverTime) {
                console.log(entry.date, "=", entry.value);
                prettyData.push({ x: entry.date, y: entry.value });
                if (index === relativePerformanceOverTime.length - 1){
                    lastPercent = entry.value.toFixed(2);
                }
                if (entry.value > max) {
                  max = entry.value;
                }
                if (entry.value < min) {
                  min = entry.value;
                }
                index++;
                console.log(index);
              }

            this.dataStore.set("min", min);
            this.dataStore.set("max", max);
            var fillStylesObj = {
                'currentColor -> lightenMore': ['currentColor', 'lightenMore'],
                'currentColor -> lighten': ['currentColor', 'lighten'],
                'currentColor -> darkenMore': ['currentColor', 'darkenMore'],
                'currentColor -> darken': ['currentColor', 'darken'],
                'lighten -> darken': ['lighten', 'darken'],
                'lightenMore -> darkenMore': ['lightenMore', 'darkenMore']
            };
            var currentAngle = 360;
            var currentColors = fillStylesObj['currentColor -> lightenMore'];
            var currentFill = currentColors.concat(currentAngle);

             let chart = new JSC.chart("chart", {
                type: 'area spline',
                title_label_text: `Overall Percentage Change ${lastPercent}%`,
                yAxis: {
                    defaultTick_gridLine_visible: false,
                    scale: {
                        range: {
                            min: min - min *.1,
                            max: max + max * 0.1,
                            padding: 1
                        }
                    }
                },
                xAxis: {
                    defaultTick: {
                        color: 'red',
                        gridLine_visible: false,
                    }
                },
                defaultSeries: {
                    color: '#45a29f',
                    shape: {fill: currentFill}
                },
                defaultPoint: {
                    marker: {
                        visible: false
                    }
                },
                series: [
                     {
                        points: prettyData
                     }
//                     ,
//                     {
//                        type: 'line',
//                        name: 'Date Bought',
//                        visible: true,
//                        onTop: true,
//                        points: [
//                            [new Date(dateBought), 0],
//                            [new Date(dateBought), dateCost]
//                        ],
//                        lineDashStyle: 'ShortDash',
//                        lineWidth: 1,
//                        color: 'black'
//                        }
                      ],
                legend_visible: false,
                autoFit: false,
            });
        }

        async renderValueChart(chartData){
            let resultArea = document.getElementById("stockresults");
            console.log("chartData" + chartData);
            var prettyData = [];
            var max = 0;
            var min = 999999;
            var totalValue = chartData.totalValue;
            var valueOverTimeArray = Object.entries(chartData.valueOverTime).map(([date, value]) => ({ date, value }));

              // Sort the array by date in ascending order
              valueOverTimeArray.sort((a, b) => new Date(a.date) - new Date(b.date));

              for (let entry of valueOverTimeArray) {
                console.log(entry.date, "=", entry.value);
                prettyData.push({ x: entry.date, y: entry.value });

                if (entry.value > max) {
                  max = entry.value;
                }
                if (entry.value < min) {
                  min = entry.value;
                }
              }

            this.dataStore.set("min", min);
            this.dataStore.set("max", max);
            var fillStylesObj = {
                'currentColor -> lightenMore': ['currentColor', 'lightenMore'],
                'currentColor -> lighten': ['currentColor', 'lighten'],
                'currentColor -> darkenMore': ['currentColor', 'darkenMore'],
                'currentColor -> darken': ['currentColor', 'darken'],
                'lighten -> darken': ['lighten', 'darken'],
                'lightenMore -> darkenMore': ['lightenMore', 'darkenMore']
            };
            var currentAngle = 360;
            var currentColors = fillStylesObj['currentColor -> lightenMore'];
            var currentFill = currentColors.concat(currentAngle);

             let chart = new JSC.chart("chart", {
                type: 'area spline',
                title_label_text: `Portfolio Value $${totalValue.toLocaleString()}`,
                yAxis: {
                    defaultTick_gridLine_visible: false,
                    scale: {
                        range: {
                            min: min - min *.1,
                            max: max + max * 0.1,
                            padding: 1
                        }
                    }
                },
                xAxis: {
                    defaultTick: {
                        color: 'red',
                        gridLine_visible: false,
                    }
                },
                defaultSeries: {
                    color: '#45a29f',
                    shape: {fill: currentFill}
                },
                defaultPoint: {
                    marker: {
                        visible: false
                    }
                },
                series: [
                     {
                        points: prettyData
                     }
//                     ,
//                     {
//                        type: 'line',
//                        name: 'Date Bought',
//                        visible: true,
//                        onTop: true,
//                        points: [
//                            [new Date(dateBought), 0],
//                            [new Date(dateBought), dateCost]
//                        ],
//                        lineDashStyle: 'ShortDash',
//                        lineWidth: 1,
//                        color: 'black'
//                        }
                      ],
                legend_visible: false,
                autoFit: false,
            });
//            document.getElementById('weekOption').addEventListener('click', this.changeTimescale('week', prettyData));
//            document.getElementById('monthOption').addEventListener('click', this.changeTimescale('month', prettyData));
//            document.getElementById('yearOption').addEventListener('click', this.changeTimescale('year', prettyData));


        }
//        async changeTimescale(timescale, prettyData){
//            const currentDate = new Date();
//              let startDate;
//                if (timescale === 'week') {
//                startDate = new Date(currentDate.getTime() - (7 * 24 * 60 * 60 * 1000)); // Last 7 days
//              } else if (timescale === 'month') {
//                startDate = new Date(currentDate.getTime() - (30 * 24 * 60 * 60 * 1000)); // Last 30 days
//              } else if (timescale === 'year') {
//                startDate = new Date(currentDate.getTime() - (30 * 24 * 60 * 60 * 1000)); // Last 30 days
//              }
//
//              // Filter the data based on the selected timescale
//              const filteredData = prettyData.filter(point => new Date(point.x) >= startDate);
//              let min = this.dataStore.get("min");
//              let max = this.dataStore.get("max");
//              // Update the chart with the filtered data
//              var fillStylesObj = {
//                              'currentColor -> lightenMore': ['currentColor', 'lightenMore'],
//                              'currentColor -> lighten': ['currentColor', 'lighten'],
//                              'currentColor -> darkenMore': ['currentColor', 'darkenMore'],
//                              'currentColor -> darken': ['currentColor', 'darken'],
//                              'lighten -> darken': ['lighten', 'darken'],
//                              'lightenMore -> darkenMore': ['lightenMore', 'darkenMore']
//                          };
//              var currentAngle = 360;
//              var currentColors = fillStylesObj['currentColor -> lightenMore'];
//              var currentFill = currentColors.concat(currentAngle);
//             let chart = new JSC.chart("chart", {
//                             type: 'area spline',
//                             title_label_text: `Portfolio Value `,
//                             yAxis: {
//                                 defaultTick_gridLine_visible: false,
//                                 scale: {
//                                     range: {
//                                         min: min - 10,
//                                         max: max + 10,
//                                         padding: 1
//                                     }
//                                 }
//                             },
//                             xAxis: {
//                                 defaultTick: {
//                                     color: 'red',
//                                     gridLine_visible: false,
//                                 }
//                             },
//                             defaultSeries: {
//                                 color: '#45a29f',
//                                 shape: {fill: currentFill}
//                             },
//                             defaultPoint: {
//                                 marker: {
//                                     visible: false
//                                 }
//                             },
//                             series: [{points: prettyData}],
//                             legend_visible: false,
//                             autoFit: false,
//                         });
//
//        }


        async getStockData(ticker, dateBought){
            const response = await this.client.getStockData(ticker, this.errorHandler);

            var prettyData = [];
            var min = 9999;
            var max = 0;
            var dateCost = 0;

            var stringDate = new Date(dateBought).toLocaleDateString();

             for (let [key,value] of Object.entries(response.pricesOverTime)){
                prettyData.push({ x: new Date(key), y: value});

                if (value > max){
                    max = value;
                }
                if (key === dateBought){
                    dateCost = value;
                }
                if (value < min){
                    min = value;
                }
             }
            var overlay = document.getElementById("overlay");
            overlay.style.display = "block";
            var expandedChartContainer = document.getElementById("expanded-chart");
             var fillStylesObj = {
                            'currentColor -> lightenMore': ['currentColor', 'lightenMore'],
                            'currentColor -> lighten': ['currentColor', 'lighten'],
                            'currentColor -> darkenMore': ['currentColor', 'darkenMore'],
                            'currentColor -> darken': ['currentColor', 'darken'],
                            'lighten -> darken': ['lighten', 'darken'],
                            'lightenMore -> darkenMore': ['lightenMore', 'darkenMore']
                        };
                        var currentAngle = 360;
                        var currentColors = fillStylesObj['currentColor -> lightenMore'];
                        var currentFill = currentColors.concat(currentAngle);
            const expandedChart = new JSC.chart(expandedChartContainer, {
                    type: 'area spline',
                    title_label_text: `Stock Price`,
                     yAxis: {
                           defaultTick_gridLine_visible: false,
                           scale: {
                               range: {
                                    min: min - min*0.1,
                                    max: max + max*.1,
                                    padding: 1
                               }
                           }
                     },
                     xAxis: {
                           defaultTick: {
                               color: 'red',
                               gridLine_visible: false,
                           },
                     },
                     defaultSeries: {
                           color: '#45a29f',
                           shape: { fill: currentFill }
                     },
                     defaultPoint: {
                           marker: {
                                visible: false
                           }
                     },
                     series: [
                        {
                          points: prettyData
                        },
                        {
                          type: 'line',
                          name: 'Date Bought',
                          visible: true,
                          onTop: true,
                          points: [
                            [new Date(dateBought), 0],
                            [new Date(dateBought), dateCost]
                          ],
                          lineDashStyle: 'ShortDash',
                          lineWidth: 1,
                          color: 'black'
                        }
                      ],

                    legend: {
                        defaultEntry: {
                            visible: false
                        },
                        visible: true,
                        position: 'inside topRight',
                        height: 20,
                        margin: [10,10,10,10],
                        customEntries: [
                            {
                                attributes: {
                                    name: 'Date Bought',
                                    value: stringDate
                                },
                                style_fontWeight: 'bold',
                                sortOrder: 1,
                                visible: true
                            }],
                    },
                    autoFit: false,
                    margin: [150, 50, 150, 50],
                    width: window.innerWidth,
                    height: window.innerHeight,
                });

            var closeButton = document.getElementById("closeButton");

            closeButton.addEventListener("click", function() {
                overlay.style.display = "none";
            });


        }

        async renderTable(portfolioList){
            const tableBody = document.getElementById('portfolio-table-body');
            tableBody.innerHTML = '';

            portfolioList.forEach((record) => {
                 const newRow = document.createElement('tr');

                 const tickerCell = document.createElement('td');
                 tickerCell.innerHTML = record.ticker;
                 newRow.appendChild(tickerCell);

                 const sharesCell = document.createElement('td');
                 sharesCell.innerHTML = record.shares;
                 newRow.appendChild(sharesCell);

                 const costCell = document.createElement('td');
                 costCell.innerHTML = '$' + record.originalCost.toLocaleString();
                 newRow.appendChild(costCell);

                 const currentCostCell = document.createElement('td');
                 currentCostCell.innerHTML = '$' + record.currentCost.toLocaleString();
                 newRow.appendChild(currentCostCell);

                 const origTotalvalueCell = document.createElement('td');
                 origTotalvalueCell.innerHTML = '$' + record.originalTotalValue.toLocaleString();
                 newRow.appendChild(origTotalvalueCell);

                 const currTotalvalueCell = document.createElement('td');
                 currTotalvalueCell.innerHTML = '$' + record.currentTotalValue.toLocaleString();
                 newRow.appendChild(currTotalvalueCell);

                 const percentChangeCell = document.createElement('td');
                 percentChangeCell.innerHTML = record.percentChange.toFixed(2) + '%';

                 if (record.percentChange > 0) {
                     percentChangeCell.classList.add('positive-change');
                 } else if (record.percentChange < 0) {
                     percentChangeCell.classList.add('negative-change');
                 }

                 newRow.appendChild(percentChangeCell);

                 const dateBoughtCell = document.createElement('td');
                 dateBoughtCell.innerHTML = record.originalDateBought;
                 newRow.appendChild(dateBoughtCell);

                 newRow.addEventListener('click', () => {
                 this.getStockData(record.ticker, record.originalDateBought);
                 });

                 tableBody.appendChild(newRow);

            });


        }

}
const main = async () => {
     console.log('Creating MakeMoneyPage instance...');
     const makeMoneyPage = new MakeMoneyPage();
     console.log('Mounting MakeMoneyPage instance...');
     makeMoneyPage.mount();
     console.log('Mounted!');
};
window.addEventListener('DOMContentLoaded', main);
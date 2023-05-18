import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import MakeMoneyClient from "../api/makeMoneyClient.js";



class MakeMoneyPage extends BaseClass {

        constructor() {
            super();
            this.bindClassMethods(['getPortfolioData', 'renderChart'], this);
            this.dataStore = new DataStore();
        }

        async mount() {
            this.client = new MakeMoneyClient();
            this.getPortfolioData();
        }

        async getPortfolioData(){
            const chartData = await this.client.getPortfolioData(this.errorHandler);
            this.renderTable(chartData.recordList);
            this.renderChart(chartData);
        }

        async renderChart(chartData){
            let resultArea = document.getElementById("stockresults");
            console.log("chartData" + chartData);
            var prettyData = [];
            var max = 0;
            var min = 999999;
            var totalValue = chartData.totalValue;
            for (let [key,value] of Object.entries(chartData.valueOverTime)){
                console.log(key + " = " + value);
//                if (key === 'originalValue'){
//                    totalValue = value;
//                    continue;
//                }
                prettyData.push({ x: key, y: value});

                if (value > max){
                    max = value;
                }
                if (value < min){
                    min = value;
                }
            }
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

            const chart = new JSC.chart("chart", {
                type: 'area spline',
                title_label_text: `Portfolio Value ${totalValue}`,
                yAxis: {
                    defaultTick_gridLine_visible: false,
                    scale: {
                        range: {
                            min: min - 10,
                            max: max + 10,
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
                series: [{points: prettyData}],
                legend_visible: false,
                autoFit: false,
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
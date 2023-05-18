import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PortalClient from "../api/portalClient.js";



class PortalPage extends BaseClass {

        constructor() {
            super();
            this.bindClassMethods(['loadTable', 'populateTableData', 'sortTableData', 'addToPortfolio'], this);
            this.dataStore = new DataStore();
        }

        async mount() {
            this.client = new PortalClient();
            document.getElementById('select-data').addEventListener('change', this.loadTable);
            this.loadTable({target: {value: 'All'}});

        }

        async loadTable(event){
            const option = event.target.value;
            console.log(option);
            const tableData = await this.client.getTableData(option, this.errorHandler);
            this.populateTableData(tableData);
        }


async populateTableData(tableData) {
  const tableBody = document.getElementById('table-body');
  tableBody.innerHTML = '';

  tableData.forEach((record) => {
    const newRow = document.createElement('tr');

    console.log(record);
    const tickerCell = document.createElement('td');
    tickerCell.innerHTML = record.ticker;
    newRow.appendChild(tickerCell);

    const ownerCell = document.createElement('td');
    ownerCell.innerHTML = record.owner;
    newRow.appendChild(ownerCell);

    const relationshipCell = document.createElement('td');
    relationshipCell.innerHTML = record.relationship;
    newRow.appendChild(relationshipCell);

    const transactionCell = document.createElement('td');
    transactionCell.innerHTML = record.transaction;
    newRow.appendChild(transactionCell);

    const costCell = document.createElement('td');
    costCell.innerHTML = '$' + record.cost.toLocaleString();
    newRow.appendChild(costCell);

    const sharesCell = document.createElement('td');
    sharesCell.innerHTML = record.shares.toLocaleString();
    newRow.appendChild(sharesCell);

    const totalvalueCell = document.createElement('td');
    totalvalueCell.innerHTML = '$' + record.totalvalue.toLocaleString();
    newRow.appendChild(totalvalueCell);

    const filingdateCell = document.createElement('td');
    const filingDate = new Date(record.filingdate + 'T00:00:00-07:00');
    const formatter = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
    const formattedDate = formatter.format(filingDate);
    filingdateCell.innerHTML = formattedDate;
    newRow.appendChild(filingdateCell);

     const buttonCell = document.createElement('td');
        const button = document.createElement('button');
        button.innerHTML = 'Add to Port';
        button.addEventListener('click', () => {
        event.preventDefault();
          const rowData = {
                 ticker: record.ticker,
                 owner: record.owner,
                 relationship: record.relationship,
                 transaction: record.transaction,
                 cost: record.cost,
                 shares: record.shares,
                 totalvalue: record.totalvalue,
                 filingdate: record.filingdate
               };
            this.addToPortfolio(rowData);
          console.log('Button clicked');
        });
        buttonCell.appendChild(button);
        newRow.appendChild(buttonCell);


    tableBody.appendChild(newRow);
  });
//  const tableHeaders = document.querySelectorAll('#data-table th');
//    tableHeaders.forEach((header) => {
//      header.addEventListener('click', () => {
//        const column = header.dataset.column;
//        const direction = header.dataset.direction;
//        const newDirection = direction === 'desc' ? 'asc' : 'desc';
//        header.dataset.direction = newDirection;
//        const sortedData = this.sortTableData(tableData, column, newDirection);
//        console.log(sortedData);
//        this.populateTableData(sortedData);
//      });
//    });
}
    async addToPortfolio(rowData){
        const response = await this.client.addToPortfolio(rowData, this.errorHandler);
    }
async sortTableData(tableData, column, direction) {
  const sortedData = [...tableData];
  sortedData.sort((a, b) => {
    if (column === 'cost') {
      return direction === 'asc' ? a.cost - b.cost : b.cost - a.cost;
    } else if (column === 'shares') {
      return direction === 'asc' ? a.shares - b.shares : b.shares - a.shares;
    } else if (column === 'totalvalue') {
      return direction === 'asc' ? a.totalvalue - b.totalvalue : b.totalvalue - a.totalvalue;
    } else if (column === 'filingdate') {
      const dateA = new Date(a.filingdate);
      const dateB = new Date(b.filingdate);
      return direction === 'asc' ? dateA - dateB : dateB - dateA;
    } else {
      return 0;
    }
  });
  return sortedData;
}

}
const main = async () => {
     console.log('Creating PortalPage instance...');
     const portalPage = new PortalPage();
     console.log('Mounting PortalPage instance...');
     portalPage.mount();
     console.log('Mounted!');
};
window.addEventListener('DOMContentLoaded', main);

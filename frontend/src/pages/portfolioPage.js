import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PortfolioClient from "../api/portfolioClient.js";



class PortfolioPage extends BaseClass {

        constructor() {
            super();
            this.bindClassMethods(['onCreate', 'saveAllLatest'], this);
            this.dataStore = new DataStore();
        }

        async mount() {
            this.client = new PortfolioClient();
            document.getElementById('contactform').addEventListener('submit', this.onCreate);
            document.getElementById("saveAllLatest").addEventListener("click", this.saveAllLatest);

        }

        async saveAllLatest(){
            let result = this.client.saveAllLatest(this.errorHandler);
            console.log(result.data);

        }
    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let ticker = document.getElementById("ticker").value;
        let owner = document.getElementById("owner").value;
        let relationship = document.getElementById("relationship").value;
        let transaction = document.getElementById("transaction").value;
        let cost = document.getElementById("cost").value;
        let shares = document.getElementById("shares").value;
        let totalvalue = document.getElementById("totalvalue").value;
        let filingdate = document.getElementById("filingdate").value;
//           console.log(ticker);
//           console.log(owner);
//           console.log(relationship);
//           console.log(cost);
//           console.log(shares);
//           console.log(totalValue);
//           console.log(filingDate);
        const createRecord = await this.client.saveRecord(ticker, owner, relationship, transaction, cost, shares, totalvalue, filingdate, this.errorHandler);
//        this.dataStore.set("record", createRecord);



    }





}
const main = async () => {
     console.log('Creating PortfolioPage instance...');
     const portfolioPage = new PortfolioPage();
     console.log('Mounting PortfolioPage instance...');
     portfolioPage.mount();
     console.log('Mounted!');
};
window.addEventListener('DOMContentLoaded', main);

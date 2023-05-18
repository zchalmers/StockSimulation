import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class PortfolioClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'saveRecord', 'saveAllLatest'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }


    async saveAllLatest(errorCallback){
        try {
            const response = await this.client.get(`/stocks/saveAllLatest`);
            return response.data;
        }catch (error){
            this.errorHandler("saveAllLatest", error, errorCallback);
        }
    }


    async saveRecord (ticker, owner, relationship, transaction, cost, shares, totalvalue, filingdate, errorCallback){
//        console.log(ticker);
//                   console.log(owner);
//                   console.log(relationship);
//                   console.log(cost);
//                   console.log(shares);
//                   console.log(totalvalue);
//                   console.log(filingdate);

        try {
        console.log("IM AT START OF SAVERECORD");
            const response = await this.client.post(`/stocks`, {
                ticker: ticker,
                owner: owner,
                relationship: relationship,
                transaction: transaction,
                cost: cost,
                shares: shares,
                totalvalue: totalvalue,
                filingdate: filingdate
            });
        return response.data;
        } catch (error){
            this.handleError("saveRecord", error, errorCallback);
        }
    }

     handleError(method, error, errorCallback) {
            console.error(method + " failed - " + error);
            if (error.response.data.message !== undefined) {
                console.error(error.response.data.message);
            }
            if (errorCallback) {
                errorCallback(method + " failed - " + error);
            }
        }
}
import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class MakeMoneyClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['getPortfolioData', 'getStockData'];
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

    async getStockData(ticker, errorCallback){
        try {
            const response = await this.client.get(`/makeMoney/stockData/${ticker}`);
            console.log(response);
            return response.data;
        }
        catch(error){
            this.handleError("getStockData", error, errorCallback);

        }

    }

    async getPortfolioData(errorCallback){
        try {
            const response = await this.client.get(`/makeMoney`);
            console.log(response);
            return response.data;
        }catch(error){
            this.handleError("getPortfolioData", error, errorCallback);
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
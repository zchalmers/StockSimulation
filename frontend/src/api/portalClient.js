import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class PortalClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['getTableData', 'addToPortfolio'];
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

    async getTableData(option, errorCallback){
    try {
        console.log(option.value);
        const tableData = await this.client.get(`stocks/table/${option}`)
        console.log(tableData);
        return tableData.data;
    }catch(error){
        this.handleError("getTableData", error, errorCallback);
    }

    }

    async addToPortfolio(rowData, errorCallback){
        try {
            const response = await this.client.post(`/makeMoney/add`, rowData);
            return response.data;
        } catch (error){
            this.handleError("addToPortfolio", error, errorCallback);
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
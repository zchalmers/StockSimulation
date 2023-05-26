import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";



class StockPage extends BaseClass{
    constructor() {
        super();
        this.bindClassMethods(['onGet', 'renderStock'], this);
        this.dataStore = new DataStore();
    }
    async mount() {
        document.getElementById('form').addEventListener('submit', this.onGet);
        this.client = new StockClient();

        this.dataStore.addChangeListener(this.renderStock)
    }







}

import React from 'react';
import { PureComponent } from 'react';
import {
    BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
} from 'recharts';


const data = [
    {
        name: 'Jan',  pv: 2400, amt: 2400,
    },
    {
        name: 'Feb',  pv: 1398, amt: 2210,
    },
    {
        name: 'Mar',  pv: 9800, amt: 2290,
    },
    {
        name: 'Apr',  pv: 3908, amt: 2000,
    },
    {
        name: 'May',  pv: 4800, amt: 2181,
    },
    {
        name: 'June',  pv: 3800, amt: 2500,
    },
    {
        name: 'July',  pv: 4300, amt: 2100,
    },
];


export default class PharmacyCharts extends React.Component{
    constructor() {
        super();
    }

    static jsfiddleUrl = 'https://jsfiddle.net/alidingling/30763kr7/';

    render() {
        return (
            <div className="container-fluid">
                <br/><br/>
                <h1>Pharmacy charts</h1>

                <BarChart
                    width={500}
                    height={300}
                    data={data}
                    margin={{
                        top: 5, right: 30, left: 20, bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="pv" fill="#8884d8" />

                </BarChart>
            </div>
        );
    }
}

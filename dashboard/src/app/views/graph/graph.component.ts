import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css']
})
export class GraphComponent implements OnInit {

  Linechart : any;  

  constructor() { }

  ngOnInit() {
    this.Linechart = new Chart('canvas', {
      type: 'line',
      data: {  
        labels: ["Jan", "Mar", "Maio", "Jul", "Set", "Nov"],  
        datasets: [  
          {  
            data: [0, 10000, 5000, 15000, 10000, 20000, 15000, 25000, 20000, 30000, 25000, 40000],  
            borderColor: '#4e73df',  
            backgroundColor: "#f6f8fd",  
          }  
        ]  
      },
      options: {  
        legend: {  
          display: false  
        },  
        scales: {  
          xAxes: [{  
            display: true  
          }],  
          yAxes: [{  
            display: true  
          }],  
        }  
      }
    });
  }

}

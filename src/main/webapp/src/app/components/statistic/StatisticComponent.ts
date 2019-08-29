import {Component, OnInit} from "@angular/core";
import {ExpenseService} from "../../services/expense/ExpenseService";

/**
 * @author Timur Berezhnoi
 */
@Component({
    selector: "statistic-component",
    templateUrl: "./statistic.html"
})
export default class StatisticComponent implements OnInit {

    private barChartType: string = "bar";

    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true
    };

    public barChartData: any[];

    private availableStatisticYears: Array<number>;
    private currentYear: number;

    constructor(private expenseService: ExpenseService) {}

    ngOnInit(): void {
        this.fetchAvailableYears();
    }

    private fetchAvailableYears() {
        this.expenseService.getExpensesStatisticYears().subscribe(
            response => {
                this.availableStatisticYears = response;
                this.currentYear = response[0];
                if(this.currentYear) {
                    this.fetchStatisticForYear();
                }
            }, error => {
                console.log(error);
            }
        );
    }

    private changeYear(year: number): void {
        this.currentYear = year;
        this.fetchStatisticForYear();
    }

    private fetchStatisticForYear(): void {
        this.expenseService.getExpensesStatisticForYear(this.currentYear).subscribe(
            (response: Array<any>) => {
                this.barChartData = [];
                response.forEach(value => {
                    this.barChartData.push({data: [value.totalSpent / 100], label: value.month})
                });
            }, (error: Object) => {
                console.log(error);
            }
        );
    }
}
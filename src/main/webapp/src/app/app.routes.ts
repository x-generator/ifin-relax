import {Routes} from "@angular/router";
import {AuthGuard} from "./services/authGuard/AuthGuard";
import SignUpComponent from "./components/signUp/SignUpComponent";
import ExpensesComponent from "./components/expenses/ExpensesComponent";
import DashboardComponent from "./components/dashboard/DashboardComponent";
import SignInComponent from "./components/signIn/SignInComponent";
import StatisticComponent from "./components/statistic/StatisticComponent";

/**
 * @author Timur Berezhnoi
 */
export const routes: Routes = [
    {path: "", component: DashboardComponent,
        children: [
            {path: "", redirectTo: "expenses", pathMatch: "full"},
            {path: "expenses", component: ExpensesComponent, canActivate: [AuthGuard]},
            {path: "statistic", component: StatisticComponent, canActivate: [AuthGuard]},
        ]
    },
    {path: "signUp", component: SignUpComponent, canActivate: [AuthGuard]},
    {path: "signIn", component: SignInComponent, canActivate: [AuthGuard]},
    {path: "**", redirectTo: "/expenses", pathMatch: "full"}
];
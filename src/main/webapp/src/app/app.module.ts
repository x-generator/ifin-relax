import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from '@angular/router';
import {routes} from "./app.routes";
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import {FormsModule} from "@angular/forms";
import {HttpModule, RequestOptions} from "@angular/http";
import {AuthGuard} from "./services/authGuard/AuthGuard";
import {DatePipeTransformer} from "./pipe/DatePipeTransformer";
import {DefaultRequestOptions} from "./configuration/DefaultRequestOptions";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import IFinRelaxApplication from "./components/application/IFinRelaxApplication";
import SignUpComponent from "./components/signUp/SignUpComponent";
import ExpensesComponent from "./components/expenses/ExpensesComponent";
import SignInComponent from "./components/signIn/SignInComponent";
import {UserService} from "./services/user/UserService";
import {ExpenseService} from "./services/expense/ExpenseService";
import DashboardComponent from "./components/dashboard/DashboardComponent";
import StatisticComponent from "./components/statistic/StatisticComponent";
import {CurrencyService} from "./services/currency/CurrencyService";
import {CookieService} from "./services/cookie/CookieService";
import NewExpenseComponent from "./components/expenses/newExpense/NewExpenseComponent";
import EditExpenseComponent from "./components/expenses/editExpense/EditExpenseComponent";
import ModalExpenseComponent from "./components/expenses/modal/ModalExpenseComponent";
import { ChartsModule } from "ng2-charts";

/**
 * @author Timur Berezhnoi
 */
@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        NgbModule.forRoot(),
        RouterModule.forRoot(routes),
        ChartsModule
    ],
    declarations: [
        IFinRelaxApplication,
        DashboardComponent,
        StatisticComponent,
        SignUpComponent,
        ExpensesComponent,
        SignInComponent,
        DatePipeTransformer,
        NewExpenseComponent,
        EditExpenseComponent,
        ModalExpenseComponent
    ],
    providers: [
        UserService,
        ExpenseService,
        AuthGuard,
        DatePipeTransformer,
        CurrencyService,
        CookieService,
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy
        },
        {
            provide: RequestOptions,
            useClass: DefaultRequestOptions
        }
    ],
    entryComponents: [ModalExpenseComponent],
    bootstrap: [IFinRelaxApplication]
})
export class AppModule {}
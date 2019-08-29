import {Component} from "@angular/core";
import {Router, NavigationEnd, Event} from '@angular/router';
import {UserService} from "../../services/user/UserService";
import {SideBarItem} from "./model/SideBarItem";

/**
 * @author Timur Berezhnoi
 */
@Component({
    selector: "dashboard-component",
    templateUrl: "./dashboard.html",
    styleUrls: ["./dashboard.css"]
})
export default class DashboardComponent {

    private sidebarItems: Array<SideBarItem> = [new SideBarItem("Expenses", "/expenses"), new SideBarItem("Statistic", "/statistic")];

    private activeItem: SideBarItem = this.sidebarItems[0];

    constructor(private userService: UserService, private router: Router) {
        router.events.subscribe((event: Event) => this.setActiveItem(event));
    }

    private signOut(event: any): void {
        event.preventDefault();
        this.userService.signOut().subscribe(
            response => {
                this.router.navigate(["signIn"]);
            },
            error => {
                console.log(error);
            });
    }

    private setActiveItem(event: Event): void {
        if(event instanceof NavigationEnd) {
            for(let item of this.sidebarItems) {
                if(event.url === item.routeLink || event.urlAfterRedirects === item.routeLink) {
                    this.activeItem = item;
                    break;
                }
            }
        }
    }
}
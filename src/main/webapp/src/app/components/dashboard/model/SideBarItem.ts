/**
 * @author Timur Berezhnoi
 */
export class SideBarItem {

    /**
     * SideBarItem constructor.
     *
     * @param _name
     * @param _routeLink
     */
    constructor(private _name: string, private _routeLink: string) {}

    public get name(): string {
        return this._name;
    }

    public get routeLink(): string {
        return this._routeLink;
    }
}
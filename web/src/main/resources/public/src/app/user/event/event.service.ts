import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/map";
import {Event} from "./event.interface";
import "rxjs/add/operator/toPromise";


@Injectable()
export class EventService {

    private getEventUr = '/restful/event?pageNumber=';
    private delEventUrl = '/restful/event/';
    private updateEventUrl = '/restful/event/';

    constructor(private _http:Http) {
    }

    getAllEvents(pageNumber:number):Observable<any> {
        return this._http.get(this.getEventUr + pageNumber)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllEventsSorted(pageNumber:number, name:string, order:boolean):Observable<any> {
        return this._http.get(this.getEventUr + pageNumber + '&&sortedBy=' + name + '&&asc=' + order)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    deleteEventById(eventId:number) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        let url = this.delEventUrl + eventId;
        console.log('delete event by id: ' + eventId);
        return this._http.delete(url, {headers: headers})
            .toPromise()
            .catch((error)=>console.error(error));

    }

    editAndSave(event:Event) {
        if (event.eventId) {
            console.log('updating event with id: ' + event.eventId);
            this.put(event);
        }
    }

    put(event:Event) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this._http.put(this.updateEventUrl, JSON.stringify(event), {headers: headers})
            .toPromise()
            .then(()=>event)
            .catch((error)=>console.error(error));
    }


}
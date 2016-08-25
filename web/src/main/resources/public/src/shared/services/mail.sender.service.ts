/**
 * Created by Anastasiia Fedorak  8/13/16.
 */
import {Injectable} from "@angular/core";
import ApiService = require("../services/api.service");
import {Http, Headers, Response, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Rx";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {Mail} from "../models/mail.interface";

@Injectable()
export class MailService {
    private url = ApiService.serverUrl + '/restful/mail/';
    private headers = new Headers({'Authorization': 'Bearer ' + localStorage.getItem('token')});

    constructor(private _http:Http){
        this.headers.append('Content-Type', 'application/json');
    }

    sendMail(mail : Mail){
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        console.log("sending http POST to " + this.url);
        console.log("json obj: " + JSON.stringify(mail));
        return this._http.post(this.url, JSON.stringify(mail), {headers: this.headers})
            .toPromise()
            .then(()=>mail)
            .catch((err)=>console.error(err));
    }
}
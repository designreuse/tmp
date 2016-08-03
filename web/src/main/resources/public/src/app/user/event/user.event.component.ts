import {Component, OnInit, OnDestroy, ViewChild} from "@angular/core";
import {CORE_DIRECTIVES} from "@angular/common";
import {Event} from "./event.interface";
import {EventService} from "./event.service";
import {PageCreator} from "../../../shared/services/page.creator.interface";
import "rxjs/Rx";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from "ng2-bootstrap/ng2-bootstrap";

@Component({
    selector: 'my-event',
    templateUrl: 'src/app/user/event/event.html',
    providers: [EventService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS]
})
export class UserEventComponent implements OnInit, OnDestroy {

    private events:Event[];
    private selectedEvent:Event = {eventId: null, name: '', author: '', description: '', date: '', path: ''};
    private pageCreator:PageCreator<Event>;
    private pageNumber:number = 1;
    private pageList:Array<number> = [];
    private totalPages:number;
    @ViewChild('delModal') public delModal:ModalDirective;
    @ViewChild('editModal') public editModal:ModalDirective;
    active:boolean = true;
    order:boolean = true;

    private eventId:number;

    constructor(private _eventService:EventService) {
    }

    openEditModal(event:Event) {
        this.selectedEvent = event;
        console.log('selected event: ' + this.selectedEvent);
        this.editModal.show();
    }

    isDateValid(date:string):boolean {
        return /\d{4}-\d{2}-\d{2}/.test(date);
    }

    onEditEventSubmit() {
        this.active = false;
        console.log('saving event: ' + this.selectedEvent);
        this._eventService.editAndSave(this.selectedEvent);
        this._eventService.getAllEvents(this.pageNumber);
        this.editModal.hide();
        setTimeout(() => this.active = true, 0);
    }

    closeEditModal() {
        console.log('closing edt modal');
        this.editModal.hide();
    }

    openDelModal(id:number) {
        this.eventId = id;
        console.log('show', this.eventId);
        this.delModal.show();
    }

    closeDelModal() {
        console.log('delete', this.eventId);
        this._eventService.deleteEventById(this.eventId);
        this.getEventsByPageNum(this.pageNumber);
        this.delModal.hide();
    }

    ngOnInit():any {
        this.getEventsByPageNum(this.pageNumber);
    }

    getEventsByPageNum(pageNumber:number) {
        this.pageNumber = +pageNumber;
        this.emptyArray();
        return this._eventService.getAllEvents(this.pageNumber)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.events = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                },
                (error) => {
                    console.error(error)
                });
    };

    prevPage() {
        this.pageNumber = this.pageNumber - 1;
        this.getEventsByPageNum(this.pageNumber)
    }

    nextPage() {
        this.pageNumber = this.pageNumber + 1;
        this.getEventsByPageNum(this.pageNumber)
    }

    emptyArray() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    preparePageList(start:number, end:number) {
        for (let i = start; i <= end; i++) {
            this.pageList.push(i);
        }
    }


    sortBy(name:string) {
        console.log('sorted by ', name);
        this.order = !this.order;
        console.log('order by asc', this.order);
        this.emptyArray();
        this._eventService.getAllEventsSorted(this.pageNumber, name, this.order)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.events = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                },
                (error) => {
                    console.error(error)
                });
    }


    ngOnDestroy():any {
        //this.subscriber.unsubscribe();
    }

}
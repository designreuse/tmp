import {Component, OnInit, ViewChild} from "@angular/core";
import {VoteAddFormComponent} from './vote_form/vote-add-form.component';
import {Vote} from './vote';
import {Option} from './option';
import {VoteService} from './vote.service';
import {OptionService} from './option.service';
import {User} from './user';


@Component({
    selector: 'vote',
    templateUrl: './src/app/home/voting/vote.html',
    styleUrls: ['./src/app/home/voting/vote.css'],
    directives: [ VoteAddFormComponent],
    providers:[VoteService, OptionService]
})
export class VoteComponent implements OnInit {

    @ViewChild('voteAddForm')
    voteAddForm:VoteAddFormComponent;
    voteArr: Vote[];
    userId:number = 1; //          XXXXXXXXXXXXXXXXXXXXXXXXXX  userId;

    constructor(private voteService:VoteService, private optionService:OptionService) {
        this.voteArr = [];
    }

    ngOnInit() {
        this.voteService.getAllVotes()
                        .then(voteArr => this.voteArr = voteArr.slice().reverse())
                        .then(()=> this.checkForUserId())
                        .then(()=> this.countNumberOfRespondents())
                        .then(() => this.calcAllProgress());
    }

    checkForUserId(): void {
        for(let i = 0; i < this.voteArr.length; i++) {
            this.voteArr[i].available = this.voteArr[i].usersId.includes(this.userId);
        }
    }

    countNumberOfRespondents():void { 
        for(let i = 0; i < this.voteArr.length;i++) {
            this.voteArr[i].numberOfRespondents = this.voteArr[i].usersId.length;
        } 
    }

    openModalWindow(): void {
        this.voteAddForm.openAddModal();
    }

    toScoreOption(option: Option, vote: Vote):void {
        if(vote.numberOfRespondents === undefined) vote.numberOfRespondents = 0 ;
        vote.numberOfRespondents++;
        option.users.push(new User());// XXXXXXXXXXXXXXXXXXXXX add empty User
        vote.available = true;
        this.calcProgressForVote(vote);
        this.optionService.toScoreOption(option.optionId, this.userId);
    }

    createVote(vote: Vote): void {
        this.voteService.addVote(vote, this.userId).then(vote => this.addVote(vote));
    }

    private addVote(vote: Vote): void {
        this.voteArr.unshift(vote);
    }
    
    calcAllProgress() {
        for(let i = 0; i < this.voteArr.length; i++) {
            this.calcProgressForVote(this.voteArr[i]);
        }
    }

    calcProgressForVote(vote:Vote): void {
        for(let i = 0 ; i < vote.options.length; i++) {
            vote.options[i].progress = this.calcProgressForOption(vote.options[i].users.length, vote.numberOfRespondents);
        }
    }

     private calcProgressForOption(usersLength: number, numberOfRespondents: number): string{
        if(numberOfRespondents !== 0 ) {
            return String(Math.round(usersLength / numberOfRespondents  * 100));
        } else {
            return "0";
        }
    }
}   
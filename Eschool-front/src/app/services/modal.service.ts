import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class ModalService {

  private subject: Subject<boolean>;
  private val: boolean;

  private textSubject: Subject<string>;

  constructor() {
    this.subject = new Subject<boolean>();
    this.textSubject = new Subject<string>();
  }

  getBoolVal() {
    return this.subject.asObservable();
  }

  setBoolVal(bool: boolean) {
    this.val = bool;
    this.subject.next(this.val);
  }

  getTextVal() {
    return this.textSubject.asObservable();
  }

  setTextVal(text: string) {
    this.textSubject.next(text);
  }

}



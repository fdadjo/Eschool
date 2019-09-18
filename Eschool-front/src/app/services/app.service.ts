import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";


@Injectable()
export class AppService {

  public login$ = new BehaviorSubject(false);

}


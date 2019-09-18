import {Component, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap';
import {ModalService} from '../../services/modal.service';

@Component({
  selector: 'app-validation-alert',
  templateUrl: './validation.alert.component.html',
  styleUrls: ['../../../../node_modules/spinkit/css/spinkit.css'],
  encapsulation: ViewEncapsulation.None
})
export class ValidationAlertComponent {

  @ViewChild('loaderModal')
  public loaderModal: ModalDirective;

  private modalStatus: boolean = true;

  title: string = 'Alert';
  text_body: string = 'Saving...';


  constructor(public bsModalRef: BsModalRef, private mService: ModalService) {
    this.mService.getBoolVal().subscribe(
      bool => {
        setTimeout(() => {
          this.modalStatus = bool;
        }, 2000);
        setTimeout(() => {
          this.hide();
        }, 3000);
      }
    );
    this.mService.getTextVal().subscribe(
      text => {
        setTimeout(() => {
          this.text_body = text;
        }, 1000);
      }
    );
  }

  setFields(title, text_body) {
    this.title = title;
    this.text_body = text_body;
  }

  public hide() {
    this.bsModalRef.hide();
  }

  public close() {
    this.bsModalRef.hide();
  }
}
